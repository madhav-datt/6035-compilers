package edu.mit.compilers;

import edu.mit.compilers.cfg.CFG;
import edu.mit.compilers.ll.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by abel on 11/18/16.
 */
public class LlSymbolTable {
    private final String methodName;
    public Hashtable<LlComponent, String> llStringTable;
    public Hashtable<LlLocationVar, String> paramTable;
    public Hashtable<LlLocationVar, Integer> arrayTable;
    public HashMap<CFG.SymbolDef, ArrayList<CFG.Tuple>> useDef;
    public Hashtable<LlLocationVar, Integer> globalArrays;
    public ArrayList<LlLocationVar> globalVars;

    public LlSymbolTable(String methodName){
        this.llStringTable = new Hashtable<>();
        this.paramTable = new Hashtable<>();
        this.arrayTable = new Hashtable<>();
        this.useDef = new HashMap<>();
        this.globalArrays = new Hashtable<>();
        this.globalVars = new ArrayList<>();
        this.methodName = methodName;

    }
    public  String getMethodName(){
        return this.methodName;
    }
    public void putOnStringTable(LlComponent key, String value){
        this.llStringTable.put(key, value);
    }

    public String getFromStringTable(LlComponent key){
        return this.llStringTable.get(key);
    }
    public Hashtable<LlComponent, String> getLlStringTable(){
        return this.llStringTable;
    }
    public void putOnParamTable(LlLocationVar key, String value){
        this.paramTable.put(key, value);
    }

    public String getFromParamTable(LlLocationVar key){
       for(LlLocationVar loc : this.paramTable.keySet()){
           if(key.equals(loc)){
               return this.paramTable.get(loc);
           }

       }
       return null;
    }
    public Hashtable<LlLocationVar, String> getParamTable(){
        return this.paramTable;
    }
    public void setUseDef(HashMap<CFG.SymbolDef, ArrayList<CFG.Tuple>> useDef){
        this.useDef = useDef;
    }
    public HashMap<CFG.SymbolDef, ArrayList<CFG.Tuple>>getUseDef(){
        return this.useDef;
    }
    public void putOnArrayTable(LlLocationVar key, int val){
        this.arrayTable.put(key, val);
    }
    public Integer getFromArrayTable(LlLocationVar key){
        for(LlLocationVar loc : this.arrayTable.keySet()){
            if(key.equals(loc)){
                return this.arrayTable.get(loc);
            }

        }
        return null;
    }
    public Hashtable<LlLocationVar, Integer> getArrayTable(){
        return this.arrayTable;
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

    public boolean isInGlobalArraysTable(LlLocationVar var){
        for(LlLocationVar locationVar : this.globalArrays.keySet()){
            if(locationVar.equals(var)){
                return true;
            }
        }
        return false;
    }

    public boolean isInGlobalVarsTable(LlLocationVar var){
        for(LlLocationVar locationVar : this.globalVars){
            if(locationVar.equals(var)){
                return true;
            }
        }
        return false;
    }
}
