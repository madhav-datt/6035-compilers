package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowIfElse extends IrCtrlFlow{
    private final IrCodeBlock elseBlock;
    private final IrCtrlFlowIf ifStmt;

    public IrCtrlFlowIfElse(IrCtrlFlowIf ifStmt, IrCodeBlock elseBlock) {
        super(ifStmt.getIfCondition(), ifStmt.getIfBodyBlock());
        this.ifStmt = ifStmt;
        this.elseBlock = elseBlock;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) verify that the IrCtrlFlowIf stmt is valid
        errorMessage += this.ifStmt.semanticCheck(scopeStack);


        // create the scope for the else block only
        scopeStack.createNewBlockScope();

        // 2) verify that the else-block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the else block scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){
        // generate the code for the conditional expression.
        this.condExpr.generateCode(assembly, register, stackFrame);

        //
        String label = assembly.getFootNote();
        this.elseBlock.generateCode(assembly, register, stackFrame);
        assembly.addLine(5, String.format("jmp .%s_DONE", label));
        assembly.addLine(0, String.format("%s: ", label));
        this.ifStmt.getIfBodyBlock().generateCode(assembly, register, stackFrame);
        assembly.addLine(5, String.format("jmp .%s_DONE", label));
        assembly.putOnFootNote(label);
        return assembly;
    }

    @Override
    public String prettyPrint(String indentSpace) {
        String prettyString = "";

        // print the if statement
        prettyString += this.ifStmt.prettyPrint(indentSpace);

        // print the else body
        prettyString += indentSpace + "|--elseBody\n";
        prettyString += this.stmtBody.prettyPrint("  " + indentSpace);

        return prettyString;
    }
}
