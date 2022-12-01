package prism;

public class VariableAtomExpression implements Expression {
	private String id;
	private boolean executed = false;

	public VariableAtomExpression(String id) {
		this.id = id;
	}

	@Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

	public VariableAtomExpression() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}
