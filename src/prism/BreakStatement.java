package prism;

public class BreakStatement implements Statement {
        private boolean executed;

        public BreakStatement() {

        }

        @Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }
}
