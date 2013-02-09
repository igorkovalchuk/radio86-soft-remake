package radio86java.basic;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import radio86java.Radio86rk;

public class BasicInterpreter {

	public void interpret(BasicStructure2 structure, Radio86rk screen) {
		
		BasicVariables variables = new BasicVariables();

		for(Expression e : structure.expressions) {
			try {
				e.interpret(screen, variables);
			} catch (ScriptException ex) {
				throw new RuntimeException(ex);
			}
		}

	}

}
