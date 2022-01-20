package ast;

import asem.Binder;

public enum BasicType implements Type {
	INT, REAL, BOOL, CHAR ;
	
	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " BASIC TYPE : ");
		
		sb.append(this.toString()+"\n");
		
		return sb.toString();
	}


	public Type getElementType() {  //not an array
		return null;
	}
	
	public Type getPointeeType() { //not a pointer
		return null;
	}

	public Clase getClase() { //not a class
		return null;
	}
	
	public int getDimension() {
		return -1;
	}
	
	public void setDimension(Type t) {
		//Nothing
	}
	
	public boolean isConst() {
		return false;
	}
	
	public void setConst() {
		System.out.println("Error de programación: aquí no deberíamos llegar");
	}
	
	public int size() {
		return 1;
	}
	
	public void bind(Binder b) {
		// Nothing to do
	}

	public Type typeCheckCompleteType() {
		return this;
	}
	
	public Type typeCheckIncompleteType(boolean moreLevels) {
		return this;
	}
	
	public boolean isReturnable() {
		return true;
	}
	
	public boolean isVoid() {
		return false;
	}

	public boolean compatibleWith(Type t) {
		if(t.getClass() == SimpleType.class || t.getClass() == BasicType.class)return true;
		else return false;
	}

	public Type deepCopy(boolean constant){
		return this;
	}

	
}
