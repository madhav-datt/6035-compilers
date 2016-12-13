package edu.mit.compilers.ll;

/**
 * Created by madhav on 12/11/16.
 */
public class LlParallelMethodStmt extends LlStatement{

    private final String parallelMethodName;
    public LlParallelMethodStmt(String methodName) {
        this.parallelMethodName = methodName;
    }

    @Override
    public String toString() {
        return "create_and_run_threads(" + this.parallelMethodName + ")";
    }

    //TODO Add codegen function
    /*
    movl	$(this.parallelMethodName), %rdi
	call	create_and_run_threads
     */
}
