package prism;

public class UnaryMinusExpression implements Expression {
	private Expression expression;
	private boolean executed = false;

	public UnaryMinusExpression(Expression expression) {
		this.expression = expression;
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
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "-" + expression.toString();
	}
}
