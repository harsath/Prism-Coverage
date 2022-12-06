package prism;

public class PostfixSubtractionExpression implements Expression {
        private boolean execute = false;
        private Expression expr;

        public PostfixSubtractionExpression(Expression expr) {
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

        @Override
        public String toString() {
                VariableAtomExpression expr_cast = (VariableAtomExpression) this.expr;
                return expr_cast.getId() + "--;";
        }
}
