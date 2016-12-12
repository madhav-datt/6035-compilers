package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlEmptyStmt;
import edu.mit.compilers.ll.LlLocation;

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

        // give method a new method scope with method returnType
        scopeStack.createNewMethodScope(this.getType());

        // check the params before the code block
        for (IrParamDecl paramDecl : this.paramsList) {

            // 1) make sure no duplicate params are declared
            if (scopeStack.checkIfSymbolExistsAtCurrentScope(paramDecl.getParamName().getValue())) {
                errorMessage += "Duplicate declaration of parameters in method declaration" +
                        " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
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

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        String methodNameLabel = this.getName();
        LlEmptyStmt emptyStmt = new LlEmptyStmt();
        builder.appendStatement(methodNameLabel, emptyStmt);
        this.methodBody.generateLlIr(builder, symbolTable);
        return null;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--method\n";

        // print the name
        prettyString += ("  " + indentSpace + "|--name: " + this.getName() + "\n");

        // print the params
        prettyString += ("    " + indentSpace + "|--paramsList:\n");
        for (IrParamDecl paramDecl: this.paramsList) {
            prettyString += paramDecl.prettyPrint("      " + indentSpace);
        }

        // print the block
        prettyString += this.methodBody.prettyPrint("    " + indentSpace);

        return prettyString;
    }
}