package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class Assignment extends Node implements CollateralOperation {
	private final Lvalue lvalue;
	private final Expression expression;
	private final TokenValue op;

	public Assignment(Lvalue identifier, Expression expression, TokenValue op) {
		this.lvalue = identifier;
		this.expression = expression;
		this.op = op;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " ASSIGNMENT\n");
		
		sb.append(lvalue.toTree(lvl+1));
		sb.append(expression.toTree(lvl+1));
		
		return sb.toString();
	}

	public void bind(Binder b) {
		lvalue.bind(b);
		expression.bind(b);
	}
	
	public boolean checkNumberConstructors() {
		return true;
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		Type t = lvalue.typeCheckExp(typer);
		Type tExp = expression.typeCheckExp(typer);
		
		if(t == null || tExp == null) return false;
		else if(t.isConst() && !(typer.inConstructor() && lvalue.isAttributeFrom(typer.getCurrentClass()))) {
			ErroresTyping.assigningConst(op);
			return false;
		}
		else if(t.getClass() == ArrayType.class) {
			ErroresTyping.assigningArray(op);
			return false;
		}
		else if(! tExp.compatibleWith(t)) {
			ErroresTyping.collisionVarValue(op, t, tExp);
			return false;
		}
		else return true;
	}
	
	public boolean searchEndReturnStm() {
		return false;
	}
	
	public void sizeAndDeltas(DeltasInfo DI) {
		//Nothing to do
	}
	
	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		lvalue.codeAddress(writer);
		expression.codeEvaluate(writer);
		writer.write("i32.store\n");
	}

}
