package edu.mit.compilers;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by abel on 10/26/16.
 */
public class AssemblyBuilder {

    private StringBuilder assemblyHeader;
    private StringBuilder assemblyBody;
    private StringBuilder assemblyBottom;

    private String footNote;

    private int labelCount;
    private Stack<String> currentBlockLabel = new Stack<>();
    private StringBuilder getFormatedLine(int offset, String asm){
        StringBuilder tempAsm = new StringBuilder();
        for(int i = 0; i < offset; i ++){
            tempAsm.append(" ");
        }
        tempAsm.append(asm);
        tempAsm.append("\n");

        return tempAsm;
    };


    public AssemblyBuilder(){
        this.assemblyHeader = new StringBuilder("");
        this.assemblyBody = new StringBuilder("");
        this.assemblyBottom = new StringBuilder("");
    }

    public void append(StringBuilder asm){
        this.assemblyBody.append(asm);
    }
    public void concat(AssemblyBuilder other){
        this.assemblyBody.append(other.getAssemblyBody());
        this.assemblyBottom.append(other.getAssemblyBottom());
        this.assemblyHeader.append(other.assemblyHeader);
    }
    public void addLine(String asm){
       this.assemblyBody.append(this.getFormatedLine(5, asm));
    }
    public void addLine(){
        this.assemblyBody.append(this.getFormatedLine(5, ""));
    }
    public void addLabel(String label){
        this.assemblyBody.append(this.getFormatedLine(0, label + ":"));
    }
    public void appendLineToBottom(String bottom){
        this.assemblyHeader.append(this.getFormatedLine(5, bottom));
    }
    public void appendLableToBottom(String bottom){
        this.assemblyHeader.append(this.getFormatedLine(0, bottom + ":"));
    }

    public void putOnFootNote(String string){
        this.footNote = string;
    }
    public String getFootNote(){
        return this.footNote;
    }

    public StringBuilder generateAssembly(){
        StringBuilder finalAsm = new StringBuilder();
        finalAsm.append(this.assemblyHeader);
        finalAsm.append("\n");
        finalAsm.append(assemblyBody);
        finalAsm.append("\n");
        finalAsm.append(assemblyBottom);
        finalAsm.append("\n");
        return finalAsm;
    }
    public String getLabelName(){
        return "L" + this.labelCount++;
    }
    public String getStringLabel(){
        return "STR_" + this.labelCount++;
    }

    @Override
    public String toString() {
        return this.getAssemblyBody().toString();
    }

    public StringBuilder getAssemblyBottom() {
        return assemblyBottom;
    }
    public StringBuilder getAssemblyBody() {
        return assemblyBody;
    }
    public StringBuilder getAssemblyHeader() {
        return assemblyHeader;
    }
    // Call this before you get into any block.
    public void getInBlock(String loopLabel){
        currentBlockLabel.push(loopLabel);
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
}
