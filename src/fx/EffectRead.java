package fx;

import java.util.HashSet;
import java.util.Set;

import regions.RegionConst;
import types.Region;
import types.Types;

public class EffectRead extends Effect {

	private Region readFrom;

	public EffectRead(Region region) {
		this.readFrom = region;
	}

	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<>();
		fx.add(this);
		return fx;
	}
	
	@Override
	public String toString() {
		return "(READ " + readFrom + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((readFrom == null) ? 0 : readFrom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EffectRead)) return false;
		EffectRead er = (EffectRead) obj;
		return Types.equivalent(er.readFrom, this.readFrom);
	}
	
	
}
