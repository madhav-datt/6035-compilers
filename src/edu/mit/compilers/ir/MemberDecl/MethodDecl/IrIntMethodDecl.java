package edu.mit.compilers.ir;
import java.util.*;

public class IrIntMethodDecl extends IrMethodDecl {

    public IrIntMethodDecl(IrType returnType, List<IrParamDecl> paramsList,
                           IrCodeBlock methodBody, IrIdent name, int lineNumber, int colNumber) {
        super(returnType, paramsList, methodBody, name, lineNumber, colNumber);
    }

}