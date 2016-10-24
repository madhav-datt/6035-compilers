package edu.mit.compilers;

import edu.mit.compilers.ir.Ir;
import edu.mit.compilers.ir.IrExprSizeOfLocation;
import edu.mit.compilers.ir.IrTypeInt;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

/**
 * Created by abel on 10/23/16.
 */
public class LocalVariableTable {
    private Hashtable<String, Ir> table = new Hashtable<>();
    private Stack<Integer> stackRegisters = new Stack<>();

    //initializes the table by adding strings corresponding to the register names with empty (null) values
    public LocalVariableTable(){

        String registers[] = {"%rax", "%rcx", "%rdx", "%rbx", "%rsi", "%rdi", "%rsp", "%rbp", "%r8", "%r9 ", "%r10", "%r11", "%r12", "%r13", "%r14", "%r15"};
        for(String register : registers){
            table.put(register, new IrTypeInt(2,2));
        }
    }

    public void pushToStack(Ir value){
        int nextStackRegister;
        if(stackRegisters.size() == 0){
            nextStackRegister = 8;
        }
        else{
            nextStackRegister = stackRegisters.peek() + 8;


        }
        table.put("-" + Integer.toString(nextStackRegister) + "(%rbp)", value);
        stackRegisters.push(nextStackRegister);
    }
    public String getByValue(Ir value){
        for(String key : table.keySet()){
            if(table.get(key).toString().equals(value.toString())){
                return key;
            }
        }
        return null;
    }
    // Do preliminary register allocation here. If both %r10 and %r11 are occupied, then push one of them
    // To the stack and free up some space
    public String getFreeLocalRegister(){
        return ((table.get("%r10") == null) ? "%r10" : "%r11");
    }
    public void freeRegister(String localRegister){
        this.table.put(localRegister, null);
    }

}
