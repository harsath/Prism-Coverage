package prism;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement implements Statement {
        private List<Statement> statements;
        private boolean executed = false;

        BlockStatement() {
                this.statements = new ArrayList<>();
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

        public void addStatement(Statement statement) {
                this.statements.add(statement);
        }

        public List<Statement> getStatements() {
                return this.statements;
        }

        public void setStatements(List<Statement> statements) {
                this.statements = statements;
        }

        @Override
        public String toString() {
                String returner = new String();
                // returner += "{ \n";
                for (Statement stmt : statements) {
                        returner += "\t";
                        returner += stmt.toString();
                }
                // returner += "} \n";
                return returner;
        }
}
