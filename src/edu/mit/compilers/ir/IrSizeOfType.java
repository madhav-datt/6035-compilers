package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

public class IrSizeOfType extends IrSizeOf {
    private final IrType type;

    public IrSizeOfType(IrType type) {
        super(type.getLineNumber(), type.getColNumber());
        this.type = type;
    }

    @Override
    public IrType getExpressionType() {
        return this.type;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){


            if(this.type instanceof IrTypeInt){
                assembly.addLine("mov $8, %r11");
            }
            if(type instanceof IrTypeBool){
                assembly.addLine("mov $1, %r11");
            }
        String stackLocation = stackFrame.getNextStackLocation();
        stackFrame.pushToRegisterStackFrame("%r11");
        assembly.addLine("mov %r11, " + stackLocation);
        assembly.putOnFootNote("%r11");
        assembly.addLine();
        return assembly;


    }
}