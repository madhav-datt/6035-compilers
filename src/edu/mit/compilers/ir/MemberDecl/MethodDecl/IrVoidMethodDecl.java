package edu.mit.compilers.ir;
import java.util.*;

public class IrVoidMethodDecl extends IrMethodDecl {

    public IrVoidMethodDecl(IrType returnType, List<IrParamDecl> parameters,
                           IrCodeBlock methodBody, IrIdent name, int lineNumber, int colNumber) {
        super(returnType, parameters, methodBody, name, lineNumber, colNumber);
    }

}