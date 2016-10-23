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

        // give method has a new local scope (which includes the method's parameters)
        scopeStack.createNewScope();

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

        // 4) verify that method signature and return types match
        if (this.getType() instanceof IrTypeVoid) {
            IrStmtReturn returnStmt = this.methodBody.getReturnStatment();

            // if the method is void it must return void or have no return stmt
            if (returnStmt != null && !(returnStmt.getExpressionType() instanceof IrTypeVoid)) {
                errorMessage += "Non-void return statement in void method"+
                        " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
            }
        }
        else {
            IrStmtReturn returnStmt = this.methodBody.getReturnStatment();

            // IrStmtReturn must match the IrType of methodDecl if non-void
            if (!returnStmt.getExpressionType().getClass().equals(this.getType().getClass())) {
                errorMessage += "Method type and return type do not match"+
                        " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
            }
        }

        return errorMessage;
    }
}