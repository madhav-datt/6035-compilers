package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;
import edu.mit.compilers.Register;

import java.util.ArrayList;
import java.util.Hashtable;

public class IrCodeBlock extends Ir {
    private final ArrayList<IrFieldDecl> fieldsList;
    private final ArrayList<IrStatement> stmtsList;
//    private LocalVariableTable localVariableTable = new LocalVariableTable();

    public IrCodeBlock(ArrayList<IrFieldDecl> fieldsList, ArrayList<IrStatement> stmtsList,
                       int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldsList = fieldsList;
        this.stmtsList = stmtsList;
    }
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable localVariableTable){

        for(IrFieldDecl fieldDecl : fieldsList){
            // doesnt store stuff in localVariableTable.
            fieldDecl.generateCode(assemblySoFar, localVariableTable);
        }
        for(IrStatement statement : stmtsList){
                statement.generateCode(assemblySoFar, localVariableTable);
            }
        assemblySoFar.append("\n");
        return "";
    }
}