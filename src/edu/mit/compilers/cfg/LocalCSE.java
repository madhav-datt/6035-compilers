package edu.mit.compilers.cfg;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.*;

import java.beans.Expression;
import java.util.*;

/**
 * Created by devinmorgan on 11/26/16.
 */
public class LocalCSE {
    private final HashMap<ExprObject, LlLocationVar> tempsForExpressions;
    private final LlBuilder builder;

    public LocalCSE(LlBuilder builder) {
        this.builder = builder;
        this.tempsForExpressions = new HashMap<>();
    }

    public static void performLocalCSE(BasicBlock bb, HashSet<LlLocation> globalVariables) {
        LocalCSE cse = new LocalCSE(bb.getBuilder());
        LinkedHashMap<String, LlStatement> labelsToStmtsMap = bb.getLabelsToStmtsMap();

        // 2) loop through the current linked HashMap
        for (String label : labelsToStmtsMap.keySet()) {
            LlStatement stmt = labelsToStmtsMap.get(label);

            // 3) only do CSE on stmts that have a computation
            if (stmt instanceof LlAssignStmtUnaryOp) {
                LlAssignStmtUnaryOp unaryOp = (LlAssignStmtUnaryOp) stmt;

                // we only store computations if they are variables (we don't do this for a[i]'s)
                if (unaryOp.getStoreLocation() instanceof LlLocationVar
                        && !(unaryOp.getOperand() instanceof LlLocationArray)) {

                    // check to see if this computation has been made before
                    UnaryExprObject uniExpr = cse.new UnaryExprObject(unaryOp);
                    if (cse.tempsForExpressions.containsKey(uniExpr)) {

                        // if it has been made before, swap the computation
                        // with the temp that already stores the value
                        LlAssignStmtRegular optimalStmt = new LlAssignStmtRegular(
                                unaryOp.getStoreLocation(),
                                cse.tempsForExpressions.get(uniExpr)
                        );
                        labelsToStmtsMap.put(label, optimalStmt);
                    }
                    // if the computation has not been made before, store it for later
                    else if (!uniExpr.containsAnyOfTheseVariables(globalVariables)) {
                        cse.tempsForExpressions.put(uniExpr, (LlLocationVar) unaryOp.getStoreLocation());
                    }

                    // check cse.tempsForExpressions for any expressions whose operand
                    // is the storeLocation and remove those expressions because they are no longer valid.
                    for (ExprObject exprObject : new HashSet<>(cse.tempsForExpressions.keySet())) {
                        if (exprObject.containsVariable(unaryOp.getStoreLocation())) {
                            cse.tempsForExpressions.remove(exprObject);
                        }
                    }
                }
            }
            else if (stmt instanceof LlAssignStmtBinaryOp) {
                LlAssignStmtBinaryOp binaryOp = (LlAssignStmtBinaryOp) stmt;

                // we only store computations if they are variables (we don't do this for a[i]'s)
                if (binaryOp.getStoreLocation() instanceof LlLocationVar
                        && !(binaryOp.getLeftOperand() instanceof LlLocationArray)
                        && !(binaryOp.getRightOperand() instanceof LlLocationArray)) {

                    // check to see if this computation has been made before
                    BinaryExprObject binaryExpr = cse.new BinaryExprObject(binaryOp);
                    if (cse.tempsForExpressions.containsKey(binaryExpr)) {

                        // if it has been made before, swap the computation
                        // with the temp that already stores the value
                        LlAssignStmtRegular optimalStmt = new LlAssignStmtRegular(
                                binaryOp.getStoreLocation(),
                                cse.tempsForExpressions.get(binaryExpr)
                        );
                        labelsToStmtsMap.put(label, optimalStmt);
                    }
                    // if the computation has not been made before, store it for later
                    else if (!binaryExpr.containsAnyOfTheseVariables(globalVariables)) {
                        cse.tempsForExpressions.put(binaryExpr, (LlLocationVar) binaryOp.getStoreLocation());
                    }

                    // check cse.tempsForExpressions for any expressions whose operand
                    // is the storeLocation and remove those expressions because they are no longer valid.
                    for (ExprObject exprObject : new HashSet<>(cse.tempsForExpressions.keySet())) {
                        if (exprObject.containsVariable(binaryOp.getStoreLocation())) {
                            cse.tempsForExpressions.remove(exprObject);
                        }
                    }
                }
            }
            else if (stmt instanceof LlAssignStmtRegular) {
                LlAssignStmtRegular stmtRegular = (LlAssignStmtRegular) stmt;

                // check cse.tempsForExpressions for any expressions whose operand
                // is the storeLocation and remove those expressions because they are no longer valid.
                for (ExprObject exprObject : new HashSet<>(cse.tempsForExpressions.keySet())) {
                    if (exprObject.containsVariable(stmtRegular.getStoreLocation())) {
                        cse.tempsForExpressions.remove(exprObject);
                    }
                }
            }
        }
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

    private abstract class ExprObject {
        protected final String operation;
        protected final LlComponent rightOperand;

        public ExprObject(String operation, LlComponent rightOperand) {
            this.operation = operation;
            this.rightOperand = rightOperand;
        }

        public abstract boolean containsVariable(LlLocation comp);

        public boolean containsAnyOfTheseVariables(Collection<LlLocation> variables) {
            for (LlLocation var : variables) {
                if (this.containsVariable(var)) {
                    return true;
                }
            }
            return false;
        }
    }

    private class UnaryExprObject extends ExprObject {
        public UnaryExprObject(LlAssignStmtUnaryOp unaryOp) {
            super(unaryOp.getOperator(), unaryOp.getOperand());
        }

        @Override
        public boolean containsVariable(LlLocation comp) {
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

        @Override
        public int hashCode() {
            return this.operation.hashCode() * this.rightOperand.hashCode();
        }
    }

    private class BinaryExprObject extends ExprObject {
        private final LlComponent leftOperand;

        public BinaryExprObject(LlAssignStmtBinaryOp binaryOp) {
            super(binaryOp.getOperation(), binaryOp.getRightOperand());
            this.leftOperand = binaryOp.getLeftOperand();
        }

        @Override
        public boolean containsVariable(LlLocation comp) {
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

        @Override
        public int hashCode() {
            return this.leftOperand.hashCode() * this.operation.hashCode() * this.rightOperand.hashCode();
        }
    }
}


/*
    1) if this is the first time you have see the computation
 */