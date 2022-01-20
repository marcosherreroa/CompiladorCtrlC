package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class Conditional extends Node implements Statement{
     private final Expression condition;
     private final Statement ifBlock;
     private final Statement elseBlock; //quizás a null
     private final TokenValue v;
     
	public Conditional(Expression condition, Statement ifBlock, Statement elseBlock, TokenValue v) {
		this.condition = condition;
		this.ifBlock = ifBlock;
		this.elseBlock = elseBlock;
		this.v = v;
	}

	
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl );
		if(elseBlock != null) sb.append(" IFELSE CLAUSE\n");
		else sb.append(" IF CLAUSE\n");
		
		sb.append(condition.toTree(lvl+1));
		sb.append(ifBlock.toTree(lvl+1));
		if (elseBlock != null) sb.append(elseBlock.toTree(lvl+1));
		
		return sb.toString();
	}

	public void bind(Binder b) {
		condition.bind(b);
		
		b.openBlock();
		ifBlock.bind(b);
		b.closeBlock();
		
		if(elseBlock != null) {
			b.openBlock();
			elseBlock.bind(b);
			b.closeBlock();
		}
		
	}
	
	public boolean checkNumberConstructors() {
		boolean OK = ifBlock.checkNumberConstructors();
		if(elseBlock != null) OK = elseBlock.checkNumberConstructors() && OK;
		
		return OK;
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		boolean OK = true;
		
		Type tExp = condition.typeCheckExp(typer);
		
		OK = ifBlock.typeCheckClause(typer) && OK;
		if(elseBlock != null) OK = elseBlock.typeCheckClause(typer) && OK;
		
		if(tExp == null) return false;
		else if(! tExp.compatibleWith(BasicType.BOOL)) {
			ErroresTyping.expectedExpressionType(v, tExp, "condition");
			return false;
		}
		else return OK;
	}
	
	public boolean searchEndReturnStm() {
		boolean OK1 = ifBlock.searchEndReturnStm();
		boolean OK2 = elseBlock == null || elseBlock.searchEndReturnStm();
		
		return OK1 && OK2;
	}
	
	public void sizeAndDeltas(DeltasInfo DI) {
		DeltasInfo DIlocal = new DeltasInfo(DI);
		
		ifBlock.sizeAndDeltas(DIlocal);
		if(DI.getC()+ DIlocal.getMax() > DI.getMax())DI.setMax(DI.getC()+ DIlocal.getMax());
		
		if(elseBlock != null) {
			DIlocal = new DeltasInfo();
			elseBlock.sizeAndDeltas(DIlocal);
			if(DIlocal.getMax() > DI.getMax())DI.setMax(DIlocal.getMax());
		}
	}

	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		condition.codeEvaluate(writer);
		writer.write("if\n");
		ifBlock.codeStm(writer, breakLabel+1, continueLabel+1);
		if(elseBlock != null) {
			writer.write("else\n");
			elseBlock.codeStm(writer,breakLabel+1, continueLabel+1);
		}
		writer.write("end\n");
	}
}
