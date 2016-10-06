package edu.mit.compilers.ir.MemberDecl.MethodDecl;
import java.util.*;

import edu.mit.compilers.ir.*;

public class IrMethodDeclInt extends IrMethodDecl {

    public IrMethodDeclInt(IrType returnType, List<IrMethodDeclParamDecl> paramsList,
                           IrCodeBlock methodBody, IrIdent name, int lineNumber, int colNumber) {
        super(returnType, paramsList, methodBody, name, lineNumber, colNumber);
    }

}