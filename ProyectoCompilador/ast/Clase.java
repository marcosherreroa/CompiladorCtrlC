package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.OtrosErrores;

public class Clase extends Node implements ProgramClause, ClassClause, Statement, Definition {
    private final Identifier identifier;
    private final List<ClassClause> clauseList;
    
    private int size;
    
	public Clase(Identifier identifier, List<ClassClause> clauseList) {
		this.identifier = identifier;
		this.clauseList = clauseList;
	}
	
	public int getSize() {
		return size;
	}
	
	public TokenValue getValue() {
		return identifier.getValue();
	}
    
	public Type getVariableType() { // si llegamos aqui es que un identificador es de variable pero ha sido declarado como de tipo
		return null;
	}
	
	public Function getFunction() { // llamada a constructora
		Function constructor = null;
		
		for(ClassClause cc : clauseList) {
			constructor = cc.searchConstructor();
			if(constructor != null) return constructor;
		}
		
		return constructor;
	}
    
    public Type getType(boolean constant) {
		Type t = new ClassType(this);
		if(constant) t.setConst();
		return t;
	}
    
    public Attribute getAttribute(String idLexema) {
    	Attribute a = null;
    	
    	for(ClassClause c: clauseList) {
    		a = c.searchAttribute(idLexema);
    		if(a != null) return a;
    	}
    	
    	return a;
    }
    
    public Method getMethod(String idLexema) {
    	Method m = null;
    	
    	for(ClassClause c: clauseList) {
    		m = c.searchMethod(idLexema);
    		if(m != null) return m;
    	}
    	
    	return m;
    }
    
    public Attribute searchAttribute(String idLexema) {
    	return null;
    }
    
    public Method searchMethod(String idLexema) {
    	return null;
    }
    
    public Constructor searchConstructor() {
    	return null;
    }
    
    public String toTree(int lvl) {
    	StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " CLASS\n");
    	
    	sb.append(identifier.toTree(lvl+1));
        for(ProgramClause pc: clauseList) {
        	sb.append(pc.toTree(lvl+1));
        }
        
        return sb.toString();
    }
    
	public void sizeAndDeltas(DeltasInfo DI) {
		//Parece que no afectaria a lo de fuera
		DeltasInfo DIlocal = new DeltasInfo();
		
		for(ClassClause clause: clauseList) {
			clause.sizeAndDeltas(DIlocal);
		}
		
		size = DIlocal.getMax();
		
		System.out.println("CLASS :: Lexema: "+ identifier.getValue().lexema+ " || Size : "+ size);
	}
    
    public void bind(Binder b) {
    	b.insertId(identifier.getValue(), this);
    	
    	b.openBlock();
    	
    	for(ClassClause c : clauseList) {
    		c.bind(b);
    	}
    	
    	b.closeBlock();
    }
    
    public boolean checkNumberConstructors() {
    	boolean OK = true;
    	int numConstructors = 0;
    	
    	for(ClassClause cc : clauseList) {
    		if(cc.searchConstructor() != null)++numConstructors;
    		
    		OK = cc.checkNumberConstructors() && OK;
    	}
    	
    	if(numConstructors > 1) {
    		OtrosErrores.tooManyConstructors(identifier.getValue());
    		return false;
    	}
    	else if (numConstructors == 0) { // si no tiene constructora se añade una vacía
    		List<Parameter> pL = new ArrayList<Parameter>();
    		DependentBlock dB = new DependentBlock(new ArrayList<Statement>());
    		clauseList.add(0,new Constructor(identifier, pL,dB, "public"));
    		return OK;
    	}
    	else return OK;
    }
   
	public boolean typeCheckClause(Typer typer) throws Exception {
		boolean OK = true;
		
		typer.openClase(this);
		
    	for(ClassClause c : clauseList) {
    		OK = c.typeCheckClause(typer) && OK;
    	}
    	
    	typer.closeClase();
    	
    	return OK;
	}
	
	public boolean checkReturns() {
		boolean OK = true;
		
		for(ClassClause cc: clauseList) {
			OK = cc.checkReturns() && OK;
		}
		
		return OK;
	}
	
	public boolean searchEndReturnStm() {
		return false;
	}
	
	public Function checkMain() {
		return null;
	}
	
	public void codeInitialization(FileWriter writer) {
		//Nothing to do
	}

	public void codeFunctions(FileWriter writer) {
		//Nothing to do (no vamos a generar código para métodos de clases)
	}
	
	public void code(FileWriter writer) {
		//Nothing to do (no vamos a generar código para métodos de clases)
	}
	
	public void codeStm(FileWriter writer, int breakLabel, int continueLabel) {
		//Nothing to do (no vamos a generar código para métodos de clases)
	}


	public void codeAddressIden(FileWriter writer) throws IOException {
		System.out.println("No deberiamos llegar aqui");
	}
	
}
