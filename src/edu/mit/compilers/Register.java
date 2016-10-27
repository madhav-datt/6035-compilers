package edu.mit.compilers;

import edu.mit.compilers.ir.Ir;

import java.util.Hashtable;

/**
 * Created by abel on 10/26/16.
 * An abstraction of the register. Holds the General purpose registers and the values stored in them.
 * There is only one global register for use by every part.
 * (String, Ir) -> Register
 */


public class Register {
    private Hashtable<String, Ir> register = new Hashtable<String, Ir>();
    private String registers[] = {"%rax", "%rcx", "%rdx", "%rbx", "%rsi", "%rdi", "%rsp", "%rbp", "%r8", "%r9 ", "%r10", "%r11", "%r12", "%r13", "%r14", "%r15"};
    private String paramRegisters[] = {"%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"};

    public void put(String reg, Ir value){
        register.put(reg, value);
    }

    public Ir getValue(String reg){
        return register.get(reg);
    }
    // Returns the location in the stack of the given value.
    public String getRegister(Ir value){
        for(String key : register.keySet()){
            if(register.get(key).toString().equals(value.toString())){
                return key;
            }
        }
        return null;
    }
    public String[] getParamRegisters(){
        return this.paramRegisters;
    }


}
