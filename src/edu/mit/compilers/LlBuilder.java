package edu.mit.compilers;

import edu.mit.compilers.ll.LlLocation;
import edu.mit.compilers.ll.LlLocationVar;
import edu.mit.compilers.ll.LlStatement;

import java.util.List;
import java.util.Stack;

/**
 * Created by abel on 11/18/16.
 */
public class LlBuilder {
    private List<LlStatement> statementList;
    private int labelCounter = 0;
    private int tempCounter = 0;

    // Add to the end of the list
    public void appendStatement(LlStatement statement){
        statementList.add(statement);
    }

    //Generates a label
    public String generateLabel(){
       return "L"+Integer.toString(labelCounter++);
    }

    //Generate Temporary Variable
    public LlLocationVar generateTemp(){
        return new LlLocationVar("t"+Integer.toString(tempCounter++));
    }
}
