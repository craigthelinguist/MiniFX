package fx;

import java.util.HashSet;
import java.util.Set;

import exprs.Expr;
import exprs.RegionConst;

public class EffectWrite extends Effect {

	private Expr writtenTo;
	
	public EffectWrite(Expr r) {
		this.writtenTo = r;
	}
	
	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<Effect>();
		fx.add(this);
		return fx;
	}
	
}
