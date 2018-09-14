package descriptions.fx;

import java.util.HashSet;
import java.util.Set;

import descriptions.DVar;
import descriptions.Kind;
import descriptions.types.TypeVar;

public class EffectVar extends Effect implements DVar {

	private String name;
	
	public EffectVar(String varName) {
		this.name = varName;
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public Set<Effect> toSet() {
		Set<Effect> fx = new HashSet<>();
		fx.add(this);
		return fx;
	}

	@Override
	public Kind kind() {
		return Kind.EFFECT;
	}

}
