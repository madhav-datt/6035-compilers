package edu.mit.compilers;

import java.util.ArrayList;

/**
 * Created by abel on 12/3/16.
 */
public class LlBuildersList {
    private ArrayList<LlBuilder> builders;
    private ArrayList<LlSymbolTable> symbolTables;

    public LlBuildersList(){
        this.builders = new ArrayList<>();
        this.symbolTables = new ArrayList<>();
    }

    public void addBuilder(LlBuilder builder){
        this.builders.add(builder);
    }
    public void addSymbolTable(LlSymbolTable symbolTable){
        this.symbolTables.add(symbolTable);
    }

    public ArrayList<LlBuilder> getBuilders(){
        return this.builders;
    }

    public ArrayList<LlSymbolTable> getSymbolTables(){
        return this.symbolTables;
    }
}
