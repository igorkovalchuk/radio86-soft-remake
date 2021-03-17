package radio86java.basic;

import javax.script.ScriptException;
import radio86java.UserInterfaceIntf;

public class BasicInterpreter {

	public void interpret(BasicStructure2 structure, UserInterfaceIntf screen) {
		
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
