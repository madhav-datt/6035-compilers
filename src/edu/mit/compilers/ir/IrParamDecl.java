package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

public class IrParamDecl extends Ir {
    private final IrType paramType;
    private final IrIdent paramName;

    public IrParamDecl(IrType paramType, IrIdent paramName) {
        super(paramName.getLineNumber(), paramName.getColNumber());
        this.paramType = paramType;
        this.paramName = paramName;
    }
    public String toString(){
        return paramType.toString() + " " + paramName.toString();
    }

    public IrType getParamType() {
        return this.paramType;
    }

    public IrIdent getParamName() {
        return this.paramName;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--param:\n";

        // print the parameter's name
        prettyString += ("  " + indentSpace + "|--name: " + this.paramName.getValue() + "\n");

        // print the parameter's type
        prettyString += this.paramType.prettyPrint("  " + indentSpace);

        return prettyString;
    }
}