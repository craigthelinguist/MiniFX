package fx;

import exprs.RegionConst;

public class EffectAlloc implements Effect {

	private RegionConst allocatedTo;
	
	public EffectAlloc(RegionConst r) {
		this.allocatedTo = r;
	}
	
}
