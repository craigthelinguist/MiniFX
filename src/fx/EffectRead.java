package fx;

import java.util.HashSet;
import java.util.Set;

import exprs.Expr;

public class EffectRead extends Effect {

	private Expr readFrom;
	
	public EffectRead(Expr region) {
		this.readFrom = region;
	}

	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<>();
		fx.add(this);
		return fx;
	}
	
}
