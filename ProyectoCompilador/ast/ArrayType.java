package ast;

import alex.TokenValue;
import asem.Binder;
import errors.ErroresTyping;

public class ArrayType extends Node implements Type {
    private Type type;
    private int dimension;
    private TokenValue v;
    
	public ArrayType(Type type, int dimension, TokenValue v) {
		this.type = type;
		this.dimension = dimension;
		this.v = v;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void setDimension(Type t) {
		dimension = t.getDimension();
		type.setDimension(t.getElementType());
	}
	
	public Type getElementType() {
		return type;
	}
	
	public Type getPointeeType() { // not a pointer
		return null;
	}
	
	public  Clase getClase() {// not a class
		return null;
	}
	
	public boolean isConst() {
		return false;
	}
	
	public void setConst() {
		type.setConst();
	}
	
	public boolean isReturnable() {
		return false;
	}
	
	public boolean isVoid() {
		return false;
	}
    
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +" ARRAY \n");
		
		if(dimension != -1)sb.append(" ".repeat(2*lvl+2) + (lvl+1)+ " DIMENSION :"+dimension+"\n");
		sb.append(type.toTree(lvl+1));
		return sb.toString();
	}
	
	public int size() {
		if(dimension == -1) return type.size();
		else return dimension*type.size();
	}

	public void bind(Binder b) {
	   type.bind(b);
	}
	
	public Type typeCheckCompleteType(){
		type = type.typeCheckCompleteType();
		
		if(type == null) return null;
		else if(dimension == -1) {
			ErroresTyping.requiresSize(v);
			return null;
		}
		else if(dimension == 0) {
			ErroresTyping.positiveDimension(v);
			return null;
		}
		else return this;
	}

	
	public Type typeCheckIncompleteType(boolean moreLevels){
		if(moreLevels) type = type.typeCheckIncompleteType(true);
		else type = type.typeCheckCompleteType();
		
		if(type == null) return null;
		else if(dimension == 0) {
			ErroresTyping.positiveDimension(v);
			return null;
		}
		else return this;
	}

	
	
	public boolean compatibleWith(Type t){
		if (getClass() != t.getClass()) return false;
		else {
			ArrayType t0 = (ArrayType)t;
			if(dimension == -1 || t0.getDimension() == -1)return type.compatibleWith(t0.getElementType());
			else return (dimension == t0.getDimension() && type.compatibleWith(t0.getElementType()));
		}

	}


	public Type deepCopy(boolean constant){
		Type t = new ArrayType(type.deepCopy(false),dimension, v);
		if(constant)t.setConst();
		return t;
	}
	
	
}
