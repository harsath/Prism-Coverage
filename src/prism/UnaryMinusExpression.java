package prism;

public class UnaryMinusExpression implements Expression {
	private Expression expression;

	public UnaryMinusExpression(Expression expression) {
		this.expression = expression;
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
