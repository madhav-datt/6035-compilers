// Generated from /Users/abel/Desktop/Fall2016/6.035/6035-compilers/src/edu/mit/compilers/grammar/DecafParser.g4 by ANTLR 4.5.3

package edu.mit.compilers;

import edu.mit.compilers.grammar.*;
import edu.mit.compilers.ir.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
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
        // TODO: collect all of the extern_decl, field_decl, and method_decl
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExtern_decl(DecafParser.Extern_declContext ctx) {

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExtern_decl(DecafParser.Extern_declContext ctx) {
        Ir irObject = this.irStack.pop();

        // assumes an instance of IrIdent is on the stack
        if (irObject instanceof IrIdent) {
            IrIdent irIdent = (IrIdent) irObject;
            IrExternDecl externDecl = new IrExternDecl(irIdent);
            declareInCurrentScopeOrReportDuplicateDecl(
                    irIdent.getValue(),
                    externDecl,
                    "enterExtern_decl: same extern declared twice"
            );
        }
        else {
            System.err.print(
                    "enterExtern_decl: popped object of wrong type"
            );
        }
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
        ArrayList<IrFieldDecl> fieldsList = new ArrayList<>();

        // Get each field from the irStack. When we reach the IrTypeVar
        // object in the stack, we have collected all of the fields
        Ir topOfStack = this.irStack.peek();
        while (this.irStack.size() > 0 && topOfStack instanceof IrFieldDecl) {
            IrFieldDecl newField = (IrFieldDecl) this.irStack.pop();
            fieldsList.add(newField);
            topOfStack = this.irStack.peek();
        }

        // the object on the stack should be a IrTypeVar object
        if (this.irStack.size() > 0 && topOfStack instanceof IrTypeVar) {
            IrTypeVar fieldsType = (IrTypeVar) this.irStack.pop();

            // loop through list in reverse to maintain original order
            // push() each field back to irStack. add() each field to scopeStack
            for (int i = fieldsList.size()-1; i >= 0; i--) {
                IrFieldDecl field = fieldsList.get(i);
                field.setType(fieldsType);
                declareInCurrentScopeOrReportDuplicateDecl(
                        field.getName(),
                        field,
                        "exitField_decl: duplicate field declared in same scope"
                );
            }
        }
        else {
            System.err.print("exitField_decl: error with IrTypeVar of newFields");
        }
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
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVarDecl(DecafParser.VarDeclContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrIdent varName = new IrIdent(ctx.ID().getText(), l.line, l.col);

        IrFieldDeclVar var = new IrFieldDeclVar(varName);
        this.irStack.push(var);
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

        IrFieldDeclArray array = new IrFieldDeclArray(arraySize, arrayName);
        this.irStack.push(array);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMethod_decl(DecafParser.Method_declContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        // When a method gets declared, we need to create a new scope for it
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

            // 2) pop the list of param_decl's
            ArrayList<IrMethodParamDecl> paramsList = new ArrayList<>();
            while (this.irStack.size() > 0 && topOfStack instanceof IrMethodParamDecl) {
                IrMethodParamDecl paramDecl = (IrMethodParamDecl) this.irStack.pop();
                paramsList.add(paramDecl);
                topOfStack = this.irStack.peek(); // update topOfStack
            }

            // 3) pop the id
            if (topOfStack instanceof IrIdent) {
                IrIdent methodName = (IrIdent) this.irStack.pop();
                topOfStack = this.irStack.peek(); // update topOfStack

                // 4) pop the method_type
                if (topOfStack instanceof IrTypeMethod) {
                    IrTypeMethod methodType = (IrTypeMethod) this.irStack.pop();
                    IrMethodDecl newMethod = new IrMethodDecl(methodType, paramsList, block, methodName);
                    declareInCurrentScopeOrReportDuplicateDecl(
                            methodName.getValue(),
                            newMethod,
                            "exitMethod_decl: duplicate method in same scope"
                    );
                }
                else {
                    System.err.print("exitMethod_decl: error with IrTypeMethod for methodType");
                }
            }
            else {
                System.err.print("exitMethod_decl: error with IrIdent for methodName");
            }
        }
        else {
            System.err.print("exitMethod_decl: error with IrCodeBlock for block");
        }
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
        if (this.irStack.size() > 0 && topOfStack instanceof IrTypeVar) {
            IrTypeVar paramType = (IrTypeVar) this.irStack.pop();

            // push the IrMethodParamDecl to the irStack
            IrMethodParamDecl paramDecl = new IrMethodParamDecl(paramType, paramName);
            this.irStack.push(paramDecl);
        }
        else {
            System.err.print("exitParam_decl: error with IrTypeVar for paramType");
        }
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
        Ir topOfStack = this.irStack.peek();
        while (this.irStack.size() > 0 && topOfStack instanceof IrStatement) {
            IrStatement statement = (IrStatement) this.irStack.pop();
            statementsList.add(statement);
            topOfStack = this.irStack.peek(); // update topOfStack
        }

        // 2) pop all of the field_decl's
        ArrayList<IrFieldDecl> fieldDeclsList = new ArrayList<>();
        while (this.irStack.size() > 0 && topOfStack instanceof IrFieldDecl) {
            IrFieldDecl fieldDecl = (IrFieldDecl) this.irStack.pop();
            fieldDeclsList.add(fieldDecl);
            topOfStack = this.irStack.peek();
        }

        // 3) Create the actual IrCodeBlock and add it to the stack
        IrCodeBlock newBlock = new IrCodeBlock(fieldDeclsList, statementsList, l.line, l.col);
        this.irStack.add(newBlock);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVar_type(DecafParser.Var_typeContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        IrTypeVar type;
        if (ctx.RES_BOOL() != null) {
            type = new IrTypeVarBool(l.line, l.col);
        }
        else {
            type = new IrTypeVarInt(l.line, l.col);
        }

        this.irStack.push(type);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMethod_type(DecafParser.Method_typeContext ctx) {
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);

        IrTypeMethod type;
        if (ctx.RES_BOOL() != null) {
            type = new IrTypeMethodBool(l.line, l.col);
        }
        else if (ctx.RES_INT() != null) {
            type = new IrTypeMethodInt(l.line, l.col);
        }
        else {
            type = new IrTypeMethodVoid(l.line, l.col);
        }

        this.irStack.push(type);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterStatement(DecafParser.StatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitStatement(DecafParser.StatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAssign_op(DecafParser.Assign_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAssign_op(DecafParser.Assign_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterCompound_assign_op(DecafParser.Compound_assign_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCompound_assign_op(DecafParser.Compound_assign_opContext ctx) { }
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
    @Override public void exitMethod_name(DecafParser.Method_nameContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLocation(DecafParser.LocationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLocation(DecafParser.LocationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExpr(DecafParser.ExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExpr(DecafParser.ExprContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExtern_arg(DecafParser.Extern_argContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExtern_arg(DecafParser.Extern_argContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBin_op(DecafParser.Bin_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBin_op(DecafParser.Bin_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArith_op(DecafParser.Arith_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArith_op(DecafParser.Arith_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterRel_op(DecafParser.Rel_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitRel_op(DecafParser.Rel_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterEq_op(DecafParser.Eq_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEq_op(DecafParser.Eq_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterCond_op(DecafParser.Cond_opContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCond_op(DecafParser.Cond_opContext ctx) { }
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
    @Override public void exitLiteral(DecafParser.LiteralContext ctx) { }

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

