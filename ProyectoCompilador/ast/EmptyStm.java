package ast;

import java.io.FileWriter;
import java.io.IOException;

import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;

public class EmptyStm extends Node implements CollateralOperation{
	
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl 
				+ " NOTHING\n");
		
		return sb.toString();
	}


	public void bind(Binder b) {
		// Nothing to do
		
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
		//nothing to do
	}
	
}
