package radio86java;

import radio86java.basic.Basic;
import radio86java.js.JavaScript;

public class InterpreterFactory {

	public static InterpreterInterface create(Language language) {
		if (language == Language.BASIC) {
			return new Basic();
		}
		else if (language == Language.JS) {
			return new JavaScript();
		}
		else throw new RuntimeException();
	}

}
