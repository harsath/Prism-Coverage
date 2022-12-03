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
		interpreter_expr_test.test_object_attribute();
		interpreter_expr_test.test_object_method_call();
		interpreter_expr_test.test_exit();
		interpreter_expr_test.test_postfix_addition_subtraction();
		interpreter_expr_test.test_array_function_parameter();
		interpreter_expr_test.test_array_insert_remove_size();

		InterpreterStatementTest interpreter_stmt_test = new InterpreterStatementTest(feature_test_root);
		interpreter_stmt_test.test_for_loop_stmt();
		interpreter_stmt_test.test_while_loop_stmt();
		interpreter_stmt_test.test_break_stmt();
		interpreter_stmt_test.test_continue_stmt();

		CoverageDeclarationTest coverage_decl_test = new CoverageDeclarationTest(robust_test_root);
		coverage_decl_test.test_1();
	}
}