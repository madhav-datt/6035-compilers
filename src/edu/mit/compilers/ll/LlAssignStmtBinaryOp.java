package edu.mit.compilers.ll;


import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtBinaryOp extends LlAssignStmt{
    private final LlComponent leftArg;
    private final String operation;
    private final LlComponent rightArg;

    public LlAssignStmtBinaryOp(LlLocation storeLocation, LlComponent leftArg, String operand, LlComponent rightArg) {
        super(storeLocation);
        this.leftArg = leftArg;
        this.operation = operand;
        this.rightArg = rightArg;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + this.leftArg.toString() + " " +this.operation  + " " + this.rightArg.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlAssignStmtBinaryOp)) {
           return false;
        }
        return ((LlAssignStmtBinaryOp)obj).rightArg.equals(this.rightArg)
                && ((LlAssignStmtBinaryOp)obj).leftArg.equals(this.leftArg)
                &&((LlAssignStmtBinaryOp)obj).operation.equals(this.operation)
                &&((LlAssignStmtBinaryOp)obj).storeLocation.equals(this.storeLocation);
    }

    private String getCommand(String operation){
        String retCommand = "";
        switch (operation){
            case "+":
                retCommand = "addq ";
                break;
            case "-":
                retCommand = "subq ";
                break;
            case "*":
                retCommand = "imulq ";
                break;
            case "/":
                retCommand = "idivq ";
                break;
            case "%":
                retCommand = "modq ";
                break;
            case ">":
                retCommand = "cmovg";
                break;
            case "<":
                retCommand = "cmovl ";
                break;
            case ">=":
                retCommand = "cmovge";
                break;
            case "<=":
                retCommand = "cmovle ";
                break;
            case "!=":
                retCommand = "cmovne ";
                break;
            case "==":
                retCommand = "cmove ";
                break;
            default:
                System.err.println("Runtime Error: Unrecognized Operation");
                System.err.println(operation);
                break;
        }
        return retCommand;
    }

    private boolean isComparison(String operation){
        String opers = "< <= > >= == !=";
        return opers.contains(operation);
    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){

        // compute the value of the expression and figure out where it is stored
        String leftExprResultStorageLoc = this.leftArg.generateCode(builder, frame, symbolTable);
        String rightExprResultStorageLoc = this.rightArg.generateCode(builder, frame, symbolTable);

        builder.addComment("generating code for " + this.toString());

        builder.addLinef("movq",leftExprResultStorageLoc + ", %r10");
        builder.addLinef("movq",rightExprResultStorageLoc + ", %r11");

        if(!isComparison(this.operation)) {
            builder.addLinef(this.getCommand(this.operation), "%r10, %r11");

        }
        else{
            builder.addLinef("cmpq", "%r10, %r11");
            builder.addLinef("movq", "$0, %r11");
            builder.addLinef("movq", "$1, %r10");
            builder.addLinef(this.getCommand(this.operation), "%r10, %r11");
        }

        String resultTemp = frame.getNextStackLocation();
        frame.pushToStackFrame(this.storeLocation);
        builder.addLinef("movq", "%r11, " + resultTemp);

        builder.addLine();

        return resultTemp;

    }
}