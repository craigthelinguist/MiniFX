package types;

public class Region implements Type {

	@Override
	public boolean subtypeOf(Type other) {
		return other instanceof Region;
	}

}
