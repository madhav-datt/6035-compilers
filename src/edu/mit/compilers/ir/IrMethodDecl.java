package edu.mit.compilers.ir;

import java.util.*;

public class IrMethodDecl extends IrMemberDecl {
    private final List<IrParamDecl> paramsList;
    private final IrCodeBlock methodBody;

    public IrMethodDecl(IrType returnType, List<IrParamDecl> paramsList,
                        IrCodeBlock methodBody, IrIdent name) {
        super(name, returnType, name.getLineNumber(), name.getColNumber());
        this.paramsList = paramsList;
        this.methodBody = methodBody;
    }

    public List<IrParamDecl> getParamsList() {
        return new ArrayList<>(this.paramsList);
    }
    public String generateCode(int n){
        String assembly = "";
        String methodName = this.getName() + ":\n";
        assembly += methodName;
        assembly += "enter $(8*2), $0 \n"; // what is 2?
        String registers[] = {"%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"};
        int m =  paramsList.size();
        for(int i = 0; i < paramsList.size(); i++){
            assembly += "mov " + registers[i] + ", " + (-8*(m-i)) +  " (%rbp) \n";
        }
        assembly +=  "... \n";//this.methodBody.generateCode();
        assembly += "leave\nret\n";
        return assembly;

    }
}