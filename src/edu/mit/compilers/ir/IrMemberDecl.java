package edu.mit.compilers.ir;

public abstract class IrMemberDecl extends Ir {
    private final IrIdent name;
    private final IrType type;

    public IrMemberDecl(IrIdent name, IrType type, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.name = name;
        this.type = type;
    }
    public IrIdent getIdent(){
        return this.name;
    }
    public String getName() {
        return this.name.getValue();
    }

    public IrType getType() {
        return this.type;
    }

//    public String generateCode(int n){
//        String assembly = "";
//        String methodName = this.getName() + ":";
//        assembly += methodName;
//        assembly += "enter $(8*2), $0"; // what is 2?
//        assembly += "mov ";
//        return assembly;
//
//    }
}