package types;

public class Unit implements Type {

	@Override
	public boolean subtypeOf(Type other) {
		return other instanceof Unit;
	}
	
	@Override
	public String toString() {
		return "Unit";
	}
	
}
