package edu.mit.compilers.ir;

import edu.mit.compilers.LlBuilder;
import edu.mit.compilers.ll.LlLocationVar;

public abstract class IrMemberDecl extends Ir {
    private final IrIdent name;
    private final IrType type;

    public IrMemberDecl(IrIdent name, IrType type, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name.getValue();
    }
    public IrIdent getIdentName() {
        return this.name;
    }



    public IrType getType() {
        return this.type;
    }
}