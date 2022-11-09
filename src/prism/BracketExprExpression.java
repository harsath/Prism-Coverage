package prism;

public class BracketExprExpression implements Expression {
	private Expression expression;

	public BracketExprExpression(Expression expression) {
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
		return "(" + expression.toString() + ")";
	}
}
