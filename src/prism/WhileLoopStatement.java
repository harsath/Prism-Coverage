package prism;

public class WhileLoopStatement implements Statement {
       private Expression expressionBlock; 
       private BlockStatement statementBlock;
       private boolean executed = false;

       public WhileLoopStatement(Expression expressionBlock, BlockStatement statementBlock) {
                this.expressionBlock = expressionBlock;
                this.statementBlock = statementBlock;
       }

       @Override
       public void setIsExecuted(boolean value) {
               this.executed = value;
       }

       @Override
       public boolean getIsExecuted() {
               return this.executed;
       }

        public Expression getExpressionBlock() {
                return this.expressionBlock;
        }

        public void setExpressionBlock(Expression expressionBlock) {
                this.expressionBlock = expressionBlock;
        }

        public BlockStatement getStatementBlock() {
                return this.statementBlock;
        }

        public void setStatementBlock(BlockStatement statementBlock) {
                this.statementBlock = statementBlock;
        }

        @Override
        public String toString() {
                String returner = "WHILE (";
                returner += expressionBlock.toString();
                returner += ") {\n";
                for (Statement stmt : statementBlock.getStatements()) {
                        returner += "\t";
                        returner += stmt.toString();
                        returner += "\n";
                }
                returner += "}";
                return returner;
        }
}
