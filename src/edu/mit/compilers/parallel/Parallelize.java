package edu.mit.compilers.parallel;

import decaf.Parallel.Analyze;
import edu.mit.compilers.ir.*;
import edu.mit.compilers.ll.LlStatement;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by madhav on 12/10/16.
 */
public class Parallelize {

    public IrProgram getProgram() {
        return program;
    }

    private IrProgram program;

    private int parallelCount;
    private final long numThreads;
    private final int lineColNumber;
    private HashSet<String> parallelizableMethods;
    private IrMethodDecl mainMethod;

    public Parallelize(IrProgram program) {
        this.program = program;
        this.parallelCount = 0;
        this.parallelizableMethods = new HashSet<>();
        this.numThreads = 8;
        this.lineColNumber = -1;


        //For each method declaration in program, check if method is parallelizable
        for (IrMethodDecl method : program.getMethodDecls()) {
            if (method.getName().equals("main")) {
                this.mainMethod = method;
                continue;
            }

            if (this.parallelizeFunction(method)) {
                this.parallelizableMethods.add(method.getName());
            }
        }

        //Stop process if no method is parallelizable
        if (this.parallelizableMethods.size() == 0)
            return;

        //Add extern declarations: set_num_threads, create_and_run_threads in dcf program
        IrExternDecl setThreads = new IrExternDecl(new IrIdent("set_num_threads", this.lineColNumber,
                this.lineColNumber));
        IrExternDecl createRunThreads = new IrExternDecl(new IrIdent("create_and_run_threads", this.lineColNumber,
                this.lineColNumber));
        program.addExternDecl(setThreads);
        program.addExternDecl(createRunThreads);

        //Call function set_num_threads from main with parameter i = numThreads
        //Create method call for set_num_threads
        ArrayList<IrArg> numThreadsArgs = new ArrayList<>();
        numThreadsArgs.add(new IrArgExpr(new IrLiteralInt(this.numThreads, this.lineColNumber, this.lineColNumber),
                this.lineColNumber, this.lineColNumber));
        IrMethodCallStmt setNumThreads = new IrMethodCallStmt(new IrIdent("set_num_threads", this.lineColNumber,
                this.lineColNumber), numThreadsArgs);

        //Add created method call to top of main
        ArrayList<IrStatement> mainMethodStatements = this.mainMethod.getMethodBody().getStmtsList();
        mainMethodStatements.add(0, setNumThreads);


        //Replace calls for each parallelizable method f() by create_and_run_threads(f) in main method
        for (IrStatement statement : mainMethodStatements) {
            if (statement instanceof IrMethodCallStmt) {
                //Check if method is parallelizable
                if (parallelizableMethods.contains(((IrMethodCallStmt) statement).getMethodName().getValue())) {
                    IrParallelSpecialStmt updatedStatement =
                            new IrParallelSpecialStmt(((IrMethodCallStmt) statement).getMethodName());
                    mainMethodStatements.set(mainMethodStatements.indexOf(statement), updatedStatement);
                }
            }
        }

    }

    private IrIdent generateParallelizationVariable() {
        return new IrIdent("#p_" + Integer.toString(this.parallelCount++),
                this.lineColNumber, this.lineColNumber);
    }

    //Recursively descend down the Expr tree to get all array access uses
    private void getArrayAccess(IrExpr expression, ArrayList<IrExpr> indexList, ArrayList<IrLocationArray> locations) {
        if (expression == null)
            return;

        if (expression instanceof IrLocationArray) {
            indexList.add(((IrLocationArray) expression).getElementIndex());
            locations.add((IrLocationArray) expression);
        }

        else if (expression instanceof IrOperBinary) {
            this.getArrayAccess(((IrOperBinary) expression).getLeftOperand(), indexList, locations);
            this.getArrayAccess(((IrOperBinary) expression).getRightOperand(), indexList, locations);
        }

        else if (expression instanceof IrOperUnary) {
            this.getArrayAccess(((IrOperUnary) expression).operand, indexList, locations);
        }
    }

    //TODO
    private ArrayList<Integer> getElementOrder(IrExpr arrayIndex, ArrayList<IrLocationVar> varList) {

    }

    //Check dependencies between passed loop statements to check for conflicts in dependencies
    private boolean checkDependencyConflicts(ArrayList<IrStatement> statements, ArrayList<IrLocationVar> loopVarList,
                                             IrIdent loopParallelVar) {

        //Temporarily ignore all loops with more than one statements - potential dependencies too high
        if (statements.size() > 1)
            return false;

        IrStatement statement = statements.get(0);
        ArrayList<IrExpr> elementIndices = new ArrayList<>();
        ArrayList<IrLocationArray> arrayLocations = new ArrayList<>();

        IrLocation storeLocation = ((IrAssignStmt) statement).getStoreLocation();
        if (storeLocation instanceof IrLocationArray) {
            elementIndices.add(((IrLocationArray) storeLocation).getElementIndex());
        }

        //Get RHS expression for assignment statements
        IrExpr rightExpr = null;
        if (statement instanceof IrAssignStmtEqual) {
            rightExpr = ((IrAssignStmtEqual) statement).getNewValue();
        }

        else if (statement instanceof IrAssignStmtMinusEqual) {
            rightExpr = ((IrAssignStmtMinusEqual) statement).getDecrementBy();
        }

        else if (statement instanceof IrAssignStmtPlusEqual) {
            rightExpr = ((IrAssignStmtPlusEqual) statement).getIncrementBy();
        }

        //Extract array access expressions from rightExpr
        getArrayAccess(rightExpr, elementIndices, arrayLocations);

        for (int i = 0; i < elementIndices.size(); i++) {
            for (int j = i + 1; j < elementIndices.size(); j++) {
                ArrayList<Integer> first = this.getElementOrder(elementIndices.get(i), loopVarList);
                ArrayList<Integer> second = this.getElementOrder(elementIndices.get(j), loopVarList);

                Analyze.AccessPattern result = Analyze.getAccessPattern(first.toArray(new Integer[first.size()]),
                        second.toArray(new Integer[second.size()]));

                if (!result.distanceExists)

            }
        }

        //Add loop parallel var to element expr of each array access/use
        for (IrLocationArray arrayLocation : arrayLocations) {
            IrExpr parallelIndex = new IrOperBinaryArith("+", arrayLocation.getElementIndex(),
                    new IrLocationVar(loopParallelVar, this.lineColNumber, this.lineColNumber));
            arrayLocation.setElementIndex(parallelIndex);
        }


        return true;
    }

