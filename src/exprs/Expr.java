package exprs;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.EffectCheckException;	
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import runtimes.Runtime;

public interface Expr {
	
	public Value reduce(Runtime rtm, DescCtx descCtx);
	
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws TypeCheckException, EffectCheckException;
	
	public boolean equals(Object other);
	
	public int hashCode();
	
}
