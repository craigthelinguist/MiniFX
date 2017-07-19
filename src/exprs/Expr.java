package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import fx.EffectCheckException;
import types.Type;
import types.TypeCheckException;

public interface Expr {
	
	public Expr reduce(Runtime rtm);
	
	public Type typeCheck(TypeContext ctx) throws TypeCheckException, EffectCheckException;
	
	public Set<Effect> effectCheck(TypeContext ctx) throws EffectCheckException, TypeCheckException;
	
	public boolean equals(Object other);
	
	public int hashCode();
	
}
