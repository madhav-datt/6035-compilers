package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

public class IrSizeOfLocation extends IrSizeOf {
    private final IrIdent fieldName;
    private Ir irDecl; // IrFieldDeclArray, IrFieldDeclVar, IrParamDecl

    public IrSizeOfLocation(IrIdent fieldName, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldName = fieldName;
    }

    @Override
    public IrType getExpressionType() {
        return new IrTypeInt(this.fieldName.getLineNumber(), this.fieldName.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) make sure the variable has been declared already
        if (scopeStack.checkIfSymbolExistsAtAnyScope(this.fieldName.getValue())) {
            Ir object = scopeStack.getSymbol(this.fieldName.getValue());

            // 2) make sure that argument is a var (and not a method)
            if (!(object instanceof IrFieldDecl) && !(object instanceof IrFieldDecl)) {
                errorMessage += "Argument for sizeof is not a type, variable, or array" +
                        " line: " + this.fieldName.getLineNumber() + " col: " + this.fieldName.getColNumber() + "\n";
            }
            else {
                // IMPORTANT: set the irDecl for the IrSizeOfLocation
                this.irDecl = object;
            }
        }
        else {
            errorMessage += "Argument in sizeof hasn't been declared" +
                    " line: " + this.fieldName.getLineNumber() + " col: " + this.fieldName.getColNumber() + "\n";
        }

        return errorMessage;
    }
    public String getTypeString(){
        if(this.irDecl instanceof IrFieldDeclArray){
            return "array";
        }
        if(this.irDecl instanceof IrFieldDeclVar){
            if(((IrFieldDeclVar) this.irDecl).getType() instanceof IrTypeInt){
                return "int";
            }
            if(((IrFieldDeclVar) this.irDecl).getType() instanceof IrTypeBool){
                return "bool";
            }

        }
        if(this.irDecl instanceof IrParamDecl){
            if(((IrParamDecl) this.irDecl).getParamType() instanceof IrTypeInt){
                return "int";
            }
            if(((IrParamDecl) this.irDecl).getParamType() instanceof IrTypeBool){
                return "bool";
            }
        }

        return "void";
    }

    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        String type = this.getTypeString();

        switch (type){
            case "array":
                Ir arrayType = ((IrFieldDeclArray)this.irDecl).getType();
                int arraySize = ((IrFieldDeclArray)this.irDecl).getArraySize();
                String arraySizeStr;
                if(arrayType instanceof IrTypeBool)
                {
                    arraySizeStr = "$" +Integer.toString(arraySize);
                    assembly.addLine("movq "+ arraySizeStr + ", %r11");

                }
                if(arrayType instanceof IrTypeInt)
                {
                    arraySizeStr = "$" + Integer.toString(arraySize*8);
                    assembly.addLine("movq "+ arraySizeStr + ", %r11");
                }

                break;

            case "bool":
                assembly.addLine("movq $1, %r11");
                break;
            case "int":
                assembly.addLine("movq $8, %r11");
                break;

        }
        String stackLocation = stackFrame.getNextStackLocation();
        stackFrame.pushToRegisterStackFrame("%r11");
        assembly.addLine("movq %r11, " + stackLocation);
        assembly.putOnFootNote(stackLocation);
        assembly.addLine("");
        return assembly;

    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--sizeOfLocation\n";

        // pretty print the location
        prettyString += ("  " + indentSpace + "|--name: " + this.fieldName.getValue() + "\n");

        return prettyString;
    }
}