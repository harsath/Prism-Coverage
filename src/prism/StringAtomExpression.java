package prism;

public class StringAtomExpression extends StringType implements Expression {
	private boolean executed = false;

	public StringAtomExpression(String value) {
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
