package prism;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeclaration implements Declaration {
	String functionName;
	ParameterListDeclaration functionParamDecl;
	AtomType returnType;
	BlockStatement functionBody;

	public FunctionDeclaration() {
		this.functionParamDecl = null;		
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
		returner += "}";
		return returner;
	}
}
