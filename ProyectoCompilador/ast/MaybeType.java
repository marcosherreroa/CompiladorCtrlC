package ast;

import asem.Binder;

public interface MaybeType {
	public String toString();
	public String toTree(int lvl);
	public void bind(Binder b);
	public MaybeType typeCheckCompleteType();
	public MaybeType typeCheckIncompleteType(boolean moreLevels);
	public boolean isReturnable();
	public boolean isVoid();
}
