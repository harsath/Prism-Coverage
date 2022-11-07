package app;

import antlr.*;
import prism.PrismProgramVisitor;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class PrismSourcefileParser {
        public static void main(String[] args) throws Exception {
                String input_file = null;
                if (args.length > 0) input_file = args[0];
                InputStream is = System.in;
                if (input_file != null) {
                        is = new FileInputStream(input_file);
                }
                ANTLRInputStream input = new ANTLRInputStream(is);
                PrismLexer lexer = new PrismLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                PrismParser parser = new PrismParser(tokens);
                parser.setBuildParseTree(true);
                ParseTree AST = parser.prog();

                PrismProgramVisitor prismVisitor = new PrismProgramVisitor();
                prismVisitor.visit(AST);
        }
}
