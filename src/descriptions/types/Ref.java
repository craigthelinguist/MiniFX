package descriptions.types;

import descriptions.regions.Region;

public class Ref implements Type {
	
	private Type contents;
	private Region region;
	
	public Ref(Type contents, Region region) {
		this.contents = contents;
		this.region = region;
	}

	@Override
	public boolean subtypeOf(Type other) {
		if (!(other instanceof Ref)) return false;
		Ref otherRef = (Ref) other;
		return contents.subtypeOf(otherRef.contents) && region.subregionOf(otherRef.region);
	}

	public Type componentType() {
		return this.contents;
	}
	
	public Region getRegion() {
		return this.region;
	}
	
	@Override
	public String toString() {
		return "Ref[" + contents + "]";
	}
	
}
