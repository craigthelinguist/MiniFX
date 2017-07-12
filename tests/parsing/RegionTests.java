package parsing;

import static org.junit.Assert.*;

import org.junit.Test;

import exprs.Expr;
import types.Types;

public class RegionTests {

	@Test
	public void newRegion() {
		try {
			Expr prog = Utils.Compile("(NEW-REGION)");
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
		try {
			Expr prog = Utils.Compile("(REF 3 Int 5)");
			Utils.ShouldntType(prog);
		}
		catch (LexException le) {
			fail("Should have lexed.");
		}
		catch (ParseException pe) {
			fail("Should have parsed.");
		}
	}
	
}
