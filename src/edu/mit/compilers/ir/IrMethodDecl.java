package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

import java.util.*;

public class IrMethodDecl extends IrMemberDecl {
    private final List<IrParamDecl> paramsList;
    private final IrCodeBlock methodBody;

    public IrMethodDecl(IrType returnType, List<IrParamDecl> paramsList,
                        IrCodeBlock methodBody, IrIdent name) {
        super(name, returnType, name.getLineNumber(), name.getColNumber());
        this.paramsList = paramsList;
        this.methodBody = methodBody;
    }

    public List<IrParamDecl> getParamsList() {
        return new ArrayList<>(this.paramsList);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // give method has a new method scope with method returnType
        scopeStack.createNewMethodScope(this.getType());

        // check the params before the code block
        for (IrParamDecl paramDecl: this.paramsList) {

            // 1) make sure no duplicate params are declared
            if (scopeStack.checkIfSymbolExistsAtCurrentScope(paramDecl.getParamName().getValue())) {
                errorMessage += "Duplicate declaration of parameters in method declaration"+
                        " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
            }
            scopeStack.addObjectToCurrentScope(paramDecl.getParamName().getValue(), paramDecl);

            // 2) check that all the IrParamDecls are valid
            errorMessage += paramDecl.semanticCheck(scopeStack);
        }

        // 3) check that the codeBlock is valid
        errorMessage += this.methodBody.semanticCheck(scopeStack);

        // delete method scope the scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }
}