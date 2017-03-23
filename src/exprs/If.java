package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Bool;
import types.Type;
import types.Types;

public class If implements Expr {

	public Expr guard;
	public Expr trueBranch;
	public Expr falseBranch;
	
	public If(Expr condition, Expr branch1, Expr branch2) {
		this.guard = condition;
		this.trueBranch = branch1;
		this.falseBranch = branch2;
	}
	
	@Override
	public Expr reduce(Runtime ctx) {
		Expr guardReduced = guard.reduce(ctx);
		if (!(guardReduced instanceof BoolConst))
			throw new RuntimeException("Guard of a conditional must be a Boolean.");
		return ((BoolConst)guardReduced).getValue() ? trueBranch.reduce(ctx) : falseBranch.reduce(ctx);
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		if (!(guard.typeCheck(ctx) instanceof Bool))
			throw new RuntimeException("Guard of a conditional must be a Boolean.");
		Type trueType = trueBranch.typeCheck(ctx);
		Type falseType = falseBranch.typeCheck(ctx);
		Type lub = Types.leastUpperBound(trueType, falseType);
		if (lub == null)
			throw new RuntimeException("Two branches of conditional must have common supertype.");
		return lub;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((falseBranch == null) ? 0 : falseBranch.hashCode());
		result = prime * result + ((guard == null) ? 0 : guard.hashCode());
		result = prime * result + ((trueBranch == null) ? 0 : trueBranch.hashCode());
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
		If other = (If) obj;
		if (falseBranch == null) {
			if (other.falseBranch != null)
				return false;
		} else if (!falseBranch.equals(other.falseBranch))
			return false;
		if (guard == null) {
			if (other.guard != null)
				return false;
		} else if (!guard.equals(other.guard))
			return false;
		if (trueBranch == null) {
			if (other.trueBranch != null)
				return false;
		} else if (!trueBranch.equals(other.trueBranch))
			return false;
		return true;
	}

}
