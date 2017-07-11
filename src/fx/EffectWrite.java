package fx;

import exprs.Expr;
import exprs.RegionConst;

public class EffectWrite implements Effect {

	private Expr writtenTo;
	
	public EffectWrite(Expr r) {
		this.writtenTo = r;
	}
	
}
