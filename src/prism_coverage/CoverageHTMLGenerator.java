package prism_coverage;

import prism.*;
import prism_interpreter.*;

public class CoverageHTMLGenerator {
        private PrismProgram prism_program;
        
        public CoverageHTMLGenerator(PrismProgram prism_program) {
                this.prism_program = prism_program;
        }

        public Integer getTotalStatements() {
                Integer returner = 0;
                for (Declaration decl : prism_program.getProgram()) {
                        
                }
                return returner;
        }

        public Integer getTotalCoveredStatements() {
                return 0;
        }

}
