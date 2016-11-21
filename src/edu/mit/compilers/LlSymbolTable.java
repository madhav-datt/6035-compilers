package edu.mit.compilers;

import edu.mit.compilers.ll.Ll;
import edu.mit.compilers.ll.LlComponent;
import edu.mit.compilers.ll.LlStatement;

import java.util.Hashtable;

/**
 * Created by abel on 11/18/16.
 */
public class LlSymbolTable {
    public Hashtable<LlComponent, String> llTable;

    public LlSymbolTable(){
        llTable = new Hashtable<>();
    }

    public void put(LlComponent key, String value){
        this.llTable.put(key, value);
    }

    public String get(LlComponent key){
        return this.llTable.get(key);
    }
}
