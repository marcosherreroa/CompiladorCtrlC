package ast;

import java.io.FileWriter;
import java.io.IOException;

import alex.TokenValue;
import asem.Binder;
import asem.Typer;
import errors.ErroresTyping;

public class ArrayAccess extends Lvalue {
   private final Lvalue lv;
   private final Expression pos;
   private final TokenValue v;
   private int arrayOrPointer;//1: acceso a array, 2: acceso a un puntero a array 
   private Type referencedType;
   
	public ArrayAccess(Lvalue lv, Expression pos, TokenValue v) {
		this.lv = lv;
		this.pos = pos;
		this.v = v;
		this.arrayOrPointer = 0;
	}
	
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " ARRAY ACCESS\n");
		
		sb.append(lv.toTree(lvl+1));
		sb.append(pos.toTree(lvl+1));
		
		return sb.toString();
	}
	
	public boolean isLiteral() {
		return false;
	}
	
	public int size() {
		return referencedType.size();
	}
	
	public boolean isAttributeFrom(Clase c) {
		return false;
	}

	public void bind(Binder b) {
		lv.bind(b);
		pos.bind(b);
	}

	public Type typeCheckExp(Typer typer) throws Exception {
		// Ademas de un acceso a array , esto podria ser un acceso a un puntero a array !!
		Type t = lv.typeCheckExp(typer);
	    Type t1 = pos.typeCheckExp(typer);
	    
	    if(t == null || t1 == null)return null;
	    else {
	    	Type elementType = t.getElementType();
	    	Type pointeeType = t.getPointeeType();
	    	
	    	if(!t1.compatibleWith(BasicType.INT)) {
		    	ErroresTyping.expectedExpressionType(v, t1, "");
		    	return null;
	        }
	    	
	        if(elementType != null) {
	        	arrayOrPointer = 1;
	        	referencedType = elementType;
	        	return elementType;
	        }
	        else if(pointeeType != null) {
	        	Type tsub = pointeeType.getElementType();
	        	if(tsub == null) {
	        		ErroresTyping.expectedArrayOrPointer(v);
		        	return null;
	        	}
	        	else {
	        		arrayOrPointer = 2;
	        		referencedType = tsub;
	        		return tsub;
	        	}
	        }
	        else {
	        	ErroresTyping.expectedArrayOrPointer(v);
	        	return null;
	        }
	    }
	}
   
	public Function typeCheckFun(Typer typer) { //no es una funcion
		return null;
	}

	public void codeAddress(FileWriter writer) throws IOException {
		if(arrayOrPointer == 1)lv.codeAddress(writer);
		else lv.codeEvaluate(writer);
		
		writer.write("i32.const "+(4*referencedType.size())+"\n");
		pos.codeEvaluate(writer);
		writer.write("i32.mul\n");
		writer.write("i32.add\n");
	}
	
}
