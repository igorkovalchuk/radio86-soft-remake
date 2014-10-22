package radio86java.js;

import java.util.HashMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import radio86java.InterpreterInterface;
import radio86java.Radio86rk;
import radio86java.Radio86rkAPI;

public class JavaScript implements InterpreterInterface {

	@Override
	public void run(String listing, Radio86rk screen) {
		Radio86rkAPI api = new Radio86rkAPI(screen);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("R", api);
		m.put("r", api);
		SimpleBindings b = new SimpleBindings(m);
		ScriptEngineManager manager = new ScriptEngineManager();
		manager.setBindings(b);
		ScriptEngine engine = manager.getEngineByName("javascript");
		try {
			Object result = engine.eval(listing);
			System.out.println("Result of ScriptEngine.eval(): " + result);
		}
		catch(ScriptException ex) {
			ex.printStackTrace();
		}
	}

}
