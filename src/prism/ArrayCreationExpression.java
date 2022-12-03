package prism;

public class ArrayCreationExpression implements Expression {
        private boolean execute;
        String type;

        public ArrayCreationExpression(String type) {
                this.type = type;
        }

        public String getType() {
                return this.type;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.execute = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.execute;
        }
}
