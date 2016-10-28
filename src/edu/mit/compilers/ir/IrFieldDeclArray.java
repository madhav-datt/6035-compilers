package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

public class IrFieldDeclArray extends IrFieldDecl {
    private final int arraySize;

    public IrFieldDeclArray(int arraySize, IrIdent name, IrType fieldType) {
        super(name, fieldType);
        this.arraySize = arraySize;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // make sure that the array size is greater than 0
        if (arraySize <= 0) {
            errorMessage = "Array size must be a non-zero positive integer" +
                    " line: " + this.getLineNumber() + " col: " + this.getColNumber();
        }

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        return assembly;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--arrayDecl\n";
        prettyString += ("  " + indentSpace + "|--name: " + this.getName() + "\n");
        prettyString += this.getType().prettyPrint("  " + indentSpace);
        prettyString += ("  " + indentSpace + "|--size: " + this.arraySize + "\n");

        return prettyString;
    }
}