package exprs;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.types.Type;
import descriptions.types.Types;

public class IntConst extends Value {

	private int value;
	
	public IntConst(int i) {
		this.value = i;
	}
	
	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) {
		return Types.IntType();
	}

	public int asInt() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		IntConst other = (IntConst) obj;
		if (value != other.value)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "" + value;
	}

}
