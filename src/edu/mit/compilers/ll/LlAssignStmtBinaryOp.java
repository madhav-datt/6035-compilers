package edu.mit.compilers.ll;


import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtBinaryOp extends LlAssignStmt{

    private final LlComponent leftOperand;
    private final String operation;
    private final LlComponent rightOperand;

    public LlAssignStmtBinaryOp(LlLocation storeLocation, LlComponent leftOperand, String operation, LlComponent rightOperand) {
        super(storeLocation);
        this.leftOperand = leftOperand;
        this.operation = operation;
        this.rightOperand = rightOperand;
    }

    public LlComponent getLeftOperand() {
        return this.leftOperand;
    }

    public String getOperation() {
        return this.operation;
    }

    public LlComponent getRightOperand() {
        return this.rightOperand;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + this.leftOperand.toString() + " " +this.operation  + " " + this.rightOperand.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlAssignStmtBinaryOp)) {
           return false;
        }
        return ((LlAssignStmtBinaryOp)obj).rightOperand.equals(this.rightOperand)
                && ((LlAssignStmtBinaryOp)obj).leftOperand.equals(this.leftOperand)
                &&((LlAssignStmtBinaryOp)obj).operation.equals(this.operation)
                &&((LlAssignStmtBinaryOp)obj).storeLocation.equals(this.storeLocation);
    }

    @Override
    public int hashCode() {
        return this.rightOperand.hashCode()
                * this.leftOperand.hashCode()
                * this.operation.hashCode()
                * this.storeLocation.hashCode();
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
            case "||":
                retCommand = "or ";
                break;
            case "&&":
                retCommand = "and ";
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
        String leftExprResultStorageLoc = this.leftOperand.generateCode(builder, frame, symbolTable);
        String rightExprResultStorageLoc = this.rightOperand.generateCode(builder, frame, symbolTable);

        builder.addComment("generating code for " + this.toString());

        builder.addLinef("movq",rightExprResultStorageLoc + ", %r10");
        builder.addLinef("movq",leftExprResultStorageLoc + ", %r11");

        if(!isComparison(this.operation)) {
            if(this.operation.equals("/")){
                builder.addLinef("movq", "%r11, %rax");
                builder.addLinef("cqo", "");
                builder.addLinef("idivq", "%r10");
                builder.addLinef("movq", "%rax, %r11");
            }
            else if(this.operation.equals("%")){
                builder.addLinef("movq", "%r11, %rax");
                builder.addLinef("cqo", "");
                builder.addLinef("idivq", "%r10");
                builder.addLinef("movq", "%rdx, %r11");
            }
            else{
                builder.addLinef(this.getCommand(this.operation), "%r10, %r11");
            }

        }
        else{

            builder.addLinef("cmp", "%r10, %r11");
            builder.addLinef("movq", "$0, %r11");
            builder.addLinef("movq", "$1, %r10");
            builder.addLinef(this.getCommand(this.operation), "%r10, %r11");
        }

//        String resultTemp = frame.getNextStackLocation();
//        frame.pushToStackFrame(this.storeLocation);
//        builder.addLinef("movq", "%r11, " + resultTemp);
         String storedIn =  builder.optimizedStore(this.storeLocation, "%r11", frame);

        builder.addLine();

        return storedIn;

    }
}