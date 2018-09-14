package testing;

import org.junit.Test;

import descriptions.types.Types;
import exprs.Exprs;
import exprs.IntConst;

public class IntTests {

	@Test
	public void intConst() {
		Utils.TestProg("3", Types.IntType(), new IntConst(3));
	}
	
	@Test
	public void addTwoInts() {
		Utils.TestProg("(+ 1 2)", Types.IntType(), new IntConst(3));
	}
	
	@Test
	public void addFourInts() {
		Utils.TestProg("(+ 1 2 3 4)", Types.IntType(), new IntConst(10));
	}
	
	@Test
	public void negateInt() {
		Utils.TestProg("(- 2)", Types.IntType(), new IntConst(-2));
	}
	
	@Test
	public void subTwoInts() {
		Utils.TestProg("(- 4 2)", Types.IntType(), new IntConst(2));
	}
	
	@Test
	public void mulTwoInts() {
		Utils.TestProg("(* 3 4)", Types.IntType(), new IntConst(12));
	}
	
	@Test
	public void divTwoInts() {
		Utils.TestProg("(/ 9 3)", Types.IntType(), new IntConst(3));
	}

}
