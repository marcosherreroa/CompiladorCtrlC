package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class WhileLoop extends Node implements Loop{
    private final Expression condition;
    private final Statement body;
    private final TokenValue v;
    
	public WhileLoop(Expression condition, Statement body, TokenValue v) {
		this.condition = condition;
		this.body = body;
		this.v = v;
	}
    
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl );
		sb.append(" WHILE LOOP\n");
		
		sb.append(condition.toTree(lvl+1));
		sb.append(body.toTree(lvl+1));
		
		return sb.toString();
	}

	
	public void bind(Binder b) {
		b.openBlock();
		
		condition.bind(b);
		
		b.openLoop(this);
		
		body.bind(b);
		
		b.closeLoop();
		
		b.closeBlock();
	}
	
	public boolean checkNumberConstructors() {
		return body.checkNumberConstructors();
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		Type tExp = condition.typeCheckExp(typer);
		
		boolean OK = body.typeCheckClause(typer);
		
		if(tExp == null)return false;
		else if(! tExp.compatibleWith(BasicType.BOOL)) {
			ErroresTyping.expectedExpressionType(v, tExp, "condition");
			return false;
		}
		else return OK;
	}
	
	public boolean searchEndReturnStm() {
		return false;
	}
	
	public void sizeAndDeltas(DeltasInfo DI) {
		DeltasInfo DIlocal = new DeltasInfo(DI);
		
		body.sizeAndDeltas(DIlocal);
		
		if(DIlocal.getMax() > DI.getMax())DI.setMax(DIlocal.getMax());
	}

	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		writer.write("block\n");
		writer.write("loop\n");
		
		condition.codeEvaluate(writer);
		writer.write("i32.eqz\n");
		writer.write("br_if 1\n");
		
		body.codeStm(writer,1,0);
		
		writer.write("br 0\n");
		writer.write("end\n");
		writer.write("end\n");
	}
}
