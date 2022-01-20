package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class ForLoop extends Node implements Loop {
     private final Declaration ini;
     private final Expression condition;
     private final CollateralOperation inc;
     private final Statement body;
     private final TokenValue v;
     
	public ForLoop(Declaration ini, Expression condition, CollateralOperation inc, Statement body, TokenValue v) {
		this.ini = ini;
		this.condition = condition;
		this.inc = inc;
		this.body = body;
		this.v = v;
	}
     
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl );
		sb.append(" FOR LOOP\n");
	
		if (ini == null) sb.append(Nothing.toTree(lvl+1));
		else sb.append(ini.toTree(lvl+1));
		
		if(condition == null) sb.append(Nothing.toTree(lvl+1));
		else sb.append(condition.toTree(lvl+1));
		
		sb.append(inc.toTree(lvl+1));
	    sb.append(body.toTree(lvl+1));
		
		return sb.toString();
	}

	public void bind(Binder b) {
		b.openBlock();
		
		if(ini != null) ini.bind(b);
		if(condition != null) condition.bind(b);
		inc.bind(b);
		
		b.openLoop(this);
		
		body.bind(b);
		
		b.closeLoop();
		
		
		b.closeBlock();
	} 
	
	public boolean checkNumberConstructors() {
		return body.checkNumberConstructors();
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		boolean OK = true;
		if(ini != null) OK = ini.typeCheckClause(typer);
		
		if(condition == null) {
			OK = inc.typeCheckClause(typer) && OK;
			OK = body.typeCheckClause(typer) && OK;
			
			return OK;
		}
		
		else {
			Type tExp = condition.typeCheckExp(typer);
			
			OK = inc.typeCheckClause(typer) && OK;
			OK = body.typeCheckClause(typer) && OK;
			
			if(tExp == null)return false;
			else if(! tExp.compatibleWith(BasicType.BOOL)) {
				ErroresTyping.expectedExpressionType(v, tExp, "condition");
				return false;
			}
			else return OK;
		}
	}

	public boolean searchEndReturnStm() {
		return false;
	}
	
	public void sizeAndDeltas(DeltasInfo DI) {
		DeltasInfo DIlocal = new DeltasInfo(DI);
		
		if(ini != null)ini.sizeAndDeltas(DIlocal);
		body.sizeAndDeltas(DIlocal);
		
		if(DIlocal.getMax() > DI.getMax())DI.setMax(DIlocal.getMax());
	}

	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		if(ini != null)ini.code(writer);
		
		writer.write("block\n");
		writer.write("loop\n");
		
		if(condition!= null)condition.codeEvaluate(writer);
		else writer.write("i32.const 1\n");
		
		writer.write("i32.eqz\n");
		writer.write("br_if 1\n");
		
		writer.write("block\n");//so we can br 0 if we have a continue;
		body.codeStm(writer,2,0);
		writer.write("end\n");//with this layout we have to br 2 if we have a break;
		
		inc.codeStm(writer,-1,-1); //no pueden aparecer break o continue en inc
		writer.write("br 0\n");
		
		writer.write("end\n");
		writer.write("end\n");
	}
}
