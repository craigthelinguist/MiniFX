package exprs;

import java.util.Set;

import ctxs.Runtime;
import ctxs.TypeContext;
import fx.Effect;
import types.Ref;
import types.Type;
import types.Types;

public class NewRef implements Expr {

	private Expr region, initialValue;
	private Type componentType;
	
	public NewRef(Expr region, Type type, Expr initValue) {
		this.region = region;
		this.componentType = type;
		this.initialValue = initValue;
	}

	@Override
	public Expr reduce(Runtime rtm) {
		RegionConst region = (RegionConst) this.region.reduce(rtm);
		Expr init = initialValue.reduce(rtm);
		return rtm.allocate(region, init);
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		Type regionType = region.typeCheck(ctx);
		if (!Types.equivalent(regionType, Types.RegionType()))
			throw new RuntimeException("Allocating in something which isn't a region.");
		Type valueType = initialValue.typeCheck(ctx);
		if (!(valueType.subtypeOf(componentType)))
			throw new RuntimeException("Initialising reference with something of inappropriate type.");
		return new Ref(componentType);
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
		NewRef other = (NewRef) obj;
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

	@Override
	public Set<Effect> effectCheck(TypeContext ctx) {
		// TODO
		return null;
	}
	
}
