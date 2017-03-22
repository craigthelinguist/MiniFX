package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;

public interface Expr {
	
	public Expr reduce(Runtime rtm);
	
	public Type typeCheck(TypeContext ctx);
	
}
