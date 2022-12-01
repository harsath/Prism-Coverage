package prism;

public class ReturnStatement implements Statement {
        private Expression expression;
	private boolean executed = false;

        ReturnStatement() {
                this.expression = null;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

        public Expression getExpression() {
                return this.expression;
        }

        public void setExpression(Expression expression) {
                this.expression = expression;
        }

        @Override
        public String toString() {
                if (expression != null) {
                        return "RETURN " + expression.toString() + ";";
                }
                return "RETURN;";
        }
}
