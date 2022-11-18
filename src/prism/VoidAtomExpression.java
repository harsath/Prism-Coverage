package prism;

public class VoidAtomExpression extends VoidType implements Expression {
	private boolean executed = false;

	public VoidAtomExpression() {
		super();
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
