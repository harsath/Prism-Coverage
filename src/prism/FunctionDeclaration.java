package prism;

public class FunctionDeclaration implements Declaration {
	String functionName;
	ParameterListDeclaration functionParamDecl;
	AtomType returnType;
	BlockStatement functionBody;
	private boolean executed = false;

	public FunctionDeclaration() {
		this.functionParamDecl = null;		
	}

	@Override
        public void setIsExecuted(boolean value) {
                this.executed = value;
        }

        @Override
        public boolean getIsExecuted() {
                return this.executed;
        }

	public ParameterListDeclaration getFunctionParamDecl() {
		return this.functionParamDecl;
	}

	public void setFunctionParamDecl(ParameterListDeclaration functionParamDecl) {
		this.functionParamDecl = functionParamDecl;
	}

	public BlockStatement getFunctionBody() {
		return this.functionBody;
	}

	public void setFunctionBody(BlockStatement functionBody) {
		this.functionBody = functionBody;
	}

	public AtomType getReturnType() {
		return this.returnType;
	}

	public void setReturnType(AtomType returnType) {
		this.returnType = returnType;
	}

	public void addParam(ParameterDeclaration param) {
		this.functionParamDecl.addParam(param);
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Override
	public String toString() {
		String returner = new String();
		returner += "FUNCTION " + returnType.toString() + " " + functionName + " (";
		if (functionParamDecl != null) {
			returner += functionParamDecl.toString();
		}
		returner += ") {\n";
		if (functionBody != null) {
			returner += functionBody.toString();
		}
		returner += "\n}";
		return returner;
	}
	public String functionSignature() {
		String returner = new String();
		returner += "FUNCTION " + returnType.toString() + " " + functionName + " (";
		if (functionParamDecl != null) {
			returner += functionParamDecl.toString();
		}
		returner += ")";
		return returner;
	}
}
