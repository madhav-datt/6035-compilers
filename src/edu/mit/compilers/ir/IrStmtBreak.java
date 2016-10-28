package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtBreak extends IrStatement {
    public IrStmtBreak(int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check to see if this statement resides in a loop scope or not
        boolean isInALoop = scopeStack.isScopeForALoop();
        if (!isInALoop) {
            errorMessage += "Break statement cannot be used outside of a for or while loop" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        String currentLoopLabel = assembly.getCurrentBlock();
        assembly.addLine(String.format("jmp .%s_DONE", currentLoopLabel));
        assembly.addLine();
        return assembly;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--breakStmt";
        return prettyString;
    }
}
