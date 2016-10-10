package edu.mit.compilers;

import edu.mit.compilers.grammar.DecafParser;
import edu.mit.compilers.grammar.DecafScanner;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.gui.Trees;

import java.io.IOException;

class Main {
    public static void main(String[] args) {
        String prefix = "/Users/devinmorgan/Documents/Java_Workspace_6035/6035-compilers/tests/semantics/";
        try {
            CharStream stream = new ANTLRFileStream(prefix + "legal/custom-01.dcf");
            DecafScanner lexer = new DecafScanner(stream);
            TokenStream tokens = new CommonTokenStream(lexer);
            DecafParser parser = new DecafParser(tokens);
            ParseTree tree = parser.program();
            ParseTreeWalker walker = new ParseTreeWalker();
            DecafListener listener = new DecafListener();
            walker.walk(listener, tree);
            Trees.inspect(tree, parser);
        }
        catch (IOException e) {
            System.out.println("There was an error:\n" + e);
        }

    }
}