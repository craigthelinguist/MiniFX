package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;

public class Var implements Expr {

	private String name;
	
	public Var(String name) {
		this.name = name;
	}
	
	@Override
	public Expr reduce(Runtime ctx) {
		if (!ctx.hasBinding(name))
			throw new RuntimeException("Variable " + name + " is undefined");
		return ctx.lookupVariable(name);
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		if (ctx.hasBinding(name)) return ctx.lookupType(name);
		else throw new RuntimeException("Typechecking variable that isn't defined.");
	}

	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Var other = (Var) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
