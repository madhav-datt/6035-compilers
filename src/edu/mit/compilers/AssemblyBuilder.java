package edu.mit.compilers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abel on 10/26/16.
 */
public class AssemblyBuilder {
    private StringBuilder assemblyHeader;
    private StringBuilder assemblyBody;
    private StringBuilder assemblyBottom;
    private String footNote;
    private List<String> label = new ArrayList<String>();

    private StringBuilder addFormatedLine(int offset, String asm){
        StringBuilder tempAsm = new StringBuilder();
        for(int i = 0; i < offset; i ++){
            tempAsm.append(" ");
        }
        tempAsm.append(asm);
        tempAsm.append("\n");

        return tempAsm;
    };

    private StringBuilder getAssemblyBody(){
        return new StringBuilder(this.assemblyBody.toString());
    }

    public AssemblyBuilder(){
        this.assemblyHeader = new StringBuilder("");
        this.assemblyBody = new StringBuilder("");
        this.assemblyBottom = new StringBuilder("");
        this.footNote = "";
    }
    public AssemblyBuilder(StringBuilder header){
        this.assemblyHeader = header;
        this.assemblyBody = new StringBuilder("");
        this.assemblyBottom = new StringBuilder("");
    }

    public void append(StringBuilder asm){
        this.assemblyBody.append(asm);

    }
    public void concat(AssemblyBuilder other){
        this.assemblyBody.append("\n");
        this.assemblyBody.append(other.getAssemblyBody());
    }
    public void addLine(int offset, String asm){
       this.assemblyBody.append(this.addFormatedLine(offset, asm));
    }
    public void appendToBottom(String s ){
        this.assemblyBottom.append(s);
    }

    public void putOnFootNote(String string){
        this.footNote = string;
    }
    public String getFootNote(){
        return this.footNote;
    }

    public StringBuilder generateAssembly(){
        StringBuilder finalAsm = this.assemblyHeader;
        finalAsm.append("\n");
        finalAsm.append(assemblyBody);
        finalAsm.append("\n");
        finalAsm.append(assemblyBottom);
        finalAsm.append("\n");
        return finalAsm;
    }
    public String getLabelName(){
        return "L" + this.label.size();
    }
    public String getStringLabel(){
        return "STR_" + this.label.size();
    }

    @Override
    public String toString() {
        return this.getAssemblyBody().toString();
    }
}
