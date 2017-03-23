package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;
import types.Types;

public class IntConst implements Expr {

	private int value;
	
	public IntConst(int i) {
		this.value = i;
	}
	
	@Override
	public Expr reduce(Runtime ctx) {
		return this;
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
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
