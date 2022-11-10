package tests;

import antlr.*;
import prism.*;
import prism_interpreter.PrismInterpreter;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class InterpreterExprTest {
        private String test_root;
	public InterpreterExprTest(String test_root) {
                this.test_root = test_root;
	}

	public void test_function_call_expr() throws Exception {
                String input_file = test_root + "test_function_call_expr.prism"; 
		AtomType return_value = interpretGetAtomType(input_file);
                if (return_value instanceof IntegerType) {
                        IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 40), "test_function_call_expr failed. Return value: " + return_value_int.getValue());
                }
	}

	public void test_unary_minus_expr() throws Exception {
                String input_file = test_root + "test_unary_minus_expr.prism";
		AtomType return_value = interpretGetAtomType(input_file);
                if (return_value instanceof IntegerType) {
                        IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == -10), "test_unary_minus_expr failed. Return value: " + return_value_int.getValue());
                }
        }

	public AtomType interpretGetAtomType(String file_path) throws Exception {
                InputStream is = new FileInputStream(file_path);
                ANTLRInputStream input = new ANTLRInputStream(is);
                PrismLexer lexer = new PrismLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                PrismParser parser = new PrismParser(tokens);
                parser.setBuildParseTree(true);
                ParseTree AST = parser.prog();

                PrismProgramVisitor prismVisitor = new PrismProgramVisitor();
                PrismProgram prism_program = (PrismProgram) prismVisitor.visit(AST);

                PrismInterpreter prism_interpreter = new PrismInterpreter(prism_program.getProgram());
		return prism_interpreter.interpret();
	}
}