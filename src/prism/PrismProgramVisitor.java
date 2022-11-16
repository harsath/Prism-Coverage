package prism;

import antlr.PrismBaseVisitor;
import antlr.PrismParser.ProgramDeclContext;

public class PrismProgramVisitor extends PrismBaseVisitor<Declaration> {
        DeclarationVisitor declVisitor = new DeclarationVisitor();

        @Override
        public Declaration visitProgramDecl(ProgramDeclContext ctx) {
                PrismProgram prism = new PrismProgram();
                for (int i = 0; i < ctx.getChildCount() - 1; i++) {
                        Declaration decl = declVisitor.visit(ctx.getChild(i));
                        prism.addDeclaration(decl);
                }
                return prism;
        }
}
