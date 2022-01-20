package ast;

public class Nothing {
    private Nothing() {};
    
	public static String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl 
				+ " NOTHING\n");
		
		return sb.toString();
	}
}
