package descriptions.fx;

import java.util.ArrayList;

public class Effects {

	private static final Effect PureEffect = new EffectUnion(new ArrayList<>());

	public static Effect PureEffect() { return PureEffect; }
	
	public static boolean equivalent (Effect e1, Effect e2) {
		return e1.toSet().equals(e2.toSet());
	}
	
}
