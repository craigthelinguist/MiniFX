package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;
import types.Types;

public class Nil implements Expr {

	@Override
	public Expr reduce(Runtime ctx) {
		return this;
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		return Types.UnitType();
	}

	@Override
	public String toString() {
		return "nil";
	}
	
}
