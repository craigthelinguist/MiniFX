package descriptions.regions;

import ctxs.descriptions.DescCtx;
import runtimes.Runtime;

public class RegionConst implements Region {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private final String name;
	private final int id;
	
	public RegionConst(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	@Override
	public boolean subregionOf(Region other) {
		// TODO: assumes region constants are disjoint
		return this.equals(other);
	}

	@Override
	public Region resolve(Runtime rt, DescCtx descCtx) {
		return this;
	}
	
	@Override
	public Region regionCheck(DescCtx descCtx) {
		return this;
	}
	
}
