package exprs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import fx.EffectCheckException;
import types.Type;
import types.TypeCheckException;

public class Begin implements Expr {

	private Expr[] exprs;
	
	public Begin(Expr[] exprs) {
		if (exprs.length == 0) throw new RuntimeException("A begin block must have at least one expression");
		this.exprs = exprs;
	}
	
	public Begin(List<Expr> exprs2) {
		this.exprs = new Expr[exprs2.size()];
		for (int i = 0; i < exprs2.size(); i++) {
			this.exprs[i] = exprs2.get(i);
		}
	}

	@Override
	public Expr reduce(Runtime ctx) {
		Expr result = null;
		for (Expr expr : exprs) {
			result = expr.reduce(ctx);
		}
		return result;
	}

	@Override
	public Type typeCheck(TypeContext ctx) throws TypeCheckException, EffectCheckException {
		Type type = null;
		for (Expr expr : exprs) {
			type = expr.typeCheck(ctx);
		}
		return type;
	}

	@Override
	public Set<Effect> effectCheck(TypeContext ctx) throws EffectCheckException, TypeCheckException {
		Set<Effect> subfx = new HashSet<>();
		for (Expr expr : exprs) {
			subfx.addAll(expr.effectCheck(ctx));
		}
		return subfx;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(exprs);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Begin other = (Begin) obj;
		if (!Arrays.equals(exprs, other.exprs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String[] children = new String[exprs.length];
		for (int i = 0; i < children.length; i++) {
			children[i] = exprs.toString();
		}
		return "(BEGIN " + String.join(" ", children) + ")";
	}
	
}
