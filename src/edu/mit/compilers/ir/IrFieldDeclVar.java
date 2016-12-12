package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;

/**
 * Created by devinmorgan on 10/11/16.
 */
public class IrFieldDeclVar extends IrFieldDecl {
    public IrFieldDeclVar(IrIdent fieldName, IrType fieldType) {
        super(fieldName, fieldType);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--varDecl\n";
        prettyString += ("  " + indentSpace + "|--name: " + this.getName() + "\n");
        prettyString += this.getType().prettyPrint("  " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        symbolTable.putOnStringTable(new LlLocationVar(this.getName()), "");

        return null;
    }
}
