package edu.mit.compilers.parallel;

import edu.mit.compilers.ir.*;

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

        //Add extern declarations: set_num_threads, create_and_run_threads in dcf program
        IrExternDecl setThreads = new IrExternDecl(new IrIdent("set_num_threads", this.lineColNumber,
                this.lineColNumber));
        IrExternDecl createRunThreads = new IrExternDecl(new IrIdent("create_and_run_threads", this.lineColNumber,
                this.lineColNumber));
        program.addExternDecl(setThreads);
        program.addExternDecl(createRunThreads);

        //For each method declaration in program, check if method is parallelizable
        for (IrMethodDecl method : program.getMethodDecls()) {
            if (method.getName().equals("main")) {
                this.mainMethod = method;
                continue;
            }

            if (this.parallelizeFunction(method))
                this.parallelizableMethods.add(method.getName());
        }

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

    private String generateParallelizationVariable() {
        return "#p_" + Integer.toString(this.parallelCount++);
    }

    //Return true if method is parallelizable
    //Make changes and parallelize methods when parallelization is possible
    private boolean parallelizeFunction(IrMethodDecl methodDecl) {
        //Check if method has return type void
        if (!(methodDecl.getType() instanceof IrTypeVoid))
            return false;

        //Check if method has no parameters
        if (methodDecl.getParamsList().size() != 0)
            return false;

        //Get loop distance vectors, check if innermost loop is parallelizable


        //Check if loop variable for innermost loop is only used inside the loop
        //Innermost loop contains only array accesses and writes, nothing crazy/unexpected going on with i

        //Replace innermost loop variable in array usage with innermost [loop variable + #p_var]
        //Change function

        return true;
    }

}
