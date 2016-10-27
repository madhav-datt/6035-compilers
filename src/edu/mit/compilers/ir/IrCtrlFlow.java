package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public abstract class IrCtrlFlow extends IrStatement {
    final IrExpr condExpr;
    final IrCodeBlock stmtBody;

    public IrCtrlFlow(IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr.getLineNumber(), condExpr.getColNumber());
        this.condExpr = condExpr;
        this.stmtBody = stmtBody;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        AssemblyBuilder asm = new AssemblyBuilder();
        this.condExpr.generateCode(asm, register, stackFrame);
        System.out.println(asm.toString());
        return assembly;
    }
}