    //Only works for perfectly nested loops - all other cases ignored
    //Check if loop variable for innermost loop is only used inside the loop
    //Innermost loop contains only array accesses and writes, nothing crazy/unexpected going on with i
    //Replace innermost loop variable in array usage with innermost [loop variable + #p_var]
    private boolean parallelizeLoop(IrCtrlFlowFor forLoop, IrIdent loopParallelVar, ArrayList<IrLocationVar> loopVarList) {
        ArrayList<IrStatement> loopStatements = forLoop.getStmtBody().getStmtsList();
        loopVarList.add(forLoop.getCounter());
        boolean innerMostParallel = false;

        //If more than one statements, none should be loops
        if (loopStatements.size() > 1) {

            //Not parallelizable if any statement inside innermost loop is a control flow/jump
            for (IrStatement statement : loopStatements) {
                if (statement instanceof IrCtrlFlow || statement instanceof IrStmtBreak ||
                        statement instanceof IrStmtContinue || statement instanceof IrStmtReturn ||
                        statement instanceof IrMethodCallStmt)
                    return false;
            }

            //Check for parallel-izability for each statement - build dependencies
            if (!this.checkDependencyConflicts(loopStatements, loopVarList, loopParallelVar))
                innerMostParallel = true;
        }

        else {
            //Loop contains a single statement
            IrStatement statement = loopStatements.get(0);
            if (statement instanceof IrCtrlFlowFor) {
                return this.parallelizeLoop((IrCtrlFlowFor) statement, loopParallelVar, loopVarList);
            }
            else {
                if (statement instanceof IrCtrlFlow || statement instanceof IrStmtBreak ||
                        statement instanceof IrStmtContinue || statement instanceof IrStmtReturn ||
                        statement instanceof IrMethodCallStmt)
                    return false;

                //Check for parallel-izability for each statement - build dependencies
                if (!this.checkDependencyConflicts(loopStatements, loopVarList, loopParallelVar))
                    innerMostParallel = true;
            }
        }

        //Make modifications to loop call if innermost loop is parallelizable
        if (innerMostParallel) {
            IrAssignStmt loopIncrement = forLoop.getCompoundAssignStmt();
            if (loopIncrement instanceof IrAssignStmtPlusEqual){
                IrExpr incrementExpr = ((IrAssignStmtPlusEqual) loopIncrement).getIncrementBy();
                incrementExpr = new IrOperBinaryArith("*", incrementExpr,
                        new IrLiteralInt(this.numThreads, this.lineColNumber, this.lineColNumber));
                ((IrAssignStmtPlusEqual) loopIncrement).setIncrementBy(incrementExpr);
            }

            else if (loopIncrement instanceof IrAssignStmtMinusEqual){
                IrExpr incrementExpr = ((IrAssignStmtMinusEqual) loopIncrement).getDecrementBy();
                incrementExpr = new IrOperBinaryArith("*", incrementExpr,
                        new IrLiteralInt(this.numThreads, this.lineColNumber, this.lineColNumber));
                ((IrAssignStmtMinusEqual) loopIncrement).setDecrementBy(incrementExpr);
            }
            return true;
        }

        return false;
    }

    //Return true if method is parallelizable
    //Make changes and parallelize methods when parallelization is possible
    private boolean parallelizeFunction(IrMethodDecl methodDecl) {
        boolean flagFunction = false;

        //Check if method has return type void
        if (!(methodDecl.getType() instanceof IrTypeVoid))
            return false;

        //Check if method has no parameters
        if (methodDecl.getParamsList().size() != 0)
            return false;

        //Generate method parallelization variable
        IrIdent parallelVar = this.generateParallelizationVariable();

        //Get loop distance vectors, check if innermost loop is parallelizable
        for (IrStatement statement : methodDecl.getMethodBody().getStmtsList()) {
            if (statement instanceof IrCtrlFlowFor) {
                if (this.parallelizeLoop((IrCtrlFlowFor) statement, parallelVar, new ArrayList<>()))
                    flagFunction = true;
            }
        }

        //Change function to include required parameter
        IrParamDecl parallelParamFunction = new IrParamDecl(new IrTypeInt(this.lineColNumber, this.lineColNumber),
                parallelVar);
        methodDecl.addToParamsList(parallelParamFunction);

        return flagFunction;
    }

}