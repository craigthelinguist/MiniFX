package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.Effect;
import descriptions.fx.EffectCheckException;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import descriptions.types.Types;
import runtimes.Runtime;

public class BoolConst extends Value {
	
	private boolean value;
	
	public BoolConst(boolean b) {
		this.value = b;
	}
	
	public boolean getValue() {
		return this.value;
	}
	
	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws EffectCheckException, TypeCheckException {
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

}
