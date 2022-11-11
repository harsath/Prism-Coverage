package tests;

public class TestRunner {
        public static void main(String[] args) throws Exception {
                String test_root = "../src/prism_source_files/feature_tests/";
                InterpreterExprTest interpreter_test = new InterpreterExprTest(test_root);
                interpreter_test.test_function_call_expr();
                interpreter_test.test_unary_minus_expr();
                interpreter_test.test_max();
                interpreter_test.test_min();
                interpreter_test.test_pow();
        }
}