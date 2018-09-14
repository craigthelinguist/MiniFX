package exprs;

import java.util.Arrays;
import java.util.List;

import ctxs.descriptions.DescCtx;
import ctxs.vars.VarCtx;
import descriptions.fx.EffectCheckException;
import descriptions.types.Int;
import descriptions.types.Type;
import descriptions.types.TypeCheckException;
import descriptions.types.Types;
import runtimes.Runtime;

public class ArithOper implements Expr {

	private Expr[] args;
	private OperType operator;

	private enum OperType {
		PLUS, MINUS, TIMES, DIVIDE;

		public String toString() {
			switch (this) {
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case TIMES:
				return "*";
			case DIVIDE:
				return "/";
			default:
				return null;
			}
		}
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
		for (int i = 0; i < args2.size(); i++) {
			this.args[i] = args2.get(i);
		}
	}

	@Override
	public Value reduce(Runtime rtm, DescCtx descCtx) {
		switch (operator) {
		case PLUS:
			int sum = 0;
			for (int i = 0; i < args.length; i++) {
				sum += ((IntConst) args[i].reduce(rtm, descCtx)).asInt();
			}
			return new IntConst(sum);
		case TIMES:
			int product = 1;
			for (int i = 0; i < args.length; i++) {
				product *= ((IntConst) args[i].reduce(rtm, descCtx)).asInt();
			}
			return new IntConst(product);
		case DIVIDE:
			int divResult = ((IntConst) args[0]).asInt();
			for (int i = 1; i < args.length; i++) {
				divResult /= ((IntConst) args[i].reduce(rtm, descCtx)).asInt();
			}
			return new IntConst(divResult);
		case MINUS:
			if (args.length == 1)
				return new IntConst(-((IntConst) args[0]).asInt());
			int result = ((IntConst) args[0]).asInt();
			for (int i = 1; i < args.length; i++) {
				result -= ((IntConst) args[i].reduce(rtm, descCtx)).asInt();
			}
			return new IntConst(result);
		default:
			throw new RuntimeException("Unimplemented arithmetic operator: " + this.operator);
		}
	}

	@Override
	public Type typeCheck(VarCtx ctx, DescCtx descCtx) throws TypeCheckException, EffectCheckException {
		for (Expr xpr : args) {
			Type t = xpr.typeCheck(ctx, descCtx);
			if (!(t instanceof Int))
				throw new TypeCheckException("Need to give Ints to arithmetic operators.");
		}
		return Types.IntType();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArithOper other = (ArithOper) obj;
		if (operator != other.operator)
			return false;
		if (!Arrays.equals(args, other.args))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String[] children = new String[args.length + 1];
		children[0] = operator.toString();
		for (int i = 1; i < children.length; i++) {
			children[i] = args[i - 1].toString();
		}
		return "(" + String.join(" ", children) + ")";
	}

}
