package testing;

import org.junit.Test;

import descriptions.types.Types;
import exprs.IntConst;
import runtimes.Standard;

public class BlockTests {
	@Test
	public void beginBlock() {
		Utils.TestProg("(LET ((r (NEW std-heap Int 5)))"
			   + "    (BEGIN"
			   + "        (SET r 10)"
			   + "        (SET r 15)"
			   + "        (GET r)))",
			   Standard.StdPrelude(),
			   Types.IntType(),
			   new IntConst(15));
	}
}
