package edu.mit.compilers.ir;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlParallelMethodStmt;

/**
 * Created by madhav on 12/11/16.
 */
public class IrParallelSpecialStmt extends IrStatement {

    public IrIdent getParallelMethodName() {
        return parallelMethodName;
    }

    private final IrIdent parallelMethodName;

    //Only created in Parallelize.java during function parallelization
    public IrParallelSpecialStmt(IrIdent methodName) {
        super(methodName.getLineNumber(), methodName.getColNumber());
        this.parallelMethodName = methodName;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        //TODO Implement
        LlParallelMethodStmt methodStmt = new LlParallelMethodStmt(parallelMethodName.getValue());
        builder.appendStatement(methodStmt);
        return null;
    }

    //Unnecessary methods as IrParallelSpecialStmt doesn't exist in the high level IR till after semantic check
    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }

    @Override
    public String prettyPrint(String indentSpace) {
        return "";
    }
}
