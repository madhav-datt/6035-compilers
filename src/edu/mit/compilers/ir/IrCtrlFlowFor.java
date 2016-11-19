package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowFor extends IrCtrlFlow {
    private final IrLocationVar counter;
    private final IrAssignStmt compoundAssignStmt;
    private final IrExpr intialIndexExpr;

    public IrCtrlFlowFor(IrLocationVar counter, IrExpr intialIndexerExpr, IrAssignStmt updateCounterStmt,
                      IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
        this.counter = counter;
        this.compoundAssignStmt = updateCounterStmt;
        this.intialIndexExpr = intialIndexerExpr;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // create the scope for the loop
        scopeStack.createNewLoopScope();

        // 1) verify the IrLocationVar
        errorMessage += this.counter.semanticCheck(scopeStack);

        // 2) verify that locationVar is not an array
        Ir object = scopeStack.getSymbol(this.counter.getLocationName().getValue());
        if (object instanceof IrFieldDeclArray) {
            errorMessage += "For loop index cannot be an array variable" +
                    " line: "+this.counter.getLineNumber() + " col: " +this.counter.getColNumber() + "\n";
        }

        // 3) make sure that initialIndexExpr is semantically correct
        errorMessage += this.intialIndexExpr.semanticCheck(scopeStack);

        // 4) verify that the initialIndexExpr is IrTypeInt
        if (!(this.intialIndexExpr.getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "Initial index value in for loop must be type int" +
                    " line: "+this.intialIndexExpr.getLineNumber() + " col: " +this.intialIndexExpr.getColNumber() + "\n";
        }

        // 5) verify that the condition is semantically correct
        errorMessage += this.condExpr.semanticCheck(scopeStack);

        // 3) verify that the condition is IrTypeBool
        if (!(this.condExpr.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "For loop condition does not evaluate to bool" +
                    " line: "+this.condExpr.getLineNumber() + " col: " +this.condExpr.getColNumber() + "\n";
        }

        // 4) verify the compoundAssignStmt
        errorMessage += this.compoundAssignStmt.semanticCheck(scopeStack);

        // 5) verify that the for-block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the current loop scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--forLoop\n";

        // print the counter initialize stmt
        prettyString += ("  " + indentSpace + "|--counter\n");
        prettyString += (this.counter.prettyPrint("    " + indentSpace));
        prettyString += ("    " + indentSpace + "|--=\n");
        prettyString += (this.intialIndexExpr.prettyPrint("    " + indentSpace));

        // print the condition expr
        prettyString += ("  " + indentSpace + "|--condExpr\n");
        prettyString += (this.condExpr.prettyPrint("    " + indentSpace));

        // print the compound assign stmt
        prettyString += ("  " + indentSpace + "|--compoundExpr\n");
        prettyString += (this.compoundAssignStmt.prettyPrint("    " + indentSpace));

        // print the for loop body
        prettyString += "  " + indentSpace + "|--body\n";
        prettyString += this.stmtBody.prettyPrint("    " + indentSpace);

        return prettyString;
    }
}
