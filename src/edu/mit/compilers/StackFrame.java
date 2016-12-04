package edu.mit.compilers;

import edu.mit.compilers.ir.Ir;
import edu.mit.compilers.ll.LlComponent;
import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;


import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by abel on 10/26/16.
 package edu.mit.compilers;

 import edu.mit.compilers.ir.Ir;


 import java.util.Hashtable;

 /**
 * Created by abel on 10/26/16.
 * An abstraction of a stack frame. Holds the names of the positions in stack in the form of offset from the base
 * pointer 8*n(%rbp).
 * (String, Ir) -> StackFrame
 */
public class StackFrame {
    private Hashtable<String, LlComponent> stackFrame;
    private Hashtable<String, String> registerStackFrame;

    private int stackTop = 0;
    public void pushToStackFrame(LlComponent value){
        // if it already exists dont add it!!
        for(LlComponent val : stackFrame.values()){
            if(val.equals(value)){
                return ;
            }
        }
        int stackPosition = -8*(++stackTop);
        stackFrame.put(Integer.toString(stackPosition) + "(%rbp)", value);
    }

    public StackFrame(){
        this.stackFrame = new Hashtable<>();
        this.registerStackFrame = new Hashtable<>();
    }


    public void pushToRegisterStackFrame(String reg){
        int stackPosition = -8*(++stackTop);
        registerStackFrame.put(Integer.toString(stackPosition) + "(%rbp)", reg);
    }
    // When you try to get or put an Ir on stack, Make sure you store its locatioName. For now, this stack frame only holds Location names and strings.
    public String getLlLocation(LlComponent value){
        for(String key : stackFrame.keySet()){
            if(stackFrame.get(key).equals(value)){
                return key;
            }
        }
        return null;
    }
    public String getRegisterLocation(String reg){
        for(String key : stackFrame.keySet()){
            if(stackFrame.get(key).toString().equals(reg.toString())){
                return key;
            }
        }
        return null;
    }

    public void getFromTable(){

    }

    public int getStackSize(){
        return this.stackTop;
    }
    public String getNextStackLocation(){
        return Integer.toString(-8*(this.stackTop + 1)) + "(%rbp)";
    }
}