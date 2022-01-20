package ast;

import java.util.List;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import codeGeneration.DeltasInfo;
import errors.ErroresTyping;
import errors.OtrosErrores;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

public class Function extends Node implements ProgramClause, Definition {
	protected final Identifier identifier;
	protected MaybeType returnType; // es null en constructoras
	protected final List<Parameter> paramList;
	protected final DependentBlock body;
	
	protected int maxStackSize; //en palabras de 4 bytes

	public Function(Identifier identifier, MaybeType returnType, List<Parameter> paramList, DependentBlock body) {
		this.identifier = identifier;
		this.returnType = returnType;
		this.paramList = paramList;
		this.body = body;
		
		this.maxStackSize = -1;
	}
	
	public MaybeType getReturnType() {
		return returnType;
	}
	
	public List<Parameter> getParamList(){
		return paramList;
	}
	
	public Type getType(boolean constant) { //si llegamos aqui es que un identificador es un tipo pero ha sido declarado como funcion
		return null;
	}
	
	public Type getVariableType() { //si llegamos aqui es un identificador es una variable pero ha sido declarado como funcionS
		return null;
	}
	
	public Function getFunction() {
		return this;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " FUNCTION DECLARATION : \n");
		
		sb.append(" ".repeat(2*lvl+2) + (lvl+1) + " RETURN TYPE \n");
		sb.append(returnType.toTree(lvl+2));
		
		sb.append(identifier.toTree(lvl+1));
		for(Parameter p: paramList) {
			sb.append(p.toTree(lvl+1));
		}
		
		sb.append(body.toTree(lvl+1));
		
		return sb.toString();
	}

    public void bind(Binder b) {
    	b.insertId(identifier.getValue(), this);
    	
    	b.openBlock();
    	
    	for(Parameter p : paramList) p.bind(b);
    	returnType.bind(b);
    	
    	b.openFunction(this);
    	
    	body.bind(b);
    	
    	b.closeFunction();
    	
    	b.closeBlock();
    }
    
    public boolean checkNumberConstructors() {
    	return body.checkNumberConstructors();
    }
    
    public boolean typeCheckClause(Typer typer) throws Exception {
    	boolean OK = true;
    	returnType = returnType.typeCheckCompleteType();
    	
    	for(Parameter p : paramList) {
    		OK = p.typeCheckParameter() && OK;
    	}
    	
    	OK = OK && body.typeCheckClause(typer);
    	
    	if(returnType == null) return false;
    	else if(!returnType.isReturnable()) {
    		ErroresTyping.invalidReturnType(identifier.getValue(), returnType);
    		return false;
    	}
    	else return OK;
    }
    
    public boolean checkReturns() {
    	if(returnType != VoidType.VOID && !body.searchEndReturnStm()) {
    		OtrosErrores.missingReturn(getValue());
    		return false;
    	}
    	
    	return true;
    }
    
    public Function checkMain() {
    	if(identifier.getValue().lexema.equals("main")) return this;
    	else return null;
    }
    
    public void sizeAndDeltas(DeltasInfo DI) { 
    	maxStackSize = 0;
    	DeltasInfo DIlocal = new DeltasInfo();
    	
    	for(Parameter p : paramList) {
    		p.sizeAndDeltas(DIlocal);
    	}
    	
    	body.sizeAndDeltas(DIlocal);
    	
    	maxStackSize = DIlocal.getMax();
    	System.out.println("FUNCTION :: Lexema: "+ identifier.getValue().lexema+ " || MaxStackSize : "+ maxStackSize);
    	//No influye al de fuera
    }
    
    public TokenValue getValue() {
		return identifier.getValue();
	}

	
    public void codeInitialization(FileWriter writer) {
    	//Nohing to do
    }
    
	public void codeFunctions(FileWriter writer) throws IOException {
		writer.write("(func $"+identifier.getValue().lexema);
		
		if(returnType.isVoid())writer.write("(type $_sig_void)\n");
		else writer.write("(type $_sig_ri32)\n(result i32)\n");
		
		writer.write("(local $temp i32)\n"
				+ "(local $localsStart i32)\n"
				+ "(local $lastAddr i32)\n"
				+ "(local $prevVal i32)\n"
				+ "(local $resultIncDec i32)\n"
				+ "   i32.const "+(4*(maxStackSize+2))
				+ "  \n   call $reserveStack  \n"
				+ "   set_local $temp\n   get_global $MP\n   get_local $temp\n   i32.store\n   get_global $MP\n   get_global $SP\n"
				+ "   i32.store offset=4\n   get_global $MP\n   i32.const 8\n   i32.add\n   set_local $localsStart\n");
		
		body.codeStm(writer,-1,-1);
		
		if(!returnType.isVoid())writer.write("i32.const 0\n");
		writer.write("call $freeStack\n");
		writer.write(")\n");
	}
	
	public void codeAddressIden(FileWriter writer) {
		System.out.println("No deberiamos llegar aqui\n");
	}

}
