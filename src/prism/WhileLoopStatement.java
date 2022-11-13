package prism;

public class WhileLoopStatement implements Statement {
       private Expression expressionBlock; 
       private BlockStatement statementBlock;

       public WhileLoopStatement(Expression expressionBlock, BlockStatement statementBlock) {
                this.expressionBlock = expressionBlock;
                this.statementBlock = statementBlock;
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
                returner += ") {";
                for (Statement stmt : statementBlock.getStatements()) {
                        returner += "\t";
                        returner += stmt.toString();
                        returner += "\n";
                }
                returner += "}";
                return returner;
        }
}
