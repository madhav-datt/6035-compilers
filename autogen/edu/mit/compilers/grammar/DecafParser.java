// Generated from /Users/devinmorgan/Documents/Java_Workspace_6035/6035-compilers/src/edu/mit/compilers/grammar/DecafParser.g by ANTLR 4.5.3

    package edu.mit.compilers.grammar;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DecafParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS_=1, COMMENT=2, L_SQUARE=3, R_SQUARE=4, L_PAREN=5, R_PAREN=6, L_CURL=7, 
		R_CURL=8, COMMA=9, SEMI_COL=10, LT_OP=11, GT_OP=12, LEQ_OP=13, GEQ_OP=14, 
		EQ_OP=15, NEQ_OP=16, AND_OP=17, OR_OP=18, NOT_OP=19, ADD_OP=20, SUB_OP=21, 
		MUL_OP=22, DIV_OP=23, MOD_OP=24, AS_OP=25, ADD_AS_OP=26, SUB_AS_OP=27, 
		RES_BOOL=28, RES_BREAK=29, RES_EXTERN=30, RES_CONTINUE=31, RES_ELSE=32, 
		RES_FOR=33, RES_WHILE=34, RES_IF=35, RES_INT=36, RES_RETURN=37, RES_SIZEOF=38, 
		RES_VOID=39, BOOL=40, CHAR=41, STRING=42, INT=43, ID=44, HEX_PREFIX=45;
	public static final int
		RULE_program = 0, RULE_extern_decl = 1, RULE_field_decl = 2, RULE_method_decl = 3, 
		RULE_block = 4, RULE_type = 5, RULE_statement = 6, RULE_assign_op = 7, 
		RULE_compound_assign_op = 8, RULE_method_call = 9, RULE_method_name = 10, 
		RULE_location = 11, RULE_expr = 12, RULE_extern_arg = 13, RULE_bin_op = 14, 
		RULE_arith_op = 15, RULE_rel_op = 16, RULE_eq_op = 17, RULE_cond_op = 18, 
		RULE_literal = 19;
	public static final String[] ruleNames = {
		"program", "extern_decl", "field_decl", "method_decl", "block", "type", 
		"statement", "assign_op", "compound_assign_op", "method_call", "method_name", 
		"location", "expr", "extern_arg", "bin_op", "arith_op", "rel_op", "eq_op", 
		"cond_op", "literal"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, "'['", "']'", "'('", "')'", "'{'", "'}'", "','", "';'", 
		"'<'", "'>'", "'<='", "'>='", "'=='", "'!='", "'&&'", "'||'", "'!'", "'+'", 
		"'-'", "'*'", "'/'", "'%'", "'='", "'+='", "'-='", "'bool'", "'break'", 
		"'extern'", "'continue'", "'else'", "'for'", "'while'", "'if'", "'int'", 
		"'return'", "'sizeof'", "'void'", null, null, null, null, null, "'0x'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "WS_", "COMMENT", "L_SQUARE", "R_SQUARE", "L_PAREN", "R_PAREN", 
		"L_CURL", "R_CURL", "COMMA", "SEMI_COL", "LT_OP", "GT_OP", "LEQ_OP", "GEQ_OP", 
		"EQ_OP", "NEQ_OP", "AND_OP", "OR_OP", "NOT_OP", "ADD_OP", "SUB_OP", "MUL_OP", 
		"DIV_OP", "MOD_OP", "AS_OP", "ADD_AS_OP", "SUB_AS_OP", "RES_BOOL", "RES_BREAK", 
		"RES_EXTERN", "RES_CONTINUE", "RES_ELSE", "RES_FOR", "RES_WHILE", "RES_IF", 
		"RES_INT", "RES_RETURN", "RES_SIZEOF", "RES_VOID", "BOOL", "CHAR", "STRING", 
		"INT", "ID", "HEX_PREFIX"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DecafParser.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DecafParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<Extern_declContext> extern_decl() {
			return getRuleContexts(Extern_declContext.class);
		}
		public Extern_declContext extern_decl(int i) {
			return getRuleContext(Extern_declContext.class,i);
		}
		public List<Field_declContext> field_decl() {
			return getRuleContexts(Field_declContext.class);
		}
		public Field_declContext field_decl(int i) {
			return getRuleContext(Field_declContext.class,i);
		}
		public List<Method_declContext> method_decl() {
			return getRuleContexts(Method_declContext.class);
		}
		public Method_declContext method_decl(int i) {
			return getRuleContext(Method_declContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RES_EXTERN) {
				{
				{
				setState(40);
				extern_decl();
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(49);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(46);
					field_decl();
					}
					} 
				}
				setState(51);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RES_BOOL) | (1L << RES_INT) | (1L << RES_VOID))) != 0)) {
				{
				{
				setState(52);
				method_decl();
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Extern_declContext extends ParserRuleContext {
		public TerminalNode RES_EXTERN() { return getToken(DecafParser.RES_EXTERN, 0); }
		public TerminalNode ID() { return getToken(DecafParser.ID, 0); }
		public TerminalNode L_PAREN() { return getToken(DecafParser.L_PAREN, 0); }
		public TerminalNode R_PAREN() { return getToken(DecafParser.R_PAREN, 0); }
		public TerminalNode SEMI_COL() { return getToken(DecafParser.SEMI_COL, 0); }
		public Extern_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extern_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterExtern_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitExtern_decl(this);
		}
	}

	public final Extern_declContext extern_decl() throws RecognitionException {
		Extern_declContext _localctx = new Extern_declContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_extern_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(RES_EXTERN);
			setState(59);
			match(ID);
			setState(60);
			match(L_PAREN);
			setState(61);
			match(R_PAREN);
			setState(62);
			match(SEMI_COL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Field_declContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode SEMI_COL() { return getToken(DecafParser.SEMI_COL, 0); }
		public List<TerminalNode> ID() { return getTokens(DecafParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DecafParser.ID, i);
		}
		public List<TerminalNode> L_SQUARE() { return getTokens(DecafParser.L_SQUARE); }
		public TerminalNode L_SQUARE(int i) {
			return getToken(DecafParser.L_SQUARE, i);
		}
		public List<TerminalNode> INT() { return getTokens(DecafParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(DecafParser.INT, i);
		}
		public List<TerminalNode> R_SQUARE() { return getTokens(DecafParser.R_SQUARE); }
		public TerminalNode R_SQUARE(int i) {
			return getToken(DecafParser.R_SQUARE, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DecafParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DecafParser.COMMA, i);
		}
		public Field_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterField_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitField_decl(this);
		}
	}

	public final Field_declContext field_decl() throws RecognitionException {
		Field_declContext _localctx = new Field_declContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_field_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			type();
			setState(70);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(65);
				match(ID);
				}
				break;
			case 2:
				{
				setState(66);
				match(ID);
				setState(67);
				match(L_SQUARE);
				setState(68);
				match(INT);
				setState(69);
				match(R_SQUARE);
				}
				break;
			}
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(72);
				match(COMMA);
				setState(78);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(73);
					match(ID);
					}
					break;
				case 2:
					{
					setState(74);
					match(ID);
					setState(75);
					match(L_SQUARE);
					setState(76);
					match(INT);
					setState(77);
					match(R_SQUARE);
					}
					break;
				}
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(85);
			match(SEMI_COL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_declContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(DecafParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DecafParser.ID, i);
		}
		public TerminalNode L_PAREN() { return getToken(DecafParser.L_PAREN, 0); }
		public TerminalNode R_PAREN() { return getToken(DecafParser.R_PAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TerminalNode RES_VOID() { return getToken(DecafParser.RES_VOID, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DecafParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DecafParser.COMMA, i);
		}
		public Method_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterMethod_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitMethod_decl(this);
		}
	}

	public final Method_declContext method_decl() throws RecognitionException {
		Method_declContext _localctx = new Method_declContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_method_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			switch (_input.LA(1)) {
			case RES_BOOL:
			case RES_INT:
				{
				setState(87);
				type();
				}
				break;
			case RES_VOID:
				{
				setState(88);
				match(RES_VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(91);
			match(ID);
			setState(92);
			match(L_PAREN);
			setState(105);
			_la = _input.LA(1);
			if (_la==RES_BOOL || _la==RES_INT) {
				{
				{
				setState(93);
				type();
				setState(94);
				match(ID);
				}
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(96);
					match(COMMA);
					{
					setState(97);
					type();
					setState(98);
					match(ID);
					}
					}
					}
					setState(104);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(107);
			match(R_PAREN);
			setState(108);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode L_CURL() { return getToken(DecafParser.L_CURL, 0); }
		public TerminalNode R_CURL() { return getToken(DecafParser.R_CURL, 0); }
		public List<Field_declContext> field_decl() {
			return getRuleContexts(Field_declContext.class);
		}
		public Field_declContext field_decl(int i) {
			return getRuleContext(Field_declContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(L_CURL);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==RES_BOOL || _la==RES_INT) {
				{
				{
				setState(111);
				field_decl();
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << RES_BREAK) | (1L << RES_CONTINUE) | (1L << RES_FOR) | (1L << RES_WHILE) | (1L << RES_IF) | (1L << RES_RETURN) | (1L << ID))) != 0)) {
				{
				{
				setState(117);
				statement();
				}
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(123);
			match(R_CURL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode RES_INT() { return getToken(DecafParser.RES_INT, 0); }
		public TerminalNode RES_BOOL() { return getToken(DecafParser.RES_BOOL, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			_la = _input.LA(1);
			if ( !(_la==RES_BOOL || _la==RES_INT) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public LocationContext location() {
			return getRuleContext(LocationContext.class,0);
		}
		public Assign_opContext assign_op() {
			return getRuleContext(Assign_opContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> SEMI_COL() { return getTokens(DecafParser.SEMI_COL); }
		public TerminalNode SEMI_COL(int i) {
			return getToken(DecafParser.SEMI_COL, i);
		}
		public Method_callContext method_call() {
			return getRuleContext(Method_callContext.class,0);
		}
		public TerminalNode RES_IF() { return getToken(DecafParser.RES_IF, 0); }
		public List<TerminalNode> L_PAREN() { return getTokens(DecafParser.L_PAREN); }
		public TerminalNode L_PAREN(int i) {
			return getToken(DecafParser.L_PAREN, i);
		}
		public TerminalNode R_PAREN() { return getToken(DecafParser.R_PAREN, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode RES_ELSE() { return getToken(DecafParser.RES_ELSE, 0); }
		public TerminalNode RES_FOR() { return getToken(DecafParser.RES_FOR, 0); }
		public List<TerminalNode> ID() { return getTokens(DecafParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DecafParser.ID, i);
		}
		public TerminalNode AS_OP() { return getToken(DecafParser.AS_OP, 0); }
		public Compound_assign_opContext compound_assign_op() {
			return getRuleContext(Compound_assign_opContext.class,0);
		}
		public TerminalNode RES_WHILE() { return getToken(DecafParser.RES_WHILE, 0); }
		public TerminalNode RES_RETURN() { return getToken(DecafParser.RES_RETURN, 0); }
		public TerminalNode RES_BREAK() { return getToken(DecafParser.RES_BREAK, 0); }
		public TerminalNode RES_CONTINUE() { return getToken(DecafParser.RES_CONTINUE, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		int _la;
		try {
			setState(173);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(127);
				location();
				setState(128);
				assign_op();
				setState(129);
				expr();
				setState(130);
				match(SEMI_COL);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				method_call();
				setState(133);
				match(SEMI_COL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(135);
				match(RES_IF);
				setState(136);
				match(L_PAREN);
				setState(137);
				expr();
				setState(138);
				match(R_PAREN);
				setState(139);
				block();
				setState(142);
				_la = _input.LA(1);
				if (_la==RES_ELSE) {
					{
					setState(140);
					match(RES_ELSE);
					setState(141);
					block();
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(144);
				match(RES_FOR);
				setState(145);
				match(L_PAREN);
				setState(146);
				match(ID);
				setState(147);
				match(AS_OP);
				setState(148);
				expr();
				setState(149);
				match(SEMI_COL);
				setState(150);
				expr();
				setState(151);
				match(SEMI_COL);
				setState(152);
				match(ID);
				setState(153);
				compound_assign_op();
				setState(154);
				expr();
				setState(155);
				match(L_PAREN);
				setState(156);
				block();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(158);
				match(RES_WHILE);
				setState(159);
				match(L_PAREN);
				setState(160);
				expr();
				setState(161);
				match(R_PAREN);
				setState(162);
				block();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(164);
				match(RES_RETURN);
				setState(166);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << L_PAREN) | (1L << NOT_OP) | (1L << SUB_OP) | (1L << RES_SIZEOF) | (1L << BOOL) | (1L << CHAR) | (1L << INT) | (1L << ID))) != 0)) {
					{
					setState(165);
					expr();
					}
				}

				setState(168);
				match(SEMI_COL);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(169);
				match(RES_BREAK);
				setState(170);
				match(SEMI_COL);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(171);
				match(RES_CONTINUE);
				setState(172);
				match(SEMI_COL);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_opContext extends ParserRuleContext {
		public TerminalNode AS_OP() { return getToken(DecafParser.AS_OP, 0); }
		public Compound_assign_opContext compound_assign_op() {
			return getRuleContext(Compound_assign_opContext.class,0);
		}
		public Assign_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterAssign_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitAssign_op(this);
		}
	}

	public final Assign_opContext assign_op() throws RecognitionException {
		Assign_opContext _localctx = new Assign_opContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assign_op);
		try {
			setState(177);
			switch (_input.LA(1)) {
			case AS_OP:
				enterOuterAlt(_localctx, 1);
				{
				setState(175);
				match(AS_OP);
				}
				break;
			case ADD_AS_OP:
			case SUB_AS_OP:
				enterOuterAlt(_localctx, 2);
				{
				setState(176);
				compound_assign_op();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Compound_assign_opContext extends ParserRuleContext {
		public TerminalNode ADD_AS_OP() { return getToken(DecafParser.ADD_AS_OP, 0); }
		public TerminalNode SUB_AS_OP() { return getToken(DecafParser.SUB_AS_OP, 0); }
		public Compound_assign_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_assign_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterCompound_assign_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitCompound_assign_op(this);
		}
	}

	public final Compound_assign_opContext compound_assign_op() throws RecognitionException {
		Compound_assign_opContext _localctx = new Compound_assign_opContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_compound_assign_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			_la = _input.LA(1);
			if ( !(_la==ADD_AS_OP || _la==SUB_AS_OP) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_callContext extends ParserRuleContext {
		public Method_nameContext method_name() {
			return getRuleContext(Method_nameContext.class,0);
		}
		public TerminalNode L_PAREN() { return getToken(DecafParser.L_PAREN, 0); }
		public TerminalNode R_PAREN() { return getToken(DecafParser.R_PAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DecafParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DecafParser.COMMA, i);
		}
		public List<Extern_argContext> extern_arg() {
			return getRuleContexts(Extern_argContext.class);
		}
		public Extern_argContext extern_arg(int i) {
			return getRuleContext(Extern_argContext.class,i);
		}
		public Method_callContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterMethod_call(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitMethod_call(this);
		}
	}

	public final Method_callContext method_call() throws RecognitionException {
		Method_callContext _localctx = new Method_callContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_method_call);
		int _la;
		try {
			setState(209);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(181);
				method_name();
				setState(182);
				match(L_PAREN);
				setState(191);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << L_PAREN) | (1L << NOT_OP) | (1L << SUB_OP) | (1L << RES_SIZEOF) | (1L << BOOL) | (1L << CHAR) | (1L << INT) | (1L << ID))) != 0)) {
					{
					setState(183);
					expr();
					setState(188);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(184);
						match(COMMA);
						setState(185);
						expr();
						}
						}
						setState(190);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(193);
				match(R_PAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(195);
				method_name();
				setState(196);
				match(L_PAREN);
				setState(205);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << L_PAREN) | (1L << NOT_OP) | (1L << SUB_OP) | (1L << RES_SIZEOF) | (1L << BOOL) | (1L << CHAR) | (1L << STRING) | (1L << INT) | (1L << ID))) != 0)) {
					{
					setState(197);
					extern_arg();
					setState(202);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(198);
						match(COMMA);
						setState(199);
						extern_arg();
						}
						}
						setState(204);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(207);
				match(R_PAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Method_nameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DecafParser.ID, 0); }
		public Method_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterMethod_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitMethod_name(this);
		}
	}

	public final Method_nameContext method_name() throws RecognitionException {
		Method_nameContext _localctx = new Method_nameContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_method_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(DecafParser.ID, 0); }
		public TerminalNode L_SQUARE() { return getToken(DecafParser.L_SQUARE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode R_SQUARE() { return getToken(DecafParser.R_SQUARE, 0); }
		public LocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_location; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterLocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitLocation(this);
		}
	}

	public final LocationContext location() throws RecognitionException {
		LocationContext _localctx = new LocationContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_location);
		try {
			setState(219);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(213);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(214);
				match(ID);
				setState(215);
				match(L_SQUARE);
				setState(216);
				expr();
				setState(217);
				match(R_SQUARE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public LocationContext location() {
			return getRuleContext(LocationContext.class,0);
		}
		public Method_callContext method_call() {
			return getRuleContext(Method_callContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode RES_SIZEOF() { return getToken(DecafParser.RES_SIZEOF, 0); }
		public TerminalNode L_PAREN() { return getToken(DecafParser.L_PAREN, 0); }
		public TerminalNode ID() { return getToken(DecafParser.ID, 0); }
		public TerminalNode R_PAREN() { return getToken(DecafParser.R_PAREN, 0); }
		public TerminalNode SUB_OP() { return getToken(DecafParser.SUB_OP, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode NOT_OP() { return getToken(DecafParser.NOT_OP, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_expr);
		try {
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(221);
				location();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(222);
				method_call();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(223);
				literal();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(224);
				match(RES_SIZEOF);
				setState(225);
				match(L_PAREN);
				setState(226);
				match(ID);
				setState(227);
				match(R_PAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(228);
				match(SUB_OP);
				setState(229);
				expr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(230);
				match(NOT_OP);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(231);
				match(L_PAREN);
				setState(232);
				expr();
				setState(233);
				match(R_PAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Extern_argContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode STRING() { return getToken(DecafParser.STRING, 0); }
		public Extern_argContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extern_arg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterExtern_arg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitExtern_arg(this);
		}
	}

	public final Extern_argContext extern_arg() throws RecognitionException {
		Extern_argContext _localctx = new Extern_argContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_extern_arg);
		try {
			setState(239);
			switch (_input.LA(1)) {
			case L_PAREN:
			case NOT_OP:
			case SUB_OP:
			case RES_SIZEOF:
			case BOOL:
			case CHAR:
			case INT:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(237);
				expr();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(238);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bin_opContext extends ParserRuleContext {
		public Arith_opContext arith_op() {
			return getRuleContext(Arith_opContext.class,0);
		}
		public Rel_opContext rel_op() {
			return getRuleContext(Rel_opContext.class,0);
		}
		public Eq_opContext eq_op() {
			return getRuleContext(Eq_opContext.class,0);
		}
		public Cond_opContext cond_op() {
			return getRuleContext(Cond_opContext.class,0);
		}
		public Bin_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bin_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterBin_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitBin_op(this);
		}
	}

	public final Bin_opContext bin_op() throws RecognitionException {
		Bin_opContext _localctx = new Bin_opContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_bin_op);
		try {
			setState(245);
			switch (_input.LA(1)) {
			case ADD_OP:
			case SUB_OP:
			case MUL_OP:
			case DIV_OP:
			case MOD_OP:
				enterOuterAlt(_localctx, 1);
				{
				setState(241);
				arith_op();
				}
				break;
			case LT_OP:
			case GT_OP:
			case LEQ_OP:
			case GEQ_OP:
				enterOuterAlt(_localctx, 2);
				{
				setState(242);
				rel_op();
				}
				break;
			case EQ_OP:
			case NEQ_OP:
				enterOuterAlt(_localctx, 3);
				{
				setState(243);
				eq_op();
				}
				break;
			case AND_OP:
			case OR_OP:
				enterOuterAlt(_localctx, 4);
				{
				setState(244);
				cond_op();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arith_opContext extends ParserRuleContext {
		public TerminalNode ADD_OP() { return getToken(DecafParser.ADD_OP, 0); }
		public TerminalNode SUB_OP() { return getToken(DecafParser.SUB_OP, 0); }
		public TerminalNode MUL_OP() { return getToken(DecafParser.MUL_OP, 0); }
		public TerminalNode DIV_OP() { return getToken(DecafParser.DIV_OP, 0); }
		public TerminalNode MOD_OP() { return getToken(DecafParser.MOD_OP, 0); }
		public Arith_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arith_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterArith_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitArith_op(this);
		}
	}

	public final Arith_opContext arith_op() throws RecognitionException {
		Arith_opContext _localctx = new Arith_opContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_arith_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD_OP) | (1L << SUB_OP) | (1L << MUL_OP) | (1L << DIV_OP) | (1L << MOD_OP))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rel_opContext extends ParserRuleContext {
		public TerminalNode LT_OP() { return getToken(DecafParser.LT_OP, 0); }
		public TerminalNode GT_OP() { return getToken(DecafParser.GT_OP, 0); }
		public TerminalNode LEQ_OP() { return getToken(DecafParser.LEQ_OP, 0); }
		public TerminalNode GEQ_OP() { return getToken(DecafParser.GEQ_OP, 0); }
		public Rel_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rel_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterRel_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitRel_op(this);
		}
	}

	public final Rel_opContext rel_op() throws RecognitionException {
		Rel_opContext _localctx = new Rel_opContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_rel_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT_OP) | (1L << GT_OP) | (1L << LEQ_OP) | (1L << GEQ_OP))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Eq_opContext extends ParserRuleContext {
		public TerminalNode EQ_OP() { return getToken(DecafParser.EQ_OP, 0); }
		public TerminalNode NEQ_OP() { return getToken(DecafParser.NEQ_OP, 0); }
		public Eq_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eq_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterEq_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitEq_op(this);
		}
	}

	public final Eq_opContext eq_op() throws RecognitionException {
		Eq_opContext _localctx = new Eq_opContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_eq_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			_la = _input.LA(1);
			if ( !(_la==EQ_OP || _la==NEQ_OP) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Cond_opContext extends ParserRuleContext {
		public TerminalNode AND_OP() { return getToken(DecafParser.AND_OP, 0); }
		public TerminalNode OR_OP() { return getToken(DecafParser.OR_OP, 0); }
		public Cond_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cond_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterCond_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitCond_op(this);
		}
	}

	public final Cond_opContext cond_op() throws RecognitionException {
		Cond_opContext _localctx = new Cond_opContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_cond_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			_la = _input.LA(1);
			if ( !(_la==AND_OP || _la==OR_OP) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(DecafParser.INT, 0); }
		public TerminalNode CHAR() { return getToken(DecafParser.CHAR, 0); }
		public TerminalNode BOOL() { return getToken(DecafParser.BOOL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DecafParserListener ) ((DecafParserListener)listener).exitLiteral(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << CHAR) | (1L << INT))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3/\u0104\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\7\2,\n\2\f\2\16\2/\13\2\3\2\7\2\62"+
		"\n\2\f\2\16\2\65\13\2\3\2\7\28\n\2\f\2\16\2;\13\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4I\n\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4Q\n\4"+
		"\7\4S\n\4\f\4\16\4V\13\4\3\4\3\4\3\5\3\5\5\5\\\n\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\7\5g\n\5\f\5\16\5j\13\5\5\5l\n\5\3\5\3\5\3\5\3\6\3\6"+
		"\7\6s\n\6\f\6\16\6v\13\6\3\6\7\6y\n\6\f\6\16\6|\13\6\3\6\3\6\3\7\3\7\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0091\n"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\5\b\u00a9\n\b\3\b\3\b\3\b\3\b\3\b\5\b\u00b0\n\b\3"+
		"\t\3\t\5\t\u00b4\n\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\7\13\u00bd\n\13"+
		"\f\13\16\13\u00c0\13\13\5\13\u00c2\n\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\7\13\u00cb\n\13\f\13\16\13\u00ce\13\13\5\13\u00d0\n\13\3\13\3\13"+
		"\5\13\u00d4\n\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00de\n\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00ee"+
		"\n\16\3\17\3\17\5\17\u00f2\n\17\3\20\3\20\3\20\3\20\5\20\u00f8\n\20\3"+
		"\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\2\2\26\2\4\6\b\n"+
		"\f\16\20\22\24\26\30\32\34\36 \"$&(\2\t\4\2\36\36&&\3\2\34\35\3\2\26\32"+
		"\3\2\r\20\3\2\21\22\3\2\23\24\4\2*+--\u0114\2-\3\2\2\2\4<\3\2\2\2\6B\3"+
		"\2\2\2\b[\3\2\2\2\np\3\2\2\2\f\177\3\2\2\2\16\u00af\3\2\2\2\20\u00b3\3"+
		"\2\2\2\22\u00b5\3\2\2\2\24\u00d3\3\2\2\2\26\u00d5\3\2\2\2\30\u00dd\3\2"+
		"\2\2\32\u00ed\3\2\2\2\34\u00f1\3\2\2\2\36\u00f7\3\2\2\2 \u00f9\3\2\2\2"+
		"\"\u00fb\3\2\2\2$\u00fd\3\2\2\2&\u00ff\3\2\2\2(\u0101\3\2\2\2*,\5\4\3"+
		"\2+*\3\2\2\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\63\3\2\2\2/-\3\2\2\2\60\62"+
		"\5\6\4\2\61\60\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\649\3"+
		"\2\2\2\65\63\3\2\2\2\668\5\b\5\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\29"+
		":\3\2\2\2:\3\3\2\2\2;9\3\2\2\2<=\7 \2\2=>\7.\2\2>?\7\7\2\2?@\7\b\2\2@"+
		"A\7\f\2\2A\5\3\2\2\2BH\5\f\7\2CI\7.\2\2DE\7.\2\2EF\7\5\2\2FG\7-\2\2GI"+
		"\7\6\2\2HC\3\2\2\2HD\3\2\2\2IT\3\2\2\2JP\7\13\2\2KQ\7.\2\2LM\7.\2\2MN"+
		"\7\5\2\2NO\7-\2\2OQ\7\6\2\2PK\3\2\2\2PL\3\2\2\2QS\3\2\2\2RJ\3\2\2\2SV"+
		"\3\2\2\2TR\3\2\2\2TU\3\2\2\2UW\3\2\2\2VT\3\2\2\2WX\7\f\2\2X\7\3\2\2\2"+
		"Y\\\5\f\7\2Z\\\7)\2\2[Y\3\2\2\2[Z\3\2\2\2\\]\3\2\2\2]^\7.\2\2^k\7\7\2"+
		"\2_`\5\f\7\2`a\7.\2\2ah\3\2\2\2bc\7\13\2\2cd\5\f\7\2de\7.\2\2eg\3\2\2"+
		"\2fb\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2il\3\2\2\2jh\3\2\2\2k_\3\2\2"+
		"\2kl\3\2\2\2lm\3\2\2\2mn\7\b\2\2no\5\n\6\2o\t\3\2\2\2pt\7\t\2\2qs\5\6"+
		"\4\2rq\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2uz\3\2\2\2vt\3\2\2\2wy\5\16"+
		"\b\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\7\n"+
		"\2\2~\13\3\2\2\2\177\u0080\t\2\2\2\u0080\r\3\2\2\2\u0081\u0082\5\30\r"+
		"\2\u0082\u0083\5\20\t\2\u0083\u0084\5\32\16\2\u0084\u0085\7\f\2\2\u0085"+
		"\u00b0\3\2\2\2\u0086\u0087\5\24\13\2\u0087\u0088\7\f\2\2\u0088\u00b0\3"+
		"\2\2\2\u0089\u008a\7%\2\2\u008a\u008b\7\7\2\2\u008b\u008c\5\32\16\2\u008c"+
		"\u008d\7\b\2\2\u008d\u0090\5\n\6\2\u008e\u008f\7\"\2\2\u008f\u0091\5\n"+
		"\6\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u00b0\3\2\2\2\u0092"+
		"\u0093\7#\2\2\u0093\u0094\7\7\2\2\u0094\u0095\7.\2\2\u0095\u0096\7\33"+
		"\2\2\u0096\u0097\5\32\16\2\u0097\u0098\7\f\2\2\u0098\u0099\5\32\16\2\u0099"+
		"\u009a\7\f\2\2\u009a\u009b\7.\2\2\u009b\u009c\5\22\n\2\u009c\u009d\5\32"+
		"\16\2\u009d\u009e\7\7\2\2\u009e\u009f\5\n\6\2\u009f\u00b0\3\2\2\2\u00a0"+
		"\u00a1\7$\2\2\u00a1\u00a2\7\7\2\2\u00a2\u00a3\5\32\16\2\u00a3\u00a4\7"+
		"\b\2\2\u00a4\u00a5\5\n\6\2\u00a5\u00b0\3\2\2\2\u00a6\u00a8\7\'\2\2\u00a7"+
		"\u00a9\5\32\16\2\u00a8\u00a7\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00aa\3"+
		"\2\2\2\u00aa\u00b0\7\f\2\2\u00ab\u00ac\7\37\2\2\u00ac\u00b0\7\f\2\2\u00ad"+
		"\u00ae\7!\2\2\u00ae\u00b0\7\f\2\2\u00af\u0081\3\2\2\2\u00af\u0086\3\2"+
		"\2\2\u00af\u0089\3\2\2\2\u00af\u0092\3\2\2\2\u00af\u00a0\3\2\2\2\u00af"+
		"\u00a6\3\2\2\2\u00af\u00ab\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\17\3\2\2"+
		"\2\u00b1\u00b4\7\33\2\2\u00b2\u00b4\5\22\n\2\u00b3\u00b1\3\2\2\2\u00b3"+
		"\u00b2\3\2\2\2\u00b4\21\3\2\2\2\u00b5\u00b6\t\3\2\2\u00b6\23\3\2\2\2\u00b7"+
		"\u00b8\5\26\f\2\u00b8\u00c1\7\7\2\2\u00b9\u00be\5\32\16\2\u00ba\u00bb"+
		"\7\13\2\2\u00bb\u00bd\5\32\16\2\u00bc\u00ba\3\2\2\2\u00bd\u00c0\3\2\2"+
		"\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c2\3\2\2\2\u00c0\u00be"+
		"\3\2\2\2\u00c1\u00b9\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3"+
		"\u00c4\7\b\2\2\u00c4\u00d4\3\2\2\2\u00c5\u00c6\5\26\f\2\u00c6\u00cf\7"+
		"\7\2\2\u00c7\u00cc\5\34\17\2\u00c8\u00c9\7\13\2\2\u00c9\u00cb\5\34\17"+
		"\2\u00ca\u00c8\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd"+
		"\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00c7\3\2\2\2\u00cf"+
		"\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\7\b\2\2\u00d2\u00d4\3\2"+
		"\2\2\u00d3\u00b7\3\2\2\2\u00d3\u00c5\3\2\2\2\u00d4\25\3\2\2\2\u00d5\u00d6"+
		"\7.\2\2\u00d6\27\3\2\2\2\u00d7\u00de\7.\2\2\u00d8\u00d9\7.\2\2\u00d9\u00da"+
		"\7\5\2\2\u00da\u00db\5\32\16\2\u00db\u00dc\7\6\2\2\u00dc\u00de\3\2\2\2"+
		"\u00dd\u00d7\3\2\2\2\u00dd\u00d8\3\2\2\2\u00de\31\3\2\2\2\u00df\u00ee"+
		"\5\30\r\2\u00e0\u00ee\5\24\13\2\u00e1\u00ee\5(\25\2\u00e2\u00e3\7(\2\2"+
		"\u00e3\u00e4\7\7\2\2\u00e4\u00e5\7.\2\2\u00e5\u00ee\7\b\2\2\u00e6\u00e7"+
		"\7\27\2\2\u00e7\u00ee\5\32\16\2\u00e8\u00ee\7\25\2\2\u00e9\u00ea\7\7\2"+
		"\2\u00ea\u00eb\5\32\16\2\u00eb\u00ec\7\b\2\2\u00ec\u00ee\3\2\2\2\u00ed"+
		"\u00df\3\2\2\2\u00ed\u00e0\3\2\2\2\u00ed\u00e1\3\2\2\2\u00ed\u00e2\3\2"+
		"\2\2\u00ed\u00e6\3\2\2\2\u00ed\u00e8\3\2\2\2\u00ed\u00e9\3\2\2\2\u00ee"+
		"\33\3\2\2\2\u00ef\u00f2\5\32\16\2\u00f0\u00f2\7,\2\2\u00f1\u00ef\3\2\2"+
		"\2\u00f1\u00f0\3\2\2\2\u00f2\35\3\2\2\2\u00f3\u00f8\5 \21\2\u00f4\u00f8"+
		"\5\"\22\2\u00f5\u00f8\5$\23\2\u00f6\u00f8\5&\24\2\u00f7\u00f3\3\2\2\2"+
		"\u00f7\u00f4\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f6\3\2\2\2\u00f8\37"+
		"\3\2\2\2\u00f9\u00fa\t\4\2\2\u00fa!\3\2\2\2\u00fb\u00fc\t\5\2\2\u00fc"+
		"#\3\2\2\2\u00fd\u00fe\t\6\2\2\u00fe%\3\2\2\2\u00ff\u0100\t\7\2\2\u0100"+
		"\'\3\2\2\2\u0101\u0102\t\b\2\2\u0102)\3\2\2\2\32-\639HPT[hktz\u0090\u00a8"+
		"\u00af\u00b3\u00be\u00c1\u00cc\u00cf\u00d3\u00dd\u00ed\u00f1\u00f7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}