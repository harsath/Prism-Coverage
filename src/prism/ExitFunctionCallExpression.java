package prism;

public class ExitFunctionCallExpression implements Expression {
        private boolean executed = false;
        private Expression exitCodeExpression;
        private int exitCode;

        public ExitFunctionCallExpression(Expression exitCodeExpression) {
                this.exitCodeExpression = exitCodeExpression;
        }

        public Expression getExitCodeExpression() {
                return this.exitCodeExpression;
        }

        public void setExitCodeExpression(Expression exitCodeExpression) {
                this.exitCodeExpression = exitCodeExpression;
        }

        public int getExitCode() {
                return this.exitCode;
        }

        public void setExitCode(int exitCode) {
                this.exitCode = exitCode;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }
}
