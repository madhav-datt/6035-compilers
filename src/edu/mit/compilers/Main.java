package edu.mit.compilers;

import edu.mit.compilers.grammar.DecafParser;
import edu.mit.compilers.grammar.DecafScanner;
import edu.mit.compilers.tools.CLI;
import edu.mit.compilers.tools.CLI.Action;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.gui.Trees;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;


class Main {

    public static void testParserCode(String[] args) {
        try {
            CLI.parse(args, new String[0]);
            InputStream inputStream = args.length == 0 ?
                    System.in : new java.io.FileInputStream(CLI.infile);
            PrintStream outputStream = CLI.outfile == null ? System.out : new java.io.PrintStream(new java.io.FileOutputStream(CLI.outfile));
            if (CLI.target == Action.SCAN) {
                DecafScanner scanner =
                        new DecafScanner(new ANTLRInputStream(inputStream));
                //        scanner.setTrace(CLI.debug);
                Token token;
                boolean done = false;
                while (!done) {
                    try {
                        for (token = scanner.nextToken();
                             token.getType() != DecafParser.EOF;
                             token = scanner.nextToken()) {
                            String type = "";
                            String text = token.getText();

                            //              System.out.println("\n\n"+token.getType());
                            switch (token.getType()) {
                                // TODO: add strings for the other types here...
                                case DecafScanner.CHAR:
                                    type = " CHARLITERAL";
                                    break;
                                case DecafScanner.INT:
                                    type = " INTLITERAL";
                                    break;
                                case DecafScanner.STRING:
                                    type = " STRINGLITERAL";
                                    break;
                                case DecafScanner.BOOL:
                                    type = " BOOLEANLITERAL";
                                    break;
                                case DecafScanner.ID:
                                    type = " IDENTIFIER";
                                    break;
                            }
                            outputStream.println(token.getLine()  + type + " " + text);
                        }
                        done = true;
                    } catch(Exception e) {
                        // print the error:
                        System.err.println(CLI.infile + " " + e);
                        //            scanner.consume();
                        scanner.skip(); // replaces
                    }
                }
            } else if (CLI.target == Action.PARSE) {
                DecafScanner scanner =
                        new DecafScanner(new ANTLRInputStream(inputStream));
                CommonTokenStream tokenStream = new CommonTokenStream(scanner); // added for Antlr4
                DecafParser parser = new DecafParser(tokenStream);
                parser.setTrace(CLI.debug);
                parser.program();
                //        if(parser.getError()) {
                if (parser.getNumberOfSyntaxErrors() > 0) {
                    System.exit(1);
                }
            } else if (CLI.target == Action.INTER ||
                    CLI.target == Action.DEFAULT) {

                DecafScanner lexer = new DecafScanner(new ANTLRInputStream(inputStream));
                TokenStream tokens = new CommonTokenStream(lexer);
                DecafParser parser = new DecafParser(tokens);
                ParseTree tree = parser.program();
                ParseTreeWalker walker = new ParseTreeWalker();
                DecafListener listener = new DecafListener();
                walker.walk(listener, tree);
//                Trees.inspect(tree, parser); // Makes pretty graph
            }
        } catch(Exception e) {
            // print the error:
            System.err.println(CLI.infile+" "+e);
        }
    }

    public static void main(String[] args) {
        Main.testParserCode(args);
    }
}