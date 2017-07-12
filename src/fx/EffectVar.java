package fx;

import java.util.HashSet;
import java.util.Set;

public class EffectVar extends Effect {

	private String name;
	
	public EffectVar(String varName) {
		this.name = varName;
	}
	
	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<>();
		fx.add(this);
		return fx;
	}

}
