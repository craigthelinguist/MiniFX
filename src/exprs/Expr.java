package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import types.Type;

public interface Expr {
	
	public Expr reduce(Runtime rtm);
	
	public Type typeCheck(TypeContext ctx);
	
	public Set<Effect> effectCheck(TypeContext ctx);
	
	public boolean equals(Object other);
	
	public int hashCode();
	
}
