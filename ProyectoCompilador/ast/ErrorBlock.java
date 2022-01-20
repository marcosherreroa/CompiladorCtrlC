package ast;

import java.util.List;

import alex.TokenValue;
import asem.Binder;

public class ErrorBlock extends DependentBlock {
	public ErrorBlock() {
		super(null);
	}

	public String toTree(int lvl) {
		StringBuilder sb = new StringBuilder(" ".repeat(2*lvl) + lvl 
				+ " ERROR EXPRESSION\n");
		
		return sb.toString();
	}

	@Override
	public void bind(Binder b) {
		// Nothing to do
		
	}

	public TokenValue getValue() {
		return null;
	}
}
