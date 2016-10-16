package edu.mit.compilers.ir;

import java.util.ArrayList;

public class IrCodeBlock extends Ir {
    private final ArrayList<IrFieldDecl> fieldsList;
    private final ArrayList<IrStatement> stmtsList;

    public IrCodeBlock(ArrayList<IrFieldDecl> fieldsList, ArrayList<IrStatement> stmtsList,
                       int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldsList = fieldsList;
        this.stmtsList = stmtsList;
    }
}