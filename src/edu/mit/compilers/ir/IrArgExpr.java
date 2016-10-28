package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrArgExpr extends IrArg {
    public IrArgExpr(IrExpr expr, int lineNum, int colNum) {
        super(expr, lineNum, colNum);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = ((IrExpr) this.getArgValue()).semanticCheck(scopeStack);

        return errorMessage;
    }

    public IrType getArgumentType() {
        return ((IrExpr) this.getArgValue()).getExpressionType();
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        ((IrExpr) this.getArgValue()).generateCode(assembly, register, stackFrame);
        return assembly;
    }

    //argument Expressions can be equivalent to expressions. Such as locations and method calls.
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if ((that instanceof IrExpr)) {
            return ((IrExpr) this.getArgValue()).equals((IrExpr)that);
        }
        if ((that instanceof IrArgExpr)) {
            Ir otherIr = (IrArgExpr)that;
            return ((IrExpr) this.getArgValue()).equals((IrExpr)((IrArgExpr)that).getArgValue());
        }
        return false;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--argExpr:\n";

        // pretty print the expression
        prettyString += ((IrExpr) this.getArgValue()).prettyPrint("  " + indentSpace);

        return prettyString;
    }
}
