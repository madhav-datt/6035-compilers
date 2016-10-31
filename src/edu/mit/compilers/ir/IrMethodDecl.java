package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

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

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // give method a new method scope with method returnType
        scopeStack.createNewMethodScope(this.getType());

        // check the params before the code block
        for (IrParamDecl paramDecl : this.paramsList) {

            // 1) make sure no duplicate params are declared
            if (scopeStack.checkIfSymbolExistsAtCurrentScope(paramDecl.getParamName().getValue())) {
                errorMessage += "Duplicate declaration of parameters in method declaration" +
                        " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
            }
            scopeStack.addObjectToCurrentScope(paramDecl.getParamName().getValue(), paramDecl);

            // 2) check that all the IrParamDecls are valid
            errorMessage += paramDecl.semanticCheck(scopeStack);
        }

        // 3) check that the codeBlock is valid
        errorMessage += this.methodBody.semanticCheck(scopeStack);

        // delete method scope the scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }

    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        // Construct the method body
        // Access the params and stuff.
        String methodName = this.getName();
        String paramRegisters[] = register.getParamRegisters();
        int m =  paramsList.size();

        AssemblyBuilder asb = new AssemblyBuilder();
        StackFrame frame = new StackFrame();
        this.methodBody.generateCode(asb, register, frame);
        String enterStatement = "enter $" + Integer.toString(8*frame.getStackSize()) + ", $0";
        if(methodName.equals("main")){
            assembly.addLine(".globl main");
            String errorLable = ".OUT_OF_RANGE";
            String errorStringLabel = ".STR_OUT_OF_RANGE";
            assembly.addLabel(errorLable);
            assembly.appendLableToBottom(errorStringLabel);
            assembly.appendLineToBottom(".string \"Argument Out of range!! \"");
            assembly.addLine("movq $"+ errorStringLabel + ", %r10");
            assembly.addLine("movq %r10, %rdi");
            assembly.addLine("call printf");

            assembly.addLine("leave");
            assembly.addLine("ret");
        }
        assembly.addLabel(methodName);
        assembly.addLine();
        assembly.addLine(enterStatement);
        for(int i = 0; i < paramsList.size(); i++){
            if(i < 6){
                assembly.addLine("movq "+ paramRegisters[i] + ", " + stackFrame.getNextStackLocation()+"\n");
                stackFrame.pushToRegisterStackFrame(paramRegisters[i]);
            }
            else{
                assembly.addLine("movq "+ Integer.toString(16 + (i-6)) +"(%rbp), " + "%r10");
                assembly.addLine("movq %r10, " + stackFrame.getNextStackLocation());
                stackFrame.pushToStackFrame(paramsList.get(i));
            }
        }
        assembly.concat(asb);

        assembly.addLine("leave");
        assembly.addLine("ret");
        assembly.addLine();

        // Handle error messages




        return assembly;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = indentSpace + "|--method\n";

        // print the name
        prettyString += ("  " + indentSpace + "|--name: " + this.getName() + "\n");

        // print the params
        prettyString += ("    " + indentSpace + "|--paramsList:\n");
        for (IrParamDecl paramDecl: this.paramsList) {
            prettyString += paramDecl.prettyPrint("      " + indentSpace);
        }

        // print the block
        prettyString += this.methodBody.prettyPrint("    " + indentSpace);

        return prettyString;
    }
}