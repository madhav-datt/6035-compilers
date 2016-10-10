package edu.mit.compilers.ir;

public class IrCodeBlock extends Ir {
    private final IrFieldDecl[] fieldsList;
    private final IrStatement[] stmtsList;

    public IrCodeBlock(IrFieldDecl[] fieldsList, IrStatement[] stmtsList, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldsList = fieldsList;
        this.stmtsList = stmtsList;
    }
}