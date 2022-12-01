package prism;

public class PrintlnFunctionCallExpression implements Expression {
	private Expression expr;
	private boolean executed = false;

	public PrintlnFunctionCallExpression(Expression expr) {
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
		return "PRINTLN(" + expr.toString() + ");";
	}	
}
