package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.EffectCheckException;
import descriptions.fx.Effects;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import descriptions.types.Types;
import exprs.ArithOper;
import exprs.BoolOper;
import exprs.Expr;
import exprs.Lambda;
import exprs.Var;
import parsing.LexException;
import parsing.Lexer;
import parsing.ParseException;
import parsing.Parser;
import runtimes.Runtime;
import utils.Pair;

public class Utils {

	public static Expr Compile(String input) throws ParseException, LexException {
		List<String> tokens = new Lexer(input).lex();
		return new Parser(tokens).parse();
	}
	
	public static String StringFromFile(File file) {
		String input = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line);
			br.close();
			input = sb.toString();
		}
		catch (IOException ioe) {
			fail("Could not read from file " + input);
		}
		return input;
	}
	
	public static Expr Compile(File file) throws ParseException, LexException, IOException {
		return Compile(StringFromFile(file));
	}
	
	public static void ShouldType(Expr ast, Type expected, DescCtx descCtx) {
		try {
			Type actual = ast.typeCheck(VarCtx.Empty(), descCtx);
			assertTrue(Types.equivalent(expected, actual));
		}
		catch (TypeCheckException | EffectCheckException e) {
			System.err.println("Should have type and effect checked, but it didn't.");
			System.err.println(e.toString());
			fail(e.toString());
		}
	}
	
	public static void ShouldntType(String prog, VarCtx varCtx, DescCtx descCtx) {
		try {
			Expr ast = Compile(prog);
			ast.typeCheck(varCtx, descCtx);
		}
		catch (ParseException | LexException e) {
			fail("Should have parsed and lexed, but it didn't.");
		}
		catch (EffectCheckException efce) {
			fail("Should have effect-checked, but it didn't.");
		}
		catch (TypeCheckException tce) {
		}
	}
	
	public static void ShouldntType(String prog) {
		ShouldntType(prog, VarCtx.Empty(), DescCtx.Empty());
	}
	
	public static void ShouldntEffectCheck(String prog, VarCtx varCtx, DescCtx descCtx) {
		try {
			Expr ast = Compile(prog);
			ast.typeCheck(varCtx, descCtx);
		}
		catch (ParseException | LexException e) {
			fail("Should have parsed and lexed, but it didn't.");
		}
		catch (EffectCheckException efce) {
			
		}
		catch (TypeCheckException tce) {
			fail("Should have typed, but it didn't.");
		}
	}
	
	public static void ShouldntEffectCheck(String prog) {
		ShouldntEffectCheck(prog, VarCtx.Empty(), DescCtx.Empty());
	}
	
	public static void ShouldReduceTo(Expr ast, Expr result) {
		ShouldReduceTo(ast, result, new Runtime(), DescCtx.Empty());
	}
	
	public static void ShouldReduceTo(Expr ast, Expr result, Runtime rtm, DescCtx descCtx) {
		Expr astReduced = ast.reduce(rtm, descCtx);
		assertEquals(astReduced, result);
	}
	
	public static void TestProg(String input, Pair<Runtime, DescCtx> runtime, Type expectedType, Expr expectedResult) {
		try {
			Expr ast = Compile(input);
			ShouldType(ast, expectedType, runtime.second());
			ShouldReduceTo(ast, expectedResult, runtime.first(), runtime.second());
		}
		catch (ParseException pe) {
			System.err.println("Error parsing");
			pe.printStackTrace();
			fail();
		}
		catch (LexException le) {
			System.err.println("Error lexing");
			le.printStackTrace();
			fail();
		}
	}
	
	public static void TestProg(String input, Type expectedType, Expr expectedResult) {
		TestProg(input,
				new Pair<Runtime, DescCtx>(new Runtime(), DescCtx.Empty()),
				expectedType,
				expectedResult);
	}
	
	public static void TestProg(File file, Type expectedType, Expr expectedResult) {
		TestProg(StringFromFile(file), expectedType, expectedResult);
	}
	
	
	public static Lambda IdIntFunction() {
		return new Lambda("x", Types.IntType(), new Var("x"));
	}
	
	public static Lambda AddTwoIntsFunction() {
		return new Lambda(
				Arrays.asList(
						new Var("x"),
						new Var("y")),
				Arrays.asList(
						Types.IntType(),
						Types.IntType()),
				Effects.PureEffect(),
				ArithOper.NewAdd(Arrays.asList(
						new Var("x"),
						new Var("y"))));	
	}
	
	public static Lambda NandFunction() {
		return new Lambda(
				Arrays.asList(
						new Var("x"),
						new Var("y")),
				Arrays.asList(
						Types.BoolType(),
						Types.BoolType()),
				Effects.PureEffect(),
				BoolOper.NewNot(Arrays.asList(
						BoolOper.NewAnd(Arrays.asList(
								new Var("x"),
								new Var("y"))))));
	}
	
}
