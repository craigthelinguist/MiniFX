package fx;

import java.util.HashSet;
import java.util.Set;

import exprs.Expr;
import exprs.RegionConst;

public class EffectAlloc extends Effect {

	private Expr allocatedTo;
	
	public EffectAlloc(Expr region) {
		this.allocatedTo = region;
	}
	
	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<Effect>();
		fx.add(this);
		return fx;
	}
	
}
