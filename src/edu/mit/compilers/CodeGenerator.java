package edu.mit.compilers;

import edu.mit.compilers.cfg.CFG;
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
        this.appendGlobalVarsAndArrays(assemblyBuilder, buildersList);
        assemblyBuilder.addLine(".globl main");
        assemblyBuilder.addLine();

        // keeps track of what strings are created and
        int countSymbolTables = 0;
        for(LlSymbolTable table : buildersList.getSymbolTables()){
            for (LlComponent component : table.getLlStringTable().keySet()){

                if(component instanceof  LlLocationVar){

                    String stringLable = assemblyBuilder.generateStringLabel( );
                    assemblyBuilder.addLabel(stringLable);
                    assemblyBuilder.addLine(".string " + table.getFromStringTable(component));
                    assemblyBuilder.putOnStringTable(table.getMethodName()+"_"+(((LlLocationVar) component).getVarName()), stringLable);
                }

            }
        }

        for(LlBuilder builder : buildersList.getBuilders()){

            LlSymbolTable oldSymbolTable = buildersList.getSymbolTables().get(countSymbolTables++);
            LlSymbolTable symbolTable = new LlSymbolTable(builder.getName());

            this.addGlobalContextToLocalTables(buildersList, symbolTable);
            this.pushArraysToSymbolTable(oldSymbolTable, symbolTable);
            String currentMethodName = "";
            assemblyBuilder.addLine("");
            StackFrame frame = new StackFrame();;
            // get the method name
            currentMethodName = builder.getName();

            // then get the params list
            if(!currentMethodName.equals("")) {

                assemblyBuilder.addLabel(currentMethodName);

                /*
                .cfi_startproc
                 pushq     %rbp
                 .cfi_def_cfa_offset 16
                 .cfi_offset 6, -16
                 movq %rsp, %rbp


                 leave
                 .cfi_def_cfa 7, 8
                 ret
                 .cfi_endproc
                             */
                assemblyBuilder.addLinef(" .cfi_startproc", "");
                assemblyBuilder.addLinef("pushq ", "%rbp");
                assemblyBuilder.addLinef(".cfi_def_cfa_offset", "16");
                assemblyBuilder.addLinef(".cfi_offset", "6, -16");
                assemblyBuilder.addLinef("movq", "%rsp, %rbp");
                assemblyBuilder.addLine();
                assemblyBuilder.setEnterLine(currentMethodName);

                pushParamsToSymbolTable(assemblyBuilder, currentMethodName, methodParams, symbolTable, frame);
            }
            int statementCounter = 0;
            for(String label  : builder.getStatementTable().keySet())
            {

                if(builder.getStatementTable().get(label) instanceof  LlEmptyStmt && !label.equals(currentMethodName)){

                    assemblyBuilder.addLabel(symbolTable.getMethodName()+"_"+label);
                }
                if(statementCounter == builder.getStatementTable().size()-1){
                    assemblyBuilder.isLastReturn = true;
                }
                builder.getStatementTable().get(label).generateCode(assemblyBuilder, frame, symbolTable);
                statementCounter++;
            }
            assemblyBuilder.replaceEnterLine(currentMethodName, frame.getStackSize());


            if(assemblyBuilder.hasReturned){
                assemblyBuilder.hasReturned = false;
                assemblyBuilder.isLastReturn = false;
            }
            else{
                assemblyBuilder.addLinef("leave","");
                assemblyBuilder.addLinef(".cfi_def_cfa","7, 8");
                assemblyBuilder.addLinef("ret", "");
                assemblyBuilder.addLinef(".cfi_endproc", "");
                assemblyBuilder.addLine();
                assemblyBuilder.hasReturned = false;
                assemblyBuilder.isLastReturn = false;
            }


        }
        assembled += assemblyBuilder.assemble();
        System.out.println(assemblyBuilder.assemble());
        return assembled;
    }

    // basically loads the params on the stackFrame so that they can be accessed later.
    private void pushParamsToSymbolTable(AssemblyBuilder builder, String methodName, HashMap<String, ArrayList<LlLocationVar>> paramsTable, LlSymbolTable symbolTable, StackFrame frame){
        // generate stack locations on the params
        String paramRegs[] = {"%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"};
        ArrayList<LlLocationVar> locations = paramsTable.get(methodName);
        for(int i = 0; i < locations.size(); i++){
          // put it in the stack
            String storageLoc;
            String stackLoc = frame.getNextStackLocation();

            if(i<6){
                storageLoc = paramRegs[i];
                builder.addLinef("movq", storageLoc + ", " + stackLoc);
                symbolTable.putOnParamTable(locations.get(i), stackLoc);

            }
            else{
                storageLoc =  Integer.toString(16 + (i-6)*8);
                builder.addLinef("movq", storageLoc + "(%rbp)" + ", %r10");
                builder.addLinef("movq", "%r10, " + stackLoc);
                symbolTable.putOnParamTable(locations.get(i), stackLoc);
            }
            frame.pushToRegisterStackFrame("%r10");
        }
    }
    // add the array names to the symbol table
    public void pushArraysToSymbolTable(LlSymbolTable oldSymbolTable, LlSymbolTable newSymbolTable){
        for(LlLocationVar key : oldSymbolTable.getArrayTable().keySet()){
            newSymbolTable.putOnArrayTable(key, oldSymbolTable.getFromArrayTable(key));
        }
    }
    // adds code for global vars and arrays at the top
    public void appendGlobalVarsAndArrays(AssemblyBuilder builder, LlBuildersList buildersList){
        for(LlLocationVar locationVar : buildersList.getGlobalArrays().keySet()){
            int arraySize = buildersList.getGlobalArrays().get(locationVar);
            builder.addLinef(".comm", locationVar.getVarName() + ", " + Integer.toString(arraySize*8) +", 64"); // might be 64
        }
        for(LlLocationVar locationVar : buildersList.getGlobalVars()){

            builder.addLinef(".comm", locationVar.getVarName() + ", 8, 8");
        }

    }

    public void addGlobalContextToLocalTables(LlBuildersList buildersList, LlSymbolTable localTable){
        for(LlLocationVar locationVar : buildersList.getGlobalArrays().keySet()){
            localTable.addToGlobalArrays(locationVar, buildersList.getGlobalArrays().get(locationVar) );
        }
        for(LlLocationVar locationVar : buildersList.getGlobalVars()){
            localTable.addToGlobalVars(locationVar);

        }
    }

}
