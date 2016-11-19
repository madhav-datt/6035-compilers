package edu.mit.compilers;

import edu.mit.compilers.ll.Ll;
import edu.mit.compilers.ll.LlComponent;
import edu.mit.compilers.ll.LlStatement;

import java.util.Hashtable;

/**
 * Created by abel on 11/18/16.
 */
public class LlSymbolTable {
    public Hashtable<LlComponent, Ll> llTable;
}
