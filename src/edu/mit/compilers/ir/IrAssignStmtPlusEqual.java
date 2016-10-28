package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtPlusEqual extends IrAssignStmt{
    private final IrExpr incrementBy;

    public IrAssignStmtPlusEqual(IrLocation storeLocation, IrExpr incrementBy) {
        super(storeLocation);
        this.incrementBy = incrementBy;
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
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        String registerLocation = stackFrame.getIrLocation(this.getStoreLocation().getLocationName());
        assembly.addLine("mov "+ registerLocation+ ", %r10");
        this.incrementBy.generateCode(assembly, register, stackFrame);
        String expressionResult = assembly.getFootNote();
        assembly.addLine("add "+ expressionResult+ ", %r10");
        assembly.addLine("mov %r10, " + registerLocation);
        return assembly;
    }
}
