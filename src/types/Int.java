package types;

public class Int implements Type {

	@Override
	public boolean subtypeOf(Type other) {
		return other instanceof Int;
	}

}
