package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Type;
import types.Types;

public class NatConst implements Expr {

	private int value; 
	
	public NatConst(int value) {
		if (value < 0) throw new RuntimeException("NatConst's must be 0 or greater");
		this.value = value;
	}
	
	@Override
	public Expr reduce(Runtime ctx) {
		return this;
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		return Types.NatType();
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
		NatConst other = (NatConst) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + value;
	}
	
}
