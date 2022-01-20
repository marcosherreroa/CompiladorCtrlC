package ast;

import asem.Binder;

public class SimpleType implements Type {
    private BasicType type;
    private boolean constant;
    
	public SimpleType(BasicType type) {
		this.type = type;
		this.constant = false;
	}
	
	public Type getElementType() { // se esta intentando acceder a un tipo basico con []
		return null;
	}
	
	public Type getPointeeType() {// se esta intentando dereferenciar un tipo basico
		return null;
	}
	
	public Clase getClase() {//not a class
		return null;
	}
	
	public int getDimension() {
		return -1;
	}
	
	public void setDimension(Type t) {
		//Nothing
	}
	
	public boolean isConst() {
		return constant;
	}
    
	public void setConst() {
		constant = true;
	}
	
	public boolean isVoid() {
		return false;
	}
	
	public int size() {
		return 1;
	}

	public String toTree(int lvl) {
		StringBuilder sb;
		if(constant) sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " SIMPLE TYPE : constant "+type.toString()+"\n");
		else sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " SIMPLE TYPE : "+type.toString()+"\n");
		
		return sb.toString();
	}
	
	public void bind(Binder b) {
		// Nothing to do
	}

	public Type typeCheckCompleteType() {
		return this;
	}
	
	public Type typeCheckIncompleteType(boolean recursive) {
		return this;
	}

	public boolean isReturnable() {
		return true;
	}
	
	public boolean compatibleWith(Type t) {
		if(t.getClass() == SimpleType.class || t.getClass() == BasicType.class)return true;
		else return false;
	}

	
	public Type deepCopy(boolean constant){
		Type t = new SimpleType(type);
		if(constant)t.setConst();
		return t;
	}
    
}
