package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationVar extends IrLocation {
    protected Ir irDeclObject; // IrFieldDeclArray, IrFieldDeclVar, IrParamDecl

    public IrLocationVar(IrIdent varName, int lineNumber, int colNumber) {
        super(varName, lineNumber, colNumber);
    }

    protected void setIrDecl(Ir irDeclObject) {
        this.irDeclObject = irDeclObject;
    }

    public Ir getIrDecl() {
        return this.irDeclObject;
    }

    @Override
    public IrType getExpressionType() {
        return this.varType;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) make sure the variable has been declared already
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.getLocationName().getValue())) {
            Ir object = scopeStack.getSymbol(this.getLocationName().getValue());

            // make sure that the identifier is a field, param, or array (but not a method)
            if (object instanceof IrFieldDeclVar) {
                IrFieldDecl var = (IrFieldDecl) object;

                // IMPORTANT: set the IrType of the IrLocationVar
                this.setLocationType(var.getType());

                // IMPORTANT: set the IrDecl of the IrLocationVar
                this.setIrDecl(object);
            }
            else if (object instanceof IrParamDecl) {
                IrParamDecl var = (IrParamDecl) object;

                // IMPORTANT: set the IrType of the IrLocationVar
                this.setLocationType(var.getParamType());

                // IMPORTANT: set the IrDecl of the IrLocationVar
                this.setIrDecl(object);
            }
            else if (object instanceof IrFieldDeclArray) {
                IrFieldDeclArray arrayObject = (IrFieldDeclArray) object;

                // IMPORTANT: set the IrType of the IrLocationVar
                this.setLocationType(arrayObject.getType());

                // IMPORTANT: set the IrDecl of the IrLocationVar
                this.setIrDecl(arrayObject);

                errorMessage += "Invalid method call or array assignment" +
                        " line: " + this.getLocationName().getLineNumber() + " col: " + this.getLocationName().getColNumber() + "\n";
            }
        }
        else {
            errorMessage += "Variable used before declared" +
                    " line: " + this.getLocationName().getLineNumber() + " col: " + this.getLocationName().getColNumber() + "\n";
        }

        return errorMessage;
    }

    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (that instanceof IrArgExpr) {
            // if that is an instance of IrLocationVar
            if(((IrArgExpr) that).getArgValue() instanceof IrLocation)
                return this.getLocationName().equals(((IrLocationVar) ((IrArgExpr) that).getArgValue()).getLocationName());
        }
        if (that instanceof IrLocationVar) {
            Ir otherIr = (IrLocationVar) that;
            return (this.getLocationName().equals(((IrLocationVar) otherIr).getLocationName()));

        }
        return false;
    }


    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--locationVar\n";

        // pretty print name
        prettyString += "  " + indentSpace + "|--name: " + this.varName.getValue() + "\n";

        // pretty print the type
        prettyString += this.varType.prettyPrint("  " + indentSpace);

        return prettyString;
    }
}
