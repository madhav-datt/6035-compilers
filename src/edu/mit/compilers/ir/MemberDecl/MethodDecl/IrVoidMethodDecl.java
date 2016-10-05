package edu.mit.compilers.ir;
import java.util.*;

public class IrVoidMethodDecl extends IrMethodDecl {

    public IrVoidMethodDecl(IrType returnType, List<IrParamDecl> paramsList,
                           IrCodeBlock methodBody, IrIdent name, int lineNumber, int colNumber) {
        super(returnType, paramsList, methodBody, name, lineNumber, colNumber);
    }

}