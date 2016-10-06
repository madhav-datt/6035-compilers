package edu.mit.compilers;

import java.io.*;
import org.antlr.v4.runtime.*;
import edu.mit.compilers.grammar.*;
import edu.mit.compilers.tools.CLI;
import edu.mit.compilers.tools.CLI.Action;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

class Main {
  public static void main(String[] args) {
    try{
        CharStream stream = new ANTLRInputStream("int i;");
        DecafScanner lexer = new DecafScanner(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        DecafParser parser = new DecafParser(tokens);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        DecafListener listener = new DecafListener();
        walker.walk(listener, tree);

    } catch(Exception e) {
      // print the error:
      System.err.println(CLI.infile+" "+e);
    }
  }
}
