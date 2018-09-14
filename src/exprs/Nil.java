package exprs;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.EffectCheckException;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import descriptions.types.Types;

public class Nil extends Value {

	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws EffectCheckException, TypeCheckException {
		return Types.UnitType();
	}

	@Override
	public String toString() {
		return "nil";
	}

}
