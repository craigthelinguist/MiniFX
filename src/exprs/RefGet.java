 package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.Effect;
import descriptions.fx.EffectCheckException;
import descriptions.fx.EffectRead;
import descriptions.types.Ref;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import runtimes.Runtime;

public class RefGet implements Expr {

	private Expr refToGet;
	
	public RefGet(Expr ref) {
		this.refToGet = ref;
	}

	@Override
	public Value reduce(Runtime rtm, DescCtx descCtx) {
		Value ref = refToGet.reduce(rtm, descCtx);
		if (!(ref instanceof Location)) {
			throw new RuntimeException("Trying to call `get` on something which is not a reference.");
		}
		return rtm.deref((Location) ref);	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((refToGet == null) ? 0 : refToGet.hashCode());
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
		RefGet other = (RefGet) obj;
		if (refToGet == null) {
			if (other.refToGet != null)
				return false;
		} else if (!refToGet.equals(other.refToGet))
			return false;
		return true;
	}

	@Override
	public Type typeCheck(VarCtx ctx,DescCtx descCtx) throws TypeCheckException, EffectCheckException {
		Type refToGetType = refToGet.typeCheck(ctx, descCtx);
		if (!(refToGetType instanceof Ref))
			throw new RuntimeException("Can only dereference a reference type.");
		Ref reference = (Ref) refToGetType;
		return reference.componentType();
	}

	@Override
	public String toString() {
		return "(GET " + refToGet + ")";
	}

}

