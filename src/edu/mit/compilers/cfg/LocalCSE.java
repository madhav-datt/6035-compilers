package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.*;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * Created by devinmorgan on 11/26/16.
 */
public class LocalCSE {
//    private final HashSet<ExprObject> computedExpressions = new HashSet<>();
    private final HashMap<ExprObject, LlLocationVar> tempsForExpressions = new HashMap<>();
    private final LlBuilder builder;

    public LocalCSE(LlBuilder builder) {
        this.builder = builder;
    }

    public static BasicBlock performLocalCSE(BasicBlock bb) {
        LocalCSE cse = new LocalCSE(bb.getBuilder());
        LinkedHashMap<String, LlStatement> optimizedMap = new LinkedHashMap<>();

        // 2) loop through the current linked hashmap
        for (String label : bb.getLabelsToStmtsMap().keySet()) {
            LlStatement stmt = bb.getLabelsToStmtsMap().get(label);

            // 3) only do CSE on stmts that have a computation
            if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                // perform the CSE and the optimized stmts to optimizedMap
                ArrayList<LlStatement> optimizedStmts = cse.getOptimizedStatementsForUnaryComputation(unaryOp);
                for (LlStatement optStmt : optimizedStmts) {
                    String optLabel = cse.builder.generateLabel();
                    optimizedMap.put(optLabel, optStmt);
                }
            }
            else if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                // perform the CSE and the optimized stmts to optimizedMap
                ArrayList<LlStatement> optimizedStmts = cse.getOptimizedStatementsForBinaryComputation(binaryOp);
                for (LlStatement optStmt : optimizedStmts) {
                    String optLabel = bb.getBuilder().generateLabel();
                    optimizedMap.put(optLabel, optStmt);
                }
            }
            else if (stmt instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular stmtRegular = (LlAssignStmtRegular) stmt;

                // delete any rows in the hashtable that contains the var being assigned in this row
                for (ExprObject expr : new ArrayList<ExprObject>(cse.tempsForExpressions.keySet())) {
                    if (expr.containsVariable(stmtRegular.getStoreLocation())) {
                        cse.tempsForExpressions.remove(expr);
                    }
                }
            }
            else {
                // if stmt does not contain computation, just add it directly
                optimizedMap.put(label, stmt);
            }
        }

        return new BasicBlock(optimizedMap, cse.builder);
    }

    private ArrayList<LlStatement> getOptimizedStatementsForUnaryComputation(LlAssignStmtUnaryOp unaryOp) {

        // 1) create the ExprObject for the unary computation in the RHS
        ArrayList<LlStatement> optimizedStatements = new ArrayList<>();
        UnaryExprObject unaryExpr = new UnaryExprObject(unaryOp);

        // 2) check to see if we have already computed this epxr before
        if (this.tempsForExpressions.containsKey(unaryExpr)) {

            // get the temp that refers to this computation
            LlLocationVar tempForComputation = this.tempsForExpressions.get(unaryExpr);
            LlAssignStmtRegular assignStmt = new LlAssignStmtRegular(unaryOp.getStoreLocation(), tempForComputation);
            optimizedStatements.add(assignStmt);
        }
        else {
            // create temp variable for the new computation
            LlLocationVar tempVar = this.builder.generateTemp();

            // add temp var and computation to the hashtable
            this.tempsForExpressions.put(unaryExpr, tempVar);

            // create optimized stmts for computation
            LlAssignStmtUnaryOp computationStmt = new LlAssignStmtUnaryOp(tempVar, unaryOp.getOperand(), unaryOp.getOperator());
            LlAssignStmtRegular assignStmt = new LlAssignStmtRegular(unaryOp.getStoreLocation(), tempVar);
            optimizedStatements.add(computationStmt);
            optimizedStatements.add(assignStmt);
        }

        // 3) delete any rows in the hashtable that contains the var being assigned in this row
        for (ExprObject expr : new ArrayList<ExprObject>(this.tempsForExpressions.keySet())) {
            if (expr.containsVariable(unaryOp.getStoreLocation())) {
                this.tempsForExpressions.remove(expr);
            }
        }

        // 4) return the list of stmts that will be added in the optimizedMap
        return optimizedStatements;
    }

    private ArrayList<LlStatement> getOptimizedStatementsForBinaryComputation(LlAssignStmtBinaryOp binaryOp) {
        // 1) create the ExprObject for the binary computation in the RHS
        ArrayList<LlStatement> optimizedStatements = new ArrayList<>();
        BinaryExprObject binaryExpr = new BinaryExprObject(binaryOp);

        // 2) check to see if we have already computed this epxr before
        if (this.tempsForExpressions.containsKey(binaryExpr)) {

            // we've seen it before, assign to the exist computation variable
            LlLocationVar tempForComputation = this.tempsForExpressions.get(binaryExpr);
            LlAssignStmtRegular assignStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), tempForComputation);
            optimizedStatements.add(assignStmt);
        }
        else {
            // create temp variable for the new computation
            LlLocationVar tempVar = this.builder.generateTemp();

            // add temp var and computation to hashtable
            this.tempsForExpressions.put(binaryExpr, tempVar);

            // create optimized stmts for computation
            LlAssignStmtBinaryOp computationStmt = new LlAssignStmtBinaryOp(tempVar,
                    binaryOp.getLeftOperand(), binaryOp.getOperation(), binaryOp.getRightOperand());
            LlAssignStmtRegular assignStmt = new LlAssignStmtRegular(binaryOp.getStoreLocation(), tempVar);
            optimizedStatements.add(computationStmt);
            optimizedStatements.add(assignStmt);
        }

        // 3) delete any rows in computedExpressions that contains the var being assigned in this row
        for (ExprObject expr : new ArrayList<ExprObject>(this.tempsForExpressions.keySet())) {
            if (expr.containsVariable(binaryOp.getStoreLocation())) {
                this.tempsForExpressions.remove(expr);
            }
        }

        // 4) return the list of stmts that will be added in the optimizedMap
        return optimizedStatements;
    }


    // create the ExprObject
    // Required API:
    // 1) .containsVariable()
    // 2) .equals()
    // 3) .hash() **

    private abstract class ExprObject {
        protected final String operation;
        protected final LlComponent rightOperand;

        public ExprObject(String operation, LlComponent rightOperand) {
            this.operation = operation;
            this.rightOperand = rightOperand;
        }

        public abstract boolean containsVariable(LlComponent comp);
    }

    private class UnaryExprObject extends ExprObject {
        public UnaryExprObject(LlAssignStmtUnaryOp unaryOp) {
            super(unaryOp.getOperator(), unaryOp.getOperand());
        }

        @Override
        public boolean containsVariable(LlComponent comp) {
            return this.rightOperand.equals(comp);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof UnaryExprObject) {
                UnaryExprObject that = (UnaryExprObject) obj;
                return this.operation.equals(that.operation) && this.rightOperand.equals(that.rightOperand);
            }
            return false;
        }
    }

    private class BinaryExprObject extends ExprObject {
        private final LlComponent leftOperand;

        public BinaryExprObject(LlAssignStmtBinaryOp binaryOp) {
            super(binaryOp.getOperation(), binaryOp.getRightOperand());
            this.leftOperand = binaryOp.getLeftOperand();
        }

        @Override
        public boolean containsVariable(LlComponent comp) {
            return this.leftOperand.equals(comp) || this.rightOperand.equals(comp);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof BinaryExprObject) {
                BinaryExprObject that = (BinaryExprObject) obj;

                // straight up equal
                if (this.operation.equals(that.operation)
                        && this.leftOperand.equals(that.leftOperand)
                        && this.rightOperand.equals(that.rightOperand)) {
                    return true;
                }

                // associativity of + and *
                if (this.operation.equals("+") || this.operation.equals("*")) {
                    if (this.operation.equals(that.operation)) {
                        if (this.leftOperand.equals(that.rightOperand) && this.rightOperand.equals(that.leftOperand)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }





}
