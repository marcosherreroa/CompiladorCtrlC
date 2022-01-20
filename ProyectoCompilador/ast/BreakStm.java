package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;

public class BreakStm extends Node implements Statement{
	private final TokenValue value;
	private Loop loop;

	public BreakStm(TokenValue value) {
		this.value = value;
		this.loop = null;
	}

	public String toTree(int lvl) {
        StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
        		" BREAK STATEMENT\n");
		
		return sb.toString();
	}
	
	public void bind (Binder b) {
		loop = b.getCurrentLoop(value);
	}
	
	public boolean checkNumberConstructors() {
		return true;
	}

	public boolean typeCheckClause(Typer typer) {
	    return true;
	}
	
	public boolean searchEndReturnStm() {
		return false;
	}

	public void sizeAndDeltas(DeltasInfo DI) {
		//Nothing to do
	}
	
	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		writer.write("br "+breakLabel+"\n");
	}
}
