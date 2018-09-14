package ctxs.vars;

import java.util.Optional;

import descriptions.types.Type;
import exprs.Var;

public interface VarCtx {
	
	public Optional<Type> lookup(String name);
	
	public default Optional<Type> lookup(Var var) {
		return lookup(var.getName());
	}
	
	public boolean hasBinding(String name);
	
	public default boolean hasBinding(Var var) {
		return hasBinding(var.getName());
	}
	
	public VarCtx extend(String name, Type type);
	
	public default VarCtx extend(Var name, Type type) {
		return extend(name, type);
	}

	public default VarCtx extend(String[] args, Type[] types) {
		VarCtx ctx = this;
		for (int i = 0; i < args.length; i++) {
			ctx = ctx.extend(args[i], types[i]);
		}
		return ctx;
	}
	
	public default VarCtx extend(Var[] args, Type[] types) {
		VarCtx ctx = this;
		for (int i = 0; i < args.length; i++) {
			ctx = ctx.extend(args[i].getName(), types[i]);
		}
		return ctx;
	}
	
	public static VarCtx Empty() {
		return new EmptyVarCtx();
	}
	
}
