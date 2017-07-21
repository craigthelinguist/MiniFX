package types;

import java.util.HashSet;
import java.util.Set;

import regions.RegionConst;

public class Region implements Type {

	private Set<Integer> parts;
	
	public Region(int id) {
		this.parts = new HashSet<>();
		this.parts.add(id);
	}
	
	public Region(RegionConst rc) {
		this.parts = new HashSet<>();
		this.parts.add(rc.getID());
	}
	
	@Override
	public boolean subtypeOf(Type other) {
		if (!(other instanceof Region))
			return false;
		return ((Region)other).parts.containsAll(parts);
	}
	
	@Override
	public String toString() {
		
		if (parts.size() == 1)
			return "(REGION " + parts.iterator().next() + ")";
		
		StringBuilder sb = new StringBuilder();
		sb.append("(REGION");
		for (Integer i: parts) {
			sb.append(" ");
			sb.append(i);
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parts == null) ? 0 : parts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Region)) return false;
		Region r = (Region) o;
		return r.parts.equals(this.parts);
	}

	
}
