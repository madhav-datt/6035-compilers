package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

import java.util.ArrayList;
import java.util.HashMap;

public class IrProgram extends Ir{

    private ScopeStack scopeStack;
    private ArrayList<IrFieldDecl> fieldDecls;
    private ArrayList<IrMethodDecl> methodDecls;
    private ArrayList<IrExternDecl> externDecls;

    public void addFieldDecl(IrFieldDecl field) { this.fieldDecls.add(field); }
    public void addMethodDecl(IrMethodDecl method) {
        this.methodDecls.add(method);
    }
    public void addExternDecl(IrExternDecl extern) {
        this.externDecls.add(extern);
    }

    public IrProgram(ScopeStack scopeStack, int lineNumber, int colNumber) {
        super(lineNumber, colNumber);
        this.scopeStack = scopeStack;
        this.fieldDecls = new ArrayList<IrFieldDecl>();
        this.methodDecls = new ArrayList<IrMethodDecl>();
        this.externDecls = new ArrayList<IrExternDecl>();
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) check that no field_decls are declared twice in same scope
        HashMap<String, IrFieldDecl> fieldsHashMap = new HashMap<>();
        for (IrFieldDecl fieldDecl: this.fieldDecls) {
            if (fieldsHashMap.containsKey(fieldDecl.getName())) {
                errorMessage += "Duplicate field_decl in same scope __filename__"+
                        " line: "+fieldDecl.getLineNumber() + " col: " +fieldDecl.getColNumber() + "\n";
            }
            fieldsHashMap.put(fieldDecl.getName(), fieldDecl);

            // check that each fieldDecl is correct
            errorMessage += fieldDecl.semanticCheck(this.scopeStack);
        }

        // 2) check that no externs are declared twice
        HashMap<String, IrExternDecl> externsHashMap = new HashMap<>();
        for (IrExternDecl externDecl: this.externDecls) {
            if (externsHashMap.containsKey(externDecl.getName())) {
                errorMessage += "Duplicate externs declared __filename__"+
                        " line: "+externDecl.getLineNumber() + " col: " + externDecl.getColNumber() + "\n";
            }
            externsHashMap.put(externDecl.getName(), externDecl);

            // check that each externDecl is correct
            errorMessage += externDecl.semanticCheck(this.scopeStack);
        }

        // 3) check that no methods are declared twice
        HashMap<String, IrMethodDecl> methodsHashmap = new HashMap<>();
        for (IrMethodDecl methodDecl: this.methodDecls) {
            if (externsHashMap.containsKey(methodDecl.getName())) {
                errorMessage += "Duplicate method declaration__filename__"+
                        " line: "+methodDecl.getLineNumber() + " col: " + methodDecl.getColNumber() + "\n";
            }
            methodsHashmap.put(methodDecl.getName(), methodDecl);

            // check that each methodDecl is correct
            errorMessage += methodDecl.semanticCheck(this.scopeStack);
        }

        // 4) check for main() method
        if (!methodsHashmap.containsKey("main")) {
            errorMessage += "No method main() in program\n";
        }
        else { // 5) make sure main() has no parameters
            IrMethodDecl mainMethod = methodsHashmap.get("main");
            if (mainMethod.getParamsList().size() > 0) {
                errorMessage += "main() method cannot have parameters" +
                        "line: " + mainMethod.getLineNumber() + "col: " + mainMethod.getColNumber();
            }
        }

        return errorMessage;
    }
}
