package exprs;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.types.Type;
import runtimes.Runtime;

public class Var extends Value {

	private String name;
	
	public Var(String name) {
		this.name = name;
	}
	
	@Override
	public Value reduce(Runtime rtm, DescCtx descCtx) {
		if (!rtm.hasBinding(name))
			throw new RuntimeException("Variable " + name + " is undefined");
		return rtm.lookupVariable(name);
	}
	
	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) {
		return ctx
				.lookup(name)
				.orElseThrow(() -> new RuntimeException("Variable " + name + " is undefined."));
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
