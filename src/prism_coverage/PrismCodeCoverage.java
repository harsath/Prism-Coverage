package prism_coverage;

import java.io.*;
import java.util.*;

import prism.*;

public class PrismCodeCoverage {
	private PrismProgram prism_program;
	private Integer total_global_variables = 0;
	private Integer total_covered_global_variables = 0;
	private Integer total_function_decls = 0;
	private Integer total_covered_function_decls = 0;
	private Integer total_class_decls = 0;
	private Integer total_covered_class_decls = 0;
	// declaration coverage + statement coverage
	private Integer total_source_code = 0;
	private Integer total_covered_source_code = 0;
	private Integer total_if_else = 0;
	private Integer total_covered_if_else = 0;
	private Integer total_statements = 0;
	private Integer total_covered_statements = 0;

	public PrismCodeCoverage(PrismProgram prism_program) throws IOException {
		this.prism_program = prism_program;
		populateTotalAndCoveredDeclarations();
		populateSourceCodeTotalAndCovered();
		// TODO: we need same for classes once we support operations over classes
	}

	public Integer getTotalSourceCode() {
		return total_source_code;
	}

	public Integer getTotalCoveredSourceCode() {
		return total_covered_source_code;
	}

	public Integer getTotalFunctionDeclarations() {
		return total_function_decls;
	}

	public Integer getTotalCoveredFunctionDeclarations() {
		return total_covered_function_decls;
	}

	public Integer getTotalGlobalVariableDeclarations() {
		return total_global_variables;
	}

	public Integer getTotalCoveredGlobalVariableDeclarations() {
		return total_covered_global_variables;
	}

	public Integer getTotalClassDeclarations() {
		return total_class_decls;
	}

	public Integer getTotalCoveredClassDeclarations() {
		return total_covered_class_decls;
	}

	public Integer getTotalIfElse() {
		return total_if_else;
	}

	public Integer getTotalCoveredIfElse() {
		return total_covered_if_else;
	}

	public Integer getTotalStatements() {
		return total_statements;
	}
	public Integer getTotalCoveredStatements() {
		return total_covered_statements;
	}
	
	private void getTotalStatementsFromFunction(BlockStatement decl) {
		for (Statement stmt : decl.getStatements()) {
			if (stmt instanceof AssignmentStatement) {
				total_statements++;
			} else if (stmt instanceof IfElseStatement) {
				total_statements++;
				BlockStatement if_block = (BlockStatement) ((IfElseStatement)stmt).getIf_statement_block();
				getTotalStatementsFromFunction(if_block);
				BlockStatement else_block = (BlockStatement) ((IfElseStatement)stmt).getElse_statement_block();
				
				if (else_block != null) {
					total_statements++;
					getTotalStatementsFromFunction(else_block);
				}
			} else if (stmt instanceof ForLoopStatement) {
				total_statements++;
				getTotalStatementsFromFunction(((ForLoopStatement)stmt).getStatementBlock());
			} else if (stmt instanceof WhileLoopStatement) {
				total_statements++; 
				getTotalStatementsFromFunction(((WhileLoopStatement)stmt).getStatementBlock());
			}else if (stmt instanceof ReturnStatement) {
				// If a function returns before hitting this particular ReturnStatement, it will
				// not be executed
				total_statements++;
			} else if (stmt instanceof VariableDeclarationStatement) {
				total_statements++;
			} else {
				total_statements++;
			}
		}
	}

