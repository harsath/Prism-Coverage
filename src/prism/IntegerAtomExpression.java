package prism;

public class IntegerAtomExpression extends IntegerType implements Expression {
	private boolean executed = false;

	public IntegerAtomExpression(int value) {
		super(value);
	}

	@Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

	@Override
	public String toString() {
		return super.toString();
	}
}
