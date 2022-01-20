package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;

public class ContinueStm extends Node implements Statement{
	private final TokenValue value;
	private Loop loop;

	public ContinueStm(TokenValue value) {
		this.value = value;
	}

	public String toTree(int lvl) {
        StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
        		" CONTINUE STATEMENT\n");
		
		return sb.toString();
	}


	public void bind(Binder b) {
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
        writer.write("br "+continueLabel+"\n");
	}
	
}
