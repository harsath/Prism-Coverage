package prism;

public class ForLoopStatement implements Statement {
	private VariableDeclarationStatement initBlock;
	private Expression conditionalBlock;
	private AssignmentStatement updationAssignmentBlock = null;
	private Expression updationExpressionBlock = null;
	private BlockStatement statementBlock;
	private boolean executed = false;

	public ForLoopStatement(VariableDeclarationStatement initBlock, Expression conditionalBlock,
			BlockStatement statementBlock) {
		this.initBlock = initBlock;
		this.conditionalBlock = conditionalBlock;
		this.statementBlock = statementBlock;
	}

	@Override
	public void setIsExecuted(boolean value) {
		this.executed = value;
	}

	@Override
	public boolean getIsExecuted() {
		return this.executed;
	}

	public BlockStatement getStatementBlock() {
		return this.statementBlock;
	}

	public void setStatementBlock(BlockStatement statementBlock) {
		this.statementBlock = statementBlock;
	}

	public VariableDeclarationStatement getInitBlock() {
		return this.initBlock;
	}

	public void setInitBlock(VariableDeclarationStatement initBlock) {
		this.initBlock = initBlock;
	}

	public Expression getConditionalBlock() {
		return this.conditionalBlock;
	}

	public void setConditionalBlock(Expression conditionalBlock) {
		this.conditionalBlock = conditionalBlock;
	}

	public AssignmentStatement getUpdationAssignmentBlock() {
		return this.updationAssignmentBlock;
	}

	public void setUpdationAssignmentBlock(AssignmentStatement updationAssignmentBlock) {
		this.updationAssignmentBlock = updationAssignmentBlock;
	}

	public Expression getUpdationExpressionBlock() {
		return this.updationExpressionBlock;
	}

	public void setUpdationExpressionBlock(Expression updationExpresionBlock) {
		this.updationExpressionBlock = updationExpresionBlock;
	}

	public String getBracketBlock() {
		String returner = "(";
		String init_block_str = initBlock.toString();
		init_block_str = init_block_str.substring(0, init_block_str.length() - 1);
		returner += init_block_str + " " + conditionalBlock.toString() + "; ";
		if (updationExpressionBlock != null) {
			returner += updationExpressionBlock.toString();
		} else {
			returner += updationAssignmentBlock.toString();
		}
		returner += ")";
		return returner;
	}

	@Override
	public String toString() {
		String returner = "FOR ";
		returner += getBracketBlock();
		returner += " {\n";
		for (Statement stmt : statementBlock.getStatements()) {
			returner += "\t";
			returner += stmt.toString();
			returner += "\n";
		}
		returner += "}";
		return returner;
	}

}
