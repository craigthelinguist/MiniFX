package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import fx.EffectAlloc;
import fx.EffectCheckException;
import regions.RegionConst;
import types.Ref;
import types.Region;
import types.Type;
import types.TypeCheckException;
import types.Types;

public class RefNew implements Expr {

	private RegionConst region;
	private Expr initialValue;
	private Type componentType;
	
	public RefNew(RegionConst region, Type type, Expr initValue) {
		this.region = region;
		this.componentType = type;
		this.initialValue = initValue;
	}

	@Override
	public Expr reduce(Runtime rtm) {
		Expr init = initialValue.reduce(rtm);
		return rtm.allocate(region, init);
	}

	@Override
	public Type typeCheck(TypeContext ctx) throws EffectCheckException, TypeCheckException {
		Type valueType = initialValue.typeCheck(ctx);
		if (!(valueType.subtypeOf(componentType)))
			throw new RuntimeException("Initialising reference with something of inappropriate type.");
		return new Ref(componentType, region.typeOf());
	}

	@Override
	public Set<Effect> effectCheck(TypeContext ctx) throws EffectCheckException, TypeCheckException {
		Set<Effect> fx = new HashSet<>();
		fx.add(new EffectAlloc(region.typeOf()));
		return fx;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((componentType == null) ? 0 : componentType.hashCode());
		result = prime * result + ((initialValue == null) ? 0 : initialValue.hashCode());
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
		RefNew other = (RefNew) obj;
		if (componentType == null) {
			if (other.componentType != null)
				return false;
		} else if (!componentType.equals(other.componentType))
			return false;
		if (initialValue == null) {
			if (other.initialValue != null)
				return false;
		} else if (!initialValue.equals(other.initialValue))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(NEW " + this.componentType + " " + this.initialValue + ")";
	}
	
}
