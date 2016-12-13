package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

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

    /*
    movl	$(this.parallelMethodName), %rdi
	call	create_and_run_threads
     */
    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable) {
        builder.addLinef("movq", "$" + this.parallelMethodName + ", %rdi");
        builder.addLinef("call", "create_and_run_threads");
        return null;
    }
}
