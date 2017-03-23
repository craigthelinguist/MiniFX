package types;

public class Bool implements Type {

	@Override
	public boolean subtypeOf(Type other) {
		return other instanceof Bool;
	}

	@Override
	public String toString() {
		return "Bool";
	}
	
}
