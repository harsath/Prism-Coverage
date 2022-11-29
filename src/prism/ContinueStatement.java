package prism;

public class ContinueStatement implements Statement {
        private boolean executed;

        public ContinueStatement() {

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
