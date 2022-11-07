package prism;

public class ReturnStatement implements Statement {
        private Expression expression;

        ReturnStatement() {
                this.expression = null;
        }

        public Expression getExpression() {
                return this.expression;
        }

        public void setExpression(Expression expression) {
                this.expression = expression;
        }
}
