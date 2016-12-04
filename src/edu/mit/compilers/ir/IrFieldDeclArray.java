package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationArray;
import edu.mit.compilers.ll.LlLocationVar;

public class IrFieldDeclArray extends IrFieldDecl {
    private final int arraySize;

    public IrFieldDeclArray(int arraySize, IrIdent name, IrType fieldType) {
        super(name, fieldType);
        this.arraySize = arraySize;
    }

    public int getArraySize(){
        return arraySize;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // make sure that the array size is greater than 0
        if (arraySize <= 0) {
            errorMessage = "Array size must be a non-zero positive integer" +
                    " line: " + this.getLineNumber() + " col: " + this.getColNumber();
        }

        return errorMessage;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--arrayDecl\n";
        prettyString += ("  " + indentSpace + "|--name: " + this.getName() + "\n");
        prettyString += this.getType().prettyPrint("  " + indentSpace);
        prettyString += ("  " + indentSpace + "|--size: " + this.arraySize + "\n");

        return prettyString;
    }

    // TODO: Fix the scary hack in this method.
    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        symbolTable.putOnStringTable(new LlLocationVar(this.getName()), "");
        return null;
    }
}