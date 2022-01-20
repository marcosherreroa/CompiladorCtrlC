package ast;

import asem.Binder;

public class PointerType extends Node implements Type {
     private Type type;
     private boolean constant;

	public PointerType(Type type) {
		this.type = type;
		this.constant = false;
	}
	
	public void setConst() {
		this.constant = true;
	}
	
	public boolean isConst() {
		return constant;
	}
	
	public boolean isReturnable() {
		return true;
	}
	
	public boolean isVoid() {
		return false;
	}
	
	public Type getPointeeType() {
		return type;
	}
	
	public Type getElementType() { //not an array
		return null;
	}
	
	public Clase getClase() {// not a class
		return null;
	}
	
	public int getDimension() {
		return -1;
	}
	
	public void setDimension(Type t) {
		type.setDimension(t.getPointeeType());
	}
	
	public int size() {
		return 1;
	}
     
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
		  " POINTER TYPE\n");
		
		sb.append(type.toTree(lvl+1));
		return sb.toString();
	}
	

	public void bind(Binder b) {
	   type.bind(b);
	}

	public Type typeCheckCompleteType(){
		type = type.typeCheckCompleteType();
		
		if(type != null) return this;
		else return null;
	}
	
	public Type typeCheckIncompleteType(boolean moreLevels){
		type = type.typeCheckIncompleteType(moreLevels);
		
		if(type != null) return this;
		else return null;
	}

	public boolean compatibleWith(Type t){
		if (getClass() != t.getClass()) return false;
		else {
			PointerType to = (PointerType)t;
			return type.compatibleWith(to.getPointeeType());
		}
	}
	
	public Type deepCopy(boolean constant){
		Type t = new PointerType(type.deepCopy(false));
		if(constant || this.constant)t.setConst();
		return t;
	}
	
}
