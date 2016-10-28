package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrLocationArray extends IrLocation {
    private final IrExpr elementIndex;

    public IrLocationArray(IrExpr elementIndex, IrIdent varName, int lineNumber, int colNumber) {
        super(varName, lineNumber, colNumber);
        this.elementIndex = elementIndex;
    }

    @Override
    public IrType getExpressionType() {
        return this.varType;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) verify that the IrExpr is semantically correct
        errorMessage += this.elementIndex.semanticCheck(scopeStack);

        // 2) make sure the array has been declared already
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.getLocationName().getValue())) {

            // 3) make sure that var is an array (and not a method or non-array)
            Ir object = scopeStack.getSymbol(this.getLocationName().getValue());
            if (!(object instanceof IrFieldDeclArray)) {
                errorMessage += "Non-array variable be accessed as an array" +
                        " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
            }
            else {
                IrFieldDeclArray array = (IrFieldDeclArray) object;

                // IMPORTANT: set the IrType of the IrLocationArray
                this.setLocationType(array.getType());
            }
        }
        else {
            errorMessage += "Array variable used before declared" +
                    " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
        }

        // 4) make sure that the IrExpr offset is an IrTypeInt
        if (!(this.elementIndex.getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "Element offset must be of type int" +
                    " line: " + this.elementIndex.getLineNumber() + " col: " + this.elementIndex.getColNumber() + "\n";
        }

        return errorMessage;
    }
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof IrLocation)) {
            return false;
        }
        return (this.getLocationName().equals(((IrLocation) that).getLocationName()));
    }

     /*
        adds a block of code to the assembly builder and return a location on the stack where the
        requested array element resides.
     */
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        // first compute the index and get the stackLocation where it might be stored.
        this.elementIndex.generateCode(assembly, register, stackFrame);
        String computedIndex = assembly.getFootNote();
        // The error codes will have special labels.
        String errorLable = ".OUT_OF_RANGE";
        // make the bounds check.
        String startLocation = stackFrame.getIrLocation(this.getLocationName()); // the size of teh array is stored in the first location in the array block.

        // first make sure the computedIndex is greater than zero
        assembly.addLine("mov " + computedIndex + ", %r10");
        assembly.addLine("mov $0, %r11");
        assembly.addLine("cmovl %r10, %r11");
        assembly.addLine("mov $1, %r10");
        assembly.addLine("cmp %r10, %r11");
        assembly.addLine("jmp " + errorLable);
        assembly.addLine();

        // then make sure the index is less than the length of the array
        assembly.addLine("mov " + computedIndex + ", %r10");
        assembly.addLine("mov "+ startLocation +", %r11");
        assembly.addLine("cmovge %r10, %r11");
        assembly.addLine("mov $1, %r10");
        assembly.addLine("cmp %r10, %r11");
        assembly.addLine("jmp " + errorLable);
        assembly.addLine();

        // multiply the index by 8 and add it to the start location of the array.

        assembly.addLine("mov " + computedIndex + ", %r10");
        assembly.addLine("mov " + startLocation + ", %r11");
        assembly.addLine("mov (%r10, %r11, 8), %r11");
        assembly.addLine("");

        // Store the found location in the stackFrame and return it.
        String stackLocation = stackFrame.getNextStackLocation();
        stackFrame.pushToRegisterStackFrame("%r11");
        assembly.addLine("mov %r11, " + stackLocation);
        assembly.addLine("");
        assembly.putOnFootNote(stackLocation);

        return assembly;
    }
}
