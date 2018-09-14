package exprs;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.EffectCheckException;
import descriptions.types.Ref;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import descriptions.types.Types;
import runtimes.Runtime;

public class RefSet implements Expr {

	private Expr referenceToSet;
	private Expr exprToSet;
	
	public RefSet(Expr ref, Expr val) {
		this.referenceToSet = ref;
		this.exprToSet = val;
	}
	
	@Override
	public Value reduce(Runtime rtm, DescCtx descCtx) {
		Value ref = referenceToSet.reduce(rtm, descCtx);
		Value expr = exprToSet.reduce(rtm, descCtx);
		if (!(ref instanceof Location)) {
			throw new RuntimeException("Trying to `set` something which isn't a reference.");
		}
		rtm.setRef((Location) ref, expr);
		return Exprs.Nil();
	}

	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws EffectCheckException, TypeCheckException {
		Type referenceToSetType = referenceToSet.typeCheck(ctx, descCtx);
		if (!(referenceToSetType instanceof Ref))
			throw new RuntimeException("Must set something of reference type.");
		Ref refType = (Ref) referenceToSetType;
		Type valType = exprToSet.typeCheck(ctx, descCtx);
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
		RefSet other = (RefSet) obj;
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
