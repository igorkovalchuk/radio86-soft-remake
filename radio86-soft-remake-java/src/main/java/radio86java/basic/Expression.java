package radio86java.basic;

import java.util.ArrayList;
import javax.script.ScriptException;
import radio86java.Radio86rk;
import static radio86java.basic.Basic.*;

public class Expression {
	
	int index;
	int[] types;
	String[] tokens;
	boolean last = false;
	

	public Expression(int index, int[] types, String[] tokens) {
		this.index = index;
		this.types = types;
		this.tokens = tokens;
	}

	// last expression in the line; useful for operator REM;
	public void setLast() {
		last = true;
	}
	
	// operator => concrete operator
	public void parse() {
		int type;
		String token;

		for(int i = 0; i < types.length; i++) {
			type = types[i];
			if (type == TYPE_STRING) {
				token = tokens[i].toUpperCase();
				Integer type2 = operator2type.get(token);
				if (type2 != null) {
					if (token.equals("PRINT")) {
						types[i] = TYPE_OPERATOR_PRINT;
						continue;
					} else if (token.equals("CUR")) {
						types[i] = TYPE_OPERATOR_CUR;
						continue;
					} else if (token.equals("PLOT")) {
						types[i] = TYPE_OPERATOR_PLOT;
						continue;
					} else if (token.equals("CLS")) {
						types[i] = TYPE_OPERATOR_CLS;
						continue;
					} else {
						types[i] = TYPE_OPERATOR;
						continue;
					}
				}
				types[i] = TYPE_VARIABLE;
			}
		}

	}

	public void interpret(Radio86rk screen, BasicVariables variables) throws ScriptException {
		
		if(types[0] == TYPE_OPERATOR_PRINT) {
			String[] parameters = readParameters(1, -1, variables);
			String parameter = "";
			if (parameters != null)
				for(String p : parameters)
					parameter += p;
			System.out.println("PRINT " + parameter);
			if (screen != null) {	
				screen.getTerminalModel().println(parameter);
				screen.updateScreen();
			}
		} else if (types[0] == TYPE_OPERATOR_CUR) {
			String[] parameters = readParameters(1, 2, variables);
			System.out.println("CUR " + parameters[0] + " " + parameters[1]);
			if (screen != null) {
				screen.getTerminalModel().point((Double.valueOf(parameters[0]).intValue()), (Double.valueOf(parameters[1])).intValue());
			}
		} else if (types[0] == TYPE_OPERATOR_CLS) {
			System.out.println("CLS");
			if (screen != null) {
				screen.getTerminalModel().cls();
				screen.updateScreen();
			}
		} else if (types[0] == TYPE_OPERATOR_PLOT) {
			String[] parameters = readParameters(1, 3, variables);
			System.out.println("PLOT " + parameters[0] + " " + parameters[1] + " " + parameters[2]);
			if (screen != null) {
				screen.getTerminalModel().plot((Double.valueOf(parameters[0]).intValue()), (Double.valueOf(parameters[1])).intValue(),(Double.valueOf(parameters[2]).intValue()));
				screen.updateScreen();
			}	
		} else if (types[0] == TYPE_VARIABLE) {
			if (types[1] == TYPE_EQUALS) {
				String[] parameters = readParameters(2, -1, variables);
				if (parameters.length != 1)
					throw new RuntimeException("Incorrect number of parameters");
				String name = tokens[0];
				variables.setVariable(name, parameters[0]);
			} else {
				throw new RuntimeException("Unknown expression: " + tokens[0]);
			}
		} else {
			throw new RuntimeException("Unknown expression: " + tokens[0]);
		}
	}

	private ArrayList<String> prms = new ArrayList<String>();

	private String[] readParameters(int index, int number, BasicVariables variables) throws ScriptException {
		prms.clear();
		int read = 0;
		
		StringBuilder string = new StringBuilder();
		int tokensN = 0;
		for(int i = index; i < types.length; i++) {
			
			int type = types[i];
			String token = tokens[i];
			
			if (type == TYPE_NEXT_PARAMETR) {
				String s2 = string.toString();
				if (tokensN > 1)
					s2 = Eval.eval(s2);
				prms.add(s2);
				string.delete(0, string.length());
				tokensN = 0;
				read++;
			} else {
				tokensN++;
				// difference between variable and function;
				if (type == TYPE_VARIABLE) {
					string.append(variables.getVariable(token));
				} else {
					string.append(token);
				}
			}
			
		}
		
		if (tokensN > 0) {
			String s2 = string.toString();
			if (tokensN > 1)
				s2 = Eval.eval(s2);
			prms.add(s2);
			read++;
		}
		
		if ((number > 0) && (read != number)) {
			throw new RuntimeException("Incorrect number of parameters");
		}
		
		String[] result = new String[prms.size()];
		for(int i = 0; i < prms.size(); i++)
			result[i] = prms.get(i);
		
		return result;
	}

	void print() {
		System.out.println("Expression, index=" + index);
		for (int i = 0; i < types.length; i++) {
			System.out.println("  Type=" + types[i] + "	Token=[" + tokens[i] + "]" + "	" + type2name.get(types[i]));
		}
	}

/*
Type=1	Token=[X]	string
Type=10	Token=[=]	=
Type=2	Token=[5]	number

or 

Type=1	Token=[PRINT]	string
Type=1	Token=[X]	string

 */
}
