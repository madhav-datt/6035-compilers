package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public abstract class IrFieldDecl extends IrMemberDecl {

    public IrFieldDecl(IrIdent name, IrType fieldType) {
        super(name, fieldType, name.getLineNumber(), name.getColNumber());
    }
    // Only mutates the LocalVariableTable. Doesn't update the
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        return "";
    }

}