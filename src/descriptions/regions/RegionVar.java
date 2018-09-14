package descriptions.regions;

import ctxs.descriptions.DescCtx;
import descriptions.DVar;
import descriptions.Kind;
import descriptions.types.TypeCheckException;
import descriptions.types.TypeVar;
import runtimes.Runtime;

public class RegionVar implements DVar, Region {

	private String name;
	
	public RegionVar(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public Kind kind() {
		return Kind.REGION;
	}

	@Override
	public boolean subregionOf(Region other) {
		// TODO
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Region resolve(Runtime rtm, DescCtx descCtx) throws TypeCheckException {
		return descCtx
				.lookupRegion(name)
				.orElseThrow(() -> new RuntimeException("Region variable " + name + " is undefined."));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		RegionVar other = (RegionVar) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public Region regionCheck(DescCtx descCtx) throws TypeCheckException {
		return descCtx
				.lookupRegion(name)
				.orElseThrow(() -> new TypeCheckException("Region variable " + name + " is undefined."));
	}

}
