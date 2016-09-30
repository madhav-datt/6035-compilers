parser grammar DecafParser;

options
{
    language = 'Java';
    tokenVocab = 'DecafScanner';
    superClass = 'Parser'; //class DecafParser extends Parser;
    //buildAST = true;
}

@parser::header {
    package edu.mit.compilers.grammar;
}

// Java glue code that makes error reporting easier.
// You can insert arbitrary Java code into your parser/lexer this way.
@lexer::members
{
  // Do our own reporting of errors so the parser can return a non-zero status
  // if any errors are detected.
  /** Reports if any errors were reported during parse. */
  private boolean error;

  @Override
  public void reportError (RecognitionException ex) {
    // Print the error via some kind of error reporting mechanism.
    error = true;
  }
  @Override
  public void reportError (String s) {
    // Print the error via some kind of error reporting mechanism.
    error = true;
  }
  public boolean getError () {
    return error;
  }

  // Selectively turns on debug mode.

  /** Whether to display debug information. */
  private boolean trace = false;

  public void setTrace(boolean shouldTrace) {
    trace = shouldTrace;
  }
  @Override
  public void traceIn(String rname) throws TokenStreamException {
    if (trace) {
      super.traceIn(rname);
    }
  }
  @Override
  public void traceOut(String rname) throws TokenStreamException {
    if (trace) {
      super.traceOut(rname);
    }
  }
}

program :
    extern_decl* field_decl* method_decl*;

extern_decl :
    RES_EXTERN ID L_PAREN R_PAREN SEMI_COL;

field_decl :
    type (ID | ID L_SQUARE INT R_SQUARE) (COMMA (ID | ID L_SQUARE INT R_SQUARE))* SEMI_COL;

method_decl :
    (type | RES_VOID) ID L_PAREN ((type ID) (COMMA (type ID))* )? R_PAREN block;

block :
    L_CURL field_decl* statement* R_CURL;

type :
    RES_INT | RES_BOOL;

statement :
    location assign_op expr SEMI_COL
    | method_call SEMI_COL
    | RES_IF L_PAREN expr R_PAREN block (RES_ELSE block)?
    | RES_FOR L_PAREN ID AS_OP expr SEMI_COL expr SEMI_COL ID compound_assign_op expr L_PAREN block
    | RES_WHILE L_PAREN expr R_PAREN block
    | RES_RETURN (expr)? SEMI_COL
    | RES_BREAK SEMI_COL
    | RES_CONTINUE SEMI_COL
    ;

assign_op :
    AS_OP | compound_assign_op;

compound_assign_op :
    ADD_AS_OP | SUB_AS_OP;

method_call :
    method_name L_PAREN (expr (COMMA expr)*)? R_PAREN
    | method_name L_PAREN (extern_arg (COMMA extern_arg)*)? R_PAREN
    ;

method_name : ID;

location :
    ID | ID L_SQUARE expr R_SQUARE;

expr :
    location
    | method_call
    | literal
    | RES_SIZEOF L_PAREN ID R_PAREN
    | SUB_OP expr
    | NOT_OP
    | L_PAREN expr R_PAREN
    ;

extern_arg :
    expr | STRING;

bin_op :
    arith_op | rel_op | eq_op | cond_op;

arith_op :
    ADD_OP | SUB_OP | MUL_OP | DIV_OP | MOD_OP;

rel_op :
    LT_OP | GT_OP | LEQ_OP | GEQ_OP;

eq_op :
    EQ_OP | NEQ_OP;

cond_op :
    AND_OP | OR_OP;

literal :
    INT | CHAR | BOOL;















