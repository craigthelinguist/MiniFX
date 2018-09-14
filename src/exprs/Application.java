package exprs;

import java.util.Arrays;
import java.util.List;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.EffectCheckException;
import descriptions.types.Subr;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import descriptions.types.Unit;
import exprs.Expr;
import runtimes.Runtime;

public class Application implements Expr {

	private Expr firstArg;
	private Expr[] rest;
	
	public Application(List<Expr> exprs) {
		if (exprs.size() < 1)
			throw new RuntimeException("Application needs at least one argument.");
		this.firstArg = exprs.get(0);
		this.rest = new Expr[exprs.size() - 1];
		for (int i = 1; i < exprs.size(); i++) {
			this.rest[i-1] = exprs.get(i);
		}
	}
	
	@Override
	public Value reduce(Runtime rtm, DescCtx descCtx) {
		Expr receiver = firstArg.reduce(rtm, descCtx);
		if (!(receiver instanceof Lambda))
			throw new RuntimeException("First expression in an application should be a function.");
		Lambda lamb = (Lambda) receiver;
		return lamb.apply(rtm, descCtx, rest);
	}

	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws EffectCheckException, TypeCheckException {
		
		// First thing in an application must be a function.
		Type type = firstArg.typeCheck(ctx, descCtx);
		if (!(type instanceof Subr))
			throw new TypeCheckException("First thing in an application must be a function.");
		Subr funcType = (Subr) type;
		
		// Check you've supplied right number of arguments to null-ary function.
		if (funcType.numArgs() == 0 && rest.length != 1) {
			throw new TypeCheckException("Wrong number of arguments supplied.");
		}
		
		if (funcType.numArgs() > 0 && funcType.numArgs() != rest.length) {
			throw new TypeCheckException("Wrong number of arguments supplied.");
		}
		// This is a null-ary function; make sure you're passing nil.
		if (funcType.numArgs() == 0) {
			Type input = rest[0].typeCheck(ctx, descCtx);
			if (!(input instanceof Unit))
				throw new TypeCheckException("Must pass nil to a null-ary function.");
			return funcType.outputType();
		}
		
		// Check actual argument types are compatible with formal argument types.
		Type[] formalTypes = funcType.inputTypes();		
		for (int i = 1; i < rest.length; i++) {
			Type actualType = rest[i-1].typeCheck(ctx, descCtx);
			if (!actualType.subtypeOf(formalTypes[i-1]))
				throw new TypeCheckException("Actual input argument must be subtype of formal argument.");
		}
		return funcType.outputType();
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(rest);
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
		if (!Arrays.equals(rest, other.rest))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String[] children = new String[rest.length];
		for (int i = 0; i < children.length; i++) {
			children[i] = rest[i].toString();
		}
		return "(" + firstArg + " " + String.join(" ", children) + ")";
	}
	
}