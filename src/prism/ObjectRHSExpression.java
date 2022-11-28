package prism;

public class ObjectRHSExpression implements Expression {
        private boolean executed; 
        private String id;
        private FunctionParamListExpression param_decl;

        public ObjectRHSExpression() {
        }

        public String getId() {
                return this.id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public FunctionParamListExpression getParameterDeclaration() {
                return this.param_decl;
        }

        public void setParameterDeclaration(FunctionParamListExpression param_decl) {
                this.param_decl = param_decl;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }
}
