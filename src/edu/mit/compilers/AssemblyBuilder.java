package edu.mit.compilers;


import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;

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
    public boolean hasReturned = false;
    public boolean isLastReturn = false;
    private String footnote;
    private  HashMap<LlLocation, String> allocatedRegisers;

    public AssemblyBuilder( ){
        code = new HashMap<>();
        this.stringTable = new HashMap<>();
        enterLines = new HashMap<>();
        this.footnote = "";
        this.allocatedRegisers = new HashMap<>();
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
        this.addLine("# " + comment);
    }

    public int getEnterLine(String methodName){
        return this.enterLines.get(methodName);
    }

    public void setEnterLine(String methodName) {
        this.enterLines.put(methodName, this.code.size());
        this.addLine();
    }

    public void putOnFootnote(String msg){
        this.footnote = msg;
    }

    public String getFootnote(){
        return this.footnote;
    }

    public int getCodeLength(){
        return this.code.size();
    }

    public void replaceEnterLine(String methodName, int stackSize){
        String replaceWith = "$(8*" + Integer.toString(stackSize + 1) + "), %rsp";
        this.replaceLine(this.getEnterLine(methodName), String.format("     "+ "%1$-6s %2$1s", "subq", replaceWith));
    }

    public void updateRegisterAllocationTable(HashMap<LlLocation, String> allocatedRegisers){
        this.allocatedRegisers = allocatedRegisers;
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

    public String optimizedStore(LlLocation var, String source, StackFrame frame){
        if(this.allocatedRegisers.keySet().contains(var)){
            this.addLinef("movq", source + ", " +this.allocatedRegisers.get(var));
            return this.allocatedRegisers.get(var);
        }
        else{
            String storeToLoc = frame.getNextStackLocation();
            this.addLinef("movq",source + ", %r10");
            this.addLinef("movq", "%r10, " + storeToLoc);
            frame.pushToStackFrame(var);
            return storeToLoc;
        }
    }

    public String optimizedStore(LlLocation var, String source, String destination){
        if(this.allocatedRegisers.keySet().contains(var)){
            this.addLinef("movq", source + ", " +this.allocatedRegisers.get(var));
            return this.allocatedRegisers.get(var);
        }
        else{

            this.addLinef("movq",source + ", %r10");
            this.addLinef("movq", "%r10, " + destination);

            return destination;
        }
    }

    public String getAllocatedReg(LlLocation var){
        return this.allocatedRegisers.get(var);
    }

    public ArrayList<String> getAllAllocatedRegs(){
        ArrayList<String> allAllocatedRegs = new ArrayList<>();
        for(String reg : this.allocatedRegisers.values()){
            if(!allAllocatedRegs.contains(reg)){
                allAllocatedRegs.add(reg);
            }
        }
        return allAllocatedRegs;
    }

    public String optimizedLoad(LlLocation var, String loadTo, StackFrame frame){
        if(this.allocatedRegisers.keySet().contains(var)){
            this.addLinef("movq",  this.allocatedRegisers.get(var) + ", " + loadTo);
            return this.allocatedRegisers.get(var);
        }
        else{
            String storeToLoc = frame.getNextStackLocation();
            this.addLinef("movq", storeToLoc + ", %r10");
            this.addLinef("movq", "%r10, " + storeToLoc);
            frame.pushToStackFrame(var);
            return storeToLoc;
        }
    }

    public String optimizedLoad(LlLocation var, String loadTo, String loadFrom){
        if(this.allocatedRegisers.keySet().contains(var)){
            this.addLinef("movq",  this.allocatedRegisers.get(var) + ", " + loadTo);
            return this.allocatedRegisers.get(var);
        }
        else{

            this.addLinef("movq", loadFrom + ", %r10");
            this.addLinef("movq", "%r10, " + loadTo);

            return loadFrom;
        }
    }


    public void calleeSave(ArrayList<String> registers, StackFrame frame){
        for(int i = 0; i < registers.size(); i ++){
            String nextLocation = frame.getNextStackLocation();
            this.addLinef("movq", registers.get(i) + ", " + nextLocation );
            frame.pushToRegisterStackFrame(registers.get(i));
        }
    }

    public void calleeRestore(ArrayList<String> registers, StackFrame frame){
        for(int i = 0; i < registers.size(); i ++){
            String registerLocation = frame.getRegisterLocation(registers.get(i));
            this.addLinef("movq",  registerLocation + ", " + registers.get(i));
        }
    }


}
