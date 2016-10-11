// Generated from /Users/abel/Desktop/Fall2016/6.035/6035-compilers/src/edu/mit/compilers/grammar/DecafParser.g4 by ANTLR 4.5.3

package edu.mit.compilers;

import edu.mit.compilers.grammar.*;
import edu.mit.compilers.ir.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;




public class DecafListener extends DecafParserBaseListener {
    private Stack<Ir> irStack = new Stack<>();
    private Stack<SymbolTable> scopeStack = new Stack<>();

    public void addToCurrentScope(String id, Ir object) {
        this.scopeStack.peek().put(id, object);
    }

    @Override public void enterProgram(DecafParser.ProgramContext ctx) {
        ProgramLocation l = new ProgramLocation(ctx);
        IrProgram program = new IrProgram(l.line, l.col);
        this.irStack.push(program);

        SymbolTable globalStack = new SymbolTable();
        this.scopeStack.push(globalStack);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitProgram(DecafParser.ProgramContext ctx) {
        Ir program = this.irStack.pop();

        if (!(program instanceof IrProgram)) {
            System.err.print("Error in exitProgram");
        }
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

        IrExternDecl externDecl = new IrExternDecl(newIdent, l.line, l.col);
        this.irStack.push(externDecl);
        this.addToCurrentScope(id, externDecl);

        // consider case where person creates the same extern_decl 2+
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExtern_decl(DecafParser.Extern_declContext ctx) {
        Ir externDecl = this.irStack.pop();
        if (externDecl instanceof IrExternDecl) {
            Ir program = this.irStack.pop();
            if (program instanceof IrProgram) {
                ((IrProgram) program).addExternDecl((IrExternDecl) externDecl);
            }
            else {
                System.err.print("Error in exitExtern (1)");
            }

        }
        else {
            System.err.print("Error in exitExtern (2)");
        }
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterField_decl(DecafParser.Field_declContext ctx) {
        ProgramLocation l = new ProgramLocation(ctx);
        IrIdent fieldName = new IrIdent(ctx.ID().toString(), l.line, l.col);
        boolean isArray = ctx.L_SQUARE().size() > 0 && ctx.R_SQUARE().size() > 0;


        if (isArray) {
            int size = Integer.getInteger(ctx.INT().get(0).toString());
//            System.out.println(ctx.I);
            IrFieldDeclArray array;

            if (ctx.type().RES_INT() != null) {
                array = new IrFieldDeclArray(size, new IrTypeInt(l.line, l.col), fieldName, l.line, l.col);
                this.irStack.push(array);
                this.addToCurrentScope(fieldName.getValue(), array);
            }
            else if (ctx.type().RES_BOOL() != null) {
                array = new IrFieldDeclArray(size, new IrTypeBool(l.line, l.col), fieldName, l.line, l.col);
                this.irStack.push(array);
                this.addToCurrentScope(fieldName.getValue(), array);
            }
            else {
                System.err.print("Error in enterField_decl: unknown Type");
            }

        }
        else {
            if (ctx.type().RES_INT() != null) {
                IrFieldDeclInt fieldDeclInt = new IrFieldDeclInt(new IrTypeInt(l.line, l.col), fieldName, l.line, l.col);
                this.irStack.push(fieldDeclInt);
                this.addToCurrentScope(fieldName.getValue(), fieldDeclInt);
            }
            else if (ctx.type().RES_BOOL() != null) {
                IrFieldDeclBool fieldDeclBool = new IrFieldDeclBool(new IrTypeBool(l.line, l.col), fieldName, l.line, l.col);
                this.irStack.push(fieldDeclBool);
                this.addToCurrentScope(fieldName.getValue(), fieldDeclBool);
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
//        /*
//            TODO: doesn't handle nested scopes with field declarations
//         */
//
//        Ir fieldDecl = this.irStack.pop();
//        if (fieldDecl instanceof IrFieldDecl) {
//            Ir program = this.irStack.pop();
//            if(program instanceof IrProgram){
//                ((IrProgram) program).addFieldDecl((IrFieldDecl) fieldDecl);
//            }
//            else{
//                System.err.print("Error in exitField_decl");
//            }
//        }
//        else {
//            System.err.print("Error in exitExtern (2)");
//        }
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