package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

import java.util.List;

/**
 * Created by devinmorgan on 10/22/16.
 */
public class IrMethodCallStmt extends IrStatement{
    private final IrIdent methodName;
    private final List<IrArg> argsList;
    private IrType methodType;

    public IrMethodCallStmt(IrIdent methodName, List<IrArg> argsList) {
        super(methodName.getLineNumber(), methodName.getColNumber());
        this.methodName = methodName;
        this.argsList = argsList;
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
                        Object unsafeArg = argsList.get(i); // i.e. a STRING

                        if (unsafeArg instanceof IrArgExpr) {
                            IrArgExpr safeArg = (IrArgExpr) unsafeArg;

                            // check that the IrArgExpr is semantically correct
                            errorMessage += safeArg.semanticCheck(scopeStack);

                            // 3) check that each argument and param types match
                            if (param.getParamType() != null && safeArg.getArgumentType() != null) {
                                if (!param.getParamType().getClass().equals(safeArg.getArgumentType().getClass())) {
                                    errorMessage += "Argument and parameter types don't match" +
                                            " line: " + safeArg.getLineNumber() + " col: " + safeArg.getColNumber() + "\n";
                                }
                            }

                            // 4) check that the argument is not an array_location
                            if (safeArg.getArgValue() instanceof IrLocation) {
                                IrLocation locArg = (IrLocation) safeArg.getArgValue();
                                Ir possibleArray = scopeStack.getSymbol(locArg.getLocationName().getValue());

                                if (possibleArray instanceof IrFieldDeclArray) {
                                    errorMessage += "Argument cannot be an array location " +
                                            " line: " + safeArg.getLineNumber() + " col: " + safeArg.getColNumber() + "\n";
                                }
                            }
                        }
                        else {
                            errorMessage += "Invalid argument" +
                                    " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
                        }
                    }
                }
                else {
                    errorMessage += "Wrong number of arguments passed to function" +
                            " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
                }

                // IMPORTANT: set the IrType of the IrMethodCallExpr
                this.methodType = methodDecl.getType();
            }
            // for an extern method_decl
            else if (object instanceof IrExternDecl) {

                // IMPORTANT: set the IrType of the IrMethodCallExpr
                this.methodType = new IrTypeInt(this.methodName.getLineNumber(), this.methodName.getColNumber());
            }
            // for non-method identifiers
            else {
                errorMessage += "Non-method identifier being called as a method" +
                        " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
            }
        }
        else {
            errorMessage += "Method called before declared" +
                    " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
        }

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        String asm = "";
        String methodName = this.methodName.getValue();
        String registers[] = register.getParamRegisters();
        for(int i = 0; i < argsList.size(); i++){
            String irLocation = stackFrame.getIrLocation(argsList.get(i));
            if(i < 6){
                // find where the argument is stored in the stackFrame. The argument might be
                // a constant or an expression
                asm += "     mov " + irLocation + ", " + registers[i]+ "\n";
            }
            else{
                asm += "     mov " + irLocation + ", " + "%r10"+ "\n";
                String nextStackFrameLocation = stackFrame.getNextStackLocation();
                asm += "     mov %r10, " + nextStackFrameLocation + "\n";

            }
//            stackFrame.pushToStackFrame(argsList.get(i));
        }
        asm  += "     call " + methodName + "\n";
        asm  += "     mov %rax, " + stackFrame.getNextStackLocation()+ "\n";
        stackFrame.pushToRegisterStackFrame("%rax");
        assembly.addLine(0, asm);
        return  assembly;

    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|__methodCallStmt\n";

        // print the method name
        prettyString += ("  " + indentSpace + "|__name: " + this.methodName.getValue() + "\n");

        // print the method type
        prettyString += this.methodType.prettyPrint("  " + indentSpace);

        // print the method args_list
        prettyString += ("  " + indentSpace + "|__argsList:\n");
        for (IrArg arg: this.argsList) {
            prettyString += arg.prettyPrint("    " + indentSpace);
        }

        return prettyString;
    }
}
