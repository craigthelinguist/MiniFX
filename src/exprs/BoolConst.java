package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import types.Type;
import types.Types;

public class BoolConst implements Expr {
	
	private boolean value;
	
	public BoolConst(boolean b) {
		this.value = b;
	}
	
	public boolean getValue() {
		return this.value;
	}
	
	@Override
	public Expr reduce(Runtime ctx) {
		return this;
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		return Types.BoolType();
	}

	public boolean asBool() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (value ? 1231 : 1237);
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
		BoolConst other = (BoolConst) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return value ? "true" : "false";
	}

	@Override
	public Set<Effect> effectCheck(TypeContext ctx) {
		return new HashSet<>();
	}
	
}
