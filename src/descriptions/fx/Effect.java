package descriptions.fx;

import java.util.Set;

public abstract class Effect {

	public abstract Set<Effect> toSet();

	public final boolean containedIn(Effect other) {
		return other.toSet().contains(this.toSet());
	}
	
}
