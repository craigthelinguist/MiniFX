package exprs;

import java.util.List;

import ctxs.Runtime;
import ctxs.TypeContext;
import types.Int;
import types.Type;
import types.Types;

public class ArithOper implements Expr {

	private Expr[] args;
	private OperType operator;
	
	private enum OperType {
		PLUS, MINUS, TIMES, DIVIDE
	}
	
	public static ArithOper NewAdd(List<Expr> args) {
		if (args.isEmpty())
			throw new RuntimeException("need at least one argument for adding.");
		ArithOper bo = new ArithOper(args);
		bo.operator = OperType.PLUS;
		return bo;
	}
	
	public static ArithOper NewDiv(List<Expr> args) {
		if (args.size() < 2)
			throw new RuntimeException("Need at least two arguments for dividing.");
		ArithOper bo = new ArithOper(args);
		bo.operator = OperType.DIVIDE;
		return bo;		
	}
	
	public static ArithOper NewMul(List<Expr> args) {
		if (args.size() < 2)
			throw new RuntimeException("Need at least two arguments for multiplying.");
		ArithOper bo = new ArithOper(args);
		bo.operator = OperType.TIMES;
		return bo;
	}
	
	public static ArithOper NewSub(List<Expr> args) {
		if (args.isEmpty())
			throw new RuntimeException("Need at least one argument for minusing.");
		ArithOper bo = new ArithOper(args);
		bo.operator = OperType.MINUS;
		return bo;
	}
	
	private ArithOper(List<Expr> args2) {
		this.args = new Expr[args2.size()];
		for (int i = 0 ; i < args2.size(); i++) {
			this.args[i] = args2.get(i);
		}
	}
	
	@Override
	public Expr reduce(Runtime rtm) {
		switch (operator) {
		case PLUS:
			int sum = 0;
			for (int i = 0; i < args.length; i++) {
				sum += ((IntConst)args[i]).asInt();
			}
			return new IntConst(sum);
		case TIMES:
			int product = 1;
			for (int i = 0; i < args.length; i++) {
				product *= ((IntConst)args[i]).asInt();
			}
			return new IntConst(product);
		case DIVIDE:
			int divResult = ((IntConst)args[0]).asInt();
			for (int i = 1; i < args.length; i++) {
				divResult /= ((IntConst)args[i]).asInt();
			}
			return new IntConst(divResult);
		case MINUS:
			if (args.length == 1) return new IntConst(-((IntConst)args[0]).asInt());
			int result = ((IntConst)args[0]).asInt();
			for (int i = 1 ; i < args.length; i++) {
				result -= ((IntConst)args[i]).asInt();
			}
			return new IntConst(result);
		default:
			throw new RuntimeException("Unimplemented arithmetic operator: " + this.operator);
		}
	}

	@Override
	public Type typeCheck(TypeContext ctx) {
		for (Expr xpr : args) {
			Type t = xpr.typeCheck(ctx);
			if (!(t instanceof Int))
				throw new RuntimeException("Need to give Ints to arithmetic operators.");
		}
		return Types.IntType();
	}

}
