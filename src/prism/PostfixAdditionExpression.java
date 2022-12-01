package prism;

public class PostfixAdditionExpression implements Expression {
        private boolean execute = false;
        private Expression expr;

        public PostfixAdditionExpression(Expression expr) {
                this.expr = expr;
        }

        public Expression getExpr() {
                return this.expr;
        }

        public void setExpr(Expression expr) {
                this.expr = expr;
        }

        @Override
        public boolean getIsExecuted() {
                return this.execute;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.execute = value;
        }
}
