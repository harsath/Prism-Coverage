package prism;

public class PrintFunctionCallExpression implements Expression {
	private Expression expr;
	private boolean executed = false;

	public PrintFunctionCallExpression(Expression expr) {
		this.expr = expr;
	}

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String toString() {
		return "PRINT(" + expr.toString() + ");";
	}
}
