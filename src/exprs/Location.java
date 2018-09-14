package exprs;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.EffectCheckException;
import descriptions.regions.Region;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;

public class Location extends Value {

	private Region region;
	private Type type;
	
	public Location(Region region, Type t) {
		this.region = region;
		this.type = t;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Location other = (Location) obj;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws EffectCheckException, TypeCheckException {
		throw new RuntimeException("Location is a runtime form and does not typecheck.");
	}

	@Override
	public String toString() {
		return "(LOCATION " + region + type + ")";
	}
	
}
