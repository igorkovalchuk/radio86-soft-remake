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
		//Radio86rkAPI api = new Radio86rkAPI(screen);
		screen.getConsole().setInteractive(false);
		Radio86rkAPI api = Radio86rkAPI.initializeInstance(screen);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("R", api);
		m.put("r", api);
		SimpleBindings b = new SimpleBindings(m);
		ScriptEngineManager manager = new ScriptEngineManager();
		manager.setBindings(b);
		ScriptEngine engine = manager.getEngineByName("javascript");

		String functions = 
				"this.pause = r.pause;" +
				"this.PAUSE = r.pause;" +
				"this.input = r.input;" +
				"this.INPUT = r.input;" +
				"this.print = r.print;" +
				"this.PRINT = r.print;" +
				"this.cur = r.cur;" +
				"this.CUR = r.cur;" +
				"this.cls = r.cls;" +
				"this.CLS = r.cls;" +
				"this.plot = r.plot;" +
				"this.PLOT = r.plot;";

		try {
			Object result = engine.eval(functions + listing);
			System.out.println("Result of ScriptEngine.eval(): " + result);
		}
		catch(ScriptException ex) {
			ex.printStackTrace();
		}
	}

}