	private  void getTotalCoveredStatementsFromFunction(BlockStatement decl) {
		for (Statement stmt : decl.getStatements()) {
			if (stmt instanceof IfElseStatement) {
				BlockStatement if_block = (BlockStatement) ((IfElseStatement)stmt).getIf_statement_block();
				if (if_block.getIsExecuted()) {
					total_covered_statements++;
					getTotalCoveredStatementsFromFunction(if_block);
				}
				
				BlockStatement else_block = (BlockStatement) ((IfElseStatement)stmt).getElse_statement_block();
				if (else_block != null) {
					if (else_block.getIsExecuted()) {
						total_covered_statements++;
						getTotalCoveredStatementsFromFunction( else_block);
					}
					
				}
			} else if (stmt instanceof ForLoopStatement) {
				
				if (((ForLoopStatement) stmt).getInitBlock().getIsExecuted()){
					total_covered_statements++;
					getTotalCoveredStatementsFromFunction(((ForLoopStatement)stmt).getStatementBlock());
				}
				
			} else if (stmt instanceof WhileLoopStatement) {
				if (stmt.getIsExecuted()) {
					total_covered_statements++;
					getTotalCoveredStatementsFromFunction(((WhileLoopStatement)stmt).getStatementBlock());
				}
			} else {
				if (stmt.getIsExecuted()) {
					total_covered_statements++;
				}
			}
				
		}
	}

	private void populateTotalAndCoveredDeclarations() throws RuntimeException {
		List<Declaration> decls = prism_program.getProgram();
		for (Declaration decl : decls) {
			if (decl instanceof ClassDeclaration) {
				total_class_decls++;
				if (decl.getIsExecuted()) {
					total_covered_class_decls++;
				}
			} else if (decl instanceof FunctionDeclaration) {
				total_function_decls++;
				codeCoverageBlockCheck(((FunctionDeclaration) decl).getFunctionBody());
				if (decl.getIsExecuted()) {
					total_covered_function_decls++;
				}
			} else if (decl instanceof VariableDeclaration) {
				total_global_variables++;
				if (decl.getIsExecuted()) {
					total_covered_global_variables++;
				}
			} else {
				throw new RuntimeException("Unrecognized declaration");
			}
		}
		total_source_code += (total_class_decls + total_function_decls + total_global_variables);
		total_covered_source_code += (total_covered_class_decls + total_covered_function_decls
				+ total_covered_global_variables);

	}

	private void populateSourceCodeTotalAndCovered() throws RuntimeException {
		List<Declaration> decls = prism_program.getProgram();
		//System.out.println(decls.size());
		for (Declaration decl : decls) {
			if (decl instanceof FunctionDeclaration) {
				total_statements++;
				getTotalStatementsFromFunction(((FunctionDeclaration)decl).getFunctionBody());
				//System.out.println(total_source_code);
				if (decl.getIsExecuted()) {
					total_covered_statements++;
					getTotalCoveredStatementsFromFunction(((FunctionDeclaration)decl).getFunctionBody());
				}
			}
		}
	}

