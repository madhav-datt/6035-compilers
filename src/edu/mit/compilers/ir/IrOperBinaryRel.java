package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperBinaryRel extends IrOperBinary {

    public IrOperBinaryRel(String operation, IrExpr leftOperand, IrExpr rightOperand) {
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

        // 2) verify that both lhs and rhs are IrType int
        if (!((this.rightOperand.getExpressionType() instanceof IrTypeInt)
                && (this.leftOperand.getExpressionType() instanceof IrTypeInt))) {
            errorMessage += "The lhs and rhs of a relational expression must be of type int" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }
    private String getJumpCommand(String operation){
        String retCommand = "";
        switch (operation){
            case "<= ":
                retCommand = "jle ";
                break;
            case ">=":
                retCommand = "jge ";
                break;
            case ">":
                retCommand = "jg ";
                break;
            case "<":
                retCommand = "jl ";
                break;
            default:
                System.err.print("Runtime Error: Unrecognized Operation");
                break;
        }
        return retCommand;
    }
    private String getMoveCommand(String operation){
        String retCommand = "";
        switch (operation){
            case "<= ":
                retCommand = "cmovle ";
                break;
            case ">=":
                retCommand = "cmovge ";
                break;
            case ">":
                retCommand = "cmovg ";
                break;
            case "<":
                retCommand = "cmovl ";
                break;
            default:
                System.err.print("Runtime Error: Unrecognized Operation");
                break;
        }
        return retCommand;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        AssemblyBuilder leftRegister = leftOperand.generateCode(assembly, register, stackFrame);
        String leftValue = leftRegister.getFootNote();
        AssemblyBuilder rightRegister = rightOperand.generateCode(assembly, register, stackFrame);
        String rightValue = leftRegister.getFootNote();
        assembly.addLine(0, "mov " + leftValue + ", %r10");
        assembly.addLine(0, "mov " + rightValue + ", %r11");
        assembly.addLine(0, "cmp %r10, %r11");
        assembly.addLine(0, "mov $0, %r11");
        assembly.addLine(0, "mov $1, %r10");
        assembly.addLine(0, this.getMoveCommand(this.operation)+" %r10, %r11");
        assembly.addLine(0, "mov %r11, %r10");
        String condResultTemp = stackFrame.getNextStackLocation();
        assembly.addLine(0, "mov %r11, " + condResultTemp);
        stackFrame.pushToRegisterStackFrame("%r11");
        assembly.addLine(0, "mov " + condResultTemp + ", %r10");
        assembly.addLine(0, "mov $1, %r10");
        assembly.addLine(0, "mov %r10, %r11");
        String label = assembly.getLabelName();
        assembly.addLine(0, getJumpCommand(this.operation)+" ." + label);
        assembly.addLine(0, "");
        assembly.putOnFootNote(label);
        return assembly;
    }
}
