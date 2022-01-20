package ast;

public class ErrorParameter extends Parameter {

	public ErrorParameter() {
		super(null, false, null);
	}

	 public String toTree(int lvl) {
	    	StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl +" ERROR\n");
	    	
	    	return sb.toString();
	   }
}
