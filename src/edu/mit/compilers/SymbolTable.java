package edu.mit.compilers;

import edu.mit.compilers.ir.Ir;

import java.util.*;

public class SymbolTable {
    private Hashtable<String, Ir> hashtable;
    protected SymbolTable parentScope;

    public SymbolTable(){
        this.hashtable = new Hashtable<String, Ir>();
    }

    public SymbolTable(SymbolTable parentScope){
        this.parentScope = parentScope;
        this.hashtable = new Hashtable<String, Ir>();
    }

    public void put(String identifier, Ir value){
        this.hashtable.put(identifier, value);
    }

    public Ir get(String s){
        for( SymbolTable e = this; e != null; e = e.parentScope ) {
            Ir found = (Ir) (e.hashtable.get(s));
            if (found != null) return found;
        }
        return null;
    }

}