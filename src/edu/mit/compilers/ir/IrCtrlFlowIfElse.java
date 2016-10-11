package edu.mit.compilers.ir;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowIfElse extends IrCtrlFlow{
    private final IrCodeBlock elseBlock;

    public IrCtrlFlowIfElse(IrCodeBlock elseBlock, IrExpr condExpr,
                            IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
        this.elseBlock = elseBlock;
    }
}
