package types;

public class Bool implements Type {

	@Override
	public boolean subtypeOf(Type other) {
		return other instanceof Bool;
	}

}
