package edu.mit.compilers.ir;

import edu.mit.compilers.LocalVariableTable;

import java.util.ArrayList;

public class IrProgram extends Ir{
//    public Ir
   private ArrayList<IrFieldDecl> fieldDecls;
   private ArrayList<IrMethodDecl> methodDecls;
   private ArrayList<IrExternDecl> externDecls;

    public void addFieldDecl(IrFieldDecl field) {
        this.fieldDecls.add(field);
    }

    public void addMethodDecl(IrMethodDecl method) {
        this.methodDecls.add(method);
    }

    public void addExternDecl(IrExternDecl extern) {
        this.externDecls.add(extern);
    }

   public IrProgram(int lineNumber, int colNumber) {
       super(lineNumber, colNumber);
       this.fieldDecls = new ArrayList<IrFieldDecl>();
       this.methodDecls = new ArrayList<IrMethodDecl>();
       this.externDecls = new ArrayList<IrExternDecl>();
   }
    // The initial entry point for code generation.
    // returns the assembly code on exit.
    public StringBuilder generateCode(StringBuilder assemblySoFar, LocalVariableTable localVariableTable){

        for(IrFieldDecl fieldDecl : fieldDecls){
            // doesnt store stuff in localVariableTable.
            fieldDecl.generateCode(assemblySoFar, localVariableTable);
        }
        for(IrMethodDecl methodDecl : methodDecls){
            // doesnt store stuff in localVariableTable.
            methodDecl.generateCode(assemblySoFar, localVariableTable);
        }
        for(IrExternDecl externDecl : externDecls){
            // doesnt store stuff in localVariableTable.
            externDecl.generateCode(assemblySoFar, localVariableTable);
        }
        return assemblySoFar;
    }

}
