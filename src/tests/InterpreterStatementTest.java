package tests;

import antlr.*;
import prism.*;
import prism_interpreter.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class InterpreterStatementTest {
	private String test_root;

	public InterpreterStatementTest(String test_root) {
		this.test_root = test_root;
	}

	public void test_continue_stmt() throws Exception {
		String input_file = test_root + "test_continue_stmt.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 10),
					"test_continue_stmt failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_break_stmt() throws Exception {
		String input_file = test_root + "test_break_stmt.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 5),
					"test_break_stmt failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_for_loop_stmt() throws Exception {
		String input_file = test_root + "test_for_loop_stmt.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 81),
					"test_for_loop_stmt failed. Return value: " + return_value_int.getValue());
		}
	}

	public void test_while_loop_stmt() throws Exception {
		String input_file = test_root + "test_for_loop_stmt.prism";
		AtomType return_value = interpretGetAtomType(input_file);
		if (return_value instanceof IntegerType) {
			IntegerType return_value_int = (IntegerType) return_value;
			TestHelpers.assertTrue((return_value_int.getValue() == 81),
					"test_while_loop_stmt failed. Return value: " + return_value_int.getValue());
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
