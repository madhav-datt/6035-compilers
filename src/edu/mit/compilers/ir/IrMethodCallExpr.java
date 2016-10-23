package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

import java.util.*;

public class IrMethodCallExpr extends IrExpr{
    private final IrIdent methodName;
    private final List<IrArg> argsList;
    private final IrType methodType;

    public IrMethodCallExpr(IrIdent methodName, IrType methodType, List<IrArg> argsList) {
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
                        IrArg arg = argsList.get(i);

                        // 3) check that each argument and param types match
                        if (!param.getExpressionType().getClass().
                                equals(arg.getArgValue().getClass())) {
                            errorMessage += "Argument doesn't match param type" +
                                    " line: " + arg.getLineNumber() + " col: " + arg.getColNumber() + "\n";
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