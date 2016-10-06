package edu.mit.compilers.ir;

import edu.mit.compilers.ir.*;

public class IrFieldDeclInt extends IrFieldDecl {
    public IrFieldDeclInt(IrType fieldType, IrIdent fieldName, int lineNumber, int colNumber) {
        super(fieldType, fieldName, lineNumber, colNumber);
    }
}