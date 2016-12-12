package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlAssignStmtRegular;
import edu.mit.compilers.ll.LlLiteralInt;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;

public class IrSizeOfType extends IrSizeOf {
    private final IrType type;

    public IrSizeOfType(IrType type) {
        super(type.getLineNumber(), type.getColNumber());
        this.type = type;
    }

    @Override
    public IrType getExpressionType() {
        return this.type;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }

    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--sizeOfType\n";

        // pretty print the location
        prettyString += this.type.prettyPrint("  " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        // Generate a type and store the size in it.
        LlLocationVar tempLocation = builder.generateTemp();

        if(type instanceof IrTypeInt){
            LlAssignStmtRegular regularAssignment;
            regularAssignment = new LlAssignStmtRegular(tempLocation, new LlLiteralInt(8));
            builder.appendStatement(regularAssignment);
        }

        if(type instanceof IrTypeBool){
            LlAssignStmtRegular regularAssignment;
            regularAssignment = new LlAssignStmtRegular(tempLocation, new LlLiteralInt(1));
            builder.appendStatement(regularAssignment);
        }
        return tempLocation;

    }
}