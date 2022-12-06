package prism;

public class ArrayOperationExpression implements Expression {
        private boolean execute = false;
        private String id;
        private String array_operation;
        private FunctionParamListExpression expr_list;

        public ArrayOperationExpression(String id, String array_operation) {
                this.id = id;
                this.array_operation = array_operation;
                this.expr_list = null;
        }

        public void setParamListExpression(FunctionParamListExpression expr_list) {
                this.expr_list = expr_list;
        }

        public FunctionParamListExpression getParamListExpression() {
                return this.expr_list;
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
                if (this.expr_list != null) {
                        returner += "( " + this.expr_list.toString() + ")";
                }
                returner += ";";
                return returner;
        }
}