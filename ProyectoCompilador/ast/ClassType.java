package ast;

import asem.Binder;

public class ClassType implements Type{
    private Clase clase;
    private boolean constant;

	public ClassType(Clase clase) {
		this.clase = clase;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +
				" CLASSTYPE ");
		
		return sb.toString();
	}
	
	public Clase getClase() {
		return clase;
	}
	
	public Attribute getAttribute(String idLexema) {
		return clase.getAttribute(idLexema);
	}

	public boolean isConst() {
		return constant;
	}
	
	public void setConst() {
		this.constant = true;
	}
	
	public boolean isReturnable() {
		return true;
	}
	
	public boolean isVoid() {
		return false;
	}
	
	public Type getElementType() { // no es un array
		return null;
	}
	
	public Type getPointeeType() {// no es un puntero
		return null;
	}
	
	public int getDimension() {
		return -1;
	}
	
	public void setDimension(Type t) {
		//Nothing
	}
	
	public int size() {
		return clase.getSize();
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

	public boolean compatibleWith(Type t) {
		if(getClass() != t.getClass())return false;
		else {
			ClassType t0 = (ClassType) t;
			return clase == t0.getClase();
		}
	}
	
	public Type deepCopy(boolean constant) {
		Type t =  new ClassType(clase);
		if(constant) t.setConst();
		return t;
	}
    
}
