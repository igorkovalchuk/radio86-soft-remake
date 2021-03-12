package radio86java.basic;

import java.util.HashMap;
import java.util.Map;
import radio86java.Radio86rk;
import radio86java.InterpreterInterface;
import radio86java.Listing;

/**
 * N = 5
 * T$ = "TEXT"
 * FOR TO STEP NEXT
 * GOTO
 * INKEY$
 * PRINT
 * 
 */
public class Basic implements InterpreterInterface {

	public /* static */ void main(String[] argv) {

		String text = 
			"10 X=5: Y=3\n" +
			"20 PRINT X: PRINT Y\n" +
			"30 PRINT (X+Y)";

		Basic b = new Basic();
		b.run(new Listing(text), null);
	}

	@Override
	public void run(Listing listing, Radio86rk screen) {
		Basic1 b1 = new Basic1();
		BasicStructure1 s1 = b1.parse(listing.getText());
		//s1.print();

		Basic2 b2 = new Basic2();
		BasicStructure2 s2 = b2.parse(s1);
		s2.print();

		for(Expression e : s2.expressions)
			e.parse();

		BasicInterpreter bi = new BasicInterpreter();
		bi.interpret(s2, screen);
	}

	static final Integer TYPE_0 = 0;

	static final Integer TYPE_STRING = 1;
	static final Integer TYPE_NUMBER = 2;
	
	//Integer TYPE_OPEN_Q1 = 3; // ""
	//Integer TYPE_CLOSE_Q1 = 4;
	
	static final Integer TYPE_OPEN_Q2 = 5; // ()
	static final Integer TYPE_CLOSE_Q2 = 6;
	
	static final Integer TYPE_NEXT_OPERATOR = 7; // :
	static final Integer TYPE_NEXT_PRINT_PARAMETR = 8; // ;
	static final Integer TYPE_NEXT_PARAMETR = 9; // ,
	static final Integer TYPE_EQUALS = 10; // =
	static final Integer TYPE_SPACE = 11; // space or tab
	
	static final Integer TYPE_Q_STRING = 12; // "string"
	
	static final Integer TYPE_ARITHMETIC = 13; // + - * /
	
	static final Integer TYPE_NEXT_LINE = 14; // \n
	
	static final Integer TYPE_FUNCTION = 512; // CHR$ SIN COS
	static final Integer TYPE_OPERATOR = 1024; // PRINT GOSUB RETURN FOR NEXT
	
	static final Integer TYPE_VARIABLE = 2048; //
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	static final Integer TYPE_OPERATOR_PRINT = TYPE_OPERATOR | 1;
	
	static final Integer TYPE_OPERATOR_GOSUB = TYPE_OPERATOR | 2;
	static final Integer TYPE_OPERATOR_RETURN = TYPE_OPERATOR | 3;
	
	static final Integer TYPE_OPERATOR_GOTO = TYPE_OPERATOR | 4;
	
	static final Integer TYPE_OPERATOR_REM = TYPE_OPERATOR | 5;
	
	static final Integer TYPE_OPERATOR_FOR = TYPE_OPERATOR | 6;
	static final Integer TYPE_OPERATOR_TO = TYPE_OPERATOR | 7;
	static final Integer TYPE_OPERATOR_STEP = TYPE_OPERATOR | 8;
	static final Integer TYPE_OPERATOR_NEXT = TYPE_OPERATOR | 9;
	
	static final Integer TYPE_OPERATOR_IF = TYPE_OPERATOR | 10;
	static final Integer TYPE_OPERATOR_THEN = TYPE_OPERATOR | 11;
	
	static final Integer TYPE_OPERATOR_INPUT = TYPE_OPERATOR | 12;
	
	static final Integer TYPE_OPERATOR_PLOT = TYPE_OPERATOR | 13;
	static final Integer TYPE_OPERATOR_LINE = TYPE_OPERATOR | 14;
	
	static final Integer TYPE_OPERATOR_CLS = TYPE_OPERATOR | 15;
	//static final Integer TYPE_OPERATOR_AT = TYPE_OPERATOR | 16;
	static final Integer TYPE_OPERATOR_CUR = TYPE_OPERATOR | 17;
	
	static final Integer TYPE_OPERATOR_DATA = TYPE_OPERATOR | 18;
	static final Integer TYPE_OPERATOR_READ = TYPE_OPERATOR | 19;
	static final Integer TYPE_OPERATOR_RESTORE = TYPE_OPERATOR | 20;
	
	static final Integer TYPE_OPERATOR_DEF = TYPE_OPERATOR | 21;
	
	static final Integer TYPE_OPERATOR_STOP = TYPE_OPERATOR | 22;
	
	static final Integer TYPE_OPERATOR_TAB = TYPE_OPERATOR | 23;
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	static final Map<String, Integer> operator2type = new HashMap<String, Integer>();
	static final Map<Integer, String> type2operator = new HashMap<Integer, String>();
	
	static {
		operator2type.put("PRINT", TYPE_OPERATOR_PRINT);
		operator2type.put("CUR", TYPE_OPERATOR_CUR);
		operator2type.put("PLOT", TYPE_OPERATOR_PLOT);
		operator2type.put("CLS", TYPE_OPERATOR_CLS);
		
		for(String operator : operator2type.keySet())
			type2operator.put(operator2type.get(operator), operator);

	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	static final Map<Integer, String> type2name = new HashMap<Integer, String>();
	static {
		type2name.put(TYPE_STRING, "string");
		type2name.put(TYPE_NUMBER, "number");
		type2name.put(TYPE_OPEN_Q2, "(");
		type2name.put(TYPE_CLOSE_Q2, ")");
		type2name.put(TYPE_NEXT_OPERATOR, ":");
		type2name.put(TYPE_NEXT_PRINT_PARAMETR, ";");
		type2name.put(TYPE_NEXT_PARAMETR, ",");
		type2name.put(TYPE_EQUALS, "=");
		type2name.put(TYPE_SPACE, "space");
		type2name.put(TYPE_Q_STRING, "quoted string");
		type2name.put(TYPE_ARITHMETIC, "arithmetic");
		type2name.put(TYPE_NEXT_LINE, "end of line");
	}

}
