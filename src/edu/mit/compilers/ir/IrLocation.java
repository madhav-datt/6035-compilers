package edu.mit.compilers.ir;

import edu.mit.compilers.ir.Ir;
import edu.mit.compilers.ir.IrIdent;

/**
 * Created by devinmorgan on 10/5/16.
 */
public abstract class IrLocation extends IrExpr {
    protected IrIdent varName;
    protected IrType varType;

    public IrLocation(IrIdent varName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.varName = varName;
    }

    public IrIdent getLocationName() {
        return this.varName;
    }
    public IrType getLocationType() {
        return this.varType;
    }

    protected void setLocationType(IrType type) {
        this.varType = type;
    }
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof IrLocation)) {
            return false;
        }
        Ir otherIr = (IrLocation)that;
        return (this.getLocationName().equals(((IrLocation)otherIr).getLocationName())) && (this.getLocationType().equals(((IrLocation)otherIr).getLocationType()));
    }
}
