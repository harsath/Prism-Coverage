package app;

import antlr.*;
import prism.*;
import prism_interpreter.PrismInterpreter;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class PrismSourcefileInterpreter {
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
                AtomType return_value = prism_interpreter.interpret();
                if (return_value instanceof IntegerType) {
                        IntegerType return_value_int = (IntegerType) return_value;
                        System.out.println(return_value_int);
                }
        }
}