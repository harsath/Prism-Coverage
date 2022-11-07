package prism;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeclaration implements Declaration {
	String functionName;
	List<ParameterDeclaration> functionParamDecl;
	AtomType returnType;
	BlockStatement functionBody;

	public FunctionDeclaration() {
		this.functionParamDecl = new ArrayList<>();		
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
		this.functionParamDecl.add(param);
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public List<ParameterDeclaration> getFunctionParamDecl() {
		return this.functionParamDecl;
	}

	public void setFunctionParamDecl(List<ParameterDeclaration> functionParamDecl) {
		this.functionParamDecl = functionParamDecl;
	}
}
