package types;

public class Nat implements Type {

	@Override
	public boolean subtypeOf(Type other) {
		return other instanceof Nat || other instanceof Int;
	}
	

}
