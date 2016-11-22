package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

public class IrLiteralBool extends IrLiteral {
    private final boolean value;

    public IrLiteralBool(boolean value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeBool(this.getLineNumber(), this.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyPrint = indentSpace + "|--boolLiteral\n";
        prettyPrint += "  " + indentSpace + "|--value: " + this.value + "\n";
        return prettyPrint;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLiteralBool literalBool = new LlLiteralBool(this.value);
        LlLocationVar locationVar = builder.generateTemp();
        LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(locationVar, literalBool);
        builder.appendStatement(regularAssignment);
        return locationVar;
    }
}