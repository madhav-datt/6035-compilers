package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlAssignStmtRegular;
import edu.mit.compilers.ll.LlLiteralInt;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;

public class IrLiteralInt extends IrLiteral {
    private final long value;

    public IrLiteralInt(long value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.getLineNumber(), this.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyPrint = indentSpace + "|--IntLiteral\n";
        prettyPrint += ("  " + indentSpace + "|--value: " + this.value + "\n");
        return prettyPrint;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLiteralInt literalInt = new LlLiteralInt(this.value);
        LlLocationVar locationVar = builder.generateTemp();
        LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(locationVar, literalInt);
        builder.appendStatement(regularAssignment);
        return locationVar;
    }
}