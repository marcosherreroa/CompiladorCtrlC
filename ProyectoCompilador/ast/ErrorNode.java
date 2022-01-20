package ast;

import java.io.FileWriter;
import java.io.IOException;

import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;

public class ErrorNode extends Node implements ProgramClause, ClassClause, Statement, Literal {
    public ErrorNode() {};
    
    public String toTree(int lvl) {
    	StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +" ERROR\n");
    	
    	return sb.toString();
    }

	@Override
	public void bind(Binder b){
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
	}

	@Override
	public boolean typeCheckClause(Typer typer){
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
		return false;
	}

	@Override
	public boolean checkReturns() {
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
		return false;
	}

	@Override
	public Function checkMain() {
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
		return null;
	}

	@Override
	public boolean searchEndReturnStm() {
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
		return false;
	}

	@Override
	public Attribute searchAttribute(String idLexema) {
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
		return null;
	}

	@Override
	public Method searchMethod(String idLexema) {
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
		return null;
	}

	@Override
	public boolean isLvalue() {
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
		return false;
	}

	@Override
	public Type typeCheckExp(Typer typer) throws Exception {
		System.out.println("Cuando hay errores no seguimos con las siguientes fases");
		return null;
	}

	@Override
	public Constructor searchConstructor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkNumberConstructors() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

	public void code(FileWriter writer) {
		//nothing to do
	}

	@Override
	public void codeEvaluate(FileWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int codeWriteTo(FileWriter writer, int profundidad, int delta) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int passByValue(FileWriter writer, int delta) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void codeInitialization(FileWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void codeFunctions(FileWriter write) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sizeAndDeltas(DeltasInfo DI) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLiteral() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
