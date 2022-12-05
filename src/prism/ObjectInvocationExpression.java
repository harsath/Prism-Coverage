package prism;

public class ObjectInvocationExpression implements Expression {
        private boolean executed;
        private boolean is_method;
        private FunctionParamListExpression param_decl;
        String id;
        String member_id;

        public ObjectInvocationExpression() {
                this.param_decl = null;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getId() {
                return this.id;
        }

        public void setMember_Id(String member_id) {
                this.member_id = member_id;
        }

        public String getMember_Id() {
                return this.member_id;
        }

        public boolean getIs_method() {
                return this.is_method;
        }

        public void setIs_method(boolean is_method) {
                this.is_method = is_method;
        }

        public FunctionParamListExpression getParam_decl() {
                return this.param_decl;
        }

        public void setParam_decl(FunctionParamListExpression param_decl) {
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

        @Override
        public String toString() {
                String returner = "";
                returner += this.id + "." + this.member_id;
                if (this.is_method) {
                        returner += "(";
                        if (this.param_decl != null) {
                                returner += this.param_decl.toString();
                        }
                        returner += ")";
                }
                returner += ";";
                return returner;
        }
}