	private void codeCoverageBlockCheck(BlockStatement block) { // For decision coverage
		List<Statement> statements = block.getStatements();

		for (Statement statement : statements) {
			if (statement instanceof IfElseStatement) {
				IfElseStatement if_cast = (IfElseStatement) statement;
				total_if_else++;
				if (if_cast.getElse_statement_block() != null) {
					total_if_else++;
				}
				if (if_cast.getIsExecuted()) {
					if (if_cast.getIf_statement_block().getIsExecuted()) {
						total_covered_if_else++;
					}
					codeCoverageBlockCheck((BlockStatement) if_cast.getIf_statement_block());
				}
				if (if_cast.getElse_statement_block() != null) {
					if (if_cast.getElse_statement_block().getIsExecuted()) {
						total_covered_if_else++;
						codeCoverageBlockCheck((BlockStatement) if_cast.getElse_statement_block());
					}	
				}
				
			} else if (statement instanceof ForLoopStatement) {
				ForLoopStatement for_cast = (ForLoopStatement) statement;
				total_if_else++;
				if (for_cast.getStatementBlock().getIsExecuted()) {
					total_covered_if_else++;
					codeCoverageBlockCheck((BlockStatement) for_cast.getStatementBlock());
				}		
			} else if(statement instanceof WhileLoopStatement) {
				WhileLoopStatement while_cast = (WhileLoopStatement) statement;
				total_if_else++;
				if(while_cast.getIsExecuted()) {
					total_covered_if_else++;
					codeCoverageBlockCheck((BlockStatement) while_cast.getStatementBlock());
				}
			}
		}
	}
	/**
	public String desicionCoverageHTML() {
		String result = "";
		result += "<div id='header-text'>" + "<p>Total Desicions: <span class='color-red'>"
		+ 		+ getTotalIfElse() + "</span></p>"
		+ "<p>Total Desicions Covered:<span class='color-red'>"
		+  getTotalCoveredIfElse() + "</span></p>";
		if (getTotalIfElse() > 0) {
			result +="<p>Total Desicion Coverage<code>"
					+ "</code>: <span class='color-red'>"
					+ (int) calcCoveragePercentage(this.getTotalCoveredIfElse(),
							this.getTotalIfElse())
					+ "%</span></p>\n </div>";
		} else {
			result +="<p>Total Desicion Coverage<code>"
					+ "</code>: <span class='color-red'> 100% (No desicions in code)</span></p>\n </div>";
		}

		
		
		List<Declaration> decls = prism_program.getProgram();
		for (Declaration decl : decls) {
			if (decl instanceof FunctionDeclaration) {
				FunctionDeclaration funcDecl = (FunctionDeclaration) decl;
				String funcSignature = funcDecl.functionSignature();
				result += "<p> " + funcSignature + " { </p>";
				result += desicionsToString(funcDecl.getFunctionBody());
				result +="} ";
			}else {
				result += decl.toString();
			}
		}
		return result;
		
	}
	
	public String desicionsToString(BlockStatement decl){
		String result = "";
		for (Statement stmt : decl.getStatements()) {
			if (stmt instanceof ForLoopStatement) {
				ForLoopStatement for_cast = (ForLoopStatement) stmt;
				if (for_cast.getConditionalBlock().getIsExecuted()) {
					result += "<p class='covered'>" + for_cast.getConditionalBlock().toString() + "</p>";
				}else {
					result += "<p class='not-covered'>" + for_cast.getConditionalBlock().toString() + "</p>";
				}
				result += desicionsToString(for_cast.getStatementBlock());	
			} else if (stmt instanceof WhileLoopStatement) {
				WhileLoopStatement while_cast = (WhileLoopStatement) stmt;
				if (while_cast.getExpressionBlock().getIsExecuted()) {
					result += "<p class='covered'>" + while_cast.getExpressionBlock().toString() + "</p>";
				}else {
					result += "<p class='not-covered'>" + while_cast.getExpressionBlock().toString() + "</p>";
				}
				result += desicionsToString(while_cast.getStatementBlock());	
			} else if (stmt instanceof IfElseStatement) {
				IfElseStatement if_cast = (IfElseStatement) stmt;
				if (if_cast.getIsExecuted() && if_cast.getElse_statement_block() != null && !if_cast.getElse_statement_block().getIsExecuted()) {
					result += "<div class='tabbed'>";
					result += "<div class='covered'>IF (" + if_cast.getExpr_condition()
							+ ") { </div></br>";
					result += desicionsToString(
							(BlockStatement) if_cast.getIf_statement_block());
					result += "</div>";
				} else if (if_cast.getIsExecuted()) {
					result += "<div class='tabbed'><div class='part-covered'>IF ("
							+ if_cast.getExpr_condition() + "){ </div></div>";

					result += desicionsToString(
							(BlockStatement) if_cast.getIf_statement_block());
				} else {
					result += "<div class='tabbed'>";
					result += "<div class='not-covered'>"
							+ desicionsToString((BlockStatement) if_cast
									.getElse_statement_block())
							+ "</div>";
					result += "</div>";
				}
				result += "<div class='covered'>";
				result += "<div class='tabbed'>} </div>";
				result += "</div>";
				if (if_cast.getElse_statement_block() != null && if_cast.getElse_statement_block().getIsExecuted()) {
					result += "<div class='covered'>";
					result += "<div class='tabbed'>ELSE { </br>"
							+ desicionsToString((BlockStatement) if_cast
									.getElse_statement_block())
							+ "</br>}</br>" + "</div>";
					result += "</div>";
					// returner +=
					// statementsToString((BlockStatement)if_cast.getElse_statement_block());
				} else {
					result += "<div class='not-covered'>";
					result += "<div class='tabbed'>ELSE { </br>"
							+ if_cast.getElse_statement_block() + "</br>}</br>"
							+ "</div>";
					result += "</div>";

				}
				
			} else {
				result += "<p >" + stmt.toString()+"</p>";
			}
		}
		
		
		return result;
	}
	
	**/
	public String generateHTML() throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		sb.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js\"></script>\n"
			+ "  <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
		
