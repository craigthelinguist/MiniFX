package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;
import types.Types;

public class NatConst implements Expr {

	private int value; 
	
	public NatConst(int value) {
		if (value < 0) throw new RuntimeException("NatConst's must be 0 or greater");
		this.value = value;
	}
	
	@Override
	public Expr reduce(Runtime ctx) {
		return this;
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		return Types.NatType();
	}

}
