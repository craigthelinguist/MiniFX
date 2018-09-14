package runtimes;

import ctxs.descriptions.DescCtx;
import utils.Pair;

public class Standard {

	public static Pair<Runtime, DescCtx> StdPrelude() {
		Runtime rtm = new Runtime();
		rtm.addHeap("std-heap");
		DescCtx ctx = DescCtx.Empty().extend("std-heap", rtm.getHeap("std-heap"));
		return new Pair<Runtime, DescCtx>(rtm, ctx);
	}
	
}
