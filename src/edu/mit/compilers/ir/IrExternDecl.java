package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;

public class IrExternDecl extends IrMemberDecl {
    public IrExternDecl(IrIdent name) {
        super(name, new IrTypeInt(name.getLineNumber(), name.getColNumber()), name.getColNumber(), name.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--" + this.getName() + "\n";
        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        return null;
    }
}