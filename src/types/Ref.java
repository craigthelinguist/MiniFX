package types;

public class Ref implements Type {
	
	private Type contents;
	
	public Ref(Type contents) {
		this.contents = contents;
	}

	@Override
	public boolean subtypeOf(Type other) {
		if (!(other instanceof Ref)) return false;
		Ref otherRef = (Ref) other;
		return contents.subtypeOf(otherRef.contents);
	}

	public Type componentType() {
		return this.contents;
	}
	
	@Override
	public String toString() {
		return "Ref[" + contents + "]";
	}
	
}
