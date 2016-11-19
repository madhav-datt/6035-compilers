package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowWhile extends IrCtrlFlow{
    public IrCtrlFlowWhile(IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // create the scope for the while loop
        scopeStack.createNewLoopScope();

        // 1) verify that the expression is valid
        errorMessage += this.condExpr.semanticCheck(scopeStack);

        // 2) make sure that the condition expression is IrTypeBool
        if (!(this.condExpr.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "Condition for while-statement must be a boolean" +
                    " line: "+this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        // 3) verify that the while block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the scope for the while loop
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--whileLoop\n";

        // print the condition expr
        prettyString += ("  " + indentSpace + "|--condExpr\n");
        prettyString += (this.condExpr.prettyPrint("    " + indentSpace));

        // print the for loop body
        prettyString += "  " + indentSpace + "|--body\n";
        prettyString += this.stmtBody.prettyPrint("    " + indentSpace);

        return prettyString;
    }
}
