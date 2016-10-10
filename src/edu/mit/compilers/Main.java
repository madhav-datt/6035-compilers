package edu.mit.compilers;

import java.io.*;
import org.antlr.v4.runtime.*;
import edu.mit.compilers.grammar.*;
import edu.mit.compilers.tools.CLI;
import edu.mit.compilers.tools.CLI.Action;

class Main {
    public static void main(String[] args) {
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
            } else if (CLI.target == Action.PARSE ||
                    CLI.target == Action.DEFAULT) {
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
            }
        } catch(Exception e) {
            // print the error:
            System.err.println(CLI.infile+" "+e);
        }
    }
}