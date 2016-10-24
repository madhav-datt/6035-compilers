package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public class IrExprSizeOfType extends IrExprSizeOf {
    private final IrType type;

    public IrExprSizeOfType(IrType type) {
        super(type.getLineNumber(), type.getColNumber());
        this.type = type;
    }

    @Override
    public IrType getExpressionType() {
        return this.type;
    }
    // Returns a constant ($<int>)
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        System.out.println("In: " + this.getClass());
        String assembly = (type instanceof  IrTypeInt ? "$8" : "$1");
        assemblySoFar.append(assembly);
        return assembly;
    }
}
