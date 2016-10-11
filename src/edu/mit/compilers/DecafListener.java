// Generated from /Users/abel/Desktop/Fall2016/6.035/6035-compilers/src/edu/mit/compilers/grammar/DecafParser.g4 by ANTLR 4.5.3

package edu.mit.compilers;

import edu.mit.compilers.grammar.*;
import edu.mit.compilers.ir.*;
import org.antlr.v4.gui.SystemFontMetrics;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;




public class DecafListener extends DecafParserBaseListener {
    private Stack<Ir> irStack = new Stack<>();
    private ScopeStack scopeStack = new ScopeStack();

    public boolean declareInCurrentScopeOrReportDuplicateDecl(String id, Ir object, String errorMsg) {
        if (this.scopeStack.checkIfSymbolExistsAtCurrentScope(id)) {
            this.scopeStack.addObjectToCurrentScope(id, object);
            this.irStack.push(object);
        }
        else {
            System.err.print(errorMsg);
        }
    }

    @Override public void enterProgram(DecafParser.ProgramContext ctx) {
        ProgramLocation l = new ProgramLocation(ctx);
        IrProgram program = new IrProgram(l.line, l.col);
        this.irStack.push(program);

        // actually create the ScopeStack when you enter the Program
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
        ProgramLocation l = new ProgramLocation(ctx);
        String id = ctx.ID().toString();
        IrIdent newIdent = new IrIdent(id, l.line, l.col);
        IrExternDecl externDecl = new IrExternDecl(newIdent);
        declareInCurrentScopeOrReportDuplicateDecl(
                id,
                externDecl,
                "enterExtern_decl: same extern declared twice"
        );

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExtern_decl(DecafParser.Extern_declContext ctx) {
        // Leave on the exitExtern on the stack.
        // We will collect when exitProgram is called
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterField_decl(DecafParser.Field_declContext ctx) {
        ProgramLocation l = new ProgramLocation(ctx);
        String fieldName = ctx.ID().toString();
        IrIdent nameIdent = new IrIdent(ctx.ID().toString(), l.line, l.col);
        boolean isArray = !ctx.L_SQUARE().isEmpty() && !ctx.R_SQUARE().isEmpty();

        if (isArray) {
            int size = Integer.getInteger(ctx.INT().get(0).toString());

            if (ctx.type().RES_INT() != null) {
                declareInCurrentScopeOrReportDuplicateDecl(
                        fieldName,
                        new IrFieldDeclArray(size, new IrTypeInt(l.line, l.col), nameIdent),
                        "enterField_decl: same array declared twice"
                );
            }
            else if (ctx.type().RES_BOOL() != null) {
                declareInCurrentScopeOrReportDuplicateDecl(
                        fieldName,
                        new IrFieldDeclArray(size, new IrTypeBool(l.line, l.col), nameIdent),
                        "enterField_decl: same array declared twice"
                );
            }
            else {
                System.err.print("Error in enterField_decl: unknown Type");
            }
        }
        else {
            if (ctx.type().RES_INT() != null) {
                declareInCurrentScopeOrReportDuplicateDecl(
                        fieldName,
                        new IrFieldDeclInt(new IrTypeInt(l.line, l.col), nameIdent),
                        "enterField_decl: same int declared twice"
                );
            }
            else if (ctx.type().RES_BOOL() != null) {
                declareInCurrentScopeOrReportDuplicateDecl(
                        fieldName,
                        new IrFieldDeclBool(new IrTypeBool(l.line, l.col), nameIdent),
                        "enterField_decl: same bool declared twice"
                );
            }
            else {
                System.err.print("Error in enterField_decl: unknown Type");
            }
        }

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitField_decl(DecafParser.Field_declContext ctx) {
        /*
            TODO: doesn't handle nested scopes with field declarations
         */
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMethod_decl(DecafParser.Method_declContext ctx) {

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
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

}

class ProgramLocation {
    public final int line;
    public final int col;
    public ProgramLocation(ParserRuleContext ctx) {
        this.line = ctx.getStart().getLine();
        this.col = ctx.getStart().getCharPositionInLine();
    }
}