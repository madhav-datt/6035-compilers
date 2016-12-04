package edu.mit.compilers;

import edu.mit.compilers.ll.*;

import java.util.Hashtable;

/**
 * Created by abel on 11/18/16.
 */
public class LlSymbolTable {
    public Hashtable<LlComponent, String> llStringTable;
    public Hashtable<LlLocationVar, String> paramTable;
    public LlSymbolTable(){
        this.llStringTable = new Hashtable<>();
        this.paramTable = new Hashtable<>();
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
}
