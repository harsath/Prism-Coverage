package prism;

public class ArrayOperationExpression implements Expression {
        private boolean execute = false;
        private String id;
        private String array_operation;
        private Expression expr;

        public ArrayOperationExpression(String id, String array_operation) {
                this.id = id;
                this.array_operation = array_operation;
                this.expr = null;
        }

        public void setExpression(Expression expr) {
                this.expr = expr;
        }

        public Expression getExpression() {
                return this.expr;
        }

        public String getId() {
                return this.id;
        }

        public String getArrayOperation() {
                return this.array_operation;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.execute = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.execute;
        }

        @Override
        public String toString() {
                String returner = "";
                returner += this.id + "." + this.array_operation;
                if (this.expr != null) {
                        returner += "( " + this.expr.toString() + ")";
                }
                returner += ";";
                return returner;
        }
}