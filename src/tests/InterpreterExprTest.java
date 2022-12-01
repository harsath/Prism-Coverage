package tests;

import java.io.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import antlr.*;
import prism.*;
import prism_interpreter.*;

public class InterpreterExprTest {
	private String test_root;

	public InterpreterExprTest(String test_root) {
		this.test_root = test_root;
	}

	public void test_postfix_addition_subtraction() throws Exception {
		String input_file = test_root + "test_postfix_addition_subtraction.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 12),
					"test_postfix_addition_subtraction failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_exit() throws Exception {
		String input_file = test_root + "test_exit.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == -1),
					"test_exit failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_object_method_call() throws Exception {
		String input_file = test_root + "test_object_method_call.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 199),
					"test_object_method_call failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_object_attribute() throws Exception {
		String input_file = test_root + "test_object_attribute.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 10),
					"test_object_attribute failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_function_call_expr() throws Exception {
		String input_file = test_root + "test_function_call_expr.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 40),
					"test_function_call_expr failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_string() throws Exception {
		String input_file = test_root + "test_string.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof StringType) {
			StringType return_value_string = (StringType) return_value;
			TestHelpers.assertTrue((return_value_string.getValue().equals("HelloWorld")),
					"test_string failed. Return value: " + return_value_string.getValue());
		}
	}

	public void test_unary_minus_expr() throws Exception {
		String input_file = test_root + "test_unary_minus_expr.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == -10),
					"test_unary_minus_expr failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_max() throws Exception {
		String input_file = test_root + "test_max.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 15),
					"test_max failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_min() throws Exception {
		String input_file = test_root + "test_min.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == -6),
					"test_min failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_pow() throws Exception {
		String input_file = test_root + "test_pow.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 4),
					"test_pow failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_print() throws Exception {
		String input_file = test_root + "test_print.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof StringType) {
			StringType return_value_string = (StringType) return_value;
			TestHelpers.assertTrue((return_value_string.getValue() == "9false60false"),
					"test_print failed. Return value: " + return_value_string);
		}
	}

	public void test_println() throws Exception {
		String input_file = test_root + "test_println.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof StringType) {
			StringType return_value_string = (StringType) return_value;
			TestHelpers.assertTrue((return_value_string.getValue() == "9\nfalse\n60\nfalse\n"),
					"test_println failed. Return value: " + return_value_string);
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
