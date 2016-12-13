package edu.mit.compilers.parallel;

import edu.mit.compilers.LlBuilder;
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


    //Only works for perfectly nested loops - all other cases ignored
    //Check if loop variable for innermost loop is only used inside the loop
    //Innermost loop contains only array accesses and writes, nothing crazy/unexpected going on with i
    //Replace innermost loop variable in array usage with innermost [loop variable + #p_var]
    private boolean parallelizeLoop(IrCtrlFlowFor forLoop, IrIdent loopParallelVar, ArrayList<IrLocationVar> loopVarList) {
        ArrayList<IrStatement> loopStatements = forLoop.getStmtBody().getStmtsList();
        loopVarList.add(forLoop.getCounter());

        //If more than one statements, none should be loops
        if (loopStatements.size() > 1) {

            //Not parallelizable if any statement inside innermost loop is a control flow/jump
            for (IrStatement statement : loopStatements) {
                if (statement instanceof IrCtrlFlow || statement instanceof IrStmtBreak ||
                        statement instanceof IrStmtContinue || statement instanceof IrStmtReturn)
                    return false;
            }

            //Check for parallelizability for each statement - build dependencies
        }

        //Loop contains a single statement
        IrStatement statement = loopStatements.get(0);
        if (statement instanceof IrCtrlFlowFor) {
            return this.parallelizeLoop((IrCtrlFlowFor) statement, loopParallelVar, loopVarList);
        }
        else {
            if (statement instanceof IrCtrlFlow || statement instanceof IrStmtBreak ||
                    statement instanceof IrStmtContinue || statement instanceof IrStmtReturn)
                return false;

            //Check for parallelizability for each statement - build dependencies

        }

        return true;
    }


    //Check if statement poses a parallelization conflict
    //Check if statement contains any array access uses/defs
    //Return true if yes - function can no longer be parallelized
    private boolean checkStatementConflict(LlStatement statement) {
        //TODO
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