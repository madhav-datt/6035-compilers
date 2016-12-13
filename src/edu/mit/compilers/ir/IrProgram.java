package edu.mit.compilers.ir;

import edu.mit.compilers.*;
import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IrProgram extends Ir{

    private ArrayList<IrFieldDecl> fieldDecls;
    private ArrayList<IrMethodDecl> methodDecls;

    public ArrayList<IrFieldDecl> getFieldDecls() {
        return fieldDecls;
    }

    public ArrayList<IrMethodDecl> getMethodDecls() {
        return methodDecls;
    }

    public ArrayList<IrExternDecl> getExternDecls() {
        return externDecls;
    }

    private ArrayList<IrExternDecl> externDecls;
    private String errorMessage;
    private ArrayList<LlBuilder> builderList;

    public void addFieldDecl(IrFieldDecl field) { this.fieldDecls.add(field); }
    public void addMethodDecl(IrMethodDecl method) {
        this.methodDecls.add(method);
    }
    public void addExternDecl(IrExternDecl extern) {
        this.externDecls.add(extern);
    }

    public IrProgram(ArrayList<IrFieldDecl> fieldDecls, ArrayList<IrMethodDecl> methodDecls,
                     ArrayList<IrExternDecl> externDecls, String errorMessage, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.fieldDecls = fieldDecls;
        this.methodDecls = methodDecls;
        this.externDecls = externDecls;
        this.errorMessage = errorMessage;
    }

    public HashSet<LlLocation> getGlobalVariables() {
        HashSet<LlLocation> globalVariables = new HashSet<>();

        for (IrFieldDecl globalVar : this.fieldDecls) {
            if (globalVar instanceof IrFieldDeclVar) {
                globalVariables.add( new LlLocationVar(globalVar.getName()) );
            }
            else if (globalVar instanceof IrFieldDeclArray) {
                globalVariables.add( new LlLocationArray(globalVar.getName(), new LlLocationVar("NotAValidName")));
            }
        }

        return globalVariables;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        // initialize the global scope
        scopeStack.createNewBlockScope();

        // 1) check that no field_decls are declared twice in same scope
        for (int i = this.fieldDecls.size()-1; i >=0 ; i--) {
            IrFieldDecl fieldDecl = this.fieldDecls.get(i);
            if (scopeStack.checkIfSymbolExistsInGlobalScope(fieldDecl.getName())) {
                this.errorMessage += "Duplicate field_decl in same scope __filename__"+
                        " line: "+fieldDecl.getLineNumber() + " col: " +fieldDecl.getColNumber() + "\n";
            }
            // check that each fieldDecl is correct
            this.errorMessage += fieldDecl.semanticCheck(scopeStack);

            // add the fieldDecl to the stack
            scopeStack.addObjectToCurrentScope(fieldDecl.getName(), fieldDecl);
        }

        // 2) check that no externs are declared twice
        for (int j = this.externDecls.size()-1; j >=0 ; j--) {
            IrExternDecl externDecl = this.externDecls.get(j);
            if (scopeStack.checkIfSymbolExistsInGlobalScope(externDecl.getName())) {
                this.errorMessage += "Duplicate externs declared __filename__"+
                        " line: "+externDecl.getLineNumber() + " col: " + externDecl.getColNumber() + "\n";
            }
            // check that each externDecl is correct
            this.errorMessage += externDecl.semanticCheck(scopeStack);

            // add the extern to the stack
            scopeStack.addObjectToCurrentScope(externDecl.getName(), externDecl);
        }

        // 3) check that no methods are declared twice
        for (int k = this.methodDecls.size()-1; k >=0 ; k--) {
            IrMethodDecl methodDecl = this.methodDecls.get(k);
            if (scopeStack.checkIfSymbolExistsInGlobalScope(methodDecl.getName())) {
                this.errorMessage += "Duplicate method declared in __filename__" +
                        " line: "+methodDecl.getLineNumber() + " col: " + methodDecl.getColNumber() + "\n";
            }
            // add the method to the stack
            scopeStack.addObjectToCurrentScope(methodDecl.getName(), methodDecl);

            // check that each methodDecl is correct
            this.errorMessage += methodDecl.semanticCheck(scopeStack);
        }

        // 4) check for main() method
        if (scopeStack.checkIfSymbolExistsInGlobalScope("main")) {
            Ir object = scopeStack.getSymbol("main");

            if (object instanceof IrMethodDecl) {
                IrMethodDecl mainMethod = (IrMethodDecl) object;

                // 5) make sure main() has no parameters
                if (mainMethod.getParamsList().size() > 0) {
                    this.errorMessage += "main() method cannot have parameters" +
                            " line: "+ mainMethod.getLineNumber() + " col: " + mainMethod.getColNumber() + "\n";
                }

                // 6) make sure main() is IrTypeVoid
                if (!(mainMethod.getType() instanceof IrTypeVoid)) {
                    this.errorMessage += "main() must be of type void" +
                            " line: "+ mainMethod.getLineNumber() + " col: " + mainMethod.getColNumber() + "\n";
                }
            }
            else { this.errorMessage += "There must be a void main() method with no parameters ";}
        }
        else {this.errorMessage += "Program must contain a main() method";}

        return this.errorMessage;
    }

    @Override
    public LlLocation generateLlIr(LlBuilder builder, LlSymbolTable symbolTable) {
        return null;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        // program header
        String prettyString = "IrProgram:\n";

        // pretty print extern_decls
        prettyString += "|--externs:\n";
        for (IrExternDecl externDecl: this.externDecls) {
            prettyString += externDecl.prettyPrint("  " + indentSpace);
        }

        // pretty print field_decls
        prettyString += "|--fields:\n";
        for (IrFieldDecl fieldDecl: this.fieldDecls) {
            prettyString += fieldDecl.prettyPrint("  " + indentSpace);
        }

        // pretty print method_decls
        prettyString += "|--methods:\n";
        for (IrMethodDecl methodDecl: this.methodDecls) {
            prettyString += methodDecl.prettyPrint("  " + indentSpace);
        }

        return prettyString;
    }

    public LlBuildersList getLlBuilders(){
        LlBuildersList buildersList = new LlBuildersList();

        for (IrMethodDecl methodDecl: this.methodDecls) {
            LlBuilder llBuilder = new LlBuilder(methodDecl.getName());
            llBuilder.params = new ArrayList<>();

            for (IrParamDecl paramDecl : methodDecl.getParamsList()) {
                LlLocationVar paramVar = new LlLocationVar(paramDecl.getParamName().getValue());
                llBuilder.params.add(paramVar);
            }

            LlSymbolTable llSymbolTable = new LlSymbolTable(methodDecl.getName());
            methodDecl.generateLlIr(llBuilder, llSymbolTable);
            buildersList.addBuilder(llBuilder);
            buildersList.addSymbolTable(llSymbolTable);
        }

        for(IrFieldDecl fieldDecl : this.fieldDecls){
            if(fieldDecl instanceof IrFieldDeclArray){

                buildersList.addToGlobalArrays(new LlLocationVar(fieldDecl.getName()), ((IrFieldDeclArray) fieldDecl).getArraySize());
            }
            if(fieldDecl instanceof IrFieldDeclVar){

                buildersList.addToGlobalVars(new LlLocationVar(fieldDecl.getName()));
            }
        }
        return buildersList;
    }
    public HashMap<String, ArrayList<LlLocationVar>> getMethodArgs(){
        HashMap<String, ArrayList<LlLocationVar>> table = new HashMap<>();
        for(IrMethodDecl decl : this.methodDecls){
            ArrayList<LlLocationVar> methodArgs = new ArrayList<>();
            for(IrParamDecl param: decl.getParamsList()){
                LlLocationVar thisParam = new LlLocationVar(param.getParamName().getValue());
                methodArgs.add(thisParam);
            }
        table.put(decl.getName(), methodArgs);
        }
        return table;
    }

    public ArrayList<LlBuilder> getBuilderList() {
        return this.getLlBuilders().getBuilders();
    }
}
