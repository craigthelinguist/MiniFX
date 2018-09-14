package descriptions.types;

public class Int implements Type {

	@Override
	public boolean subtypeOf(Type other) {
		return other instanceof Int;
	}

	@Override
	public String toString() {
		return "Int";
	}
	
}
