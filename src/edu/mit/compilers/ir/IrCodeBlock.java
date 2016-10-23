package edu.mit.compilers.ir;

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
    public String semanticCheck() {
        String errorMessage = "";

        // 1) check that no identifiers declared twice in same scope
        HashMap<String, Ir> fieldsHashMap = new HashMap<>();
        for (IrFieldDecl fieldDecl: this.fieldsList) {
            if (fieldsHashMap.containsKey(fieldDecl.getName())) {
                errorMessage += "Duplicate declaration in same scope __filename__"+
                        " line: "+fieldDecl.getLineNumber() + " col: " +fieldDecl.getColNumber() + "\n";
            }
        }

        // 2) check that each statement is valid
        for (IrStatement stmt: this.stmtsList) {
            errorMessage += stmt.semanticCheck();
        }

        return errorMessage;
    }
}