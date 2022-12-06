package prism_coverage;
 
import java.io.*;
import java.util.*;
 
import org.antlr.v4.codegen.model.chunk.ThisRulePropertyRef_ctx;
 
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
 
	private Integer counter = 0;
	private Map<Integer,String> lines = new HashMap<Integer,String>();
	//Data-Coverage
	private Map<String, String> du_pairs = new HashMap<String,String>();
	// (variable:X, (1,2), (1,4), (5,3)....) 
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
				ClassDeclaration classDecl = (ClassDeclaration) decl;
				
				for (FunctionDeclaration func : classDecl.getClassBody().getMethods().getMethods()) {
					codeCoverageBlockCheck(func.getFunctionBody());
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
			}else {
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
			}else if (decl instanceof ClassDeclaration) {
				ClassDeclaration classDecl = (ClassDeclaration) decl;
				
				for (FunctionDeclaration func : classDecl.getClassBody().getMethods().getMethods()) {
					total_statements++;
					getTotalStatementsFromFunction(func.getFunctionBody());
					
					if (func.getIsExecuted()) {
						total_covered_statements++;
						getTotalCoveredStatementsFromFunction(func.getFunctionBody());
					}
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
 
	public String dataCoverage() {
		String result = "";
		List<Declaration> decls = prism_program.getProgram();
		//List<Declaration> decls = prism_program.getProgram();
		for (Declaration decl : decls) {
			if (decl instanceof FunctionDeclaration) {
				FunctionDeclaration funcDecl = (FunctionDeclaration) decl;
				String funcSignature = funcDecl.functionSignature();
				result += "<p >" + this.counter +" "+ funcSignature + " { </p>";
				this.lines.put( counter,funcSignature); this.counter++;
				result += datasToString(funcDecl.getFunctionBody());
				result += "<div >} </div>";
			}
		}
 
		//System.out.println(this.lines);
		for (Declaration decl : decls) {
			if (decl instanceof FunctionDeclaration) {
				FunctionDeclaration funcDecl = (FunctionDeclaration) decl;
				findDU(funcDecl.getFunctionBody(), funcDecl.getFunctionParamDecl());
				findDUMatch(null);
 
			}
		}
		Set<String> set = new HashSet<String>();
		
		for (String var : this.du_pairs.keySet()) {
			if (this.du_pairs.get(var) != null) {
				String[] lns = this.du_pairs.get(var).split(",");
				result += "</br>" + var ;
				for (String l : lns) {
					set.add(l.strip());
				}
				System.out.println(var + " "+ set.toString());
				for (String s : set) {
					result += "(" + lns[0] + ", ";
					result += s + ")";
				}
				set = new HashSet<String>();
			}
		}
		return result;
	}
 
	public void findDUPairs(BlockStatement block) {
 
		// put in declarations
		for (Statement stmt : block.getStatements()) {
			if (stmt instanceof VariableDeclaration) {
				VariableDeclaration stmt_cast = (VariableDeclaration)stmt;
				findDUMatch(stmt_cast.getExpression().toString().split(" "));
			}else if(stmt instanceof VariableDeclarationStatement) {
				VariableDeclarationStatement stmt_cast = (VariableDeclarationStatement) stmt;
				findDUMatch(stmt_cast.getExpression().toString().split(" "));
			}else if(stmt instanceof ForLoopStatement) {
				ForLoopStatement stmt_cast = (ForLoopStatement) stmt;
				findDUPairs(stmt_cast.getStatementBlock());
			}else if(stmt instanceof WhileLoopStatement) {
				WhileLoopStatement stmt_cast = (WhileLoopStatement) stmt;
				findDUPairs(stmt_cast.getStatementBlock());
			}else if (stmt instanceof AssignmentStatement) {
				AssignmentStatement stmt_cast = (AssignmentStatement) stmt;
 
			}else if(stmt instanceof IfElseStatement) {
				IfElseStatement stmt_cast = (IfElseStatement) stmt;
				findDUPairs((BlockStatement)stmt_cast.getIf_statement_block());
 
				if (stmt_cast.getElse_statement_block() !=null) {
					findDUPairs((BlockStatement)stmt_cast.getElse_statement_block());
				}
			}else if(stmt instanceof ReturnStatement) {
				ReturnStatement stmt_cast = (ReturnStatement) stmt;
				String[] expr = stmt_cast.getExpression().toString().split(" ");
				findDUMatch(expr);	
			}
 
		}
 
 
 
	}
	public void findDU(BlockStatement block, ParameterListDeclaration params) {
		if (params != null) {
			for (ParameterDeclaration param : params.getParamList()) {
				this.du_pairs.put(param.getId(), null);
			}	
		}
 
		// put in declarations
		for (Statement stmt : block.getStatements()) {
			if (stmt instanceof VariableDeclaration) {
				VariableDeclaration stmt_cast = (VariableDeclaration)stmt;
				this.du_pairs.put(stmt_cast.getId(), null);
			}else if(stmt instanceof VariableDeclarationStatement) {
				VariableDeclarationStatement stmt_cast = (VariableDeclarationStatement) stmt;
 
				this.du_pairs.put(stmt_cast.getId(), null);
 
				if (stmt_cast.getExpression().toString().contains(du_pairs.toString())) {
					//System.out.println(stmt_cast);
				}
			}else if(stmt instanceof ForLoopStatement) {
				ForLoopStatement stmt_cast = (ForLoopStatement) stmt;
				findDU(stmt_cast.getStatementBlock(), null);
			}else if(stmt instanceof WhileLoopStatement) {
				WhileLoopStatement stmt_cast = (WhileLoopStatement) stmt;
				findDU(stmt_cast.getStatementBlock(), null);
			}else if (stmt instanceof AssignmentStatement) {
				AssignmentStatement stmt_cast = (AssignmentStatement) stmt;
				this.du_pairs.put(stmt_cast.getLhs().toString(), null);
 
			}else if(stmt instanceof IfElseStatement) {
				IfElseStatement stmt_cast = (IfElseStatement) stmt;
				findDU((BlockStatement)stmt_cast.getIf_statement_block(), null);
 
				if (stmt_cast.getElse_statement_block() !=null) {
					findDU((BlockStatement)stmt_cast.getElse_statement_block(), null);
				}
			}
 
 
		}
 
 
 
	}
 
	public void findDUMatch(String[] exprs) {
		// Need to asociate lines with declarations,
		// then need to associtate uses with the declarations.
		//System.out.println(exprs[0]);
		for(Integer key : this.lines.keySet()) {
			for(String du : this.du_pairs.keySet()) {
				
				if (this.lines.get(key).contains(" " + du +" ")) {
					if(this.du_pairs.get(du) == null) {
						this.du_pairs.put(du, key.toString() );
					} else {
						this.du_pairs.put(du, this.du_pairs.get(du).concat(", " + key.toString()));
					}
					//System.out.println(this.lines.get(key).toString() + " " + du);
				}
			}
		}
		
		//System.out.println(this.du_pairs.toString());
 
	}
 
 
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
				+"				   white-space:pre;\n "		
				+ "							}\n	"
				+ "                        #main-body {\n"
				+ "                                width: 70%;\n" + "                        }\n"
				+ "                </style>");
		sb.append("<title>Code Coverage Result");
		sb.append("</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<ul class=\"nav nav-tabs\">\n"
				+ "    <li class=\"active\"><a data-toggle=\"tab\" href=\"#statementCoverage\">Control-Flow Coverage</a></li>\n"
				+ "    <li><a data-toggle=\"tab\" href=\"#dataCoverage\">Data-Flow Coverage</a></li>\n"
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
				if( decl instanceof ClassDeclaration) {
					ClassDeclaration classDecl = (ClassDeclaration) decl;
					String classSignature = classDecl.getClassName();

						sb.append("<p class='covered'>CLASS " + classSignature + " {</p>");
						sb.append("<p class='covered'>ATTRIBUTES</p>");
						for (VariableDeclaration var : classDecl.getClassBody().getAttributes().getAttributes()) {
							sb.append("<div class='covered'><div class='tabbed'>"+ var.toString()+"</div></div>");
						}
						
						sb.append("<p class='covered'>METHODS</p>");
						for (FunctionDeclaration func : classDecl.getClassBody().getMethods().getMethods()) {
							FunctionDeclaration funcDecl = (FunctionDeclaration) func;
							String funcSignature = funcDecl.functionSignature();
		 
							sb.append( "<p class='covered'>" + funcSignature + " { </p>");
							sb.append( statementsToString(funcDecl.getFunctionBody()));
							sb.append("<div class='covered'>} </div>");
						}
						
				}else {
					sb.append("<p class='not-covered'>" + decl.toString() + "</p>");
				}
				
			}
		}
		sb.append("</div>");
		sb.append("<div id=\"dataCoverage\" class=\"tab-pane fade\">");
		sb.append(dataCoverage());
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
							&& if_cast.getElse_statement_block() != null && !if_cast.getElse_statement_block().getIsExecuted()) {
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
					if (if_cast.getElse_statement_block()!= null && if_cast.getElse_statement_block().getIsExecuted()) {
						if (((BlockStatement)if_cast.getElse_statement_block()).getStatements().size() >0 ) {
							returner += "<div class='covered'>";
							returner += "<div class='tabbed'>ELSE { </br>"
									+ statementsToString((BlockStatement) if_cast
											.getElse_statement_block()).toString()
									+ "</br>}</br>" + "</div>";
							returner += "</div>";
						}
						
					} else { 
						if ( if_cast.getElse_statement_block() != null && ((BlockStatement)if_cast.getElse_statement_block()).getStatements().size() >0 ) {
							returner += "<div class='not-covered'>";
							returner += "<div class='tabbed'>ELSE { </br>"
									+ if_cast.getElse_statement_block() + "</br>}</br>"
									+ "</div>";
							returner += "</div>";
						}
						
 
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
			if (statement instanceof ForLoopStatement) {
				ForLoopStatement for_stmt = (ForLoopStatement) statement;
				result += "<div class='part-covered'><div class='tabbed'>FOR "
						+ for_stmt.getBracketBlock() + " { </div></div>";

				result += "<div class='not-covered'><div class='tabbed'>"
						+ statementsToString(for_stmt.getStatementBlock()) + "}</div></div>";
			} else if (statement instanceof WhileLoopStatement) {
				WhileLoopStatement while_stmt = (WhileLoopStatement) statement;
				result += "<div class='part-covered'><div class='tabbed'>WHILE "
						+ while_stmt.getBracketBlock() + " { </div></div>";

				result += "<div class='not-covered'><div class='tabbed'>"
						+ statementsToString(while_stmt.getStatementBlock()) + "}</div></div>";
			} else {
				result += "<div class='not-covered'>";
				result += "<div class='tabbed'>" + statement.toString() + "</div>";
				result += "</div>";
			}
		}
		return result;
	}
 
	public double calcCoveragePercentage(Integer covered, Integer total) {
		return ((double) covered / (double) total) * 100;
	}
 
 
 
 
	public String datasToString(BlockStatement statements) {
		String returner = new String();
		if (statements != null) {
			if (statements.getStatements().size() > 0) { // If there are statements in function block
				for (Statement statement : statements.getStatements()) {
					if (statement instanceof IfElseStatement) {
						IfElseStatement if_cast = (IfElseStatement) statement;
						if (if_cast.getIsExecuted() && if_cast.getElse_statement_block() != null && !if_cast.getElse_statement_block().getIsExecuted()) {
								returner += "<div >";
								returner += "<div class='tabbed'>" + this.counter +" IF (" + if_cast.getExpr_condition()
										+ ") { </br>";
								this.lines.put( counter,"IF (" + if_cast.getExpr_condition()); this.counter++;
	 
								returner += datasToString(
										(BlockStatement) if_cast.getIf_statement_block());
								returner += "</div>";
						} else if (if_cast.getIsExecuted()) {
							returner += "<div ><div class='tabbed'>" +this.counter+" IF ("
									+ if_cast.getExpr_condition() + "){ </div></div>";
							this.lines.put( counter,"IF (" + if_cast.getExpr_condition()); this.counter++;
							returner += datasToString(
									(BlockStatement) if_cast.getIf_statement_block());
						} else {
							returner += "<div >";
							returner += "<div class='tabbed'>"
									+ datasToString((BlockStatement) if_cast
											.getElse_statement_block()).toString()
									+ "</div>";
							returner += "</div>";
						}
						returner += "<div >";
						returner += "<div class='tabbed'>} </div>";
						returner += "</div>";
						if (if_cast.getElse_statement_block() != null && if_cast.getElse_statement_block().getIsExecuted()) {
							
								if (((BlockStatement)if_cast.getElse_statement_block()).getStatements().size() >0 ) {
									returner += "<div>";
									returner += "<div class='tabbed'>"+ this.counter+"ELSE { </br>";
									this.lines.put( counter,"ELSE"); this.counter++;
									returner += datasToString((BlockStatement) if_cast
													.getElse_statement_block()).toString()
											+ "</br>}</br>" + "</div>";
									returner += "</div>";
								}
								
							
						} else {
							if ( if_cast.getElse_statement_block() != null && ((BlockStatement)if_cast.getElse_statement_block()).getStatements().size() >0 ) {
								returner += "<div>";
								returner += "<div class='tabbed'>"+ this.counter+ " ELSE { </br>"
										+ if_cast.getElse_statement_block() + "</br>}</br>"
										+ "</div>";
								this.lines.put( this.counter, "ELSE"); this.counter++;
								returner += "</div>";
							}
						
						}
	 
					} else if (statement instanceof ForLoopStatement) {
						ForLoopStatement stmt_cast = (ForLoopStatement) statement;
						String for_sig = "FOR (" + stmt_cast.getInitBlock().toString() + ";" + stmt_cast.getConditionalBlock().toString() + ";" +stmt_cast.getUpdationAssignmentBlock()  +")";
	 
						returner += "<div class='tabbed'>" +this.counter.toString() + " "+ for_sig;
						this.lines.put( this.counter,for_sig);this.counter++;
	 
						returner += "<div class='tabbed'>" +datasToString(stmt_cast.getStatementBlock()) + "</div>";
					} else if (statement instanceof WhileLoopStatement) {
	 
						WhileLoopStatement stmt_cast = (WhileLoopStatement) statement;
						returner += "<div class='tabbed'>" +this.counter.toString() + " WHILE (" + stmt_cast.getExpressionBlock() +")";
						this.lines.put( this.counter,"WHILE (" + stmt_cast.getExpressionBlock() +")");this.counter++;
						returner += "<div class='tabbed'>" +datasToString(stmt_cast.getStatementBlock())+ "</div>";
					} else {
						returner += dataToString(statement);
					}
				}
			}
		}
		
		return returner;
	}
 
	public String dataToString(Statement statement) {
		String result = new String();
		result += "<div >";
		result += "<div class='tabbed'>" +this.counter+ " " + statement.toString() + "</div>";
		this.lines.put( this.counter, statement.toString()); this.counter++;
		result += "</div>";
		return result;
	}
 
 
}