package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrOperBinaryArith extends IrOperBinary {

    public IrOperBinaryArith(String operation, IrExpr leftOperand, IrExpr rightOperand) {
        super(operation, leftOperand, rightOperand);
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.leftOperand.getLineNumber(), this.leftOperand.getColNumber());
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
            errorMessage += "The lhs and rhs of an arithmetic expression must be of type int" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }
    private String getCommand(String operation){
        String retCommand = "";
        switch (operation){
            case "+":
                retCommand = "add ";
                break;
            case "-":
                retCommand = "sub ";
                break;
            case "*":
                retCommand = "imul ";
                break;
            default:
                System.err.print("Runtime Error: Unrecognized Operation");
                break;
        }
        return retCommand;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        if(this.getOperation().equals("/") || this.getOperation().equals("%")){
            AssemblyBuilder leftRegister = leftOperand.generateCode(assembly, register, stackFrame);
            String leftValue = leftRegister.getFootNote();
            AssemblyBuilder rightRegister = rightOperand.generateCode(assembly, register, stackFrame);
            String rightValue = rightRegister.getFootNote();
            assembly.addLine("movq " + leftValue + ", %rax");
            assembly.addLine("movq " + rightValue + ", %r10");
            assembly.addLine("cqo ");
            assembly.addLine("idiv %r10");
            if(this.getOperation().equals("/")){
                assembly.addLine("movq %rax, %r10");
            }
            else if(this.getOperation().equals("%")){
                assembly.addLine("movq %rbx, %r10");
            }



        }
        else{
            AssemblyBuilder leftRegister = leftOperand.generateCode(assembly, register, stackFrame);
            String leftValue = leftRegister.getFootNote();
            AssemblyBuilder rightRegister = rightOperand.generateCode(assembly, register, stackFrame);
            String rightValue = rightRegister.getFootNote();
            assembly.addLine("movq " + leftValue + ", %r10");
            assembly.addLine("movq " + rightValue + ", %r11");
            assembly.addLine(this.getCommand(this.getOperation()) + "%r11, %r10");
        }
        String resultTemp = stackFrame.getNextStackLocation();
        assembly.addLine("movq %r10, " + resultTemp);
        stackFrame.pushToRegisterStackFrame("%r10");
        assembly.putOnFootNote(resultTemp);
        return assembly;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--binaryArithOper\n";

        // pretty print the lhs
        prettyString += "  " + indentSpace + "|--lhs\n";
        prettyString += this.leftOperand.prettyPrint("    " + indentSpace);

        // print the operator
        prettyString += "  " + indentSpace + "|--op: " + this.operation + "\n";

        // pretty print the rhs
        prettyString += "  " + indentSpace + "|--rhs\n";
        prettyString += this.rightOperand.prettyPrint("    " + indentSpace);

        return prettyString;
    }
}
