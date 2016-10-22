// Generated from /Users/abel/Desktop/Fall2016/6.035/6035-compilers/src/edu/mit/compilers/grammar/DecafParser.g4 by ANTLR 4.5.3

package edu.mit.compilers;

import edu.mit.compilers.grammar.*;
import edu.mit.compilers.ir.*;
import org.antlr.v4.gui.SystemFontMetrics;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;




public class DecafListener extends DecafParserBaseListener {
    private Stack<Ir> irStack = new Stack<>();
    private ScopeStack scopeStack = new ScopeStack();

    private void declareInCurrentScopeOrReportDuplicateDecl(String id, Ir object, String errorMsg) {
        if (!this.scopeStack.checkIfSymbolExistsAtCurrentScope(id)) {
            this.scopeStack.addObjectToCurrentScope(id, object);
            this.irStack.push(object);
        }
        else {
            System.err.print(errorMsg);
        }
    }

    @Override public void enterProgram(DecafParser.ProgramContext ctx) {
        // creates the global scope
        this.scopeStack.createNewScope();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitProgram(DecafParser.ProgramContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrProgram wholeProgram = new IrProgram(l.line, l.col);

        // 1) method_decls will be on the top of the stack so collect them
        // first
        Ir topOfStack = this.irStack.peek();
        while (topOfStack instanceof IrMethodDecl) {
            IrMethodDecl newMethodDecl = (IrMethodDecl) this.irStack.pop();
            wholeProgram.addMethodDecl(newMethodDecl);
            topOfStack = this.irStack.peek();
        }

        // 2) now collect the field_decls
        while (topOfStack instanceof IrFieldDecl) {
            IrFieldDecl newFieldDecl = (IrFieldDecl) this.irStack.pop();
            wholeProgram.addFieldDecl(newFieldDecl);
            topOfStack = this.irStack.peek();
        }

        // 3) lastly, collect the extern_decls
        while (topOfStack instanceof IrExternDecl) {
            IrExternDecl newExternDecl = (IrExternDecl) this.irStack.pop();
            wholeProgram.addExternDecl(newExternDecl);
            topOfStack = this.irStack.peek();
        }

        if (this.irStack.size() != 0) {
            System.err.print("irStack not empty after reaching end of program");
        }

        // delete the global stack since we have created the program
        this.scopeStack.deleteCurrentScope();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExtern_decl(DecafParser.Extern_declContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExtern_decl(DecafParser.Extern_declContext ctx) {
        // assumes an instance of IrIdent is on the stack
        Ir irObject = this.irStack.pop();
        if (irObject instanceof IrIdent) {
            IrIdent irIdent = (IrIdent) irObject;
            IrExternDecl externDecl = new IrExternDecl(irIdent);
            declareInCurrentScopeOrReportDuplicateDecl(
                    irIdent.getValue(),
                    externDecl,
                    "enterExtern_decl: same extern declared twice"
            );
        }
        else {System.err.print("enterExtern_decl: popped object of wrong type");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterField_decl(DecafParser.Field_declContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitField_decl(DecafParser.Field_declContext ctx) {
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrType) {
            // pop the IrType because we are done creating fields
            this.irStack.pop();
        }
        else {System.err.print("exitField_decl: missing IrType from stack");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVarDecl(DecafParser.VarDeclContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>Note: the IrFieldDeclVar that are put onto the stack don't have
     * a type set at this point. Their type gets determined in </p>
     */
    @Override public void exitVarDecl(DecafParser.VarDeclContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrIdent varName = new IrIdent(ctx.ID().getText(), l.line, l.col);
        Ir topOfStack = this.irStack.peek();

        if (topOfStack instanceof IrType) {
            // pop the varType so we know what type of var this is
            IrType varType = (IrType) this.irStack.pop();
            IrFieldDeclVar newVar = new IrFieldDeclVar(varName, varType);
            declareInCurrentScopeOrReportDuplicateDecl(
                    varName.getValue(),
                    newVar,
                    "exitVarDecl: duplicate var declared in same scope"
            );

            // put the varType back on the stack in case there are more fields
            // after this one
            this.irStack.push(varType);
        }
        else {System.err.print("exitVarDecl: error with IrType of newVar");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArrayDecl(DecafParser.ArrayDeclContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArrayDecl(DecafParser.ArrayDeclContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrIdent arrayName = new IrIdent(ctx.ID().getText(), l.line, l.col);
        int arraySize = Integer.getInteger(ctx.INT().getText());
        Ir topOfStack = this.irStack.peek();

        if (topOfStack instanceof IrType) {
            // pop the arrayType so we know what type of var this is
            IrType arrayType = (IrType) this.irStack.pop();
            IrFieldDeclArray newArray = new IrFieldDeclArray(arraySize, arrayName, arrayType);
            declareInCurrentScopeOrReportDuplicateDecl(
                    arrayName.getValue(),
                    newArray,
                    "exitArrayDecl: duplicate var declared in same scope"
            );

            // put the arrayType back on the stack in case there are more fields
            // after this one
            this.irStack.push(arrayType);
        }
        else {System.err.print("exitArrayDecl: error with IrType of newArray");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMethod_decl(DecafParser.Method_declContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // create a new scope for the method_decl
        this.scopeStack.createNewScope();

        // push the IrIdent onto the stack
        IrIdent methodName = new IrIdent(ctx.ID().getText(), l.line, l.col);
        this.irStack.push(methodName);
    }
    @Override public void exitMethod_decl(DecafParser.Method_declContext ctx) {
        Ir topOfStack = this.irStack.peek();
        // 1) pop the block
        if (topOfStack instanceof IrCodeBlock) {
            IrCodeBlock block = (IrCodeBlock) this.irStack.pop();
            topOfStack = this.irStack.peek(); // update topOfStack

            // 2) pop off the param_decl's and create a list
            ArrayList<IrParamDecl> paramsList = new ArrayList<>();
            while (topOfStack instanceof IrParamDecl) {
                IrParamDecl paramDecl = (IrParamDecl) this.irStack.pop();
                paramsList.add(0, paramDecl);
                topOfStack = this.irStack.peek(); // update topOfStack
            }

            // 3) pop the id
            if (topOfStack instanceof IrIdent) {
                IrIdent methodName = (IrIdent) this.irStack.pop();
                topOfStack = this.irStack.peek(); // update topOfStack

                // 4) pop the method_type
                if (topOfStack instanceof IrType) {
                    IrType methodType = (IrType) this.irStack.pop();
                    IrMethodDecl newMethod = new IrMethodDecl(methodType, paramsList, block, methodName);
                    declareInCurrentScopeOrReportDuplicateDecl(
                            methodName.getValue(),
                            newMethod,
                            "exitMethod_decl: duplicate method in same scope"
                    );

                }
                else {System.err.print("exitMethod_decl: error with IrType for methodType");}
            }
            else {System.err.print("exitMethod_decl: error with IrIdent for methodName");}
        }
        else {System.err.print("exitMethod_decl: error with IrCodeBlock for block");}

        // delete the current scope since we finished creating the method
        this.scopeStack.deleteCurrentScope();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterParam_decl(DecafParser.Param_declContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitParam_decl(DecafParser.Param_declContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrIdent paramName = new IrIdent(ctx.ID().getText(), l.line, l.col);

        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrType) {
            IrType paramType = (IrType) this.irStack.pop();

            // parameters are part of the local scope of the method so
            // we will add them to the stack and to the current scope
            IrParamDecl paramDecl = new IrParamDecl(paramType, paramName);
            declareInCurrentScopeOrReportDuplicateDecl(
                    paramName.getValue(),
                    paramDecl,
                    "exitParam_decl: duplicate parameter in same method signature"
            );
        }
        else {System.err.print("exitParam_decl: error with IrTypeVar for paramType");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBlock(DecafParser.BlockContext ctx) {
        // we won't create a new scope here. Instead we will create
        // the new scope upon entering pieces of code that have blocks
        // associated with them (i.e. if-stmt, method_decl, etc.)
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBlock(DecafParser.BlockContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // 1) the IrStatments will be on the top of the stack so pop
        // them first
        ArrayList<IrStatement> statementsList = new ArrayList<>();
        Ir topOfStack = this.irStack.peek();
        while (topOfStack instanceof IrStatement) {
            IrStatement statement = (IrStatement) this.irStack.pop();
            statementsList.add(0, statement);
            topOfStack = this.irStack.peek(); // update topOfStack
        }

        // 2) pop all of the field_decl's
        ArrayList<IrFieldDecl> fieldDeclsList = new ArrayList<>();
        while (topOfStack instanceof IrFieldDecl) {
            IrFieldDecl fieldDecl = (IrFieldDecl) this.irStack.pop();
            fieldDeclsList.add(0, fieldDecl);
            topOfStack = this.irStack.peek();
        }

        // 3) Create the actual IrCodeBlock and add it to the stack
        IrCodeBlock newBlock = new IrCodeBlock(fieldDeclsList, statementsList, l.line, l.col);
        this.irStack.push(newBlock);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVar_type(DecafParser.Var_typeContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVar_type(DecafParser.Var_typeContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        IrType type;
        if (ctx.RES_BOOL() != null) {
            type = new IrTypeBool(l.line, l.col);
        }
        else {
            type = new IrTypeInt(l.line, l.col);
        }

        this.irStack.push(type);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMethod_type(DecafParser.Method_typeContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMethod_type(DecafParser.Method_typeContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        IrType type;
        if (ctx.RES_BOOL() != null) {
            type = new IrTypeBool(l.line, l.col);
        }
        else if (ctx.RES_INT() != null) {
            type = new IrTypeInt(l.line, l.col);
        }
        else {
            type = new IrTypeVoid(l.line, l.col);
        }

        this.irStack.push(type);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAssignStmt(DecafParser.AssignStmtContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAssignStmt(DecafParser.AssignStmtContext ctx) {
        // 1) get the expression from off the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr expr = (IrExpr) this.irStack.pop();

            // 2) get the location from off the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrLocation) {
                IrLocation loc = (IrLocation) this.irStack.pop();

                // 3-5) determine the assignment operator then
                // create the IrAssignStmt and add it to the stack
                if (ctx.assign_op().AS_OP() != null) { // location = expr
                    IrAssignStmt assignStmt = new IrAssignStmtEqual(loc, expr);
                    this.irStack.push(assignStmt);
                }
                else if (ctx.assign_op().compound_assign_op().ADD_AS_OP() != null) { // location += expr
                    IrAssignStmtPlusEqual plusEqual = new IrAssignStmtPlusEqual(loc, expr);
                    this.irStack.push(plusEqual);
                }
                else if (ctx.assign_op().compound_assign_op().SUB_AS_OP() != null) { // location -= expr
                    IrAssignStmtMinusEqual minusEqual = new IrAssignStmtMinusEqual(loc, expr);
                    this.irStack.push(minusEqual);
                }
                else {System.err.print("exitAssignStmt: problem with type of IrAssignStatement");}
            }
            else {System.err.print("exitAssignStmt: IrLocation missing from top of stack");}
        }
        else {System.err.print("exitAssignStmt: IrExpression missing from top of stack");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterIf_stmt(DecafParser.If_stmtContext ctx) {
        this.scopeStack.createNewScope();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitIf_stmt(DecafParser.If_stmtContext ctx) {
        // 1) pop the block from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrCodeBlock) {
            IrCodeBlock ifBody = (IrCodeBlock) this.irStack.pop();

            // 2) get the expr from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr ifCondition = (IrExpr) this.irStack.pop();

                // 3) make sure expr is of type bool
                if (ifCondition.getExpressionType() instanceof IrTypeBool) {

                    // 4) create the IrCtrlFlowIf and add it to irStack
                    IrCtrlFlowIf ifStmt = new IrCtrlFlowIf(ifCondition, ifBody);
                    this.irStack.push(ifStmt);
                }
                else {System.err.print("exitIf_stmt: ifStmt condition is not of type IrTypeBool");}
            }
            else {System.err.print("exitIf_stmt: top of stack is not an IrExpr");}
        }
        else {System.err.print("exitIf_stmt: top of stack is not a IrCodeBlock");}

        // delete the current scope since we are done creating the If-Stmt
        this.scopeStack.deleteCurrentScope();
    }
    @Override public void enterIfAndElseStmt(DecafParser.IfAndElseStmtContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitIfAndElseStmt(DecafParser.IfAndElseStmtContext ctx) {
        // 1) grab the codeBlock from the stack (it is the else-stmt body)
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrCodeBlock) {
            IrCodeBlock elseBody = (IrCodeBlock) this.irStack.pop();

            // 2) grab the ifStmt from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrCtrlFlowIf) {
                IrCtrlFlowIf ifStmt = (IrCtrlFlowIf) this.irStack.pop();

                // 3) create the ifElse stmt and add it to the stack
                IrCtrlFlowIfElse ifElseStmt = new IrCtrlFlowIfElse(ifStmt, elseBody);
                this.irStack.push(ifElseStmt);
            }
            else {System.err.print("exitIfAndElseStmt: top of stack is not an IrCtrlFlowIf");}
        }
        else {System.err.print("exitIfAndElseStmt: top of stack is not a CodeBlock");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterForLoop(DecafParser.ForLoopContext ctx) {
        this.scopeStack.createNewScope();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitForLoop(DecafParser.ForLoopContext ctx) {
        // 1) pop the block
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrCodeBlock) {
            IrCodeBlock forLoopBody = (IrCodeBlock) this.irStack.pop();

            // 2) pop the incrementer IrExpr
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr incrementerExpr = (IrExpr) this.irStack.pop();

                // 3) make sure the incrementer expr is of IrTypeInt
                if (incrementerExpr.getExpressionType() instanceof IrTypeInt) {

                    // 5) pop the var_location for the incrementer expr
                    topOfStack = this.irStack.peek();
                    if (topOfStack instanceof IrLocationVar) {
                        IrLocationVar compoundAssignLocation = (IrLocationVar) this.irStack.pop();

                        // 8) make sure the compoundAsignLocation is of type IrTypeInt
                        if (compoundAssignLocation.getExpressionType() instanceof IrTypeInt) {

                            // 9) pop the IrExpr for the loop condition
                            topOfStack = this.irStack.peek();
                            if (topOfStack instanceof IrExpr) {
                                IrExpr conditionExpr = (IrExpr) this.irStack.pop();

                                // 10) make sure the condition expr is of type IrTypeBool
                                if (conditionExpr.getExpressionType() instanceof IrTypeBool) {

                                    // 11) pop the start incrementer IrExpr from the stack
                                    topOfStack = this.irStack.peek();
                                    if (topOfStack instanceof IrExpr) {
                                        IrExpr startingExpValue = (IrExpr) this.irStack.pop();

                                        // 12) make sure start incrementer IrExpr is of IrTypeInt
                                        if (startingExpValue.getExpressionType() instanceof IrTypeInt) {

                                            // 13) pop the next var_location
                                            topOfStack = this.irStack.peek();
                                            if (topOfStack instanceof IrLocationVar) {
                                                IrLocationVar regularAssignLocation = (IrLocationVar) this.irStack.pop();

                                                // 15) make sure the regular assign expr is of type IrTypeInt
                                                if (regularAssignLocation.getExpressionType() instanceof IrTypeInt) {

                                                    // 16) determine the type of the compound_assign_op (+= or -=)
                                                    if (ctx.compound_assign_op().SUB_AS_OP() != null) {
                                                        IrAssignStmtMinusEqual minusEqual = new IrAssignStmtMinusEqual(compoundAssignLocation, incrementerExpr);

                                                        // 17) create the IrCtrlFlowFor and add it to the stack
                                                        IrCtrlFlowFor forLoop = new IrCtrlFlowFor(regularAssignLocation, minusEqual, conditionExpr, forLoopBody);
                                                        this.irStack.push(forLoop);
                                                    } else if (ctx.compound_assign_op().ADD_AS_OP() != null) {
                                                        IrAssignStmtPlusEqual plusEqual = new IrAssignStmtPlusEqual(compoundAssignLocation, incrementerExpr);

                                                        // 17) create the IrCtrlFlowFor and add it to the stack
                                                        IrCtrlFlowFor forLoop = new IrCtrlFlowFor(regularAssignLocation, plusEqual, conditionExpr, forLoopBody);
                                                        this.irStack.push(forLoop);
                                                    }
                                                    else {System.err.print("exitForLoop: problem with identifying type of compound_assign_op");}
                                                }
                                                else {System.err.print("exitForLoop: problem with 1st assignment stmt; not IrTypeInt");}
                                            }
                                            else {System.err.print("exitForLoop: regular assignment var location");}
                                        }
                                        else {System.err.print("exitForLoop: incrementing expression not IrTypeInt");}
                                    }
                                    else {System.err.print("exitForLoop: starting incrementer expression not found on stack");}
                                }
                                else {System.err.print("exitForLoop: condition expr not IrTypeBool");}
                            }
                            else {System.err.print("exitForLoop: condition expr not found on stack");}
                        }
                        else {System.err.print("exitForLoop: compound assign expr not IrTypeInt");}
                    }
                    else {System.err.print("exitForLoop: var location for compound assign expr not found on stack");}
                }
                else {System.err.print("exitForLoop: compound assign incrementer epxr not IrTypeInt");}
            }
            else {System.err.print("exitForLoop: compound assign incrementer expr not found on stack");}
        }
        else {System.err.print("exitForLoop: for loop body not found on stack");}

        this.scopeStack.deleteCurrentScope();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterWhileLoop(DecafParser.WhileLoopContext ctx) {
        this.scopeStack.createNewScope();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitWhileLoop(DecafParser.WhileLoopContext ctx) {
        // 1) pop the block from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrCodeBlock) {
            IrCodeBlock whileBody = (IrCodeBlock) this.irStack.pop();

            // 2) get the expr from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr whileCondition = (IrExpr) this.irStack.pop();

                // 3) make sure expr is of type bool
                if (whileCondition.getExpressionType() instanceof IrTypeBool) {

                    // 4) create the IrCtrlFlowWhile and add it to irStack
                    IrCtrlFlowWhile whileStmt = new IrCtrlFlowWhile(whileCondition, whileBody);
                    this.irStack.push(whileStmt);
                }
                else {System.err.print("exitWhileLoop: whileStmt condition is not of type IrTypeBool");}
            }
            else {System.err.print("exitWhileLoop: top of stack is not an IrExpr");}
        }
        else {System.err.print("exitWhileLoop: top of stack is not a IrCodeBlock");}

        // delete the current scope since we are done creating the whileStmt
        this.scopeStack.deleteCurrentScope();
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterReturnExprStmt(DecafParser.ReturnExprStmtContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitReturnExprStmt(DecafParser.ReturnExprStmtContext ctx) {
        // pop the expression from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr expr = (IrExpr) this.irStack.pop();

            // create the IrStmtReturnExpr and push to irStack
            IrStmtReturnExpr returnExpr = new IrStmtReturnExpr(expr);
            this.irStack.push(returnExpr);
        }
        else {System.err.print("exitReturnExprStmt: problem with IrExpr on topOfStack");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterReturnVoidStmt(DecafParser.ReturnVoidStmtContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitReturnVoidStmt(DecafParser.ReturnVoidStmtContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // create the returnStmt and push it to  irStack
        IrStmtReturnVoid returnVoid = new IrStmtReturnVoid(l.line, l.col);
        this.irStack.push(returnVoid);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBreakStmt(DecafParser.BreakStmtContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBreakStmt(DecafParser.BreakStmtContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // create the break stmt add it to irStack
        IrStmtBreak breakStmt = new IrStmtBreak(l.line, l.col);
        this.irStack.push(breakStmt);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterContinueStmt(DecafParser.ContinueStmtContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitContinueStmt(DecafParser.ContinueStmtContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // create the continue stmt add it to irStack
        IrStmtContinue continueStmt = new IrStmtContinue(l.line, l.col);
        this.irStack.push(continueStmt);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMethod_call(DecafParser.Method_callContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMethod_call(DecafParser.Method_callContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        Ir topOfStack = this.irStack.peek();

        // 1) get all of the extern_args from the stack
        ArrayList<IrArg> argsList = new ArrayList<>();
        while (topOfStack instanceof IrArg) {
            IrArg externArg = (IrArg) this.irStack.pop();
            argsList.add(0, externArg);
            topOfStack = this.irStack.peek();
        }

        // 2) get the methodName (IrIdent)
        if (topOfStack instanceof IrIdent) {
            IrIdent methodName = (IrIdent) topOfStack;

            // 3) look up the method in scope to make sure it was declared
            if (this.scopeStack.checkIfSymbolExistsAtAnyScope(methodName.getValue())) {

                // a) determine if the method is an extern (or not)
                Ir object = this.scopeStack.getSymbol(methodName.getValue());
                if (object instanceof IrMethodDecl) {
                    IrMethodDecl method = (IrMethodDecl) object;
                    IrType returnType = method.getType();
                    List<IrParamDecl> paramsList = method.getParamsList();

                    // make sure the count and the types of the params and args match
                    if (argsList.size() == paramsList.size()) {
                        // Todo: make sure the types of the parameters match the types of the arguments for non-extern methods

                        // create the actual IrMethodCall and add it to the stack
                        IrMethodCall methodCall = new IrMethodCall(methodName, returnType, argsList);
                        this.irStack.push(methodCall);
                    }
                    else {System.err.print("exitMethod_call: number of IrParamDecls doesn't match number of passed IrArgs");}
                }
                else if (object instanceof IrExternDecl) {
                    IrExternDecl externMethod = (IrExternDecl) object;
                    IrType returnType = new IrTypeInt(l.line, l.col);

                    // we don't need to check whether the params are correct
                    // for IrExternDecl (or the number of args)

                    // create the actual IrMethodCall and add it to the stack
                    IrMethodCall externMethodCall = new IrMethodCall(methodName, returnType, argsList);
                    this.irStack.push(externMethodCall);
                }
                else {System.err.print("exitMethod_call: error with instanceof for type of object in the stack");}
            }
            else {System.err.print("exitMethod_call: method was not declared/ or is not in scopeStack");}
        }
        else {System.err.print("exitMethod_call: ID for methodName is not in irStack");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMethod_name(DecafParser.Method_nameContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMethod_name(DecafParser.Method_nameContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrIdent methodName = new IrIdent(ctx.ID().getText(), l.line, l.col);
        this.irStack.push(methodName);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVar_location(DecafParser.Var_locationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVar_location(DecafParser.Var_locationContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrIdent varName = new IrIdent(ctx.ID().getText(), l.line, l.col);

        // make sure that the variable has already been declared
        if (this.scopeStack.checkIfSymbolExistsAtAnyScope(varName.getValue())) {
            Ir object = this.scopeStack.getSymbol(varName.getValue());

            // make sure that the variable is actually a var and not
            // a methodCall or an array
            if (object instanceof IrFieldDeclVar) {
                IrFieldDeclVar var = (IrFieldDeclVar) object;

                // now we can get the type of the variable
                IrType varType = var.getType();

                // create the actual IrLocation now and add it to the stack
                IrLocationVar loc = new IrLocationVar(varName, varType, l.line, l.col);
                this.irStack.push(loc);
            }
            else {System.err.print("enterVarLocation: location is not of type IrFieldDecl. Possible syntax error");}
        }
        else {System.err.print("enterVarLocation: location accessed without being in any scope");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArray_location(DecafParser.Array_locationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArray_location(DecafParser.Array_locationContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // get the expr from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr expr = (IrExpr) this.irStack.pop();

            // make sure the expr is of type IrTypeInt so
            // that we can use it as an element index
            if (expr.getExpressionType() instanceof IrTypeInt) {
                IrTypeInt varType = (IrTypeInt) expr.getExpressionType();

                // look up the the variable to make sure it was declared
                IrIdent varName = new IrIdent(ctx.ID().getText(), l.line, l.col);
                if (this.scopeStack.checkIfSymbolExistsAtAnyScope(varName.getValue())) {
                    Ir object = this.scopeStack.getSymbol(varName.getValue());

                    // check to make sure that the variable is an array
                    if (object instanceof IrFieldDeclArray) {
                        IrFieldDeclArray fieldDeclArray = (IrFieldDeclArray) object;

                        // create the actual IrLocationArray and add it to irStack
                        IrLocationArray locOfArray = new IrLocationArray(expr, varName, varType, l.line, l.col);
                        this.irStack.push(locOfArray);
                    }
                    else {System.err.print("exitArrayLocation: object in scope is not an IrFieldDeclArray");}
                }
                else {System.err.print("exitArrayLocation: error with Ident; not found in scope");}
            }
            else {System.err.print("exitArrayLocation: error with IrExpr; not of type IrTypeInt");}
        }
        else {System.err.print("exitArrayLocation: object on top of stack not an IrExpr");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArithExpr(DecafParser.ArithExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArithExpr(DecafParser.ArithExprContext ctx) {
        // TODO: Fix for order of operations

        // 1) pop the rhs from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr rhs = (IrExpr) this.irStack.pop();

            // 2) pop the lhs from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr lhs = (IrExpr) this.irStack.pop();

                // 3) make sure rhs and lhs are both type IrTypeInt
                if (rhs.getExpressionType() instanceof IrTypeInt
                        && lhs.getExpressionType() instanceof IrTypeInt) {

                    // 4) get the type of ArithExpr frm the stack
                    if (ctx.arith_op().ADD_OP() != null) {
                        String addition = ctx.arith_op().ADD_OP().getText();

                        // 5) create the IrOperBinaryArith and add it irStack
                        IrOperBinaryArith arithExpr = new IrOperBinaryArith(addition, lhs, rhs);
                        this.irStack.push(arithExpr);
                    }
                    else if (ctx.arith_op().SUB_OP() != null) {
                        String subtraction = ctx.arith_op().SUB_OP().getText();

                        // 5) create the IrOperBinaryArith and add it irStack
                        IrOperBinaryArith arithExpr = new IrOperBinaryArith(subtraction, lhs, rhs);
                        this.irStack.push(arithExpr);
                    }
                    else if (ctx.arith_op().DIV_OP() != null) {
                        String division = ctx.arith_op().DIV_OP().getText();

                        // 5) create the IrOperBinaryArith and add it irStack
                        IrOperBinaryArith arithExpr = new IrOperBinaryArith(division, lhs, rhs);
                        this.irStack.push(arithExpr);
                    }
                    else if (ctx.arith_op().MUL_OP() != null) {
                        String multiplication = ctx.arith_op().MUL_OP().getText();

                        // 5) create the IrOperBinaryArith and add it irStack
                        IrOperBinaryArith arithExpr = new IrOperBinaryArith(multiplication, lhs, rhs);
                        this.irStack.push(arithExpr);
                    }
                    else if (ctx.arith_op().MOD_OP() != null) {
                        String modulus = ctx.arith_op().MOD_OP().getText();

                        // 5) create the IrOperBinaryArith and add it irStack
                        IrOperBinaryArith arithExpr = new IrOperBinaryArith(modulus, lhs, rhs);
                        this.irStack.push(arithExpr);
                    }
                    else {System.err.print("exitArithExpr: problem with determining exprType");}
                }
                else {System.err.print("exitArithExpr: rhs or lhs not IrTypeInt");}
            }
            else {System.err.print("exitArithExpr: lhs not on the stack");}
        }
        else {System.err.print("exitArithExpr: rhs not on the stack");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterRelExpr(DecafParser.RelExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitRelExpr(DecafParser.RelExprContext ctx) {
        // TODO: Fix for order of operations
        // 1) pop the rhs from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr rhs = (IrExpr) this.irStack.pop();

            // 2) pop the lhs from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr lhs = (IrExpr) this.irStack.pop();

                // 3) make sure rhs and lhs are both type IrTypeInt
                if (rhs.getExpressionType() instanceof IrTypeInt
                        && lhs.getExpressionType() instanceof IrTypeInt) {

                    // 4) get the type of RelExpr frm the stack
                    if (ctx.rel_op().GEQ_OP() != null) {
                        String greaterThanEqualTo = ctx.rel_op().GEQ_OP().getText();

                        // 5) create the IrOperBinaryRel and add it irStack
                        IrOperBinaryRel relExpr = new IrOperBinaryRel(greaterThanEqualTo, lhs, rhs);
                        this.irStack.push(relExpr);
                    }
                    else if (ctx.rel_op().LEQ_OP() != null) {
                        String lessThanEqualTo = ctx.rel_op().LEQ_OP().getText();

                        // 5) create the IrOperBinaryRel and add it irStack
                        IrOperBinaryRel relExpr = new IrOperBinaryRel(lessThanEqualTo, lhs, rhs);
                        this.irStack.push(relExpr);
                    }
                    else if (ctx.rel_op().LT_OP() != null) {
                        String lessThan = ctx.rel_op().LT_OP().getText();

                        // 5) create the IrOperBinaryRel and add it irStack
                        IrOperBinaryRel relExpr = new IrOperBinaryRel(lessThan, lhs, rhs);
                        this.irStack.push(relExpr);
                    }
                    else if (ctx.rel_op().GT_OP() != null) {
                        String greaterThan = ctx.rel_op().GT_OP().getText();

                        // 5) create the IrOperBinaryRel and add it irStack
                        IrOperBinaryRel relExpr = new IrOperBinaryRel(greaterThan, lhs, rhs);
                        this.irStack.push(relExpr);
                    }
                    else {System.err.print("enterRelExpr: problem with determining exprType");}
                }
                else {System.err.print("enterRelExpr: rhs or lhs not IrTypeInt");}
            }
            else {System.err.print("enterRelExpr: lhs not on the stack");}
        }
        else {System.err.print("enterRelExpr: rhs not on the stack");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNotExpr(DecafParser.NotExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNotExpr(DecafParser.NotExprContext ctx) {
        // pop the expression to be NOT-ed off the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr expr = (IrExpr) this.irStack.pop();

            // ensure that the expression is of IrTypeBool
            if (expr.getExpressionType() instanceof IrTypeBool) {

                // create the NOT-ed expression and add to irStack
                IrOperUnaryNot negatedExpr = new IrOperUnaryNot(expr);
                this.irStack.push(negatedExpr);
            }
            else {System.err.print("exitNotExpr: expr was not of type IrTypeBool");}
        }
        else {System.err.print("exitNotExpr: the object on top of the stack was an IrExpr");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterParenExpr(DecafParser.ParenExprContext ctx) {
        // TODO: Fix for order of operations
        // do nothing for right now
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitParenExpr(DecafParser.ParenExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNonVoidMethodCall(DecafParser.NonVoidMethodCallContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNonVoidMethodCall(DecafParser.NonVoidMethodCallContext ctx) {
        // 1) get the method from the stack (but DON'T pop it)
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrMethodCall) {
            IrMethodCall method = (IrMethodCall) topOfStack;
            IrType methodType = method.getExpressionType();

            // 2) make sure that its type is either IrTypeInt or IrTypeBool
            if (methodType instanceof IrTypeBool || methodType instanceof IrTypeInt) {
                // Good!
            }
            else {
                // Uh oh!! Report an error
                System.err.print("exitNonVoidMethodCall: there was a Void method where it shouldn't have been");
            }
        }
        else {System.err.print("exitNonVoidMethodCall: no IrMethodCall on top of stack");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterEquateExpr(DecafParser.EquateExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEquateExpr(DecafParser.EquateExprContext ctx) {
        // 1) get the RHS expr from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr rhsExpr = (IrExpr) this.irStack.pop();

            // 2) get the LHS epxr from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr lhsExpr = (IrExpr) this.irStack.pop();

                // 3) make sure that both IrExpr are of the same IrType
                if (rhsExpr.getExpressionType() == lhsExpr.getExpressionType()) {

                    // 4) determine the type of equality (!= or ==)
                    if (ctx.eq_op().EQ_OP() != null) {
                        String equalsEquals = ctx.eq_op().EQ_OP().getText();

                        // 5) create the IrOperBinaryEq and add it irStack
                        IrOperBinaryEq equalsEqualsExpr = new IrOperBinaryEq(equalsEquals, lhsExpr, rhsExpr);
                        this.irStack.push(equalsEqualsExpr);
                    }
                    else if (ctx.eq_op().NEQ_OP() != null) {
                        String notEquals = ctx.eq_op().EQ_OP().getText();

                        // 5) create the IrOperBinaryEq and add it irStack
                        IrOperBinaryEq notEqualsExpr = new IrOperBinaryEq(notEquals, lhsExpr, rhsExpr);
                        this.irStack.push(notEqualsExpr);
                    }
                    else {System.err.print("exitEquateExpr: problem determining type of equality expr");}
                }
                else {System.err.print("exitEquateExpr: rhs and lhs don't have the same IrType");}
            }
            else {System.err.print("exitEquateExpr: top of stack isn't IrExpr (for lhs expr)");}
        }
        else {System.err.print("exitEquateExpr: top of stack isn't IrExpr (for rhs expr)");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterCondExpr(DecafParser.CondExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCondExpr(DecafParser.CondExprContext ctx) {
        // TODO: Fix for order of operations
        // 1) pop the rhs from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr rhs = (IrExpr) this.irStack.pop();

            // 2) pop the lhs from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr lhs = (IrExpr) this.irStack.pop();

                // 3) make sure rhs and lhs are both type IrTypeBool
                if (rhs.getExpressionType() instanceof IrTypeBool
                        && lhs.getExpressionType() instanceof IrTypeBool) {

                    // 4) get the type of CondExpr frm the stack
                    if (ctx.cond_op().AND_OP() != null) {
                        String exprType = ctx.cond_op().AND_OP().getText();

                        // 5) create the IrOperBinaryCond expr and push to irstack
                        IrOperBinaryCond condExpr = new IrOperBinaryCond(exprType, lhs, rhs);
                        this.irStack.push(condExpr);
                    }
                    else if (ctx.cond_op().OR_OP() != null) {
                        String exprType = ctx.cond_op().OR_OP().getText();

                        // 5) create the IrOperBinaryCond expr and push to irstack
                        IrOperBinaryCond condExpr = new IrOperBinaryCond(exprType, lhs, rhs);
                        this.irStack.push(condExpr);
                    }
                    else {System.err.print("exitCondExpr: problem with determining exprType");}
                }
                else {System.err.print("exitCondExpr: rhs or lhs not IrTypeBool");}
            }
            else {System.err.print("exitCondExpr: lhs not on the stack");}
        }
        else {System.err.print("exitCondExpr: rhs not on the stack");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNegateExpr(DecafParser.NegateExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNegateExpr(DecafParser.NegateExprContext ctx) {
        // pop the expression to be negated off the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr expr = (IrExpr) this.irStack.pop();

            // ensure that the expression is of IrTypeInt
            if (expr.getExpressionType() instanceof IrTypeInt) {

                // create the negated expression and add to irStack
                IrOperUnaryNeg negatedExpr = new IrOperUnaryNeg(expr);
                this.irStack.push(negatedExpr);
            }
            else {System.err.print("exitNegateExpr: expr is not of type IrTypeInt");}
        }
        else {System.err.print("exitNegateExpr: object on top of stack was not IrExpr");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSizeOfVar(DecafParser.SizeOfVarContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSizeOfVar(DecafParser.SizeOfVarContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // 1) look up the location that corresponds to the ID in the
        // sizeof expression (int, bool, or array)
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrIdent) {
            IrIdent varName = (IrIdent) this.irStack.pop();

            // check that the var is in the stack (has already been declared)
            if (this.scopeStack.checkIfSymbolExistsAtAnyScope(varName.getValue())) {
                Ir object = this.scopeStack.getSymbol(varName.getValue());

                // make sure the object is an IrFieldDecl (array or var)
                if (object instanceof IrFieldDecl) {
                    IrFieldDecl field = (IrFieldDecl) object;
                    IrType fieldType = field.getType();

                    // create the IrSizeOfLocation and add it to the stack
                    IrSizeOfLocation sizeOfField = new IrSizeOfLocation(field, l.line, l.col);
                    this.irStack.push(sizeOfField);
                }
                else {System.err.print("enterSizeOfVar: sizeof object was not an IrFieldDecl");}
            }
            else {System.err.print("enterSizeOfVar: sizeof object was in the scope");}
        }
        else {System.err.print("enterSizeOfVar: sizeof was has no argument (ID)");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSizeOfType(DecafParser.SizeOfTypeContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSizeOfType(DecafParser.SizeOfTypeContext ctx) {
        // get the type argument from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrTypeBool || topOfStack instanceof IrTypeInt) {
            IrType type = (IrType) this.irStack.pop();

            // create the IrSizeOfType object and add it to irStack
            IrSizeOfType sizeOfType = new IrSizeOfType(type);
            this.irStack.push(sizeOfType);
        }
        else { System.err.print("exitSizeOfType: argument for sizeof is not IrTypeBool or IrTypeInt");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArgExpr(DecafParser.ArgExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArgExpr(DecafParser.ArgExprContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // get the type argument from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr expr = (IrExpr) this.irStack.pop();

            // create the IrArgExpr object and add it to irStack
            IrArgExpr argExpr = new IrArgExpr(expr, l.line, l.col);
            this.irStack.push(argExpr);
        }
        else { System.err.print("exitArgExpr: object in the stack was not an IrExpr");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArgString(DecafParser.ArgStringContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArgString(DecafParser.ArgStringContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // check to make sure that a string was provided
        if (ctx.STRING() != null) {
            String string = ctx.STRING().getText();

            // create the IrArgString and add it to irStack
            IrArgString irArgString = new IrArgString(string, l.line, l.col);
            this.irStack.push(irArgString);
        }
        else { System.err.print("exitArgString: no string was found in ctx");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLiteral(DecafParser.LiteralContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLiteral(DecafParser.LiteralContext ctx) {
        DecafListener.ProgramLocation l = new ProgramLocation(ctx);

        // 1) check which type of literal it is
        if (ctx.INT() != null) {
            int intValue = Integer.parseInt(ctx.INT().getText());
            IrLiteralInt intLiteral = new IrLiteralInt(intValue, l.line, l.col);
            this.irStack.push(intLiteral);
        }
        else if (ctx.BOOL() != null) {
            boolean boolValue = Boolean.parseBoolean(ctx.BOOL().getText());
            IrLiteralBool boolLiteral = new IrLiteralBool(boolValue, l.line, l.col);
            this.irStack.push(boolLiteral);
        }
        else if (ctx.CHAR() != null) {
            char charValue = ctx.CHAR().getText().charAt(0);
            IrLiteralChar charLiteral = new IrLiteralChar(charValue, l.line, l.col);
            this.irStack.push(charLiteral);
        }
        else {System.err.print("exitLiteral: the ctx did not have a CHAR, BOOL, or INT");}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterEveryRule(ParserRuleContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEveryRule(ParserRuleContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void visitTerminal(TerminalNode node) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void visitErrorNode(ErrorNode node) { }

    class ProgramLocation {
        public final int line;
        public final int col;
        public ProgramLocation(ParserRuleContext ctx) {
            this.line = ctx.getStart().getLine();
            this.col = ctx.getStart().getCharPositionInLine();
        }
    }

}

