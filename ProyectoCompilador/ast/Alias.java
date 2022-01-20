package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;

public class Alias extends Node implements ProgramClause, ClassClause, Statement, Definition {
   private final Identifier identifier;
   private Type type;
   
   public Alias(Identifier identifier, Type type) {
		this.identifier = identifier;
		this.type = type;
   }
   
   public TokenValue getValue() {
		return identifier.getValue();
	}
   
   public String toTree(int lvl) {
	   StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
				" ALIAS\n");
		sb.append(type.toTree(lvl+1));
		sb.append(identifier.toTree(lvl+1));
		
		return sb.toString();
   }
   
   public void sizeAndDeltas(DeltasInfo DI) {
	   //Nothing to do
   }

	public void bind(Binder b) {
		type.bind(b);
		b.insertId(identifier.getValue(), this);
	}
	
	public boolean checkNumberConstructors() {
		return true;
	}
	
	public boolean typeCheckClause(Typer typer) throws Exception {
		type = type.typeCheckCompleteType(); 

		if(type == null) return false;
		else return true;
	}
	
	public boolean checkReturns() {
		return true;
	}
	
	public boolean searchEndReturnStm() {
		return false;
	}
	
	public Function checkMain() {
		return null;
	}
	
	public Type getVariableType() { // si llegamos aqui es que un identificador deberia ser una variable pero ha sido declarado como tipo
		return null;
	}
	
	public Function getFunction() { // no es una funcion
		return null;
	}
	
	public Type getType(boolean constant){
		return type.deepCopy(constant);
	}
	
	public Attribute searchAttribute(String idLexema) { // not an attribute
		return null;
	}
	
	public Method searchMethod(String idLexema) { // not a method 
		return null;
	}
	
	public Constructor searchConstructor() {
		return null;
	}

	
	public void codeInitialization(FileWriter writer) {
		//Nothing to do
	}
	
	public void codeFunctions(FileWriter writer) {
		//Nothing to do
	}
	
	public void code(FileWriter writer) {
		//Nothing to do
	}
	
	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) {
		//Nothing to do
	}

	public void codeAddressIden(FileWriter writer) throws IOException {
		System.out.println("No deberiamos llegar aqui");
	}
	
	

	
	 

   
}
