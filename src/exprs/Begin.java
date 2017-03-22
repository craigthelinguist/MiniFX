package exprs;

import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;

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
	public Type typeCheck(TypeContext ctx) {
		Type type = null;
		for (Expr expr : exprs) {
			type = expr.typeCheck(ctx);
		}
		return type;
	}

}
