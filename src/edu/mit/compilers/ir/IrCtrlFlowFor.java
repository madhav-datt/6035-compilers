package edu.mit.compilers.ir;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.Register;
import edu.mit.compilers.ScopeStack;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrCtrlFlowFor extends IrCtrlFlow {
    private final IrLocationVar counter;
    private final IrAssignStmt compoundAssignStmt;
    private final IrExpr intialIndexExpr;

    public IrCtrlFlowFor(IrLocationVar counter, IrExpr intialIndexerExpr, IrAssignStmt updateCounterStmt,
                      IrExpr condExpr, IrCodeBlock stmtBody) {
        super(condExpr, stmtBody);
        this.counter = counter;
        this.compoundAssignStmt = updateCounterStmt;
        this.intialIndexExpr = intialIndexerExpr;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // create the scope for the loop
        scopeStack.createNewLoopScope();

        // 1) verify the IrLocationVar
        errorMessage += this.counter.semanticCheck(scopeStack);

        // 2) verify that locationVar is not an array
        Ir object = scopeStack.getSymbol(this.counter.getLocationName().getValue());
        if (object instanceof IrFieldDeclArray) {
            errorMessage += "For loop index cannot be an array variable" +
                    " line: "+this.counter.getLineNumber() + " col: " +this.counter.getColNumber() + "\n";
        }

        // 3) make sure that initialIndexExpr is semantically correct
        errorMessage += this.intialIndexExpr.semanticCheck(scopeStack);

        // 4) verify that the initialIndexExpr is IrTypeInt
        if (!(this.intialIndexExpr.getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "Initial index value in for loop must be type int" +
                    " line: "+this.intialIndexExpr.getLineNumber() + " col: " +this.intialIndexExpr.getColNumber() + "\n";
        }

        // 3) verify that the condition is IrTypeBool
        if (!(this.condExpr.getExpressionType() instanceof IrTypeBool)) {
            errorMessage += "For loop condition does not evaluate to bool" +
                    " line: "+this.condExpr.getLineNumber() + " col: " +this.condExpr.getColNumber() + "\n";
        }

        // 4) verify the compoundAssignStmt
        errorMessage += this.compoundAssignStmt.semanticCheck(scopeStack);

        // 5) verify that the for-block is valid
        errorMessage += this.stmtBody.semanticCheck(scopeStack);

        // delete the current loop scope
        scopeStack.deleteCurrentScope();

        return errorMessage;
    }
    public AssemblyBuilder generateCode(AssemblyBuilder assembly, Register register, StackFrame stackFrame){

        // Initialize the counter
        this.intialIndexExpr.generateCode(assembly, register, stackFrame);
        String registerForAssignment = assembly.getFootNote();
        String variableLocation = stackFrame.getIrLocation(counter.getLocationName());
        assembly.addLine(("mov " +  registerForAssignment + ", %r10"));
        assembly.addLine(("mov %r10, " + variableLocation));
        String ifConditionLabel = assembly.getLabelName();
        assembly.addLabel("." + ifConditionLabel);
        this.condExpr.generateCode(assembly, register, stackFrame);

        assembly.addLine(String.format("jne .%s_DONE", ifConditionLabel));

        assembly.getInBlock(ifConditionLabel);
        this.stmtBody.generateCode(assembly, register, stackFrame);

        // Add the compound assignment at the end of the loop body.
        compoundAssignStmt.generateCode(assembly, register, stackFrame);
        assembly.getOutOfBlock();

        assembly.addLine(String.format("jmp .%s", ifConditionLabel));
        assembly.addLine();
        assembly.addLabel(String.format(".%s_DONE", ifConditionLabel));
        return assembly;
    }
}
