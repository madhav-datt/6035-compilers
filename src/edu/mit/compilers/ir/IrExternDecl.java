package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

public class IrExternDecl extends IrMemberDecl {
    public IrExternDecl(IrIdent name) {
        super(name, new IrTypeInt(name.getLineNumber(), name.getColNumber()), name.getColNumber(), name.getColNumber());
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        return "";
    }

    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        return assembly;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--" + this.getName() + "\n";
        return prettyString;
    }
}