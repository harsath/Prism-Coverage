package prism;

public class IfElseStatement implements Statement {
        private Expression expr_condition;
        private Statement if_statement_block;
        private Statement else_statement_block;

        IfElseStatement() {
                else_statement_block = null;
        }

        public Expression getExpr_condition() {
                return this.expr_condition;
        }

        public void setExpr_condition(Expression expr_condition) {
                this.expr_condition = expr_condition;
        }

        public Statement getIf_statement_block() {
                return this.if_statement_block;
        }

        public void setIf_statement_block(Statement if_statement_block) {
                this.if_statement_block = if_statement_block;
        }

        public Statement getElse_statement_block() {
                return this.else_statement_block;
        }

        public void setElse_statement_block(Statement else_statement_block) {
                this.else_statement_block = else_statement_block;
        }

        @Override
        public String toString() {
                String returner = new String();
                returner += "IF (" + expr_condition.toString() + ") { \n" + if_statement_block.toString() + "}\n";
                if (else_statement_block != null) {
                        returner += "ELSE { \n" + else_statement_block.toString() + "}\n";
                }
                return returner;
        }
}
