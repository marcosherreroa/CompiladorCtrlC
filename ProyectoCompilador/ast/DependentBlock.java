package ast;

import java.util.List;

import asem.Binder;

public class DependentBlock extends Block {
	public DependentBlock(List<Statement> stmList) {
		super(stmList);
	}

	public void bind (Binder b) {
	
		for(Statement stm : stmList) {
			stm.bind(b);
		}
	
	}
	
}
