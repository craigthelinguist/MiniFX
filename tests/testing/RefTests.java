package testing;

import org.junit.Test;

import ctxs.descriptions.DescCtx;
import descriptions.types.Ref;
import descriptions.types.Types;
import exprs.IntConst;
import exprs.Location;
import runtimes.Runtime;
import runtimes.Standard;
import utils.Pair;

public class RefTests {

	@Test
	public void newRef() {
		Pair<Runtime, DescCtx> rtm = Standard.StdPrelude();
		Utils.TestProg("(NEW std-heap Int 5)",
				rtm,
				new Ref(Types.IntType(), rtm.first().getHeap("std-heap")),
				new Location(rtm.first().getHeap("std-heap"), Types.IntType()));
	}
	
	@Test
	public void readRef() {
		Utils.TestProg("(GET (NEW std-heap Int 5))",
				Standard.StdPrelude(),
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void letWithRef() { 
		Utils.TestProg("(LET ((loc (NEW std-heap Int 5))) (GET loc))",
				Standard.StdPrelude(),
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void setRef() {
		Utils.TestProg("(LET ((loc (NEW std-heap Int 5)))"
			   + "    (LET ((x (SET loc 10)))"
			   + "         (GET loc))) ",
			   Standard.StdPrelude(),
			   Types.IntType(),
			   new IntConst(10));
	}
	
}
