package ast;

import asem.Binder;

public class VoidType implements MaybeType {
	private VoidType() {}
	
    public static VoidType VOID = new VoidType();
    
	public boolean isReturnable() {
		return true;
	}
	
	public boolean isVoid() {
		return true;
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl + " VOID\n");
		
		return sb.toString();
	}

	public void bind(Binder b) {
		//nothing to do
	}

	public MaybeType typeCheckCompleteType(){
		return this;
	}
	
	public MaybeType typeCheckIncompleteType(boolean moreLevels){
		return this;
	}

	public boolean compatibleWith(MaybeType t){
		return t.getClass() == VoidType.class;
	}

	
    
}
