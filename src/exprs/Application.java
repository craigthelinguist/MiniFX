package exprs;

import java.util.Arrays;
import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
import exprs.Expr;
import types.Arrow;
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
		
		// First thing in an application must be a function.
		Type funcType = exprs[0].typeCheck(ctx);
		if (!(funcType instanceof Arrow))
			throw new RuntimeException("First thing in an application must be a function.");
		Lambda function = (Lambda) exprs[0];
		
		// Check you've supplied right number of arguments.
		if (function.numArgs() != exprs.length - 1)
			throw new RuntimeException("Wrong number of arguments supplied.");
		
		// Check actual argument types are compatible with formal argument types.
		Type[] formalTypes = function.getTypes();		
		for (int i = 1; i < exprs.length; i++) {
			Type actualType = exprs[i].typeCheck(ctx);
			if (!actualType.subtypeOf(formalTypes[i]))
				throw new RuntimeException("Actual input argument must be subtype of formal argument.");
		}
		return function.outputType();

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
		Application other = (Application) obj;
		if (!Arrays.equals(exprs, other.exprs))
			return false;
		return true;
	}

}