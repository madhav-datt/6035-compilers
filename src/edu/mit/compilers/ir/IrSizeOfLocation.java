package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

public class IrSizeOfLocation extends IrSizeOf {
    private final IrIdent fieldName;
    private Ir irDecl; // IrFieldDeclArray, IrFieldDeclVar, IrParamDecl

    public IrSizeOfLocation(IrIdent fieldName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldName = fieldName;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.fieldName.getLineNumber(), this.fieldName.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) make sure the variable has been declared already
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.fieldName.getValue())) {
            Ir object = scopeStack.getSymbol(this.fieldName.getValue());

            // 2) make sure that argument is a var (and not a method)
            if (!(object instanceof IrFieldDecl) && !(object instanceof IrFieldDecl)) {
                errorMessage += "Argument for sizeof is not a type, variable, or array" +
                        " line: " + this.fieldName.getLineNumber() + " col: " + this.fieldName.getColNumber() + "\n";
            }
            else {
                // IMPORTANT: set the irDecl for the IrSizeOfLocation
                this.irDecl = object;
            }
        }
        else {
            errorMessage += "Argument in sizeof hasn't been declared" +
                    " line: " + this.fieldName.getLineNumber() + " col: " + this.fieldName.getColNumber() + "\n";
        }

        return errorMessage;
    }
    public String getTypeString(){
        if(this.irDecl instanceof IrFieldDeclArray){
            return "array";
        }
        if(this.irDecl instanceof IrFieldDeclVar){
            if(((IrFieldDeclVar) this.irDecl).getType() instanceof IrTypeInt){
                return "int";
            }
            if(((IrFieldDeclVar) this.irDecl).getType() instanceof IrTypeBool){
                return "bool";
            }

        }
        if(this.irDecl instanceof IrParamDecl){
            if(((IrParamDecl) this.irDecl).getParamType() instanceof IrTypeInt){
                return "int";
            }
            if(((IrParamDecl) this.irDecl).getParamType() instanceof IrTypeBool){
                return "bool";
            }
        }

        return "void";
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--sizeOfLocation\n";

        // pretty print the location
        prettyString += ("  " + indentSpace + "|--name: " + this.fieldName.getValue() + "\n");

        return prettyString;
    }


    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLocationVar tempLocation = builder.generateTemp();

        if(this.irDecl instanceof IrFieldDeclVar){
            IrFieldDecl fieldDecl = (IrFieldDecl) this.irDecl;
            if(fieldDecl.getType() instanceof IrTypeInt){
                LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(tempLocation, new LlLiteralInt(8));
                builder.appendStatement(regularAssignment);
            }
            else if(fieldDecl.getType() instanceof IrTypeInt){
                LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(tempLocation, new LlLiteralInt(1));
                builder.appendStatement(regularAssignment);
            }
        }
         if(this.irDecl instanceof IrFieldDeclArray){
            IrFieldDeclArray fieldDeclArray = (IrFieldDeclArray) this.irDecl;
            if(fieldDeclArray.getType() instanceof IrTypeBool){
                int arraySize = fieldDeclArray.getArraySize();
                LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(tempLocation, new LlLiteralInt(arraySize));
                builder.appendStatement(regularAssignment);
            }
            else if(fieldDeclArray.getType() instanceof IrTypeInt){
                int arraySize = fieldDeclArray.getArraySize()*8;
                LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(tempLocation, new LlLiteralInt(arraySize));
                builder.appendStatement(regularAssignment);
            }


        }
        else if(this.irDecl instanceof IrParamDecl){
            IrParamDecl paramDecl = (IrParamDecl)this.irDecl;
                if(paramDecl.getParamType() instanceof IrTypeInt){
                    LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(tempLocation, new LlLiteralInt(8));
                    builder.appendStatement(regularAssignment);
                }
                else if(paramDecl.getParamType() instanceof IrTypeInt){
                    LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(tempLocation, new LlLiteralInt(1));
                    builder.appendStatement(regularAssignment);
                }
        }

        return tempLocation;

    }
}