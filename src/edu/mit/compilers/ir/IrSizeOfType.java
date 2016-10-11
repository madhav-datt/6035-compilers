package edu.mit.compilers.ir;

public class IrSizeOfType extends IrSizeOf {
    private final IrTypeVar type;

    public IrSizeOfType(IrTypeVar type) {
        super(type.getLineNumber(), type.getColNumber());
        this.type = type;
    }
}