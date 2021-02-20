package radio86java.basic;

import static radio86java.basic.Basic.*;

/**
 * result = structure of tokens;
 */
public class Basic1 {

	private BasicStructure1 structure = new BasicStructure1();

	private Integer type = TYPE_0;	
	private String token = "";

	private int index = 0;

	public BasicStructure1 parse(String text) {
		String[] lines = text.split("\n");
		int last = lines.length - 1;
		for(int i = 0; i <= last; i++) {
			String line = lines[i];
			parseLine(line);
			if (i < last)
				single(TYPE_NEXT_LINE, "\n");
		}
		return structure;
	}

	private void parseLine(String line) {

		for(int i = 0; i < line.length(); i++) {
			
			this.index = i;
			
			char c = line.charAt(i);
			String s = String.valueOf(c);
			
			if ((type == TYPE_Q_STRING) && (c != '\"')) {
				token += s;
				continue;
			} else if (s.matches("[A-Za-z]")) {
			
				if (type == TYPE_0) {
					type = TYPE_STRING;
					token = s;
					continue;
				} else if (type == TYPE_STRING || type == TYPE_Q_STRING) {
					token += s;
					continue;
				}
				error(line);
				
			} else if (c == ' ' || c == '	') {
				
				single(TYPE_SPACE, s);
				
			} else if (s.matches("[0-9]")) {
			
				if (type == TYPE_STRING || type == TYPE_Q_STRING) {
					token += s;
					continue;
				} else if (type == TYPE_NUMBER) {
					token += s;
					continue;
				} else if (type == TYPE_0) {
					type = TYPE_NUMBER;
					token = s;
					continue;
				}
				error(line);
				
			} else if (c == '(') {
				
				single(TYPE_OPEN_Q2, s);
				
			} else if (c == ')') {
				
				single(TYPE_CLOSE_Q2, s);
								
			} else if (c == '"') {
				
				if (type != TYPE_Q_STRING) {
					flush(); // previous;
					type = TYPE_Q_STRING;
					token = "";
					continue;
				} else {
					flush(); // quoted string;
					type = TYPE_0;
					token = "";
					continue;
				}
				
			} else if (c == ':') {
				single(TYPE_NEXT_OPERATOR, s);
			//} else if (c == '\n') {
				//single(TYPE_NEXT_LINE, s);
			} else if (c == ';') {
				single(TYPE_NEXT_PRINT_PARAMETR, s);
			} else if (c == ',') {
				single(TYPE_NEXT_PARAMETR, s);
			} else if (c == '=') {
				single(TYPE_EQUALS, s);
			} else if (c == '+' || c == '-' || c == '*' || c == '/') {
				single(TYPE_ARITHMETIC,s);
			}
			
		}
		
	}

	private void flush(Integer type1, String token1) {
		if (type1 == TYPE_SPACE)
			return;
		structure.types.add(type1);
		structure.tokens.add(token1);
	}

	private void flush() {
		if (type != TYPE_0) {
			structure.types.add(type);
			structure.tokens.add(token);
		}
		type = TYPE_0;
		token = "";
	}

	private void single(Integer singleType, String singleChar) {
		if (type == TYPE_Q_STRING) {
			token += singleChar;
		} else {
			flush();
			flush(singleType, singleChar);
		}
	}

	private void error(String line) {
		throw new RuntimeException("Syntax error, type=" + type + ", position=" + index + ", line=" + line);
	}

}
