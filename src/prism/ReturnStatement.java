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

        @Override
        public String toString() {
                if (expression != null) {
                        return "RETURN " + expression.toString() + ";";
                }
                return "RETURN;";
        }
}
