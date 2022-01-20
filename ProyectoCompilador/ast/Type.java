package ast;

public interface Type extends MaybeType{
	public boolean isConst();
	public void setConst();
	public Type getElementType();
	public Type getPointeeType();
	public Clase getClase();
	public int getDimension();
	public void setDimension(Type t);
	
	public int size();
	
	public Type typeCheckCompleteType();
	public Type typeCheckIncompleteType(boolean moreLevels);
	public boolean compatibleWith(Type t);
	public Type deepCopy(boolean constant);
}
