package edu.mit.compilers;

import edu.mit.compilers.grammar.*;
import edu.mit.compilers.ir.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;




public class DecafListener extends DecafParserBaseListener {
    private Stack<Ir> irStack = new Stack<>();
    private boolean errorFlag = false;
    private String errorMessage = "";

    public boolean detectedSemanticErrors() {
        return this.errorFlag;
    }

    @Override public void enterProgram(DecafParser.ProgramContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitProgram(DecafParser.ProgramContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        ArrayList<IrFieldDecl> fieldDecls = new ArrayList<>();
        ArrayList<IrMethodDecl> methodDecls = new ArrayList<>();
        ArrayList<IrExternDecl> externDecls = new ArrayList<>();




        // 1) method_decls will be on the top of the stack so collect them
        // first
        while (this.irStack.size() > 0 && this.irStack.peek() instanceof IrMethodDecl) {
            IrMethodDecl newMethodDecl = (IrMethodDecl) this.irStack.pop();
            methodDecls.add(newMethodDecl);
        }

        // 2) now collect the field_decls
        while (this.irStack.size() > 0 && this.irStack.peek() instanceof IrFieldDecl) {
            IrFieldDecl newFieldDecl = (IrFieldDecl) this.irStack.pop();
            fieldDecls.add(newFieldDecl);
        }

        // 3) lastly, collect the extern_decls
        while (this.irStack.size() > 0 && this.irStack.peek() instanceof IrExternDecl) {
            IrExternDecl newExternDecl = (IrExternDecl) this.irStack.pop();
            externDecls.add(newExternDecl);
        }

        if (this.irStack.size() != 0) {
            this.errorMessage += "irStack not empty after reaching end of program\n";
        }

        // check the semantics of the final program
        IrProgram wholeProgram = new IrProgram(fieldDecls, methodDecls, externDecls, this.errorMessage, l.line, l.col);

        this.errorMessage += wholeProgram.semanticCheck(new ScopeStack());
        if (!errorMessage.equals("")) {
            this.errorFlag = true;
            System.err.println(errorMessage);
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExtern_decl(DecafParser.Extern_declContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        IrIdent externName = new IrIdent(ctx.ID().getText(), l.line, l.col);
        this.irStack.push(externName);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExtern_decl(DecafParser.Extern_declContext ctx) {
        // pop the extern name (IrIdent) from the top of the stack
        if (this.irStack.size() > 0 && this.irStack.peek() instanceof IrIdent) {
            IrIdent irIdent = (IrIdent) this.irStack.pop();
            IrExternDecl externDecl = new IrExternDecl(irIdent);
            this.irStack.push(externDecl);
        }
        else {this.errorMessage += "enterExtern_decl: popped object of wrong type\n";}
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
        if (this.irStack.peek() instanceof IrType) {
            // pop the IrType because we are done creating fields
            this.irStack.pop();
        }
        else {this.errorMessage += "exitField_decl: missing IrType from stack\n";}
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
            this.irStack.push(newVar);

            // put the varType back on the stack in case there are more fields
            // after this one
            this.irStack.push(varType);
        }
        else {this.errorMessage += "exitVarDecl: error with IrType of newVar\n";}
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
        int arraySize = Integer.parseInt(ctx.INT().getText());

        if (this.irStack.size() > 0 && this.irStack.peek() instanceof IrType) {
            // pop the arrayType so we know what type of var this is
            IrType arrayType = (IrType) this.irStack.pop();
            IrFieldDeclArray newArray = new IrFieldDeclArray(arraySize, arrayName, arrayType);
            this.irStack.push(newArray);

            // put the arrayType back on the stack in case there are more fields
            // after this one
            this.irStack.push(arrayType);
        }
        else {this.errorMessage += "exitArrayDecl: error with IrType of newArray\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMethod_decl(DecafParser.Method_declContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

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

            // 3) pop the method_type
            if (topOfStack instanceof IrType) {
                IrType methodType = (IrType) this.irStack.pop();
                topOfStack = this.irStack.peek(); // update topOfStack

                // 4) pop the ID (for the method name)
                if (topOfStack instanceof IrIdent) {
                    IrIdent methodName = (IrIdent) this.irStack.pop();
                    IrMethodDecl newMethod = new IrMethodDecl(methodType, paramsList, block, methodName);
                    AssemblyBuilder asm = new AssemblyBuilder();
                    Register reg = new Register();
                    StackFrame frame = new StackFrame();
                    newMethod.generateCode(asm, reg, frame);
                    System.out.println(asm.generateAssembly());
                    this.irStack.push(newMethod);
                }
                else {this.errorMessage += "exitMethod_decl: error with IrType for methodType\n";}
            }
            else {this.errorMessage += "exitMethod_decl: error with IrIdent for methodName\n";}
        }
        else {this.errorMessage += "exitMethod_decl: error with IrCodeBlock for block\n";}
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

        if (this.irStack.size() > 0 && this.irStack.peek() instanceof IrType) {
            IrType paramType = (IrType) this.irStack.pop();
            IrParamDecl paramDecl = new IrParamDecl(paramType, paramName);
            this.irStack.push(paramDecl);
        }
        else {this.errorMessage += "exitParam_decl: error with IrTypeVar for paramType\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBlock(DecafParser.BlockContext ctx) { }
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
        while (this.irStack.size() > 0 && this.irStack.peek() instanceof IrStatement) {
            IrStatement statement = (IrStatement) this.irStack.pop();
            statementsList.add(0, statement);
        }

        // 2) pop all of the field_decl's
        ArrayList<IrFieldDecl> fieldDeclsList = new ArrayList<>();
        while (this.irStack.size() > 0 && this.irStack.peek() instanceof IrFieldDecl) {
            IrFieldDecl fieldDecl = (IrFieldDecl) this.irStack.pop();
            fieldDeclsList.add(0, fieldDecl);
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

                // 4) determine the assignment operator then
                if (ctx.assign_op().AS_OP() != null) { // location = expr

                    // 5) create the IrAssignStmt and add it to the stack
                    IrAssignStmt assignStmt = new IrAssignStmtEqual(loc, expr);
                    this.irStack.push(assignStmt);
                }
                else if (ctx.assign_op().compound_assign_op().ADD_AS_OP() != null) { // location += expr

                    // 6) create the IrAssignStmt and add it to the stack
                    IrAssignStmtPlusEqual plusEqual = new IrAssignStmtPlusEqual(loc, expr);
                    this.irStack.push(plusEqual);
                }
                else if (ctx.assign_op().compound_assign_op().SUB_AS_OP() != null) { // location -= expr

                    // 6) create the IrAssignStmt and add it to the stack
                    IrAssignStmtMinusEqual minusEqual = new IrAssignStmtMinusEqual(loc, expr);
                    this.irStack.push(minusEqual);
                }
                else {this.errorMessage += "exitAssignStmt: problem with type of IrAssignStatement\n";}
            }
            else {this.errorMessage += "exitAssignStmt: IrLocation missing from top of stack\n";}
        }
        else {this.errorMessage += "exitAssignStmt: IrExpression missing from top of stack\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAnyMethodCall(DecafParser.AnyMethodCallContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAnyMethodCall(DecafParser.AnyMethodCallContext ctx) {
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
            IrIdent methodName = (IrIdent) this.irStack.pop();

            // create the actual IrMethodCallStmt and add it to the stack
            IrMethodCallStmt methodCall = new IrMethodCallStmt(methodName, argsList);
            this.irStack.push(methodCall);
        }
        else {this.errorMessage += "exitAnyMethodCall: ID for methodName is not in irStack\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterIf_stmt(DecafParser.If_stmtContext ctx) { }
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

                // 4) create the IrCtrlFlowIf and add it to irStack
                IrCtrlFlowIf ifStmt = new IrCtrlFlowIf(ifCondition, ifBody);
                this.irStack.push(ifStmt);
            }
            else {this.errorMessage += "exitIf_stmt: top of stack is not an IrExpr\n";}
        }
        else {this.errorMessage += "exitIf_stmt: top of stack is not a IrCodeBlock\n";}
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

            // 2) grab the ifStmt from the stack (which includes the if-stmt code block)
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrCtrlFlowIf) {
                IrCtrlFlowIf ifStmt = (IrCtrlFlowIf) this.irStack.pop();

                // 3) create the ifElse stmt and add it to the stack
                IrCtrlFlowIfElse ifElseStmt = new IrCtrlFlowIfElse(ifStmt, elseBody);
                this.irStack.push(ifElseStmt);
            }
            else {this.errorMessage += "exitIfAndElseStmt: top of stack is not an IrCtrlFlowIf\n";}
        }
        else {this.errorMessage += "exitIfAndElseStmt: top of stack is not a CodeBlock\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterElse_stmt(DecafParser.Else_stmtContext ctx) {
        // 1) add an IrResWordElse to the stack (which is not an IrStatement)
        // this will prevent the else block from swallowing the IrCtrlStmtIf
        // that comes before the else stmt in an IrCtrlFlowIfElse
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrResWordElse resWorldElse = new IrResWordElse(l.line, l.col);
        this.irStack.push(resWorldElse);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitElse_stmt(DecafParser.Else_stmtContext ctx) {
        // 1) top of stack will be the else body IrCodeBlock so pop this
        if (this.irStack.peek() instanceof IrCodeBlock) {
            IrCodeBlock elseBlock = (IrCodeBlock) this.irStack.pop();

            // 2) top of stack should be the IrResWord so pop this next
            if (this.irStack.peek() instanceof IrResWordElse) {
                this.irStack.pop(); // just pop this we don't care about saving it

                // 3) put the IrCodeBlock back on the stack
                this.irStack.push(elseBlock);
            }
            else {this.errorMessage += "exitElse_stmt: IrResWordElse not on top of stack\n";}
        }
        else {this.errorMessage += "exitElse_stmt: else IrCodeBlock not on stack\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterForLoop(DecafParser.ForLoopContext ctx) { }
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

                // 5) pop the var_location for the incrementer expr
                topOfStack = this.irStack.peek();
                if (topOfStack instanceof IrLocationVar) {
                    IrLocationVar compoundAssignLocation = (IrLocationVar) this.irStack.pop();

                    // 9) pop the IrExpr for the loop condition
                    topOfStack = this.irStack.peek();
                    if (topOfStack instanceof IrExpr) {
                        IrExpr conditionExpr = (IrExpr) this.irStack.pop();

                        // 11) pop the start incrementer IrExpr from the stack
                        topOfStack = this.irStack.peek();
                        if (topOfStack instanceof IrExpr) {
                            IrExpr startingExpValue = (IrExpr) this.irStack.pop();

                            // 13) pop the next var_location
                            topOfStack = this.irStack.peek();
                            if (topOfStack instanceof IrLocationVar) {
                                IrLocationVar regularAssignLocation = (IrLocationVar) this.irStack.pop();

                                // 16) determine the type of the compound_assign_op (+= or -=)
                                if (ctx.compound_assign_op().SUB_AS_OP() != null) {
                                    IrAssignStmtMinusEqual minusEqual = new IrAssignStmtMinusEqual(compoundAssignLocation, incrementerExpr);

                                    // 17) create the IrCtrlFlowFor and add it to the stack
                                    IrCtrlFlowFor forLoop = new IrCtrlFlowFor(regularAssignLocation, startingExpValue, minusEqual, conditionExpr, forLoopBody);
                                    this.irStack.push(forLoop);
                                } else if (ctx.compound_assign_op().ADD_AS_OP() != null) {
                                    IrAssignStmtPlusEqual plusEqual = new IrAssignStmtPlusEqual(compoundAssignLocation, incrementerExpr);

                                    // 17) create the IrCtrlFlowFor and add it to the stack
                                    IrCtrlFlowFor forLoop = new IrCtrlFlowFor(regularAssignLocation, startingExpValue, plusEqual, conditionExpr, forLoopBody);
                                    this.irStack.push(forLoop);
                                }
                                else {this.errorMessage += "exitForLoop: problem with identifying type of compound_assign_op\n";}
                            }
                            else {this.errorMessage += "exitForLoop: regular assignment var location\n";}
                        }
                        else {this.errorMessage += "exitForLoop: starting incrementer expression not found on stack\n";}
                    }
                    else {this.errorMessage += "exitForLoop: condition expr not found on stack\n";}
                }
                else {this.errorMessage += "exitForLoop: var location for compound assign expr not found on stack\n";}
            }
            else {this.errorMessage += "exitForLoop: compound assign incrementer expr not found on stack\n";}
        }
        else {this.errorMessage += "exitForLoop: for loop body not found on stack\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterWhileLoop(DecafParser.WhileLoopContext ctx) { }
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

                // 4) create the IrCtrlFlowWhile and add it to irStack
                IrCtrlFlowWhile whileStmt = new IrCtrlFlowWhile(whileCondition, whileBody);
                this.irStack.push(whileStmt);
            }
            else {this.errorMessage += "exitWhileLoop: top of stack is not an IrExpr\n";}
        }
        else {this.errorMessage += "exitWhileLoop: top of stack is not a IrCodeBlock\n";}
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
        else {this.errorMessage += "exitReturnExprStmt: problem with IrExpr on topOfStack\n";}
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
    @Override public void exitMethod_call(DecafParser.Method_callContext ctx) { }
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

        // create the actual IrLocation now and add it to the stack
        IrLocationVar loc = new IrLocationVar(varName, l.line, l.col);
        this.irStack.push(loc);
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
            IrIdent varName = new IrIdent(ctx.ID().getText(), l.line, l.col);

            // create the actual IrLocationArray and add it to irStack
            IrLocationArray locOfArray = new IrLocationArray(expr, varName, l.line, l.col);
            this.irStack.push(locOfArray);
        }
        else {this.errorMessage += "exitArrayLocation: object on top of stack not an IrExpr\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArithMulExpr(DecafParser.ArithMulExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArithMulExpr(DecafParser.ArithMulExprContext ctx) {
        // TODO: Fix for order of operations

        // 1) pop the rhs from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr rhs = (IrExpr) this.irStack.pop();

            // 2) pop the lhs from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr lhs = (IrExpr) this.irStack.pop();

                if (ctx.arith_op_mul().DIV_OP() != null) {
                    String division = ctx.arith_op_mul().DIV_OP().getText();

                    // 5) create the IrOperBinaryArith and add it irStack
                    IrOperBinaryArith arithExpr = new IrOperBinaryArith(division, lhs, rhs);
                    this.irStack.push(arithExpr);
                }
                else if (ctx.arith_op_mul().MUL_OP() != null) {
                    String multiplication = ctx.arith_op_mul().MUL_OP().getText();

                    // 5) create the IrOperBinaryArith and add it irStack
                    IrOperBinaryArith arithExpr = new IrOperBinaryArith(multiplication, lhs, rhs);
                    this.irStack.push(arithExpr);
                }
                else if (ctx.arith_op_mul().MOD_OP() != null) {
                    String modulus = ctx.arith_op_mul().MOD_OP().getText();

                    // 5) create the IrOperBinaryArith and add it irStack
                    IrOperBinaryArith arithExpr = new IrOperBinaryArith(modulus, lhs, rhs);
                    this.irStack.push(arithExpr);
                }
                else {this.errorMessage += "exitArithMulExpr: problem with determining exprType\n";}
            }
            else {this.errorMessage += "exitArithMulExpr: lhs not on the stack\n";}
        }
        else {this.errorMessage += "exitArithMulExpr: rhs not on the stack\n";}
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArithAddExpr(DecafParser.ArithAddExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArithAddExpr(DecafParser.ArithAddExprContext ctx) {
        // TODO: Fix for order of operations

        // 1) pop the rhs from the stack
        Ir topOfStack = this.irStack.peek();
        if (topOfStack instanceof IrExpr) {
            IrExpr rhs = (IrExpr) this.irStack.pop();

            // 2) pop the lhs from the stack
            topOfStack = this.irStack.peek();
            if (topOfStack instanceof IrExpr) {
                IrExpr lhs = (IrExpr) this.irStack.pop();

                // 4) get the type of ArithExpr frm the stack
                if (ctx.arith_op_add().ADD_OP() != null) {
                    String addition = ctx.arith_op_add().ADD_OP().getText();

                    // 5) create the IrOperBinaryArith and add it irStack
                    IrOperBinaryArith arithExpr = new IrOperBinaryArith(addition, lhs, rhs);
                    this.irStack.push(arithExpr);
                }
                else if (ctx.arith_op_add().SUB_OP() != null) {
                    String subtraction = ctx.arith_op_add().SUB_OP().getText();

                    // 5) create the IrOperBinaryArith and add it irStack
                    IrOperBinaryArith arithExpr = new IrOperBinaryArith(subtraction, lhs, rhs);
                    this.irStack.push(arithExpr);
                }
                else {this.errorMessage += "exitArithAddExpr: problem with determining exprType\n";}
            }
            else {this.errorMessage += "exitArithAddExpr: lhs not on the stack\n";}
        }
        else {this.errorMessage += "exitArithAddExpr: rhs not on the stack\n";}
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
                else {this.errorMessage += "enterRelExpr: problem with determining exprType\n";}
            }
            else {this.errorMessage += "enterRelExpr: lhs not on the stack\n";}
        }
        else {this.errorMessage += "enterRelExpr: rhs not on the stack\n";}
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

            // create the NOT-ed expression and add to irStack
            IrOperUnaryNot negatedExpr = new IrOperUnaryNot(expr);
            this.irStack.push(negatedExpr);
        }
        else {this.errorMessage += "exitNotExpr: the object on top of the stack was an IrExpr\n";}
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
            IrIdent methodName = (IrIdent) this.irStack.pop();

            // create the actual IrMethodCallExpr and add it to the stack
            IrMethodCallExpr methodCall = new IrMethodCallExpr(methodName, argsList);
            this.irStack.push(methodCall);
        }
        else {this.errorMessage += "exitNonVoidMethodCall: ID for methodName is not in irStack\n";}
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

                // 4) determine the type of equality (!= or ==)
                if (ctx.eq_op().EQ_OP() != null) {
                    String equalsEquals = ctx.eq_op().EQ_OP().getText();

                    // 5) create the IrOperBinaryEq and add it irStack
                    IrOperBinaryEq equalsEqualsExpr = new IrOperBinaryEq(equalsEquals, lhsExpr, rhsExpr);
                    this.irStack.push(equalsEqualsExpr);
                }
                else if (ctx.eq_op().NEQ_OP() != null) {
                    String notEquals = ctx.eq_op().NEQ_OP().getText();

                    // 5) create the IrOperBinaryEq and add it irStack
                    IrOperBinaryEq notEqualsExpr = new IrOperBinaryEq(notEquals, lhsExpr, rhsExpr);
                    this.irStack.push(notEqualsExpr);
                }
                else {this.errorMessage += "exitEquateExpr: problem determining type of equality expr\n";}
            }
            else {this.errorMessage += "exitEquateExpr: top of stack isn't IrExpr (for lhs expr)\n";}
        }
        else {this.errorMessage += "exitEquateExpr: top of stack isn't IrExpr (for rhs expr)\n";}
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

                // 4) get the type of CondExpr frm the stack
                if (ctx.AND_OP() != null) {
                    String exprType = ctx.AND_OP().getText();

                    // 5) create the IrOperBinaryCond expr and push to irstack
                    IrOperBinaryCond condExpr = new IrOperBinaryCond(exprType, lhs, rhs);
                    this.irStack.push(condExpr);
                }
                else if (ctx.OR_OP() != null) {
                    String exprType = ctx.OR_OP().getText();

                    // 5) create the IrOperBinaryCond expr and push to irstack
                    IrOperBinaryCond condExpr = new IrOperBinaryCond(exprType, lhs, rhs);
                    this.irStack.push(condExpr);
                }
                else {this.errorMessage += "exitCondExpr: problem with determining exprType\n";}
            }
            else {this.errorMessage += "exitCondExpr: lhs not on the stack\n";}
        }
        else {this.errorMessage += "exitCondExpr: rhs not on the stack\n";}
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

            // create the negated expression and add to irStack
            IrOperUnaryNeg negatedExpr = new IrOperUnaryNeg(expr);
            this.irStack.push(negatedExpr);
        }
        else {this.errorMessage += "exitNegateExpr: object on top of stack was not IrExpr\n";}
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
        IrIdent varName = new IrIdent(ctx.ID().getText(), l.line, l.col);

        // create the IrSizeOfLocation and add it to the stack
        IrSizeOfLocation sizeOfField = new IrSizeOfLocation(varName, l.line, l.col);
        this.irStack.push(sizeOfField);
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
        else { this.errorMessage += "exitSizeOfType: argument for sizeof is not IrTypeBool or IrTypeInt\n";}
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
        else { this.errorMessage += "exitArgExpr: object in the stack was not an IrExpr\n";}
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
        else { this.errorMessage += "exitArgString: no string was found in ctx\n";}
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
            String intString = ctx.INT().getText();

            // remove the suffix "ll" if it exists
            if (intString.contains("ll")) {
                intString = intString.substring(0, intString.length()-2);
            }

            // for 0x "hex" ints
            if (intString.contains("0x")) {
                intString = intString.substring(2);

                // 2) handle case for really large integer overflow
                try {
                    long hexInt = Long.parseLong(intString, 16);
                    IrLiteralInt intLiteral = new IrLiteralInt(hexInt, l.line, l.col);
                    this.irStack.push(intLiteral);
                }
                catch (NumberFormatException exception) {
                    this.errorMessage += ("int literal overflow line: " + l.line + " col: " + l.col + "\n");

                    // this is a dummy intLiteral to avoid disrupting the irStack
                    IrLiteralInt intLiteral = new IrLiteralInt(-69, l.line, l.col);
                    this.irStack.push(intLiteral);
                }
            }
            // for decimal ints
            else {
                // 2) handle case for really large integer overflow
                try {
                    long decimalInt = Long.parseLong(intString);
                    IrLiteralInt intLiteral = new IrLiteralInt(decimalInt, l.line, l.col);
                    this.irStack.push(intLiteral);
                }
                catch (NumberFormatException exception) {
                    this.errorMessage += ("int literal overflow line: " + l.line + " col: " + l.col + "\n");

                    // this is a dummy intLiteral to avoid disrupting the irStack
                    IrLiteralInt intLiteral = new IrLiteralInt(-69, l.line, l.col);
                    this.irStack.push(intLiteral);
                }
            }
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
        else {this.errorMessage += "exitLiteral: the ctx did not have a CHAR, BOOL, or INT\n";}
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

