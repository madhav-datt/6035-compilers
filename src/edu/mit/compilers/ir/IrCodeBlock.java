package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlLocation;

import java.util.ArrayList;

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
    /*
        Doesn't mutate the LlBuilder builder object because a block never exists on its own. Instead it creates a new assembly builder and returns it
        to the owning Ir. I.e. method, conditional statement ...
     */

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        for (IrStatement statement: this.stmtsList) {
            if(statement instanceof IrStmtContinue){
                if(builder.pickPocket() != null) {
                    ((IrStatement) builder.pickPocket()).generateLlIr(builder, symbolTable);
                }
            }
            statement.generateLlIr(builder, symbolTable);
        }
        for (IrFieldDecl field: this.fieldsList) {
            if(field instanceof IrFieldDeclArray){
                field.generateLlIr(builder, symbolTable);
            }
        }
        return null;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--codeBlock:\n";

        // pretty print statement
        for (IrFieldDecl fieldDecl: this.fieldsList) {
            prettyString += fieldDecl.prettyPrint("  " + indentSpace);
        }

        // pretty print field decl
        for (IrStatement statement: this.stmtsList) {
            prettyString += statement.prettyPrint("  " + indentSpace);
        }

        return prettyString;
    }
}