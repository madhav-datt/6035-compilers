package edu.mit.compilers;

import edu.mit.compilers.ll.LlLocationVar;
import edu.mit.compilers.ll.LlStatement;
import org.antlr.v4.misc.OrderedHashMap;

import java.util.*;
import java.util.Stack;

/**
 * Created by abel on 11/18/16.
 */
public class LlBuilder {
    private final String name;
    private LinkedHashMap<String, LlStatement> statementTable;
    private int labelCounter = 0;
    private int tempCounter = 0;

    private Stack<String> currentBlockLabel = new Stack<>();
    private Object pocket;

    public LlBuilder(String name){
        this.statementTable = new LinkedHashMap<>();
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    // Add to the end of the list

    public void appendStatement(LlStatement statement){
        String label = this.generateLabel();
        statementTable.put(label, statement);
    }

    public void appendStatement(String label, LlStatement statement){
        if(statementTable.containsKey(label)){
            System.err.println("Duplicate label key . Please use the label generator! ");
            System.err.println("Key :" + label);
            System.err.println("Statement : " + statement.toString());
            System.err.println("StackSize "+ labelCounter);

        }
        else{
            statementTable.put(label, statement);
        }
    }

    //Generates a label
    public String generateLabel(){
       return "L"+Integer.toString(labelCounter++);
    }


    //Generate Temporary Variable
    public LlLocationVar generateTemp(){
        return new LlLocationVar("#_t"+Integer.toString(tempCounter++));
    }

    public LlLocationVar generateStrTemp(){
        return new LlLocationVar("#str_t"+Integer.toString(tempCounter++));
    }

    public void putInPocket(Object o){
        this.pocket = o;
    }
    public void emptyPocket(){
        this.pocket = null;
    }

    public Object pickPocket(){
        return this.pocket ;
    }

    public LinkedHashMap<String, LlStatement> getStatementTable() {
        return statementTable;
    }

    // Call this when you get out of a block.
    public void getOutOfBlock(){
        currentBlockLabel.pop();
    }

    public String getCurrentBlock(){
        if(currentBlockLabel.size() > 0){
            return currentBlockLabel.peek();
        }
        return null;
    }

    public void getInBlock(String loopLabel){
        currentBlockLabel.push(loopLabel);
    }

    @Override
    public String toString() {
        String st = "";
        for(String key : statementTable.keySet()){
            st += String.format("%1$15s :  ", key);
            st += statementTable.get(key) + "\n";

        }
        return st;
    }
}
