package ast;

import java.util.List;

import asem.Binder;

public class IndependentBlock extends Block implements Statement {
	public IndependentBlock(List<Statement> stmList) {
		super(stmList);
	}

	public void bind (Binder b) {
	    b.openBlock();
	    
		for(Statement stm : stmList) {
			stm.bind(b);
		}
	    
		b.closeBlock();
	}
}
