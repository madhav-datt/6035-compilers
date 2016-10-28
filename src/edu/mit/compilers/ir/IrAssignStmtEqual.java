package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

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

    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        String asm = "";
        // compute the value of the expression and figure out where it is stored
        String exprResultStorageLoc = this.newValue.generateCode(assembly, register, stackFrame).getFootNote();
        asm  += "mov " + exprResultStorageLoc + " , %r10" + "\n";
        String nextStackLocation = stackFrame.getNextStackLocation();
        asm += "mov %r10, " + nextStackLocation+"\n";
        // make sure to store the identity of the variable which is just assigned value.
        stackFrame.pushToStackFrame(this.getStoreLocation());
        assembly.addLine(0, asm);
        assembly.putOnFootNote(nextStackLocation);
        return assembly;
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
