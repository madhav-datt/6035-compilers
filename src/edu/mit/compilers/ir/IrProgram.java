package edu.mit.compilers.ir;

import java.util.ArrayList;

public class IrProgram{
//    public Ir
   private ArrayList<Ir> fieldDecls;
   private ArrayList<Ir> methodDecls;
   private ArrayList<Ir> externDecls;

   public ArrayList<Ir> getFieldDecls(){
      return this.fieldDecls;
   }
   public void setFieldDecls(ArrayList<Ir> fieldDecls){
      this.fieldDecls = fieldDecls;
   }
   public ArrayList<Ir> getMethodDecls(){
      return this.methodDecls;
   }
   public void setMethodDecls(ArrayList<Ir> methodDecls){
      this.methodDecls = methodDecls;
   }
   public ArrayList<Ir> getExternDecls(){
      return this.externDecls;
   }
   public void setExternDecls(ArrayList<Ir> externDecls){
      this.externDecls = externDecls;
   }
   public IrProgram() {

   }
}
