package edu.mit.compilers;

import edu.mit.compilers.ir.Ir;

import java.util.Hashtable;
import java.util.Stack;

/**
 * Created by devinmorgan on 10/10/16.
 */
public class ScopeStack {
    private final Stack<ScopeStack.SymbolTable> stack;

    public ScopeStack() {
        this.stack = new Stack<ScopeStack.SymbolTable>();
    }

    public void addObjectToCurrentScope(String id, Ir object) {
        this.stack.peek().hashtable.put(id,object);
    }

    public boolean checkIfSymbolExistsAtAnyScope(String id) {
        for( SymbolTable e = this.stack.peek(); e != null; e = e.parentScope ) {
            Ir found = e.hashtable.get(id);
            if (found != null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkIfSymbolExistsAtCurrentScope(String id) {
        return this.stack.peek().hashtable.contains(id);
    }

    public Ir getSymbol(String id) {
        for( SymbolTable e = this.stack.peek(); e != null; e = e.parentScope ) {
            Ir found = e.hashtable.get(id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public SymbolTable deleteCurrentScope() {
        if (this.stack.size() > 0) {
            this.stack.pop();
        }
        return null;
    }

    public SymbolTable createNewScope() {
        // start the ScopeStack out with a Global Scope
        if (this.stack.size() == 0) {
            ScopeStack.SymbolTable globalScope = this.new SymbolTable();
            this.stack.add(globalScope);
            return globalScope;
        }
        // add child scopes if a Global Scope already exists
        else {
            ScopeStack.SymbolTable parentScope = this.stack.peek();
            ScopeStack.SymbolTable newScope = this.new SymbolTable(parentScope);
            this.stack.push(newScope);
            return newScope;
        }
    }

    class SymbolTable {
        protected Hashtable<String, Ir> hashtable;
        protected SymbolTable parentScope;

        protected SymbolTable(){
            this.hashtable = new Hashtable<String, Ir>();
            this.parentScope = null;
        }

        protected SymbolTable(SymbolTable parentScope) {
            this.hashtable = new Hashtable<String, Ir>();
            this.parentScope = parentScope;
        }

    }

}


