package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public abstract class IrCtrlFlow extends IrStatement {
    final IrExpr condExpr;

    public IrExpr getCondExpr() {
        return condExpr;
    }

    public IrCodeBlock getStmtBody() {
        return stmtBody;
    }

    final IrCodeBlock stmtBody;

    public IrCtrlFlow(IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr.getLineNumber(), condExpr.getColNumber());
        this.condExpr = condExpr;
        this.stmtBody = stmtBody;
    }

}
