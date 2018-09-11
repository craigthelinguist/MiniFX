package testing;

import org.junit.Test;

import exprs.IntConst;
import exprs.Location;
import regions.RegionConst;
import types.Ref;
import types.Region;
import types.Types;

public class RefTests {

	@Test
	public void newRef() {
		Utils.TestProg("(REF (REGION 1) Int 5)",
				new Ref(Types.IntType(), new Region(1)),
				new Location(new RegionConst(1), 0));
	}
	
	@Test
	public void readRef() {
		Utils.TestProg("(GET (REF (REGION 1) Int 5))",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void letWithRef() {
		Utils.TestProg("(LET ((loc (REF (REGION 1) Int 5))) (GET loc))",
				Types.IntType(),
				new IntConst(5));
	}
	
	@Test
	public void setRef() {
		Utils.TestProg("(LET ((loc (REF (REGION 1) Int 5)))"
			   + "    (LET ((x (SET loc 10)))"
			   + "         (GET loc))) ",
			   Types.IntType(),
			   new IntConst(10));
	}
	
}
