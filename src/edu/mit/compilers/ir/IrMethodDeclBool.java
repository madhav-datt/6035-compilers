package edu.mit.compilers.ir;
import java.util.*;

public class IrMethodDeclBool extends IrMethodDecl {
    public IrMethodDeclBool(IrType returnType, List<IrMethodDeclParamDecl> paramsList,
                            IrCodeBlock methodBody, IrIdent name, int lineNumber, int colNumber) {
        super(returnType, paramsList, methodBody, name, lineNumber, colNumber);
    }
}