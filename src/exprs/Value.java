package exprs;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.EffectCheckException;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import runtimes.Runtime;

public abstract class Value implements Expr {

	@Override
	public Value reduce(Runtime rtm, DescCtx descCtx) {
		return this;
	}
	
	public abstract Type typeCheck(VarCtx ctx, DescCtx descCtx) throws EffectCheckException, TypeCheckException;

}
