package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/16/16.
 */
public class IrArgString extends IrArg {
    public IrArgString(String string, int lineNum, int colNum) {
        super(string, lineNum, colNum);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }


    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof IrArgString)) {
            return false;
        }
        Ir otherIr = (IrArgString)that;
        return ((String) this.getArgValue()).equals(((IrArgString) otherIr).getArgValue());
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--argString:\n";

        // pretty print the expression
        prettyString += ("  " + indentSpace + "|--string: \"" + (this.getArgValue() + "\"\n"));

        return prettyString;
    }
}
