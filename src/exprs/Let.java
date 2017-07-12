package exprs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import types.Type;

public class Let implements Expr {

	private String[] varNames;
	private Expr[] toBinds;
	private Expr body;
	
	public Let(String[] varNames, Expr[] toBinds, Expr body) {
		this.varNames = varNames;
		this.toBinds = toBinds;
		this.body = body;
	}
	
	public Let(List<Var> namesToBind, List<Expr> exprs, Expr body) {
		if (exprs.size() != namesToBind.size()) 
			throw new RuntimeException("Must have a binding for every expression.");
		this.varNames = new String[namesToBind.size()];
		this.toBinds = new Expr[exprs.size()];
		for (int i = 0; i < exprs.size(); i++) {
			this.varNames[i] = namesToBind.get(i).getName();
			this.toBinds[i] = exprs.get(i);
		}
		this.body = body;
	}

	@Override
	public Expr reduce(Runtime rtm) {
		Expr[] bindingsReduced = new Expr[toBinds.length];
		for (int i = 0; i < toBinds.length; i++) {
			bindingsReduced[i] = toBinds[i].reduce(rtm);
			rtm = rtm.extend(varNames[i], bindingsReduced[i]);
		}
		return body.reduce(rtm);
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		Type[] types = new Type[toBinds.length];
		for (int i = 0; i < toBinds.length; i++) {
			types[i] = toBinds[i].typeCheck(ctx);
			ctx = ctx.extend(varNames[i], types[i]);
		}
		return body.typeCheck(ctx);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + Arrays.hashCode(toBinds);
		result = prime * result + Arrays.hashCode(varNames);
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
		Let other = (Let) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (!Arrays.equals(toBinds, other.toBinds))
			return false;
		if (!Arrays.equals(varNames, other.varNames))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String[] bindings = new String[varNames.length];
		for (int i = 0; i < bindings.length; i++) {
			bindings[i] = "(" + varNames + " " + toBinds + ")";
		}
		return "(LET " + String.join(" ", bindings) + " " + body + ")";
	}
	
	@Override
	public Set<Effect> effectCheck(TypeContext ctx) {
		Set<Effect> fx = new HashSet<>();
		for (Expr expr : toBinds) {
			fx.addAll(expr.effectCheck(ctx));
		}
		Type[] types = new Type[toBinds.length];
		for (int i = 0; i < toBinds.length; i++) {
			types[i] = toBinds[i].typeCheck(ctx);
		}
		return body.effectCheck(ctx.extend(varNames, types));
	}
	
}
