package fx;

import exprs.RegionConst;

public class EffectRead implements Effect {

	private RegionConst readFrom;
	
	public EffectRead(RegionConst r) {
		this.readFrom = r;
	}
	
}
