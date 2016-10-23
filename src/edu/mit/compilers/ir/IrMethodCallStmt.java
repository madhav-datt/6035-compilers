package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

import java.util.List;

/**
 * Created by devinmorgan on 10/22/16.
 */
public class IrMethodCallStmt extends IrStatement{
    private final IrIdent methodName;
    private final List<IrArg> argsList;
    private final IrType methodType;

    public IrMethodCallStmt(IrIdent methodName, IrType methodType, List<IrArg> argsList) {
        super(methodName.getLineNumber(), methodName.getColNumber());
        this.methodName = methodName;
        this.argsList = argsList;
        this.methodType = methodType;
    }

    public IrType getExpressionType() {
        return this.methodType;
    }

    public String toString(){
        return this.methodName + "(" +this.argsList.toString()+ ")";
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check that the method has already been declared
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.methodName.getValue())) {
            Ir object = scopeStack.getSymbol(this.methodName.getValue());

            // for normal method_decl
            if (object instanceof IrMethodDecl) {
                IrMethodDecl methodDecl = (IrMethodDecl) object;

                // 2) check for same number of params
                List<IrParamDecl> declParams = methodDecl.getParamsList();
                if (declParams.size() == this.argsList.size()) {
                    for (int i = 0; i < declParams.size(); i++) {
                        IrParamDecl param = declParams.get(i);
                        Object unsafeArg = argsList.get(i).getArgValue(); // i.e. a STRING

                        if (unsafeArg instanceof IrExpr) {
                            IrExpr safeArg = (IrExpr) unsafeArg;

                            // 3) check that each argument and param types match
                            if (!param.getExpressionType().getClass().equals(safeArg.getExpressionType().getClass())) {
                                errorMessage += "Argument and parameter types don't match" +
                                        " line: " + safeArg.getLineNumber() + " col: " + safeArg.getColNumber() + "\n";
                            }

                            // 4) check that the argument is not an array_location
                            if (safeArg instanceof IrLocation) {
                                IrLocation locArg = (IrLocation) safeArg;
                                Ir possibleArray = scopeStack.getSymbol(locArg.getLocationName().getValue());

                                if (possibleArray instanceof IrFieldDeclArray) {
                                    errorMessage += "Argument cannot be an array location " +
                                            " line: " + safeArg.getLineNumber() + " col: " + safeArg.getColNumber() + "\n";
                                }
                            }
                        }
                        else {
                            errorMessage += "Invalid argumenttype" +
                                    " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
                        }
                    }
                }
            }

            // for an extern method_decl
            if (object instanceof IrExternDecl) {
                // we don't need to check anything here
            }
        }
        else {
            errorMessage += "Method called before declared" +
                    " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
        }

        return errorMessage;
    }
}
