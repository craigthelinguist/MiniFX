package testing;


import org.junit.Test;

import exprs.Exprs;
import exprs.IntConst;
import types.Types;

public class BooleanTests {

	@Test
	public void boolConstTrue() {
		Utils.TestProg("true", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void boolConstFalse() {
		Utils.TestProg("false", Types.BoolType(), Exprs.False());
	}

	@Test
	public void notTrue() {
		Utils.TestProg("(not true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void notFalse() {
		Utils.TestProg("(not false)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void andTrueAndTrue() {
		Utils.TestProg("(and true true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void andFalseAndTrue() {
		Utils.TestProg("(and false true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void orFalseAndTrue() {
		Utils.TestProg("(or false true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void orFalseAndFalse() {
		Utils.TestProg("(or false false)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void xorFalseAndTrue() {
		Utils.TestProg("(xor false true)", Types.BoolType(), Exprs.True());
	}
	
	@Test
	public void xorTrueAndTrue() {
		Utils.TestProg("(xor true true)", Types.BoolType(), Exprs.False());
	}
	
	@Test
	public void conditional() {
		Utils.TestProg("(IF true 1 0)", Types.IntType(), new IntConst(1));
	}
	
	@Test
	public void conditional2() {
		Utils.TestProg("(IF false 1 0)",  Types.IntType(), new IntConst(0));
	}
	
}
