package prism_coverage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        
        public Integer getTotalIfElse() { return total_if_else; }

		public Integer getTotalCoveredIfElse() { return total_covered_if_else; }
        
        
        private Integer getTotalStatementsFromFunction(FunctionDeclaration fn_decl) {
                return fn_decl.getFunctionBody().getStatements().size();
        }

        private Integer getTotalCoveredStatementsFromFunction(FunctionDeclaration fn_decl) {
                Integer returner = 0;
                for (Statement stmt : fn_decl.getFunctionBody().getStatements()) {
                        if (stmt instanceof AssignmentStatement) {
                                if (stmt.getIsExecuted()) {
                                        returner++;
                                }
                        } else if (stmt instanceof IfElseStatement) {
                                IfElseStatement if_else_block = (IfElseStatement) stmt;
                                if (if_else_block.getIf_statement_block().getIsExecuted()) {
                                        returner++;
                                } else {
                                        if ((if_else_block.getElse_statement_block() != null)
                                                        && (if_else_block.getElse_statement_block().getIsExecuted())) {
                                                returner++;
                                        }
                                }
                        } else if (stmt instanceof ForLoopStatement) {
                                // A for-loop may or may nor execute. If the condition is false on the first
                                // iteration, the BlockStatement wouldn't
                                // get executed even once.
                                ForLoopStatement for_loop_stmt = (ForLoopStatement) stmt;
                                if (for_loop_stmt.getStatementBlock().getIsExecuted()) {
                                        returner++;
                                }
                        } else if (stmt instanceof WhileLoopStatement) {
                                WhileLoopStatement while_loop_stmt = (WhileLoopStatement) stmt;
                                if (while_loop_stmt.getStatementBlock().getIsExecuted()) {
                                        returner++;
                                }
                        } else if (stmt instanceof ReturnStatement) {
                                // If a function returns before hitting this particular ReturnStatement, it will
                                // not be executed
                                if (stmt.getIsExecuted()) {
                                        returner++;
                                }
                        } else if (stmt instanceof VariableDeclarationStatement) {
                                if (stmt.getIsExecuted()) {
                                        returner++;
                                }
                        }
                }
                return returner;
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
                                codeCoverageBlockCheck(((FunctionDeclaration)decl).getFunctionBody());
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
                for (Declaration decl : decls) {
                        if (decl instanceof FunctionDeclaration) {
                                total_source_code += getTotalStatementsFromFunction((FunctionDeclaration) decl);
                                if (decl.getIsExecuted()) {
                                        total_covered_source_code += getTotalCoveredStatementsFromFunction(
                                                        (FunctionDeclaration) decl);
                                }
                        }
                }
        }
        
        private void codeCoverageBlockCheck(BlockStatement block) { //For decision coverage
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
        			if (if_cast.getElse_statement_block().getIsExecuted())
        			{
        				total_covered_if_else++;
            			codeCoverageBlockCheck((BlockStatement) if_cast.getElse_statement_block());
        			}
        		}
        	}
        }
        public String generateHTML() throws IOException {
        	StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<style>\n"
            		+ "                        .color-red {\n"
            		+ "                                color: red;\n"
            		+ "                        }\n"
            		+ "                        .covered {\n"
            		+ "                                background-color: #50DBB4;\n"
            		+ "                                margin-bottom: 0px;\n"
            		+ "                                padding: 1px;\n"
            		+ "								   white-space:pre;\n"			
            		+ "                        }\n"
            		+ "                        .not-covered {\n"
            		+ "                                background-color: #FF6263;\n"
            		+ "                                margin-bottom: 0px;\n"
            		+ "                                padding: 1px;\n"
            		+ "								   white-space:pre;\n"	
            		+ "                        }\n"
            		+"						   .tabbed {\n"
            		+ "                                padding-left: 3em;\n"
            		+ "							}\n	"			
            		+ "                        #main-body {\n"
            		+ "                                width: 70%;\n"
            		+ "                        }\n"
            		+ "                </style>");
            sb.append("<title>Code Coverage Result");
            sb.append("</title>");
            sb.append("</head>");
            sb.append("<body>");
            sb.append("</body>");
            sb.append("</html>");
            
            String input_file_name = new String();
        	sb.append("<div id=\"header-text\">\n"
        			+ "<p>Total Statements: <span class=\"color-red\">" + getTotalFunctionDeclarations() +"</span></p>\n"
        			+ "<p>Total Statements Covered:<span class=\"color-red\">" +getTotalCoveredFunctionDeclarations()+ "</span></p>\n"
        			+ "<p>Total Statement Coverage<code>"+input_file_name+"</code>: <span class=\"color-red\">"
        			+ (int)calcCoveragePercentage(this.getTotalCoveredFunctionDeclarations(), this.getTotalFunctionDeclarations())+"%</span></p>\n"
        			
					+ "<p>Total Desicions : <span class=\"color-red\">" + getTotalIfElse() +"</span></p>\n"
					+ "<p>Total Desicions Covered: <span class=\"color-red\">" +getTotalCoveredIfElse()+ "</span></p>\n");
        	if (getTotalIfElse()> 0) {
        		sb.append("<p>Total Desicion Coverage<code>"+input_file_name+"</code>: <span class=\"color-red\">"
        				+ (int)calcCoveragePercentage(this.getTotalCoveredIfElse(), this.getTotalIfElse())+"%</span></p>\n </div>");
        	}else {
        		sb.append("<p>Total Desicion Coverage<code>"+input_file_name+"</code>: <span class=\"color-red\"> 100%</span></p>\n </div>");
        	}
					
					
        
            String result = sb.toString();
            List<Declaration> decls = prism_program.getProgram();
            for (Declaration decl : decls) {
            	if (decl.getIsExecuted()) {
            		if (decl instanceof FunctionDeclaration) {
            			FunctionDeclaration funcDecl = (FunctionDeclaration) decl;
            			String funcSignature = funcDecl.functionSignature();
            			
            			result += "<p class=\"covered\">"+ funcSignature + " { </p>";
            			result += statementsToString(funcDecl.getFunctionBody());
            			result += "<div class=\"covered\">} </div>";
            		}
            	} else {
            		result += "<p class=\"not-covered\">"+ decl.toString() +"</p>";
            	}
            }    	
            return result;
        }
        
        public void writeHTMLOutput(String file_path) throws IOException {
        	String body = generateHTML();
            
            FileWriter fstream ;
            fstream = new FileWriter(file_path);
        	
        	BufferedWriter out = new BufferedWriter(fstream);
        	
        	out.write(body);
        	out.close();
        }

        public String statementsToString(BlockStatement statements) {
        	String returner = new String();
        	
        	if (statements.getStatements().size() > 0) { //If there are statements in function  block
				for (Statement statement :statements.getStatements()) {
					if (statement instanceof IfElseStatement) {
						IfElseStatement if_cast = (IfElseStatement) statement;
	        			if (if_cast.getIsExecuted()) {
	        				returner += "<div class=\"covered\">";
	        				returner += "<div class=\"tabbed\">IF ("+ if_cast.getExpr_condition() +") { </br>" + "</div>";
	        				
	        				returner += statementsToString((BlockStatement)if_cast.getIf_statement_block());
	        				returner += "</div>";
	        			} else {
	        				returner += "<div class=\"not-covered\">";
	        				returner += "<div class=\"tabbed\">" + statement.toString() + "</div>";
	        				returner += "</div>";
	        			}
	        			returner += "<div class=\"covered\">";
	        			returner += "<div class=\"tabbed\">} </div>";
	        			returner += "</div>";
	        			if (if_cast.getElse_statement_block().getIsExecuted()) {
	        				returner += "<div class=\"covered\">";
	        				returner += "<div class=\"tabbed\">ELSE { </br>"+ if_cast.getElse_statement_block() +"</br>}</br>" + "</div>";
	        				returner += "</div>";
	        				//returner += statementsToString((BlockStatement)if_cast.getElse_statement_block());
	        			} else {
	        				returner += "<div class=\"not-covered\">";
	        				returner += "<div class=\"tabbed\">ELSE { </br>"+ if_cast.getElse_statement_block() +"</br>}</br>" + "</div>";
	        				returner += "</div>";

	        			}
	        			
					}else {
						
						if (statement.getIsExecuted()) {
							returner += "<div class=\"covered\">";
	        				returner += "<div class=\"tabbed\">	" + statement.toString() + "</div>";
	        				returner += "</div>";
	        			} else {
	        				returner += "<div class=\"not-covered\">";
	        				returner += "<div class=\"tabbed\">	" + statement.toString() + "</br></div>";
	        				returner += "</div>";
	        			}
					}
				}
        	}
        	return returner;
        }

        
        public double calcCoveragePercentage(Integer covered, Integer total) {
        	return ((double)covered / (double)total) * 100;
        }
        
        
        
}