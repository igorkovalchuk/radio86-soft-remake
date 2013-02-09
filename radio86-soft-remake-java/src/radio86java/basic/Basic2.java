package radio86java.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static radio86java.basic.Basic.*;

/**
            List

line number 0 expression1
            1 expression2
            2 expression3

line number 3 expression1
            4 expression2
            5 expression3

Map
line number => index in the list of expressions

10 => 0
20 => 3

expression => types[]
           => tokens[]

 */
public class Basic2 {

	public BasicStructure2 parse(BasicStructure1 structure1) {
		
		ArrayList<Integer> types = structure1.getTypes();
		ArrayList<String> tokens = structure1.getTokens();
		
		ArrayList<Integer> types1 = new ArrayList<Integer>();
		ArrayList<String> tokens1 = new ArrayList<String>();
		
		int type;
		String token;
		boolean startline = true;

		List<Expression> exprs = new ArrayList<Expression>();
		int ei = 0;

		Map<Integer, Integer> linenumbers = new HashMap<Integer, Integer>();

		for(int i = 0; i < types.size(); i++) {

			type = types.get(i);
			token = tokens.get(i);

			if (startline && (type == TYPE_NUMBER)) {
				
				linenumbers.put(Integer.valueOf(token), ei);
				
			} else if (type == TYPE_NEXT_OPERATOR || type == TYPE_NEXT_LINE) {

				if (types1.size() > 0) {
					Expression e = new Expression(ei, get1(types1), get2(tokens1));
					exprs.add(e);
					tokens1.clear();
					types1.clear();
					ei++;
				}

				if (type == TYPE_NEXT_LINE) {
					if (exprs.size() > 0) {
						Expression e = exprs.get(exprs.size() - 1);
						e.setLast();
					}
					startline = true;
				} else {
					startline = false;
				}

			} else {
				
				types1.add(type);
				tokens1.add(token);
				startline = false;
				
			}
		}

		if (types1.size() > 0) {
			Expression e = new Expression(ei, get1(types1), get2(tokens1));
			exprs.add(e);
			tokens1.clear();
			types1.clear();
			//ei++;
		}

		BasicStructure2 structure2 = new BasicStructure2(exprs, linenumbers);
		return structure2;
	}

	private int[] get1(List<Integer> list) {
		int[] result = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

	private String[] get2(List<String> list) {
		String[] result = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}

/*

input:

Type=2	Token=[10]	number
Type=1	Token=[X]	string
Type=10	Token=[=]	=
Type=2	Token=[5]	number
Type=7	Token=[:]	:
Type=1	Token=[Y]	string
Type=10	Token=[=]	=
Type=2	Token=[3]	number
Type=14	Token=[
]	end of line
Type=2	Token=[20]	number
Type=1	Token=[PRINT]	string
Type=1	Token=[X]	string
Type=7	Token=[:]	:
Type=1	Token=[PRINT]	string
Type=1	Token=[Y]	string
Type=14	Token=[
]	end of line
Type=2	Token=[30]	number
Type=1	Token=[PRINT]	string
Type=5	Token=[(]	(
Type=1	Token=[X]	string
Type=13	Token=[+]	operator
Type=1	Token=[Y]	string
Type=6	Token=[)]	)
 */

}
