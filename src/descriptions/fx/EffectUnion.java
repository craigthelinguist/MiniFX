package descriptions.fx;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EffectUnion extends Effect {

	private Effect[] effects;
	
	public EffectUnion(List<Effect> fx) {
		effects = new Effect[fx.size()];
		for (int i = 0; i < effects.length; i++) {
			effects[i] = fx.get(i);
		}
	}
	
	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<>();
		for (Effect effect : effects) {
			fx.addAll(effect.toSet());
		}
		return fx;
	}
	
	
	
}
