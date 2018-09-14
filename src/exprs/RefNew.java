package exprs;

import java.util.HashSet;
import java.util.Set;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.Effect;
import descriptions.fx.EffectAlloc;
import descriptions.fx.EffectCheckException;
import descriptions.regions.Region;
import descriptions.regions.RegionConst;
import descriptions.types.Ref;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import descriptions.types.Types;
import runtimes.Runtime;

public class RefNew implements Expr {

	private Region region;
	private Expr initialValue;
	private Type componentType;

	public RefNew(Region region, Type type, Expr initValue) {
		this.region = region;
		this.componentType = type;
		this.initialValue = initValue;
	}

	@Override
	public Value reduce(Runtime rtm, DescCtx descCtx) {
		Value val = initialValue.reduce(rtm, descCtx);
		try {
			Region rg = region.resolve(rtm, descCtx);
			if (!(rg instanceof RegionConst)) {
				throw new RuntimeException("Trying to allocate to region union " + rg);
			}
			return rtm.allocate((RegionConst)rg, componentType, val);
		} catch (TypeCheckException tce) {
			throw new RuntimeException("Trying to allocate to unbound region variable " + region);
		}
	}

	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws EffectCheckException, TypeCheckException {
		Type valueType = initialValue.typeCheck(ctx, descCtx);
		if (!(valueType.subtypeOf(componentType)))
			throw new RuntimeException("Initialising reference with something of inappropriate type.");
		Region region = this.region.regionCheck(descCtx);
		return new Ref(componentType, region);
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
