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
     private String getMoveCommand(String operation){
        String retCommand = "";
        switch (operation){
            case "!=":
                retCommand = "cmovne ";
                break;
            case "==":
                retCommand = "cmove ";
          
        }
        return retCommand;
    }

    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        AssemblyBuilder leftRegister = leftOperand.generateCode(assembly, register, stackFrame);
        String leftValue = leftRegister.getFootNote();
        AssemblyBuilder rightRegister = rightOperand.generateCode(assembly, register, stackFrame);
        String rightValue = rightRegister.getFootNote();
        assembly.addLine("movq " + leftValue + ", %r10");
        assembly.addLine("movq " + rightValue + ", %r11");
        assembly.addLine("cmp %r10, %r11");
        assembly.addLine("movq $0, %r11");
        assembly.addLine("movq $1, %r10");
        assembly.addLine(this.getMoveCommand(this.operation)+" %r10, %r11");
        String condResultTemp = stackFrame.getNextStackLocation();
        assembly.addLine("movq %r11, " + condResultTemp);

        stackFrame.pushToRegisterStackFrame("%r11");
        assembly.putOnFootNote(condResultTemp);
        assembly.addLine("");
        return assembly;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--binaryEquateOper\n";

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
