 package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import fx.EffectCheckException;
import fx.EffectRead;
import types.Ref;
import types.Type;
import types.TypeCheckException;

public class RefGet implements Expr {

	private Expr refToGet;
	
	public RefGet(Expr ref) {
		this.refToGet = ref;
	}

	@Override
	public Expr reduce(Runtime rtm) {
		Expr loc = refToGet.reduce(rtm);
		if (!(loc instanceof Location))
			throw new RuntimeException("Can only dereference a location.");
		Location location = (Location) loc;
		return rtm.dereference(location);
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
	public Type typeCheck(TypeContext ctx) throws TypeCheckException, EffectCheckException {
		Type refToGetType = refToGet.typeCheck(ctx);
		if (!(refToGetType instanceof Ref))
			throw new RuntimeException("Can only dereference a reference type.");
		Ref reference = (Ref) refToGetType;
		return reference.componentType();
	}

	@Override
	public String toString() {
		return "(GET " + refToGet + ")";
	}

	@Override
	public Set<Effect> effectCheck(TypeContext ctx) throws TypeCheckException, EffectCheckException {
		Set<Effect> fx = new HashSet<>();
		Ref refType = (Ref) refToGet.typeCheck(ctx);
		fx.add(new EffectRead(refType.getRegion()));
		return fx;
	}
	
}

