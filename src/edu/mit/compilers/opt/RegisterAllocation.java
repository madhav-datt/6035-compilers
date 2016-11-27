package edu.mit.compilers.opt;

import edu.mit.compilers.Register;

import java.util.ArrayList;

/**
 * Created by madhav on 11/26/16.
 */
public class RegisterAllocation {

    private ArrayList<Register> availableRegisters;

    public RegisterAllocation(ArrayList<Register> givenRegisters) {
        this.availableRegisters = givenRegisters;
    }
}
