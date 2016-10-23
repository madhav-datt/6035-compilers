package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

import java.util.ArrayList;
import java.util.HashMap;

public class IrCodeBlock extends Ir {
    private final ArrayList<IrFieldDecl> fieldsList;
    private final ArrayList<IrStatement> stmtsList;

    public IrCodeBlock(ArrayList<IrFieldDecl> fieldsList, ArrayList<IrStatement> stmtsList,
                       int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldsList = fieldsList;
        this.stmtsList = stmtsList;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check that no identifiers declared twice in same scope
        for (IrFieldDecl fieldDecl: this.fieldsList) {
            if (scopeStack.checkIfSymbolExistsAtCurrentScope(fieldDecl.getName())) {
                errorMessage += "Duplicate declaration in same scope __filename__"+
                        " line: "+fieldDecl.getLineNumber() + " col: " +fieldDecl.getColNumber() + "\n";
            }
            scopeStack.addObjectToCurrentScope(fieldDecl.getName(), fieldDecl);

            // make sure each IrFieldDecl is correct
            errorMessage += fieldDecl.semanticCheck(scopeStack);
        }

        // 2) check that each statement is valid
        for (IrStatement stmt: this.stmtsList) {
            errorMessage += stmt.semanticCheck(scopeStack);
        }

        return errorMessage;
    }

    public IrStmtReturn getReturnStatment() {

        for (IrStatement stmt: this.stmtsList) {
            if (stmt instanceof IrStmtReturn) {
                return (IrStmtReturn) stmt;
            }
        }

        return null;
    }
}