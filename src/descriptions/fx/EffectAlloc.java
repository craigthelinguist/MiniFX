package descriptions.fx;

import java.util.HashSet;
import java.util.Set;

import descriptions.regions.Region;
import descriptions.regions.Regions;
import descriptions.types.Types;
import exprs.Expr;

public class EffectAlloc extends Effect {

	private Region allocatedTo;
	
	public EffectAlloc(Region region) {
		this.allocatedTo = region;
	}
	
	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<Effect>();
		fx.add(this);
		return fx;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allocatedTo == null) ? 0 : allocatedTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EffectAlloc)) return false;
		EffectAlloc ea = (EffectAlloc) obj;
		return Regions.Equivalent(ea.allocatedTo, this.allocatedTo);
	}
	
	@Override
	public String toString() {
		return "(ALLOC " + allocatedTo + ")";
	}
	
}
