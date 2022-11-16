package tests;

public class TestRunner {
	public static void main(String[] args) throws Exception {
		String test_root = "../src/prism_source_files/feature_tests/";
		InterpreterExprTest interpreter_expr_test = new InterpreterExprTest(test_root);
		interpreter_expr_test.test_function_call_expr();
		interpreter_expr_test.test_string();
		interpreter_expr_test.test_unary_minus_expr();
		interpreter_expr_test.test_max();
		interpreter_expr_test.test_min();
		interpreter_expr_test.test_pow();

		InterpreterStatementTest interpreter_stmt_test = new InterpreterStatementTest(test_root);
		interpreter_stmt_test.test_for_loop_stmt();
		interpreter_stmt_test.test_while_loop_stmt();
	}
}