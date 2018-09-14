package descriptions.regions;

import java.util.List;

import ctxs.descriptions.DescCtx;
import descriptions.types.TypeCheckException;
import runtimes.Runtime;

public class RegionUnion implements Region {

	private Region[] regions;
	
	public RegionUnion(Region[] regions) {
		this.regions = regions;
	}

	public RegionUnion(List<Region> regions) {
		this.regions = (Region[]) regions.toArray();
	}

	public Region[] getRegions() {
		return regions;
	}

	public void setRegions(Region[] regions) {
		this.regions = regions;
	}

	@Override
	public boolean subregionOf(Region other) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Region resolve(Runtime rtm, DescCtx descCtx) throws TypeCheckException {
		// TODO: should probably flatten
		Region[] regions2 = new Region[regions.length];
		for (int i = 0; i < regions.length; i++) {
			regions2[i] = regions[i].resolve(rtm, descCtx);
		}
		return new RegionUnion(regions2);
	}
	
	public Region regionCheck(DescCtx descCtx) throws TypeCheckException {
		Region[] regions2 = new Region[regions.length];
		for (int i = 0; i < regions.length; i++) {
			regions2[i] = regions[i].regionCheck(descCtx);
		}
		return new RegionUnion(regions2);
	}

}
