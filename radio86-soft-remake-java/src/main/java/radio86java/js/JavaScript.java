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

                String[] builtinFunctions1 = new String[] {
                    "pause(seconds)",
                    "print(message)",
                    "println(message)",
                    "cur(x, y)",
                    "tab(n)",
                    "cr()",
                    "lf()",
                    "crlf()",
                    "freeze()", // additional function;
                    "unfreeze()", // additional function;
                    "spc(n)",
                    "cls()",
                    "plot(x, y, z)",
                    "line(x, y)",
                    "circle(x, y, r)" // additional function;
                };

                String[] builtinFunctions2 = new String[] {
                    "function inkey(mode) { return r86.inkey(mode); }",
                    "function input(message) { return r86.input(message); }",
                    "function screen(x, y) { return r86.screen(x, y); }",
                    "function cos(a) { return Math.cos(a); }",
                    "function sin(a) { return Math.sin(a); }"
                };

                StringBuilder functions = new StringBuilder();

                for(String f : builtinFunctions1) {
                    functions.append("function ").append(f)
                            .append(" { r86.").append(f).append("; }\n");
                }

                for(String f : builtinFunctions2) {
                    functions.append(f).append("\n");
                }

		try {
			Object result = engine.eval(functions.toString() + listing);
			System.out.println("Result of ScriptEngine.eval(): " + result);
		}
		catch(ScriptException ex) {
			ex.printStackTrace();
		}
	}

}
