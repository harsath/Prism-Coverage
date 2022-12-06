package test_runner;

public class TestHelpers {
        public static void assertTrue(boolean value, String message) {
                if (!value) {
                        System.err.println(message);
                        System.exit(1);
                }
        }
}
