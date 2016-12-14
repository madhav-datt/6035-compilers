package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlAssignStmtUnaryOp extends LlAssignStmt{

    private final LlComponent operand;
    private final String operator;

    public LlAssignStmtUnaryOp(LlLocation storeLocation, LlComponent operand, String operator) {
        super(storeLocation);
        this.operand = operand;
        this.operator = operator;
    }

    public LlComponent getOperand() {
        return this.operand;
    }

    public String getOperator() {
        return this.operator;
    }

    @Override
    public String toString() {
        return this.storeLocation.toString() + " = " + operator + " " + operand.toString();
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LlAssignStmtUnaryOp)) {
            return false;
        }
        return ((LlAssignStmtUnaryOp)obj).operand.equals(this.operand)
                &&((LlAssignStmtUnaryOp)obj).operator.equals(this.operator)
                &&((LlAssignStmtUnaryOp)obj).storeLocation.equals(this.storeLocation);
    }

    @Override
    public int hashCode() {
        return this.operator.hashCode() * this.operand.hashCode() * this.storeLocation.hashCode();
    }

    private boolean isRegister(String loc){
        String r = "%r12 %r13 %r14 %r15 %rbx";
        return r.contains(loc);

    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){
        // compute the value of the expression and figure out where it is stored
        String copyFrom = "";
        builder.addComment("generating code for " + this.toString());
        String exprResultLocation = this.operand.generateCode(builder, frame, symbolTable);

        if(isRegister(exprResultLocation)){
            copyFrom = exprResultLocation;

        }
        else{
            copyFrom = "%r10";
            builder.addLinef("movq", exprResultLocation + ", %r10");
        }

        String returnLocation;
        if(this.operator.equals("!")){

            builder.addLinef("xorq", "$1, "+copyFrom);

            returnLocation = builder.optimizedStore(this.storeLocation, copyFrom, frame);
            builder.addLine();
            return returnLocation;
        }
        else if(this.operator.equals("-")){
            builder.addLinef("movq", exprResultLocation + ", "+copyFrom);
            builder.addLinef("neg",  "%r10");
            returnLocation = builder.optimizedStore(this.storeLocation, copyFrom, frame);
            builder.addLine();
            return returnLocation;
        }




        return  null;

    }
}
