package exprs;

import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
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
		String[] varNames = new String[namesToBind.size()];
		Expr[] toBinds = new Expr[exprs.size()];
		for (int i = 0; i < exprs.size(); i++) {
			varNames[i] = namesToBind.get(i).getName();
			toBinds[i] = exprs.get(i);
		}
		this.body = body;
	}

	@Override
	public Expr reduce(Runtime rtm) {
		Expr[] bindingsReduced = new Expr[toBinds.length];
		for (int i = 0; i < toBinds.length; i++) {
			bindingsReduced[i] = toBinds[i].reduce(rtm);
		}
		Runtime rtm2 = rtm.extend(varNames, bindingsReduced);
		return body.reduce(rtm2);
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		Type[] types = new Type[toBinds.length];
		for (int i = 0; i < toBinds.length; i++) {
			types[i] = toBinds[i].typeCheck(ctx);
		}
		return body.typeCheck(ctx.extend(varNames, types));
	}

	
	
}
