package exprs;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Ref;
import types.Type;
import types.Types;

public class Set implements Expr {

	private Expr referenceToSet;
	private Expr exprToSet;
	
	public Set(Expr ref, Expr val) {
		this.referenceToSet = ref;
		this.exprToSet = val;
	}
	
	@Override
	public Expr reduce(Runtime rtm) {
		Expr location = referenceToSet.reduce(rtm);
		if (!(location instanceof Location))
			throw new RuntimeException("Can only set a location.");
		Location loc = (Location) location;
		Expr newValue = exprToSet.reduce(rtm);
		rtm.memWrite(loc, newValue);
		return Exprs.Nil();
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		Type referenceToSetType = referenceToSet.typeCheck(ctx);
		if (!(referenceToSetType instanceof Ref))
			throw new RuntimeException("Must set something of reference type.");
		Ref refType = (Ref) referenceToSetType;
		Type valType = exprToSet.typeCheck(ctx);
		if (!valType.subtypeOf(refType.componentType()))
			throw new RuntimeException("Must set a reference with something of its component type.");
		return Types.UnitType();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exprToSet == null) ? 0 : exprToSet.hashCode());
		result = prime * result + ((referenceToSet == null) ? 0 : referenceToSet.hashCode());
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
		Set other = (Set) obj;
		if (exprToSet == null) {
			if (other.exprToSet != null)
				return false;
		} else if (!exprToSet.equals(other.exprToSet))
			return false;
		if (referenceToSet == null) {
			if (other.referenceToSet != null)
				return false;
		} else if (!referenceToSet.equals(other.referenceToSet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(SET " + referenceToSet + " " + exprToSet + ")";
	}
	
}