		sb.append("<style>\n" + "                        .color-red {\n"
				+ "                                color: red;\n" + "                        }\n"
				+ "                        .covered {\n"
				+ "                                background-color: #50DBB4;\n"
				+ "                                margin-bottom: 0px;\n"
				+ "                                padding: 1px;\n"
				+ "								   white-space:pre;\n}"
				+ "			   .part-covered {\n"
				+ "                                background-color: #F6FF00;\n\n"
				+ "				   margin-bottom: 0px;\n"
				+ "				   padding: 1px;\n"
				+ "                                                               white-space:pre;\n}"
				+ "			   .not-covered {\n"
				+ "                                background-color: #FF6263;\n"
				+ "                                margin-bottom: 0px;\n"
				+ "                                padding: 1px;\n"
				+ "								   white-space:pre;\n"
				+ "                        }\n"
				+ "						   .tabbed {\n"
				+ "                                padding-left: 3em;\n"
				+ "							}\n	"
				+ "                        #main-body {\n"
				+ "                                width: 70%;\n" + "                        }\n"
				+ "                </style>");
		sb.append("<title>Code Coverage Result");
		sb.append("</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<ul class=\"nav nav-tabs\">\n"
				+ "    <li class=\"active\"><a data-toggle=\"tab\" href=\"#statementCoverage\">Statement Coverage</a></li>\n"
				+ "    <li><a data-toggle=\"tab\" href=\"#desicionCoverage\">Desicion Coverage</a></li>\n"
				+ "    <li><a data-toggle=\"tab\" href=\"#menu2\">Menu 2</a></li>\n"
				+ "    <li><a data-toggle=\"tab\" href=\"#menu3\">Menu 3</a></li>\n"
				+ "  </ul>"
			+ "<div class=\"tab-content\">");
		
