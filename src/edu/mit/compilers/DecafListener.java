// Generated from /Users/abel/Desktop/Fall2016/6.035/6035-compilers/src/edu/mit/compilers/grammar/DecafParser.g4 by ANTLR 4.5.3

package edu.mit.compilers;

import edu.mit.compilers.grammar.*;
import edu.mit.compilers.ir.*;
import org.antlr.v4.gui.SystemFontMetrics;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Stack;




public class DecafListener extends DecafParserBaseListener {
    private Stack<Ir> irStack = new Stack<>();
    private ScopeStack scopeStack = new ScopeStack();

    public void declareInCurrentScopeOrReportDuplicateDecl(String id, Ir object, String errorMsg) {
        if (!this.scopeStack.checkIfSymbolExistsAtCurrentScope(id)) {
            this.scopeStack.addObjectToCurrentScope(id, object);
            this.irStack.push(object);
        }
        else {
            System.err.print(errorMsg);
        }
    }

    @Override public void enterProgram(DecafParser.ProgramContext ctx) {
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

            // once we create the extern_decl, add it to the stack
            this.irStack.push(externDecl);
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
    @Override public void enterField_decl(DecafParser.Field_declContext ctx) {
        // the way we know we there are no more new variables to declare
        // is by observing the IrType object in the irStack
        DecafListener.ProgramLocation l = this.new ProgramLocation(ctx);
        IrType fieldsType;
        if (ctx.type().RES_BOOL() != null) {
            fieldsType = new IrTypeBool(l.line, l.col);
            this.irStack.push(fieldsType);
        }
        else if (ctx.type().RES_INT() != null) {
            fieldsType = new IrTypeInt(l.line, l.col);
            this.irStack.push(fieldsType);
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitField_decl(DecafParser.Field_declContext ctx) {
        ArrayList<IrFieldDecl> fieldsList = new ArrayList<>();

        // Get each field from the irStack. When we reach the IrType
        // object in the stack, we have collected all of the fields
        Ir topOfStack = this.irStack.peek();
        while (this.irStack.size() > 0 && topOfStack instanceof IrFieldDecl) {
            IrFieldDecl newField = (IrFieldDecl) this.irStack.pop();
            fieldsList.add(newField);
            topOfStack = this.irStack.peek();
        }

        // the object on the stack should be a IrType object
        if (this.irStack.size() > 0 && topOfStack instanceof IrType) {
            IrType fieldsType = (IrType) this.irStack.pop();

            // loop through list in reverse to maintain original push()
            // each field back to irStack. add() each field to scopeStack
            for (int i = fieldsList.size()-1; i >= 0; i--) {
                IrFieldDecl field = fieldsList.get(i);
                declareInCurrentScopeOrReportDuplicateDecl(
                        field.getName(),
                        field,
                        "exitField_decl: duplicate field declared in same scope"
                );
            }
        }
        else {
            System.err.print("exitField_decl: missing IrType of newFields");
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
        // push type or RES_VOID onto the stack
        IrType methodType;
        if (ctx.RES_VOID() != null) {
            methodType = new IrTypeVoid(l.line, l.col);
            this.irStack.push(methodType);
        }
        else if (ctx.type().RES_INT() != null){
            methodType = new IrTypeInt(l.line, l.col);
            this.irStack.push(methodType);
        }
        else if (ctx.type().RES_BOOL() != null) {
            methodType = new IrTypeBool(l.line, l.col);
            this.irStack.push(methodType);
        }

        // push the IrIdent onto the stack
        IrIdent methodName = new IrIdent(ctx.ID().getText(), l.line, l.col);
        this.irStack.push(methodName);
    }
    @Override public void exitMethod_decl(DecafParser.Method_declContext ctx) { }
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
    @Override public void exitBlock(DecafParser.BlockContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterType(DecafParser.TypeContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitType(DecafParser.TypeContext ctx) { }
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

