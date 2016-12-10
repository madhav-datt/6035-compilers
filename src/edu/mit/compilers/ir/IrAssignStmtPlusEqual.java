package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.LlAssignStmtBinaryOp;
import edu.mit.compilers.ll.LlAssignStmtRegular;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtPlusEqual extends IrAssignStmt{
    private final IrExpr incrementBy;

    public IrAssignStmtPlusEqual(IrLocation storeLocation, IrExpr incrementBy) {
        super(storeLocation);
        this.incrementBy = IrExpr.canonicalizeExpr(incrementBy);
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
                    errorMessage += "Can't use += on the array itself" +
                            " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
                }
            }
        }

        // 3) verify that the expr is semantically correct
        errorMessage += this.incrementBy.semanticCheck(scopeStack);

        // 4) make sure that the IrExpr and IrLocation are IrTypeInt
        if (!((this.incrementBy.getExpressionType() instanceof IrTypeInt) &&
                this.getStoreLocation().getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "The variable and expression of += must be of type int" +
                    " line: " + this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--assignStmtPlusEquals\n";

        // pretty print the lhs
        prettyString += ("  " + indentSpace + "|--lhs\n");
        prettyString += this.getStoreLocation().prettyPrint("    " +indentSpace);

        // print the rhs
        prettyString += ("  " + indentSpace + "|--rhs\n");
        prettyString += this.incrementBy.prettyPrint("    " + indentSpace);

        return prettyString;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        LlLocation decrementTemp = this.incrementBy.generateLlIr(builder, symbolTable);
        LlLocationVar minusExprTemp = builder.generateTemp();
        LlAssignStmtBinaryOp assignStmtBinaryOp = new LlAssignStmtBinaryOp(minusExprTemp, new LlLocationVar(this.getStoreLocation().getLocationName().getValue()), " + ", decrementTemp);
        builder.appendStatement(assignStmtBinaryOp);
        LlAssignStmtRegular regularAssignment = new LlAssignStmtRegular(new LlLocationVar(this.getStoreLocation().getLocationName().getValue()),  minusExprTemp);
        builder.appendStatement(regularAssignment);
        return null;
    }
}
