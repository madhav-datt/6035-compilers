package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowIf extends IrCtrlFlow {
    public IrCtrlFlowIf(IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
    }

    public IrExpr getIfCondition() {
        return this.condExpr;
    }

    public IrCodeBlock getIfBodyBlock() {
        return this.stmtBody;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // create the scope for the if-statement
        scopeStack.createNewBlockScope();

        // 1) verify that the expression is valid
        errorMessage += this.condExpr.semanticCheck(scopeStack);

        // 2) make sure that the condition expression is IrTypeBool
        if (!(this.condExpr.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "Condition for if-statement must be a boolean" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        // 3) verify that the block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the current scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        this.condExpr.generateCode(assembly, register, stackFrame);
        // go to the last part of the done block
        String label = assembly.getFootNote();
        assembly.addLine(5, String.format("jmp .%s_DONE", label));
        assembly.addLine(0, String.format("%s: ", label));
        this.getIfBodyBlock().generateCode(assembly, register, stackFrame);
        assembly.addLine(5, String.format("jmp .%s_DONE", label));
        assembly.putOnFootNote(label);
        return assembly;
    }
}
