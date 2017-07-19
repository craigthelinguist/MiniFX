package parsing;

import static org.junit.Assert.*;

import org.junit.Test;

import exprs.Expr;
import exprs.IntConst;
import types.Types;

public class RegionTests {

	@Test
	public void newRegion() {
		try {
			Expr prog = Utils.Compile("NEW-REGION");
			Utils.ShouldType(prog, Types.RegionType());
		}
		catch (LexException le) {
			fail("Failed to lex: " + le.getMessage());
		}
		catch (ParseException pe) {
			fail("Failed to parse: " + pe.getMessage());
		}
	}
	
	@Test
	public void badRegion() {
		Utils.ShouldntType("(REF 3 Int 5");
	}
	
	@Test
	public void regionAsArguments() {
		Utils.TestProg("((LAMBDA ((r Region)) PURE 5) NEW-REGION)", Types.IntType(), new IntConst(5));
	}
	
}
