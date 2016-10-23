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

    private static void runFilesInDirectory(String dirPath) {
        File dir = new File(dirPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {

                // get the fileExtension
                String filePath = child.getPath();
                String fileExtension = "none";
                int i = filePath.lastIndexOf('.');
                if (i > 0) {
                    fileExtension = filePath.substring(i+1);
                }

                // run the test in the file
                if (fileExtension.equals("dcf")) {
                    try {
                        System.out.println("\n\n" + child.getPath());
                        CharStream stream = new ANTLRFileStream(child.getPath());
                        DecafScanner lexer = new DecafScanner(stream);
                        TokenStream tokens = new CommonTokenStream(lexer);
                        DecafParser parser = new DecafParser(tokens);
                        ParseTree tree = parser.program();
                        ParseTreeWalker walker = new ParseTreeWalker();
                        DecafListener listener = new DecafListener();
                        walker.walk(listener, tree);
                    }
                    catch (IOException e) {
                        System.out.println("There was an error:\n" + e);
                    }
                }
            }
        }
    }

    private static void runFile(String filePath) {
        // get the fileExtension
        String fileExtension = "none";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            fileExtension = filePath.substring(i+1);
        }

        // run the test in the file
        if (fileExtension.equals("dcf")) {
            try {
                System.out.println("\n\n" + filePath);
                CharStream stream = new ANTLRFileStream(filePath);
                DecafScanner lexer = new DecafScanner(stream);
                TokenStream tokens = new CommonTokenStream(lexer);
                DecafParser parser = new DecafParser(tokens);
                ParseTree tree = parser.program();
                ParseTreeWalker walker = new ParseTreeWalker();
                DecafListener listener = new DecafListener();
                walker.walk(listener, tree);
//            Trees.inspect(tree, parser);
            }
            catch (IOException e) {
                System.out.println("There was an error:\n" + e);
            }
        }
    }

    public static void main(String[] args) {
//        // test the parser
//        testParserCode(args);

//        // all tests
        String legalTests = "./tests/semantics-hidden/legal/";
//        Main.runFilesInDirectory(legalTests);

        String illegalTests = "./tests/semantics-hidden/illegal/";
        Main.runFilesInDirectory(illegalTests);

        String customTest = "./tests/semantics/legal/custom-01.dcf";
        Main.runFile(customTest);
    }
}