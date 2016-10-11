package edu.mit.compilers.ir;
import java.util.*;

public class IrMethodDeclVoid extends IrMethodDecl {

    public IrMethodDeclVoid(IrType returnType, List<IrMethodParamDecl> paramsList,
                            IrCodeBlock methodBody, IrIdent name) {
        super(returnType, paramsList, methodBody, name);
    }

}