package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtReturnExpr extends IrStmtReturn{
    private final IrExpr result;

    public IrStmtReturnExpr(IrExpr result) {
        super(result.getLineNumber(),result.getColNumber());
        this.result = result;
    }

    @Override
    public IrType getExpressionType() {
        return this.result.getExpressionType();
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check to make sure the IrExpr is semantically correct
        errorMessage += this.result.semanticCheck(scopeStack);

        // 2) check to make sure that the IrStmtReturn exists within a method
        IrType methodType = scopeStack.getScopeReturnType();
        if (methodType != null) {

            // 3) check if method signature and return type match
            if (this.getExpressionType() != null) {
                if (!methodType.getClass().equals(this.getExpressionType().getClass())) {
                    errorMessage += "Return type does not match method return type" +
                            " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
                }
            }
        }
        else {
            // we are not in a method so we should not have an IrStmtReturnExpr
            errorMessage += "Return statements can only have a value in non-void methods."+
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        AssemblyBuilder asm = this.result.generateCode(assembly, register, stackFrame);
        String resultReg = asm.getFootNote();
        String retStr = "mov " + resultReg + ", %rax";
        asm.addLine(5, retStr);

        return asm;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|__returnExpr";

        // pretty print the expression
        indentSpace += this.result.prettyPrint("  " + indentSpace);

        return prettyString;
    }
}
