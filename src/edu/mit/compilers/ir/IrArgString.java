package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;

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

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLocationVar locationVar = builder.generateStrTemp();
        symbolTable.putOnStringTable(locationVar, (String) this.getArgValue());
        return locationVar;
    }
}
