package edu.mit.compilers;

import edu.mit.compilers.ir.IrFieldDeclArray;
import edu.mit.compilers.ir.IrFieldDeclVar;
import edu.mit.compilers.ll.LlLocationArray;
import edu.mit.compilers.ll.LlLocationVar;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by abel on 12/3/16.
 */
public class LlBuildersList {
    private ArrayList<LlBuilder> builders;
    private ArrayList<LlSymbolTable> symbolTables;
    private Hashtable<LlLocationVar, Integer> globalArrays;
    private ArrayList<LlLocationVar> globalVars;
    public LlBuildersList(){
        this.builders = new ArrayList<>();
        this.symbolTables = new ArrayList<>();
        this.globalArrays = new Hashtable<>();
        this.globalVars = new ArrayList<>();
    }

    public void addBuilder(LlBuilder builder){
        this.builders.add(builder);
    }
    public void addSymbolTable(LlSymbolTable symbolTable){
        this.symbolTables.add(symbolTable);
    }

    public void addToGlobalArrays(LlLocationVar var, int size){
        this.globalArrays.put(var, size);
    }

    public Hashtable<LlLocationVar, Integer> getGlobalArrays(){
        return this.globalArrays;
    }

    public void addToGlobalVars(LlLocationVar var ){
        this.globalVars.add(var);
    }

    public ArrayList<LlLocationVar> getGlobalVars(){
        return this.globalVars;
    }

    public ArrayList<LlBuilder> getBuilders(){
        return this.builders;
    }

    public ArrayList<LlSymbolTable> getSymbolTables(){
        return this.symbolTables;
    }
}
