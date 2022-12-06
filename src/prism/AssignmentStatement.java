package prism;

public class AssignmentStatement implements Statement {
        private Expression lhs;
        private Expression rhs;
        private boolean executed = false;

        AssignmentStatement(Expression lhs, Expression rhs) {
                this.lhs = lhs;
                this.rhs = rhs;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

        public Expression getLhs() {
                return this.lhs;
        }

        public void setLhs(Expression lhs) {
                this.lhs = lhs;
        }

        public Expression getRhs() {
                return this.rhs;
        }

        public void setRhs(Expression rhs) {
                this.rhs = rhs;
        }

        @Override
        public String toString() {
                return lhs.toString() + " = " + rhs.toString() + ";\n";
        }
}