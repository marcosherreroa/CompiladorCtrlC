package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;

public class PrintStm extends Node implements Statement {
	private final Expression expression;
	private final TokenValue v;

	public PrintStm(Expression expression, TokenValue v) {
		this.expression = expression;
		this.v = v;
	}

	public String toTree(int lvl) {
        StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " PRINT STATEMENT\n");
		
		sb.append(expression.toTree(lvl+1));
		
		return sb.toString();
	}

	public void bind(Binder b) {
		expression.bind(b);
	}
	
	public boolean checkNumberConstructors() {
		return true;
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		Type t = expression.typeCheckExp(typer);
		
		if(t == null)return false;
		else return true;
	}
	
	public boolean searchEndReturnStm() {
		return false;
	}
	
	public void sizeAndDeltas(DeltasInfo DI) {
		//Nothing to do
	}

	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		expression.codeEvaluate(writer); // solo generamos codigo para imprimir ints
		writer.write("call $print\n");
	}

}
