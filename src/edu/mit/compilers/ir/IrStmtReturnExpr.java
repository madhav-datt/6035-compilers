package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrStmtReturnExpr extends IrStatement{
    private final IrExpr result;

    public IrStmtReturnExpr(IrExpr result) {
        super(result.getLineNumber(),result.getColNumber());
        this.result = result;
    }
    public IrExpr getExpr(){
        return this.result;
    }
    public String generateCode(String reg){
        return String.format("mov $s, $s", reg, "%rax");
    }
}
