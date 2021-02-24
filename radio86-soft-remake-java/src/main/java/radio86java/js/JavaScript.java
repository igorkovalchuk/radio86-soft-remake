package radio86java.js;

import java.util.List;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import radio86java.InterpreterInterface;
import radio86java.Radio86rk;
import radio86java.Radio86rkAPI;

public class JavaScript implements InterpreterInterface {

	@Override
	public void run(String listing, Radio86rk screen) {

		screen.getConsole().setInteractive(false);

		Radio86rkAPI api = Radio86rkAPI.initializeInstance(screen);

		ScriptEngineManager manager = new ScriptEngineManager();

                ScriptEngine engine = manager.getEngineByName("graal.js");

                Bindings bindings = engine.createBindings();
                bindings.put("polyglot.js.allowHostAccess", true);
                bindings.put("r86", api);
                engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

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
                    "circle(x, y, r)", // additional function;
                    "poke(addr, value)",
                    "log(message)",
                };

                String[] builtinFunctions2 = new String[] {
                    "function inkey(mode) { return r86.inkey(mode); }",
                    "function input(message) { return r86.input(message); }",
                    "function screen(x, y) { return r86.screen(x, y); }",
                    "function cos(a) { return Math.cos(a); }",
                    "function sin(a) { return Math.sin(a); }",
                    "function peek(addr) { return r86.peek(addr); }",
                    "function chr(value) { return r86.chr(value); }",
                    "function asc(ch) { return r86.asc(ch); }",
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

        private void printEngines() {
                List<ScriptEngineFactory> engines = (new ScriptEngineManager()).getEngineFactories();
                for (ScriptEngineFactory f: engines) {
                    System.out.println(f.getLanguageName() + " "
                            + f.getEngineName() + " " + f.getNames().toString());
                }
        }
}
