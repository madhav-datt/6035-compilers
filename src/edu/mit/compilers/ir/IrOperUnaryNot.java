package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperUnaryNot extends IrOperUnary{
    public IrOperUnaryNot(IrExpr operand) {
        super(operand);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeBool(this.operand.getLineNumber(), this.operand.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check that the operand is valid
        errorMessage += this.operand.semanticCheck(scopeStack);

        // 2) verify that the operand is IrTypeBool
        if (!(this.operand.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "The not '!' operand must be used on a bool" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        // Compute the value of the operand and save it on to a register
        this.operand.generateCode(assembly, register, stackFrame);
        String operandReg = assembly.getFootNote();

        assembly.addLine("movq "+ operandReg +" %r10");
        assembly.addLine("not %r10");

        String resultTemp = stackFrame.getNextStackLocation();
        assembly.addLine("movq %r10, " + resultTemp);

        stackFrame.pushToRegisterStackFrame("%r10");
        assembly.putOnFootNote(resultTemp);
        assembly.addLine("");

        return assembly;

    }
}
