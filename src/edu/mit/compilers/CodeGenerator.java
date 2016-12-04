package edu.mit.compilers;

import edu.mit.compilers.ir.IrProgram;
import edu.mit.compilers.ll.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abel on 10/22/16.
 */
public class CodeGenerator {

    // get the High Level Ir Program
    public String generateCode(IrProgram program){
        String assembled = "";
        LlBuildersList buildersList = program.getLlBuilders();
        HashMap<String, ArrayList<LlLocationVar>> methodParams = program.getMethodArgs();
        // prepatch the strings and stuff
        AssemblyBuilder assemblyBuilder = new AssemblyBuilder();
        assemblyBuilder.addLine(".globl main");
        assemblyBuilder.addLine();
        // keeps track of what strings are created and

        for(LlSymbolTable table : buildersList.getSymbolTables()){
            for (LlComponent component : table.getLlStringTable().keySet()){
                if(component instanceof  LlLocationVar){
                    String stringLable = assemblyBuilder.generateStringLabel( );
                    assemblyBuilder.addLabel(stringLable);
                    assemblyBuilder.addLine(".string " + table.getFromStringTable(component));
                    assemblyBuilder.putOnStringTable(((LlLocationVar) component).getVarName(), stringLable);
                }
            }
        }
        for(LlBuilder builder : buildersList.getBuilders()){
            LlSymbolTable symbolTable = new LlSymbolTable();
            String currentMethodName = "";
            assemblyBuilder.addLine("");
            StackFrame frame = new StackFrame();;
            // get the method name
            for(String label  : builder.getStatementTable().keySet()){
                if(methodParams.containsKey(label)){
                    currentMethodName = label;
                }
            }

            // then get the params list
            if(!currentMethodName.equals("")) {
                pushParamsToSymbolTable(currentMethodName, methodParams, symbolTable);
                assemblyBuilder.addLabel(currentMethodName);
                assemblyBuilder.setEnterLine(currentMethodName);

            }

            for(String label  : builder.getStatementTable().keySet())
            {

                if(builder.getStatementTable().get(label) instanceof  LlEmptyStmt && !label.equals(currentMethodName)){

                    assemblyBuilder.addLabel(label);
                }
                builder.getStatementTable().get(label).generateCode(assemblyBuilder, frame, symbolTable);
            }
            assemblyBuilder.replaceEnterLine(currentMethodName, frame.getStackSize());

            assemblyBuilder.addLinef("leave","");
            assemblyBuilder.addLinef("ret", "");
        }
        assembled += assemblyBuilder.assemble();
        System.out.println(assemblyBuilder.assemble());
        ///Users/abel/Desktop
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("/Users/abel/Desktop/out.s"), "utf-8"))) {
            writer.write(assembled);
        }
        catch(IOException e){
            System.out.println(e);
        }
        return assembled;
    }

    // basically loads the params on the stackFrame so that they can be accessed later.
    private void pushParamsToSymbolTable(String methodName, HashMap<String, ArrayList<LlLocationVar>> paramsTable, LlSymbolTable symbolTable){
        // generate stack locations on the params
        String paramRegs[] = {"%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"};
        ArrayList<LlLocationVar> locations = paramsTable.get(methodName);
        for(int i = 0; i < locations.size(); i++){
          // put it in the stack
            String storageLoc;
            if(i<6){
                storageLoc = paramRegs[i];
                symbolTable.putOnParamTable(locations.get(i), storageLoc);
            }
            else{
                storageLoc =  Integer.toString(16 + (i-6)*8);
                symbolTable.putOnParamTable(locations.get(i), storageLoc);
            }
        }
    }

}
