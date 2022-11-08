package prism;

import antlr.PrismBaseVisitor;
import antlr.PrismParser.ProgContext;

public class PrismProgramVisitor extends PrismBaseVisitor<Declaration> {
        DeclarationVisitor declVisitor = new DeclarationVisitor();

       @Override
       public Declaration visitProg(ProgContext ctx) {
                PrismProgram prism = new PrismProgram();
                for (int i = 0; i < ctx.getChildCount(); i++) {
                        Declaration decl = declVisitor.visit(ctx.getChild(i));
                        prism.addDeclaration(decl);
                }
                return prism;
       } 
}
