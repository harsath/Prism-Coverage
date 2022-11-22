package prism;

public class FunctionCallExpression implements Expression {
        private FunctionParamListExpression functionParam;
        private String functionName;
        private boolean executed = false;

        FunctionCallExpression() {
                this.functionParam = null;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

        public FunctionParamListExpression getFunctionParamList() {
                return functionParam;
        }

        public void setFunctionParamList(FunctionParamListExpression functionParam) {
                this.functionParam = functionParam;
        }

        public String getFunctionName() {
                return functionName;
        }

        public void setFunctionName(String functionName) {
                this.functionName = functionName;
        }

        @Override
        public String toString() {
                String returner = new String();
                returner += functionName + "(";
                if (functionParam != null) {
                        returner += functionParam.toString();
                }
                returner += ")";
                return returner;
        }
}
