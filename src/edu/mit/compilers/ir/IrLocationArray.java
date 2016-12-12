package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlAssignStmtRegular;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationArray;
import edu.mit.compilers.ll.LlLocationVar;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationArray extends IrLocation {
    private final IrExpr elementIndex;

    public IrLocationArray(IrExpr elementIndex, IrIdent varName, int lineNumber, int colNumber) {
        super(varName, lineNumber, colNumber);
        this.elementIndex = IrExpr.canonicalizeExpr(elementIndex);
    }

    @Override
    public IrType getExpressionType() {
        return this.varType;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) verify that the IrExpr is semantically correct
        errorMessage += this.elementIndex.semanticCheck(scopeStack);

        // 2) make sure the array has been declared already
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.getLocationName().getValue())) {

            // 3) make sure that var is an array (and not a method or non-array)
            Ir object = scopeStack.getSymbol(this.getLocationName().getValue());
            if (!(object instanceof IrFieldDeclArray)) {
                errorMessage += "Non-array variable be accessed as an array" +
                        " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
            }
            else {
                IrFieldDeclArray array = (IrFieldDeclArray) object;

                // IMPORTANT: set the IrType of the IrLocationArray
                this.setLocationType(array.getType());
            }
        }
        else {
            errorMessage += "Array variable used before declared" +
                    " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
        }

        // 4) make sure that the IrExpr offset is an IrTypeInt
        if (!(this.elementIndex.getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "Element offset must be of type int" +
                    " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
        }

        return errorMessage;
    }

    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof IrLocation)) {
            return false;
        }
        return (this.getLocationName().equals(((IrLocation) that).getLocationName()));
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|__arrayAccess\n";

        // print the name
        prettyString += ("  " + indentSpace + "|__name: " + this.varName.getValue() + "\n");

        // print the type
        prettyString += this.varType.prettyPrint("  " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        if(this.elementIndex instanceof IrLocationArray){
            LlLocation indexExpressionTemp = this.elementIndex.generateLlIr(builder, symbolTable);
            LlLocation arrayReturnTemp = builder.generateTemp();
            LlAssignStmtRegular arrValueAssignment = new LlAssignStmtRegular(arrayReturnTemp, indexExpressionTemp);
            builder.appendStatement(arrValueAssignment);
            LlLocationArray locationArray = new LlLocationArray(this.varName.getValue(), (LlLocationVar) arrayReturnTemp);
            return locationArray;
        }
        else{

            LlLocation indexExpressionTemp = this.elementIndex.generateLlIr(builder, symbolTable);
            LlLocationArray locationArray = new LlLocationArray(this.varName.getValue(), ((LlLocationVar)indexExpressionTemp));
            return locationArray;
        }




    }
}
