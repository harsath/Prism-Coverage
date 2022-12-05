package prism;

public class ArrayAtomExpression extends ArrayType implements Expression {
        private boolean execute;

        public ArrayAtomExpression(String type) {
                super(type);
        }

        @Override
        public boolean getIsExecuted() {
                return this.execute;
        }

        @Override
        public void setIsExecuted(boolean value) {
                this.execute = value;
        }

        @Override
        public String toString() {
                return super.toString();
        }
}
