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
		m.put("R86", api);
		m.put("r86", api);
		SimpleBindings b = new SimpleBindings(m);
		ScriptEngineManager manager = new ScriptEngineManager();
		manager.setBindings(b);
		ScriptEngine engine = manager.getEngineByName("javascript");

		String functions = 
				"this.inkey = r86.inkey;" +
				"this.INKEY = r86.inkey;" +
				"this.pause = r86.pause;" +
				"this.PAUSE = r86.pause;" +
				"this.input = r86.input;" +
				"this.INPUT = r86.input;" +
				"this.screen = r86.screen;" +
				"this.SCREEN = r86.screen;" +
				"this.print = r86.print;" +
				"this.PRINT = r86.print;" +
				"this.cur = r86.cur;" +
				"this.CUR = r86.cur;" +
				"this.tab = r86.tab;" +
				"this.TAB = r86.tab;" +
				"this.spc = r86.spc;" +
				"this.SPC = r86.spc;" +
				"this.cls = r86.cls;" +
				"this.CLS = r86.cls;" +
				"this.plot = r86.plot;" +
				"this.PLOT = r86.plot;" +
				"this.line = r86.line;" +
				"this.LINE = r86.line;";

		try {
			Object result = engine.eval(functions + listing);
			System.out.println("Result of ScriptEngine.eval(): " + result);
		}
		catch(ScriptException ex) {
			ex.printStackTrace();
		}
	}

}
