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
        
        public double calcStatementCoveragePercentage() {
        	return ((double)this.getTotalCoveredFunctionDeclarations() / (double)this.getTotalFunctionDeclarations()) * 100;
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
            		+ "                                margin-bottom: 2px;\n"
            		+ "                                padding: 5px;\n"
            		+ "								   white-space:pre;\n"			
            		+ "                        }\n"
            		+ "                        .not-covered {\n"
            		+ "                                background-color: #FF6263;\n"
            		+ "                                margin-bottom: 2px;\n"
            		+ "                                padding: 5px;\n"
            		+ "								   white-space:pre;\n"	
            		+ "                        }\n"
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
            			+ "<p>Total Coverage for <code>"+input_file_name+"</code>: <span class=\"color-red\">"+ (int)calcStatementCoveragePercentage()+"%</span></p>\n"
            			+ " </div>"
            			 );
                for (Declaration decl : decls) {
                	if (decl.getIsExecuted()) {
                		out.write("<p class=\"covered\">"+ decl.toString() +"</p>");
                	} else {
                		out.write("<p class=\"not-covered\">"+ decl.toString() +"</p>");
                	}
                }
                out.close();       	
            }catch (Exception e){
            	throw new IOException("Cannot write to file output.html");
            }
            return result;
        }

        
}