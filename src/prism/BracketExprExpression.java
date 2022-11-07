package prism;

public class BracketExprExpression implements Expression {
	Expression expression;

	public BracketExprExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "(" + expression.toString() + ")";
	}
}
