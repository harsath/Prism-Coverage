package app;

import antlr.*;
import prism.*;
import prism_interpreter.*;
import prism_coverage.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class PrismSourcefileCoverage {
        public static void main(String[] args) throws Exception {
                String input_file = "";
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
                PrismProgram prism_program = (PrismProgram) prismVisitor.visit(AST);
		PrismInterpreter prism_interpreter = new PrismInterpreter(prism_program.getProgram());
		prism_interpreter.interpret();
                PrismCodeCoverage prism_coverage = new PrismCodeCoverage(prism_program);
                File test = new File(input_file);
                prism_coverage.writeHTMLOutput("Coverage-"+test.getName() +".html");
        }
}
