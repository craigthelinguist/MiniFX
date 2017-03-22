package exprs;

import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
import exprs.Expr;
import types.Type;

public class Application implements Expr {

	private Expr[] exprs;
	
	public Application(List<Expr> exprs) {
		if (exprs.size() < 1)
			throw new RuntimeException("Application needs at least one argument.");
		this.exprs = new Expr[exprs.size()];
		for (int i = 0; i < exprs.size(); i++) {
			this.exprs[i] = exprs.get(i);
		}
	}
	
	@Override
	public Expr reduce(Runtime rtm) {
		Expr receiver = exprs[0].reduce(rtm);
		if (!(receiver instanceof Lambda))
			throw new RuntimeException("First expression in an application should be a function.");
		Lambda lamb = (Lambda) receiver;
		return lamb.apply(rtm, exprs);
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}
	
}