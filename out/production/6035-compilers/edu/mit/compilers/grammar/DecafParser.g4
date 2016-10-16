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
catch [RecognitionException ex] {
   System.out.println("Program parsing failed");
   System.exit(1);
}

extern_decl :
    RES_EXTERN ID L_PAREN R_PAREN SEMI_COL;
catch [RecognitionException ex] {
   System.out.println("Extern declaration parsing failed");
   System.exit(1);
}

field_decl :
    var_type field (COMMA field)* SEMI_COL;
catch [RecognitionException ex] {
   System.out.println("Field declaration parsing failed");
   System.exit(1);
}

field:
    ID #VarDecl
    | ID L_SQUARE INT R_SQUARE #ArrayDecl
    ;

method_decl :
    method_type ID L_PAREN (param_decl (COMMA param_decl)* )? R_PAREN block;
catch [RecognitionException ex] {
   System.out.println("Method parsing failed");
   System.exit(1);
}

param_decl : var_type ID;

block :
    L_CURL field_decl* statement* R_CURL;

var_type :
    RES_INT | RES_BOOL;

method_type :
    RES_INT | RES_BOOL | RES_VOID;

statement :
    location assign_op expr SEMI_COL #AssignStmt
    | method_call SEMI_COL #AnyMethodCall
    | RES_IF L_PAREN expr R_PAREN block (RES_ELSE block)? #IfStmt
    | RES_FOR L_PAREN ID AS_OP expr SEMI_COL expr SEMI_COL ID compound_assign_op expr R_PAREN block #ForLoop
    | RES_WHILE L_PAREN expr R_PAREN block #WhileLoop
    | RES_RETURN (expr)? SEMI_COL #ReturnStmt
    | RES_BREAK SEMI_COL #BreakStmt
    | RES_CONTINUE SEMI_COL #ContinueStmt
    ;
catch [RecognitionException ex] {
   System.out.println("Statement parsing failed");
   System.exit(1);
}

assign_op :
    AS_OP | compound_assign_op;

compound_assign_op :
    ADD_AS_OP | SUB_AS_OP;

//assign_stmt :
//    location assign_op expr SEMI_COL;

//return_stmt :
//    RES_RETURN (expr)? SEMI_COL;

method_call :
    // normal methods are subsets of external_methods
    // so we don't gain anything by differentiating between
    // extern methods and internal methods in the parser,
    // we will need to manually check which type of method (extern or normal)
    // a method call actually is
    //    | method_name L_PAREN (expr (COMMA expr)*)? R_PAREN
    method_name L_PAREN (extern_arg (COMMA extern_arg)*)? R_PAREN
    ;
catch [RecognitionException ex] {
   System.out.println("Method-call parsing failed");
   System.exit(1);
}

method_name : ID;

location :
    ID #VarLocation
    | ID L_SQUARE expr R_SQUARE #ArrayLocation
    ;

expr :
    location #DummyLabel
    | method_call #NonVoidMethodCall
    | literal #DummyLabel
    | sizeof_call #DummyLabel
    | SUB_OP expr #NegateExpr
    | NOT_OP expr #NotExpr
    | expr arith_op expr #ArithExpr
    | expr rel_op expr #RelExpr
    | expr eq_op expr #EqateExpr
    | expr cond_op expr #CondExpr
    | L_PAREN expr R_PAREN #ParenExpr
    ;
catch [RecognitionException ex] {
   System.out.println("Expression parsing failed");
   System.exit(1);
}

sizeof_call :
    RES_SIZEOF L_PAREN ID R_PAREN #SizeOfVar
    | RES_SIZEOF L_PAREN var_type R_PAREN #SizeOfType
    ;

extern_arg :
    expr | STRING;
catch [RecognitionException ex] {
   System.out.println("Argument parsing failed");
   System.exit(1);
}

//uni_op :
//    SUB_OP | NOT_OP;

//bin_op :
//    arith_op | rel_op | eq_op | cond_op;

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
