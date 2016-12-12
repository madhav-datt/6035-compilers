lexer grammar DecafScanner;

options
{
  language = 'Java';
  superClass = 'Lexer'; //class DecafScanner extends Lexer;
}

@lexer::header {
    package edu.mit.compilers.grammar;
}

// Selectively turns on debug tracing mode.
// You can insert arbitrary Java code into your parser/lexer this way.
@lexer::memebers
{
  /** Whether to display debug information. */
  private boolean trace = false;

  public void setTrace(boolean shouldTrace) {
    trace = shouldTrace;
  }
  @Override
  public void traceIn(String rname) throws CharStreamException {
    if (trace) {
      super.traceIn(rname);
    }
  }
  @Override
  public void traceOut(String rname) throws CharStreamException {
    if (trace) {
      super.traceOut(rname);
    }
  }
}

// Note that here, the {} syntax allows you to literally command the lexer
// to skip mark this token as skipped, or to advance to the next line
// by directly adding Java commands.
WS_ : (' ' | '\t' | '\n' ) -> skip;
COMMENT : ('//'|'#') (~'\n')* '\n' -> skip;

// Miscillaneous characters
L_SQUARE : '[';
R_SQUARE : ']';
L_PAREN : '(';
R_PAREN : ')';
L_CURL : '{';
R_CURL : '}';
COMMA : ',';
SEMI_COL :  ';';

// boolean ops
LT_OP : '<';
GT_OP : '>';
LEQ_OP : '<=';
GEQ_OP : '>=';
EQ_OP : '==';
NEQ_OP : '!=';
AND_OP : '&&';
OR_OP : '||';
NOT_OP : '!';

// arithmatic ops
ADD_OP  : '+';
SUB_OP : '-';
MUL_OP : '*';
DIV_OP : '/';
MOD_OP : '%';

// assignment op
AS_OP : '=';
ADD_AS_OP : '+=';
SUB_AS_OP : '-=';


// reserved words
RES_BOOL : 'bool';
RES_BREAK : 'break';
RES_EXTERN : 'extern';
RES_CONTINUE : 'continue';
RES_ELSE : 'else';
RES_FOR : 'for';
RES_WHILE : 'while';
RES_IF : 'if';
RES_INT : 'int';
RES_RETURN : 'return';
RES_SIZEOF : 'sizeof';
RES_VOID : 'void';

// prefixes
HEX_PREFIX: '0x' -> more, mode(HEX_MODE);

// modes
mode HEX_MODE;
HEX_INT : (DIGIT | HEX_LETTERS)+ ('ll')? -> type(INT), mode(DEFAULT_MODE);

// return to DEFAULT_MODE
mode DEFAULT_MODE;

// main tokens
BOOL : 'true' | 'false';
CHAR : '\'' (ESC | ALLOWED_CHARS ) '\'';
STRING : '"' (ESC | ALLOWED_CHARS )* '"';
INT : DIGIT+ ('ll')?;
ID : (ALL_LETTERS | '_')(ALL_LETTERS | DIGIT | '_')*;


// fragments
fragment ESC : '\\n' | '\\t' | '\\\\' | '\\"' | '\\\''; //'\\' ('n' | 't' | '\\' | '"' | '\'');
fragment ALLOWED_CHARS : ' '..'!' | '#'..'&' | '('..'[' | ']'..'~' ;
fragment HEX_LETTERS : 'a'..'f' | 'A'..'F';
fragment DIGIT : '0'..'9';
fragment ALL_LETTERS : HEX_LETTERS | 'g'..'z' | 'G'..'Z';
