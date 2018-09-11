package testing;
import java.util.List;

import org.junit.Test;

import parsing.LexException;
import parsing.Lexer;

import static org.junit.Assert.*;

public class LexingTests {

	private List<String> lex(String input) {
		try {
			return new Lexer(input).lex();
		} catch (LexException le) {
			System.err.println("Failed lexing");
			le.printStackTrace();
			fail();
		}
		return null;
	}
	
	private void lexTest(String input, String... expected) {
		List<String> tokens = lex(input);
		for (int i = 0; i < expected.length; i++) {
			if (!expected[i].equals(tokens.get(i)))
				fail();
		}
	}
	
	@Test
	public void lexInt() {
		lexTest("3", "3");
	}
	
	@Test
	public void lexMultiDigitInt() {
		lexTest("33", "33");
	}

	@Test
	public void lexFalse() {
		lexTest("false", "false");
	}
	
	@Test
	public void lexTrue() {
		lexTest("true", "true");
	}
	
	@Test
	public void lexNil() {
		lexTest("nil", "nil");
	}
	
	@Test
	public void lexAdding() {
		lexTest("(+ 1 2)", "(", "+", "1", "2", ")");
	}
	
	@Test
	public void lexAnding() {
		lexTest("(and true false)", "(", "and", "true", "false");
	}
	
	@Test
	public void lexNestedStuff() {
		lexTest("(and true (or false false))", "(", "and", "true", "(", "or", "false", "false", ")", ")");
	}
	
	@Test
	public void lexLambda() {
		lexTest("(LAMBDA (x Int) x)", "(", "LAMBDA", "(", "x", "Int", ")", "x", ")");
	}
	
}
