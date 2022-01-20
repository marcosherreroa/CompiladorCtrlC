package ast;

import java.util.List;
import java.util.ListIterator;

import asem.Typer;
import codeGeneration.DeltasInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

public abstract class Block extends Node{
	protected List<Statement> stmList;

	public Block(List<Statement> stmList) {
		this.stmList = stmList;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " BLOCK\n");
		
		for(Statement s: stmList) {
			sb.append(s.toTree(lvl+1));
		}
		
		return sb.toString();
	}
	
	public boolean checkNumberConstructors() {
		boolean OK = true;
		
		for(Statement stm: stmList) {
			OK = stm.checkNumberConstructors() && OK;
		}
		
		return OK;
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		boolean OK = true;
		
		for(Statement stm : stmList) {
			OK = stm.typeCheckClause(typer) && OK;
		}
		
		return OK;
	}
	
	public boolean searchEndReturnStm() {
		boolean found = false;
		ListIterator<Statement> it = stmList.listIterator(stmList.size());
		
		while(it.hasPrevious()) {
			found = it.previous().searchEndReturnStm();
			if(found)break;
		}
		
		return found;
	}
	
	public void sizeAndDeltas(DeltasInfo DI) {
		DeltasInfo DIlocal = new DeltasInfo(DI);
		
		for(Statement stm : stmList) {
			stm.sizeAndDeltas(DIlocal);
		}
		
		if(DIlocal.getMax() > DI.getMax()) DI.setMax(DIlocal.getMax());
	}

	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		for(Statement stm : stmList) {
			stm.codeStm(writer, breakLabel, continueLabel);
		}
	}
}
