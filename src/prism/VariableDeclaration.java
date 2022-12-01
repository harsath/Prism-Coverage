package prism;

public class VariableDeclaration implements Declaration {
        private Expression expression;
        private String id;
        private String type;
	private boolean executed = false;

        public VariableDeclaration(String id, Expression expression) {
                this.id = id;
                this.expression = expression;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
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

        public String getType() {
                return this.type;
        }

        public void setType(String type) {
                this.type = type;
        }

        @Override
        public String toString() {
                String returner = "";
                if (expression != null) {
                        returner += type + " " + id + " = " + expression.toString()+ ";\n";
                        return returner;
                }
                return id + ";\n";
        }
}
