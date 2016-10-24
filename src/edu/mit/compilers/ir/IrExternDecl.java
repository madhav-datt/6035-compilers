package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public class IrExternDecl extends IrMemberDecl {
    public IrExternDecl(IrIdent name) {
        super(name, new IrTypeInt(name.getLineNumber(), name.getColNumber()), name.getColNumber(), name.getColNumber());
    }

    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
       return "";
    }
}