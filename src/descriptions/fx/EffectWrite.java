package descriptions.fx;

import java.util.HashSet;	
import java.util.Set;

import descriptions.regions.Region;
import descriptions.regions.Regions;
import descriptions.types.Types;
import exprs.Expr;

public class EffectWrite extends Effect {

	private Region writtenTo;
	
	public EffectWrite(Region r) {
		this.writtenTo = r;
	}
	
	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<Effect>();
		fx.add(this);
		return fx;
	}
	
	@Override
	public String toString() {
		return "(WRITE " + writtenTo + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((writtenTo == null) ? 0 : writtenTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EffectWrite)) return false;
		EffectWrite ew = (EffectWrite) obj;
		return Regions.Equivalent(ew.writtenTo, this.writtenTo);
	}
	
}
