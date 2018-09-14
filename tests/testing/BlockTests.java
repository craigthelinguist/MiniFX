package testing;

import org.junit.Test;

import descriptions.types.Types;
import exprs.IntConst;

public class BlockTests {
	@Test
	public void beginBlock() {
		Utils.TestProg("(LET ((r (REF (REGION 1) Int 5)))"
			   + "    (BEGIN"
			   + "        (SET r 10)"
			   + "        (SET r 15)"
			   + "        (GET r)))",
			   Types.IntType(),
			   new IntConst(15));
	}
}
