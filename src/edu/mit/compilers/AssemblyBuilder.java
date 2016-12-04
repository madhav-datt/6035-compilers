package edu.mit.compilers;

import java.util.*;

/**
 * Created by abel on 10/26/16.
 * Table mapping of lines --> strings
 */
public class AssemblyBuilder {

    private HashMap<Integer, String> code;
    private int labelCounter = 0;
    private HashMap<String, String> stringTable;
    private HashMap<String, Integer> enterLines;
    public AssemblyBuilder(){
        code = new HashMap<>();
        this.stringTable = new HashMap<>();
        enterLines = new HashMap<>();
    }

    public void append(String s){
        this.code.put(this.code.size(), s);
    }

    public void addLabel(String label){
        this.append(label + ":");
    }

    public void addLine(){

            this.append("");

    }

    public void addLinef(String command, String body){
        String st = "";
        st += String.format("%1$-6s %2$1s", command, body);
        this.addLine(st);
    }
    public void addComment(String comment){
        //this.addLine("# " + comment);
    }

    public int getEnterLine(String methodName){
        return this.enterLines.get(methodName);
    }

    public void setEnterLine(String methodName) {
        this.enterLines.put(methodName, this.code.size());
        this.addLine();
    }



    public int getCodeLength(){
        return this.code.size();
    }

    public void replaceEnterLine(String methodName, int stackSize){
        String replaceWith = "$(8*" + Integer.toString(stackSize) + "), $0";
        this.replaceLine(this.getEnterLine(methodName), String.format("     "+ "%1$-6s %2$1s", "enter", replaceWith));
    }


    public void addLine(String s){
        this.append("     " + s);
    }

    public void replaceLine(int lineNum, String s){
        this.code.put(lineNum, s);
    }

    public void replaceLineWithLabel(int lineNum, String label){
        this.replaceLine(lineNum, label + ":");
    }

    public void replaceLineWithCode(int lineNum, String s){
        this.replaceLine(lineNum, "     " + s);
    }

    public String generateStringLabel(){
        return ".STR_" + Integer.toString(labelCounter++);
    }

    public void  putOnStringTable(String key, String value){
        this.stringTable.put(key, value);
    }
    public String getFromStringTable(String key){
        return this.stringTable.get(key);
    }

    public String assemble(){
        // sort the keys first
        Set<Integer> keys = this.code.keySet();
        StringBuilder assembled = new StringBuilder("");
        for(Integer key : keys){
            assembled.append(this.code.get(key) + "\n");
        }

        return assembled.toString();
    }

}
