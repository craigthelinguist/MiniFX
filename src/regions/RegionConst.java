package regions;

import types.Region;

public class RegionConst {

	private int id;
	
	public RegionConst(int id) {
		this.id = id;
	}
	
	public int getID() {
		return this.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegionConst other = (RegionConst) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public Region typeOf() {
		return new Region(this);
	}
	
}
