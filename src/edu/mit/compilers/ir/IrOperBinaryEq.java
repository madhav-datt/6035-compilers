package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperBinaryEq extends IrOperBinary {

    public IrOperBinaryEq(String operation, IrExpr leftOperand, IrExpr rightOperand) {
        super(operation, leftOperand, rightOperand);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeBool(this.leftOperand.getLineNumber(), this.leftOperand.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check that rhs and lhs are valid
        errorMessage += this.rightOperand.semanticCheck(scopeStack);
        errorMessage += this.leftOperand.semanticCheck(scopeStack);

        // 2) verify that both lhs and rhs are either IrTypeInt or IrTypeBool
        boolean bothAreInts = (this.rightOperand.getExpressionType() instanceof IrTypeInt)
        && (this.leftOperand.getExpressionType() instanceof IrTypeInt);
        boolean bothAreBools = (this.rightOperand.getExpressionType() instanceof IrTypeBool)
        && (this.leftOperand.getExpressionType() instanceof IrTypeBool);
        if (!bothAreBools && !bothAreInts) {
            errorMessage += "The lhs and rhs of an equator operation must both be ints or both be bools" +
                    " line: " + this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        AssemblyBuilder leftRegister = leftOperand.generateCode(assembly, register, stackFrame);
        String leftValue = leftRegister.getFootNote();
        AssemblyBuilder rightRegister = rightOperand.generateCode(assembly, register, stackFrame);
        String rightValue = leftRegister.getFootNote();
        assembly.addLine(5, "mov " + leftValue + ", %r10");
        assembly.addLine(5, "mov " + rightValue + ", %r11");
        assembly.addLine(5, "cmp %r10, %r11");
        assembly.addLine(5, "mov $0, %r11");
        assembly.addLine(5, "mov $1, %r10");
        assembly.addLine(5, "cmove %r10, %r11");
        assembly.addLine(5, "mov %r11, %r10");
        String condResultTemp = stackFrame.getNextStackLocation();
        assembly.addLine(5, "mov %r11, " + condResultTemp);
        stackFrame.pushToRegisterStackFrame("%r11");
        assembly.addLine(5, "mov " + condResultTemp + ", %r10");
        assembly.addLine(5, "mov $1, %r10");
        assembly.addLine(5, "cmp %r10, %r11");
        String label = assembly.getLabelName();
        assembly.addLine(5, "je ." + label);
        assembly.putOnFootNote(label);
       // assembly.putOnFootNote(condResultTemp);
        return assembly;
    }
}
