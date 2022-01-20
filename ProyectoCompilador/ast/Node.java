package ast;

public abstract class Node {
	public abstract String toTree(int lvl);
	
	public String toString() {
		return toTree(0);
	}
}
