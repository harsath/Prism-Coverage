package tests;

public class TestRunner {
	public static void main(String[] args) throws Exception {
		String feature_test_root = "../src/prism_source_files/feature_tests/";
		String robust_test_root = "../src/prism_source_files/robust_tests/";

		InterpreterExprTest interpreter_expr_test = new InterpreterExprTest(feature_test_root);
		interpreter_expr_test.test_function_call_expr();
		interpreter_expr_test.test_string();
		interpreter_expr_test.test_unary_minus_expr();
		interpreter_expr_test.test_max();
		interpreter_expr_test.test_min();
		interpreter_expr_test.test_pow();
		interpreter_expr_test.test_print();
		interpreter_expr_test.test_println();

		InterpreterStatementTest interpreter_stmt_test = new InterpreterStatementTest(feature_test_root);
		interpreter_stmt_test.test_for_loop_stmt();
		interpreter_stmt_test.test_while_loop_stmt();

		CoverageDeclarationTest coverage_decl_test = new CoverageDeclarationTest(robust_test_root);
		coverage_decl_test.test_1();
	}
}