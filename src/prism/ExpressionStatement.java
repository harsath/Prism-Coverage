package prism;

public class ExpressionStatement implements Statement {
        private Expression expression;

        ExpressionStatement(Expression expression) {
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
                return expression.toString();
        }

}
