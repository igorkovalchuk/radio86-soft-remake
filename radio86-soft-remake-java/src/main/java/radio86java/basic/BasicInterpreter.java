package radio86java.basic;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;
import radio86java.uiswing.UserInterfaceImpl;

public class BasicInterpreter {

	public void interpret(BasicStructure2 structure, UserInterfaceImpl screen) {
		
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
