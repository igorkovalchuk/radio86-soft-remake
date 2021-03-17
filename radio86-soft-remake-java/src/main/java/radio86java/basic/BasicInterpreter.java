package radio86java.basic;

import javax.script.ScriptException;
import radio86java.UserInterfaceIntf;
import radio86java.ComputerModelIntf;

public class BasicInterpreter {

	public void interpret(BasicStructure2 structure, UserInterfaceIntf screen,
			ComputerModelIntf computerModel) {
		
		BasicVariables variables = new BasicVariables();

		for(Expression e : structure.expressions) {
			try {
				e.interpret(screen, computerModel, variables);
			} catch (ScriptException ex) {
				throw new RuntimeException(ex);
			}
		}

	}

}
