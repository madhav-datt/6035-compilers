// Generated from /home/madhav/6035-compilers/src/edu/mit/compilers/grammar/DecafScanner.g4 by ANTLR 4.5.3

    package edu.mit.compilers.grammar;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DecafScanner extends Lexer {
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
	public static final int HEX_MODE = 1;
	public static String[] modeNames = {
		"DEFAULT_MODE", "HEX_MODE"
	};

	public static final String[] ruleNames = {
		"WS_", "COMMENT", "L_SQUARE", "R_SQUARE", "L_PAREN", "R_PAREN", "L_CURL", 
		"R_CURL", "COMMA", "SEMI_COL", "LT_OP", "GT_OP", "LEQ_OP", "GEQ_OP", "EQ_OP", 
		"NEQ_OP", "AND_OP", "OR_OP", "NOT_OP", "ADD_OP", "SUB_OP", "MUL_OP", "DIV_OP", 
		"MOD_OP", "AS_OP", "ADD_AS_OP", "SUB_AS_OP", "RES_BOOL", "RES_BREAK", 
		"RES_EXTERN", "RES_CONTINUE", "RES_ELSE", "RES_FOR", "RES_WHILE", "RES_IF", 
		"RES_INT", "RES_RETURN", "RES_SIZEOF", "RES_VOID", "HEX_PREFIX", "HEX_INT", 
		"BOOL", "CHAR", "STRING", "INT", "ID", "ESC", "ALLOWED_CHARS", "HEX_LETTERS", 
		"DIGIT", "ALL_LETTERS"
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


	public DecafScanner(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DecafScanner.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2/\u0155\b\1\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\3\2\3\2\3\2\3\2\3\3\3\3\3\3\5\3r\n\3\3\3\7\3u\n\3\f\3\16\3x\13\3"+
		"\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3"+
		"\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3"+
		"\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\34\3"+
		"\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3"+
		"\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3"+
		"!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\3$\3%\3%\3%\3%\3&\3&\3&\3&\3"+
		"&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3"+
		"*\3*\6*\u0104\n*\r*\16*\u0105\3*\3*\5*\u010a\n*\3*\3*\3*\3+\3+\3+\3+\3"+
		"+\3+\3+\3+\3+\5+\u0118\n+\3,\3,\3,\5,\u011d\n,\3,\3,\3-\3-\3-\7-\u0124"+
		"\n-\f-\16-\u0127\13-\3-\3-\3.\6.\u012c\n.\r.\16.\u012d\3.\3.\5.\u0132"+
		"\n.\3/\3/\5/\u0136\n/\3/\3/\3/\7/\u013b\n/\f/\16/\u013e\13/\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\5\60\u014a\n\60\3\61\3\61\3\62"+
		"\3\62\3\63\3\63\3\64\3\64\5\64\u0154\n\64\2\2\65\4\3\6\4\b\5\n\6\f\7\16"+
		"\b\20\t\22\n\24\13\26\f\30\r\32\16\34\17\36\20 \21\"\22$\23&\24(\25*\26"+
		",\27.\30\60\31\62\32\64\33\66\348\35:\36<\37> @!B\"D#F$H%J&L\'N(P)R/T"+
		"\2V*X+Z,\\-^.`\2b\2d\2f\2h\2\4\2\3\7\4\2\13\f\"\"\3\2\f\f\6\2\"#%(*]_"+
		"\u0080\4\2CHch\4\2I\\i|\u0162\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2\n"+
		"\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2\2\24\3\2\2"+
		"\2\2\26\3\2\2\2\2\30\3\2\2\2\2\32\3\2\2\2\2\34\3\2\2\2\2\36\3\2\2\2\2"+
		" \3\2\2\2\2\"\3\2\2\2\2$\3\2\2\2\2&\3\2\2\2\2(\3\2\2\2\2*\3\2\2\2\2,\3"+
		"\2\2\2\2.\3\2\2\2\2\60\3\2\2\2\2\62\3\2\2\2\2\64\3\2\2\2\2\66\3\2\2\2"+
		"\28\3\2\2\2\2:\3\2\2\2\2<\3\2\2\2\2>\3\2\2\2\2@\3\2\2\2\2B\3\2\2\2\2D"+
		"\3\2\2\2\2F\3\2\2\2\2H\3\2\2\2\2J\3\2\2\2\2L\3\2\2\2\2N\3\2\2\2\2P\3\2"+
		"\2\2\2R\3\2\2\2\2V\3\2\2\2\2X\3\2\2\2\2Z\3\2\2\2\2\\\3\2\2\2\2^\3\2\2"+
		"\2\3T\3\2\2\2\4j\3\2\2\2\6q\3\2\2\2\b}\3\2\2\2\n\177\3\2\2\2\f\u0081\3"+
		"\2\2\2\16\u0083\3\2\2\2\20\u0085\3\2\2\2\22\u0087\3\2\2\2\24\u0089\3\2"+
		"\2\2\26\u008b\3\2\2\2\30\u008d\3\2\2\2\32\u008f\3\2\2\2\34\u0091\3\2\2"+
		"\2\36\u0094\3\2\2\2 \u0097\3\2\2\2\"\u009a\3\2\2\2$\u009d\3\2\2\2&\u00a0"+
		"\3\2\2\2(\u00a3\3\2\2\2*\u00a5\3\2\2\2,\u00a7\3\2\2\2.\u00a9\3\2\2\2\60"+
		"\u00ab\3\2\2\2\62\u00ad\3\2\2\2\64\u00af\3\2\2\2\66\u00b1\3\2\2\28\u00b4"+
		"\3\2\2\2:\u00b7\3\2\2\2<\u00bc\3\2\2\2>\u00c2\3\2\2\2@\u00c9\3\2\2\2B"+
		"\u00d2\3\2\2\2D\u00d7\3\2\2\2F\u00db\3\2\2\2H\u00e1\3\2\2\2J\u00e4\3\2"+
		"\2\2L\u00e8\3\2\2\2N\u00ef\3\2\2\2P\u00f6\3\2\2\2R\u00fb\3\2\2\2T\u0103"+
		"\3\2\2\2V\u0117\3\2\2\2X\u0119\3\2\2\2Z\u0120\3\2\2\2\\\u012b\3\2\2\2"+
		"^\u0135\3\2\2\2`\u0149\3\2\2\2b\u014b\3\2\2\2d\u014d\3\2\2\2f\u014f\3"+
		"\2\2\2h\u0153\3\2\2\2jk\t\2\2\2kl\3\2\2\2lm\b\2\2\2m\5\3\2\2\2no\7\61"+
		"\2\2or\7\61\2\2pr\7%\2\2qn\3\2\2\2qp\3\2\2\2rv\3\2\2\2su\n\3\2\2ts\3\2"+
		"\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2wy\3\2\2\2xv\3\2\2\2yz\7\f\2\2z{\3\2"+
		"\2\2{|\b\3\2\2|\7\3\2\2\2}~\7]\2\2~\t\3\2\2\2\177\u0080\7_\2\2\u0080\13"+
		"\3\2\2\2\u0081\u0082\7*\2\2\u0082\r\3\2\2\2\u0083\u0084\7+\2\2\u0084\17"+
		"\3\2\2\2\u0085\u0086\7}\2\2\u0086\21\3\2\2\2\u0087\u0088\7\177\2\2\u0088"+
		"\23\3\2\2\2\u0089\u008a\7.\2\2\u008a\25\3\2\2\2\u008b\u008c\7=\2\2\u008c"+
		"\27\3\2\2\2\u008d\u008e\7>\2\2\u008e\31\3\2\2\2\u008f\u0090\7@\2\2\u0090"+
		"\33\3\2\2\2\u0091\u0092\7>\2\2\u0092\u0093\7?\2\2\u0093\35\3\2\2\2\u0094"+
		"\u0095\7@\2\2\u0095\u0096\7?\2\2\u0096\37\3\2\2\2\u0097\u0098\7?\2\2\u0098"+
		"\u0099\7?\2\2\u0099!\3\2\2\2\u009a\u009b\7#\2\2\u009b\u009c\7?\2\2\u009c"+
		"#\3\2\2\2\u009d\u009e\7(\2\2\u009e\u009f\7(\2\2\u009f%\3\2\2\2\u00a0\u00a1"+
		"\7~\2\2\u00a1\u00a2\7~\2\2\u00a2\'\3\2\2\2\u00a3\u00a4\7#\2\2\u00a4)\3"+
		"\2\2\2\u00a5\u00a6\7-\2\2\u00a6+\3\2\2\2\u00a7\u00a8\7/\2\2\u00a8-\3\2"+
		"\2\2\u00a9\u00aa\7,\2\2\u00aa/\3\2\2\2\u00ab\u00ac\7\61\2\2\u00ac\61\3"+
		"\2\2\2\u00ad\u00ae\7\'\2\2\u00ae\63\3\2\2\2\u00af\u00b0\7?\2\2\u00b0\65"+
		"\3\2\2\2\u00b1\u00b2\7-\2\2\u00b2\u00b3\7?\2\2\u00b3\67\3\2\2\2\u00b4"+
		"\u00b5\7/\2\2\u00b5\u00b6\7?\2\2\u00b69\3\2\2\2\u00b7\u00b8\7d\2\2\u00b8"+
		"\u00b9\7q\2\2\u00b9\u00ba\7q\2\2\u00ba\u00bb\7n\2\2\u00bb;\3\2\2\2\u00bc"+
		"\u00bd\7d\2\2\u00bd\u00be\7t\2\2\u00be\u00bf\7g\2\2\u00bf\u00c0\7c\2\2"+
		"\u00c0\u00c1\7m\2\2\u00c1=\3\2\2\2\u00c2\u00c3\7g\2\2\u00c3\u00c4\7z\2"+
		"\2\u00c4\u00c5\7v\2\2\u00c5\u00c6\7g\2\2\u00c6\u00c7\7t\2\2\u00c7\u00c8"+
		"\7p\2\2\u00c8?\3\2\2\2\u00c9\u00ca\7e\2\2\u00ca\u00cb\7q\2\2\u00cb\u00cc"+
		"\7p\2\2\u00cc\u00cd\7v\2\2\u00cd\u00ce\7k\2\2\u00ce\u00cf\7p\2\2\u00cf"+
		"\u00d0\7w\2\2\u00d0\u00d1\7g\2\2\u00d1A\3\2\2\2\u00d2\u00d3\7g\2\2\u00d3"+
		"\u00d4\7n\2\2\u00d4\u00d5\7u\2\2\u00d5\u00d6\7g\2\2\u00d6C\3\2\2\2\u00d7"+
		"\u00d8\7h\2\2\u00d8\u00d9\7q\2\2\u00d9\u00da\7t\2\2\u00daE\3\2\2\2\u00db"+
		"\u00dc\7y\2\2\u00dc\u00dd\7j\2\2\u00dd\u00de\7k\2\2\u00de\u00df\7n\2\2"+
		"\u00df\u00e0\7g\2\2\u00e0G\3\2\2\2\u00e1\u00e2\7k\2\2\u00e2\u00e3\7h\2"+
		"\2\u00e3I\3\2\2\2\u00e4\u00e5\7k\2\2\u00e5\u00e6\7p\2\2\u00e6\u00e7\7"+
		"v\2\2\u00e7K\3\2\2\2\u00e8\u00e9\7t\2\2\u00e9\u00ea\7g\2\2\u00ea\u00eb"+
		"\7v\2\2\u00eb\u00ec\7w\2\2\u00ec\u00ed\7t\2\2\u00ed\u00ee\7p\2\2\u00ee"+
		"M\3\2\2\2\u00ef\u00f0\7u\2\2\u00f0\u00f1\7k\2\2\u00f1\u00f2\7|\2\2\u00f2"+
		"\u00f3\7g\2\2\u00f3\u00f4\7q\2\2\u00f4\u00f5\7h\2\2\u00f5O\3\2\2\2\u00f6"+
		"\u00f7\7x\2\2\u00f7\u00f8\7q\2\2\u00f8\u00f9\7k\2\2\u00f9\u00fa\7f\2\2"+
		"\u00faQ\3\2\2\2\u00fb\u00fc\7\62\2\2\u00fc\u00fd\7z\2\2\u00fd\u00fe\3"+
		"\2\2\2\u00fe\u00ff\b)\3\2\u00ff\u0100\b)\4\2\u0100S\3\2\2\2\u0101\u0104"+
		"\5f\63\2\u0102\u0104\5d\62\2\u0103\u0101\3\2\2\2\u0103\u0102\3\2\2\2\u0104"+
		"\u0105\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0109\3\2"+
		"\2\2\u0107\u0108\7n\2\2\u0108\u010a\7n\2\2\u0109\u0107\3\2\2\2\u0109\u010a"+
		"\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u010c\b*\5\2\u010c\u010d\b*\6\2\u010d"+
		"U\3\2\2\2\u010e\u010f\7v\2\2\u010f\u0110\7t\2\2\u0110\u0111\7w\2\2\u0111"+
		"\u0118\7g\2\2\u0112\u0113\7h\2\2\u0113\u0114\7c\2\2\u0114\u0115\7n\2\2"+
		"\u0115\u0116\7u\2\2\u0116\u0118\7g\2\2\u0117\u010e\3\2\2\2\u0117\u0112"+
		"\3\2\2\2\u0118W\3\2\2\2\u0119\u011c\7)\2\2\u011a\u011d\5`\60\2\u011b\u011d"+
		"\5b\61\2\u011c\u011a\3\2\2\2\u011c\u011b\3\2\2\2\u011d\u011e\3\2\2\2\u011e"+
		"\u011f\7)\2\2\u011fY\3\2\2\2\u0120\u0125\7$\2\2\u0121\u0124\5`\60\2\u0122"+
		"\u0124\5b\61\2\u0123\u0121\3\2\2\2\u0123\u0122\3\2\2\2\u0124\u0127\3\2"+
		"\2\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0128\3\2\2\2\u0127"+
		"\u0125\3\2\2\2\u0128\u0129\7$\2\2\u0129[\3\2\2\2\u012a\u012c\5f\63\2\u012b"+
		"\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012b\3\2\2\2\u012d\u012e\3\2"+
		"\2\2\u012e\u0131\3\2\2\2\u012f\u0130\7n\2\2\u0130\u0132\7n\2\2\u0131\u012f"+
		"\3\2\2\2\u0131\u0132\3\2\2\2\u0132]\3\2\2\2\u0133\u0136\5h\64\2\u0134"+
		"\u0136\7a\2\2\u0135\u0133\3\2\2\2\u0135\u0134\3\2\2\2\u0136\u013c\3\2"+
		"\2\2\u0137\u013b\5h\64\2\u0138\u013b\5f\63\2\u0139\u013b\7a\2\2\u013a"+
		"\u0137\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u0139\3\2\2\2\u013b\u013e\3\2"+
		"\2\2\u013c\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d_\3\2\2\2\u013e\u013c"+
		"\3\2\2\2\u013f\u0140\7^\2\2\u0140\u014a\7p\2\2\u0141\u0142\7^\2\2\u0142"+
		"\u014a\7v\2\2\u0143\u0144\7^\2\2\u0144\u014a\7^\2\2\u0145\u0146\7^\2\2"+
		"\u0146\u014a\7$\2\2\u0147\u0148\7^\2\2\u0148\u014a\7)\2\2\u0149\u013f"+
		"\3\2\2\2\u0149\u0141\3\2\2\2\u0149\u0143\3\2\2\2\u0149\u0145\3\2\2\2\u0149"+
		"\u0147\3\2\2\2\u014aa\3\2\2\2\u014b\u014c\t\4\2\2\u014cc\3\2\2\2\u014d"+
		"\u014e\t\5\2\2\u014ee\3\2\2\2\u014f\u0150\4\62;\2\u0150g\3\2\2\2\u0151"+
		"\u0154\5d\62\2\u0152\u0154\t\6\2\2\u0153\u0151\3\2\2\2\u0153\u0152\3\2"+
		"\2\2\u0154i\3\2\2\2\24\2\3qv\u0103\u0105\u0109\u0117\u011c\u0123\u0125"+
		"\u012d\u0131\u0135\u013a\u013c\u0149\u0153\7\b\2\2\5\2\2\4\3\2\t-\2\4"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}