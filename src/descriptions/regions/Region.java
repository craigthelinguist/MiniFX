package descriptions.regions;

import ctxs.descriptions.DescCtx;
import descriptions.types.TypeCheckException;
import runtimes.Runtime;

public interface Region {

	public boolean subregionOf(Region other);
	
	public Region resolve(Runtime rtm, DescCtx descCtx) throws TypeCheckException;

	public Region regionCheck(DescCtx descCtx) throws TypeCheckException;
	
}
