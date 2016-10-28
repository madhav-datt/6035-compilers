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

    public int getArraySize(){
        return arraySize;
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
        // Allocate a contagious block of memory for use by the array.

        String start = stackFrame.getNextStackLocation();

        assembly.addLine("movq $," + Integer.toString(this.arraySize)+" %r10");
        assembly.addLine("movq %r10, " + start);
        stackFrame.pushToStackFrame(this.getIdentName());
        for(int i = 0; i < this.arraySize; i ++){
            String nextLocation = stackFrame.getNextStackLocation();
            assembly.addLine("movq $0, %r10");
            assembly.addLine("movq %r10, " + nextLocation);
            stackFrame.pushToRegisterStackFrame("$0");
        }

        return assembly;
    }
}