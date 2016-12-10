package edu.mit.compilers.ir;


import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

/**
* Created by devinmorgan on 10/5/16.
*/
public class IrAssignStmtEqual extends IrAssignStmt {

    private final IrExpr newValue;

    public IrAssignStmtEqual(IrLocation storeLocation, IrExpr newValue) {
        super(storeLocation);
        this.newValue = newValue;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) verify that the storeLocation is semantically correct
        errorMessage += this.getStoreLocation().semanticCheck(scopeStack);

        if (this.getStoreLocation() instanceof IrLocationVar) {

            // 2) check to make sure the var isn't a lone array var
            if (scopeStack.checkIfSymbolExistsAtAnyScope(this.getStoreLocation().getLocationName().getValue())) {
                Ir object = scopeStack.getSymbol(this.getStoreLocation().getLocationName().getValue());
                if (object instanceof IrFieldDeclArray) {
                    errorMessage += "Can't re-assign an array to an expression" +
                            " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
                }
            }
        }

        // 3) verify that the expr is semantically correct
        errorMessage += this.newValue.semanticCheck(scopeStack);

        // 4) make sure that the IrExpr and IrLocation are the same IrType
        boolean bothAreInts = (this.newValue.getExpressionType() instanceof IrTypeInt)
                && (this.getStoreLocation().getExpressionType() instanceof IrTypeInt);
        boolean bothAreBools = (this.newValue.getExpressionType() instanceof IrTypeBool)
                && (this.getStoreLocation().getExpressionType() instanceof IrTypeBool);
        if (!bothAreBools && !bothAreInts) {
            errorMessage += "The variable to be assigned and expression must both be of type int or of type bool" +
                    " line: " + this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLocation tempVal = newValue.generateLlIr(builder, symbolTable);

        if(this.getStoreLocation() instanceof IrLocationArray){
            LlLocation arrayLocation = ((IrLocationArray) this.getStoreLocation()).generateLlIr(builder, symbolTable);
            LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(arrayLocation, tempVal);
            builder.appendStatement(regularAssignment);
        }
        else {
            LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(new LlLocationVar(this.getStoreLocation().getLocationName().getValue()), tempVal);
            builder.appendStatement(regularAssignment);
        }
        return null;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--assignStmtEquals\n";

        // pretty print the lhs
        prettyString += ("  " + indentSpace + "|--lhs\n");
        prettyString += this.getStoreLocation().prettyPrint("    " +indentSpace);

        // print the rhs
        prettyString += ("  " + indentSpace + "|--rhs\n");
        prettyString += this.newValue.prettyPrint("    " + indentSpace);

        return prettyString;
    }
}


