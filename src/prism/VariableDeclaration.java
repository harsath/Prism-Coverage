package prism;

public class VariableDeclaration implements Declaration {
        // The type of variable is deduced from the Expression (IntegerType or VoidType or BooleanType).
        // If expression is null, it is deduced as VoidType.
        private Expression expression;
        private String id;

        VariableDeclaration(String id, Expression expression) {
                this.id = id;
                this.expression = expression;
        }

        public Expression getExpression() {
                return this.expression;
        }

        public void setExpression(Expression expression) {
                this.expression = expression;
        }

        public String getId() {
                return this.id;
        }

        public void setId(String id) {
                this.id = id;
        }
        @Override
        public String toString() {
                if (expression != null) {
                        return id + " = " + expression.toString()+ "\n";
                }
                return id + "\n";
        }
}
