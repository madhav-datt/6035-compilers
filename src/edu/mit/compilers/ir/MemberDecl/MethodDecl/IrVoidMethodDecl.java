package edu.mit.compilers.ir;
import java.util.*;

public class IrVoidMethodDecl extends IrMethodDecl {

    public IrVoidMethodDecl(IrType returnType, IrIdent methodName, List<IrParamDecl> parameters,
                           IrCodeBlock methodBody, int lineNumber, int colNumber) {
        super(returnType, methodName, parameters, methodBody, lineNumber, colNumber);
    }

}