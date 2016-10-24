package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

public class IrExprSizeOfLocation extends IrExprSizeOf {
    private final IrFieldDecl field;

    public IrExprSizeOfLocation(IrFieldDecl field, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.field = field;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.field.getLineNumber(), this.field.getColNumber());
    }
    public String generateCode(StringBuilder assemblySoFar, LocalVariableTable table){
        String assembly = "";
        System.out.println("In: " + this.getClass());
        if(this.field instanceof IrFieldDeclInt){
            assembly = "$8";
        }
        else if(this.field instanceof IrFieldDeclBool){
            assembly = "$1";
        }
        else if(this.field instanceof IrFieldDeclArray) {
            if (this.field.getType() instanceof IrTypeInt) {
                assembly = "$" + Integer.toString(((IrFieldDeclArray) this.field).getArraySize() * 8);
            }
            if (this.field.getType() instanceof IrTypeBool) {
                assembly = "$" + Integer.toString(((IrFieldDeclArray) this.field).getArraySize());
            }
        }
        assemblySoFar.append(assembly + "\n");
        return assembly;
    }
}