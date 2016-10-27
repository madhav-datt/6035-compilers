package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

public class IrLiteralChar extends IrLiteral {
    private final char value;

    public IrLiteralChar(char value, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.value = value;
    }

    @Override
    public IrType getExpressionType() {
        // it's definitely not of type void but it
        // is also not of type int or type bool
        return new IrTypeVoid(this.getLineNumber(), this.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        return assembly;
    }
}