package prism_coverage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import prism.*;

public class PrismCodeCoverage {
        private PrismProgram prism_program;
        private Integer total_global_variabels = 0;
        private Integer total_covered_global_variables = 0;
        private Integer total_function_decls = 0;
        private Integer total_covered_function_decls = 0;
        private Integer total_class_decls = 0;
        private Integer total_covered_class_decls = 0;
        private Integer total_if_else = 0;
        private Integer total_covered_if_else = 0;
        private String file_path="";
        
        
        public PrismCodeCoverage(PrismProgram prism_program, String file_path) {
                this.prism_program = prism_program;
                this.file_path = file_path;
                populateTotalAndCoveredDeclarations();
                
				try {
					String res = generateHTMLOutput();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }

        public Integer getTotalFunctionDeclarations() { return total_function_decls; }
        
        public Integer getTotalCoveredFunctionDeclarations() { return total_covered_function_decls; }

        public Integer getTotalGlobalVariableDeclarations() { return total_global_variabels; }

        public Integer getTotalCoveredGlobalVariableDeclarations() { return total_covered_global_variables; }

        public Integer getTotalClassDeclarations() { return total_class_decls; }

        public Integer getTotalCoveredClassDeclarations() { return total_covered_class_decls; }
        
        public Integer getTotalIfElse() { return total_if_else; }

		public Integer getTotalCoveredIfElse() { return total_covered_if_else; }

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
                                FunctionDeclaration func = (FunctionDeclaration) decl;
                                codeCoverageBlockCheck(func.getFunctionBody());
                                if (decl.getIsExecuted()) {
                                        total_covered_function_decls++;
                                }
                        } else if (decl instanceof VariableDeclaration) {
                                total_global_variabels++;
                                if (decl.getIsExecuted()) {
                                        total_covered_global_variables++;
                                }
                        } else {
                                throw new RuntimeException("Unrecognized declaration");
                        }
                }
        }
        
        private void codeCoverageBlockCheck(BlockStatement block) {
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
        

		public double calcCoveragePercentage(Integer covered, Integer total) {
        	return ((double)covered / (double)total) * 100;
        }
        public String generateHTMLOutput() throws IOException {
        	String result = "";
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
            try {
            	FileWriter fstream = new FileWriter("output.html");
            	BufferedWriter out = new BufferedWriter(fstream);
            	List<Declaration> decls = prism_program.getProgram();
            	
            	/* Some ridiculousness to get the file name from the input string */
            	int file_path_len = this.file_path.split("/").length;
            	String input_file_name = this.file_path.split("/")[file_path_len - 1];
            	
            	out.write(sb.toString());
            	
            	out.write("<div id=\"header-text\">\n"
            			+ "<p>Total Statements: <span class=\"color-red\">" + getTotalFunctionDeclarations() +"</span></p>\n"
            			+ "<p>Total Statements Covered: <span class=\"color-red\">" +getTotalCoveredFunctionDeclarations()+ "</span></p>\n"
            			+ "<p>Total Coverage for <code>"+input_file_name+"</code>: <span class=\"color-red\">"
            			+ (int)calcCoveragePercentage(this.getTotalCoveredFunctionDeclarations(), this.getTotalFunctionDeclarations())+"%</span></p>\n"
            			
						+ "<p>Total Desicions : <span class=\"color-red\">" + getTotalIfElse() +"</span></p>\n"
						+ "<p>Total Desicions Covered: <span class=\"color-red\">" +getTotalCoveredIfElse()+ "</span></p>\n"
						+ "<p>Total Desicion Coverage for <code>"+input_file_name+"</code>: <span class=\"color-red\">"+ (int)calcCoveragePercentage(this.getTotalCoveredIfElse(), this.getTotalIfElse())+"%</span></p>\n"
            			+ " </div>"
            			 );
                for (Declaration decl : decls) {
                	if (decl.getIsExecuted()) {
                		if (decl instanceof FunctionDeclaration) {
                			FunctionDeclaration funcDecl = (FunctionDeclaration) decl;
                			String funcSignature = funcDecl.functionSignature();
                			
                			out.write("<p class=\"covered\">"+ funcSignature + " { </p>");
                			out.write(statementsToString(funcDecl.getFunctionBody()));
                			out.write("<div class=\"covered\">} </div>");
                		}
                	} else {
                		out.write("<p class=\"not-covered\">"+ decl.toString() +"</p>");
                	}
                }
                out.close();       	
            }catch (Exception e){
            	e.printStackTrace();
            }
            return result;
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
        
}