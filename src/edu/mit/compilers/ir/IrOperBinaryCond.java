package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperBinaryCond extends IrOperBinary {

    public IrOperBinaryCond(String operation, IrExpr leftOperand, IrExpr rightOperand) {
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

        // 2) verify that both lhs and rhs are IrTypeBool
        if (!((this.rightOperand.getExpressionType() instanceof IrTypeBool)
                && (this.leftOperand.getExpressionType() instanceof IrTypeBool))) {
            errorMessage += "The lhs and rhs of an conditional expression must be of type bool" +
                    " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
        }

        return errorMessage;
    }
    private String getCommand(String operation){
        String retCommand = "";
        switch (operation){
            case "&&":
                retCommand = "and ";
                break;
            case "||":
                retCommand = "or ";
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
        assembly.addLine(5, "mov " + leftValue + ", %r10");
        assembly.addLine(5, "mov " + rightValue + ", %r11");
        assembly.addLine(5, this.getCommand(this.getOperation()) + "%r10, %r11");
        String resultTemp = stackFrame.getNextStackLocation();
        assembly.addLine(5, "mov %r11, " + resultTemp);
        stackFrame.pushToRegisterStackFrame("%r11");
        assembly.putOnFootNote(resultTemp);
        return assembly;
    }

}