		String input_file_name = new String();
		sb.append("<div id=\"statementCoverage\" class=\"tab-pane fade in active\">");
		sb.append("<div id='header-text'>\n" + "<p>Total Statements: <span class='color-red'>"
				+ total_statements + "</span></p>\n"
				+ "<p>Total Statements Covered:<span class='color-red'>"
				+ getTotalCoveredStatements() + "</span></p>\n"
				+ "<p>Total Statement Coverage<code>" + input_file_name
				+ "</code>: <span class='color-red'>"
				+ (int) calcCoveragePercentage(getTotalCoveredStatements(),getTotalStatements()) + "%</span></p>\n"
				+ "</span></p>\n");
		if (getTotalIfElse() > 0) {
			sb.append("<p>Total Desicions: <span class='color-red'>"
					+ getTotalIfElse() + "</span></p>\n"
					+ "<p>Total Desicions Covered:<span class='color-red'>"
					+ getTotalCoveredIfElse() + "</span></p>\n");
			sb.append("<p>Total Desicion Coverage<code>" + input_file_name
					+ "</code>: <span class='color-red'>"
					+ (int) calcCoveragePercentage(this.getTotalCoveredIfElse(),
							this.getTotalIfElse())
					+ "%</span></p>\n </div>");
		} else {
			sb.append("<p>Total Desicion Coverage<code>" + input_file_name
					+ "</code>: <span class='color-red'> 100%</span></p>\n </div>");
		}

		
		List<Declaration> decls = prism_program.getProgram();
		for (Declaration decl : decls) {
			if (decl.getIsExecuted()) {
				if (decl instanceof FunctionDeclaration) {
					FunctionDeclaration funcDecl = (FunctionDeclaration) decl;
					String funcSignature = funcDecl.functionSignature();

					sb.append( "<p class='covered'>" + funcSignature + " { </p>");
					sb.append( statementsToString(funcDecl.getFunctionBody()));
					sb.append("<div class='covered'>} </div>");
				}
			} else {
				sb.append("<p class='not-covered'>" + decl.toString() + "</p>");
			}
		}
		
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		String result = sb.toString();
		return result;
	}

	public void writeHTMLOutput(String file_path) throws IOException {
		String body = generateHTML();

		FileWriter fstream;
		fstream = new FileWriter(file_path);

		BufferedWriter out = new BufferedWriter(fstream);

		out.write(body);
		out.close();
	}

	public String statementsToString(BlockStatement statements) {
		String returner = new String();

		if (statements.getStatements().size() > 0) { // If there are statements in function block
			for (Statement statement : statements.getStatements()) {
				if (statement instanceof IfElseStatement) {
					IfElseStatement if_cast = (IfElseStatement) statement;
					if (if_cast.getIsExecuted()
							&& !if_cast.getElse_statement_block().getIsExecuted()) {
						returner += "<div class='covered'>";
						returner += "<div class='tabbed'>IF (" + if_cast.getExpr_condition()
								+ ") { </br>";

						returner += statementsToString(
								(BlockStatement) if_cast.getIf_statement_block());
						returner += "</div>";
					} else if (if_cast.getIsExecuted()) {
						returner += "<div class='part-covered'><div class='tabbed'>IF ("
								+ if_cast.getExpr_condition() + "){ </div></div>";

						returner += statementsToString(
								(BlockStatement) if_cast.getIf_statement_block());
					} else {
						returner += "<div class='not-covered'>";
						returner += "<div class='tabbed'>"
								+ statementsToString((BlockStatement) if_cast
										.getElse_statement_block()).toString()
								+ "</div>";
						returner += "</div>";
					}
					returner += "<div class='covered'>";
					returner += "<div class='tabbed'>} </div>";
					returner += "</div>";
					if (if_cast.getElse_statement_block().getIsExecuted()) {
						returner += "<div class='covered'>";
						returner += "<div class='tabbed'>ELSE { </br>"
								+ statementsToString((BlockStatement) if_cast
										.getElse_statement_block()).toString()
								+ "</br>}</br>" + "</div>";
						returner += "</div>";
						// returner +=
						// statementsToString((BlockStatement)if_cast.getElse_statement_block());
					} else {
						returner += "<div class='not-covered'>";
						returner += "<div class='tabbed'>ELSE { </br>"
								+ if_cast.getElse_statement_block() + "</br>}</br>"
								+ "</div>";
						returner += "</div>";

					}

				} else if (statement instanceof ForLoopStatement) {
					if (((ForLoopStatement) statement).getStatementBlock().getIsExecuted()) { // If
															// it
															// is
															// executed
						returner += statementToString(statement, true);
					} else {
						returner += statementToString(statement, false);
					}
				} else if (statement instanceof WhileLoopStatement) {
					if (((WhileLoopStatement) statement).getStatementBlock().getIsExecuted()) { // If
															// it
															// is
															// executed
						returner += statementToString(statement, true);
					} else {
						returner += statementToString(statement, false);
					}
				} else {
					returner += statementToString(statement, statement.getIsExecuted());
				}
			}
		}
		return returner;
	}

	public String statementToString(Statement statement, Boolean covered) {
		String result = new String();
		if (covered) {
			result += "<div class='covered'>";
			result += "<div class='tabbed'>" + statement.toString() + "</div>";
			// result += statement.toString() ;
			result += "</div>";
		} else {
			result += "<div class='not-covered'>";
			result += "<div class='tabbed'>" + statement.toString() + "</div>";
			result += "</div>";
		}
		return result;
	}

	public double calcCoveragePercentage(Integer covered, Integer total) {
		return ((double) covered / (double) total) * 100;
	}

}