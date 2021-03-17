package radio86java.basic;

import javax.script.*;

public class Eval {

  public /* static */ void main(String[] argv) throws Exception {
    eval("'abc' + 'def'");
    eval("'abc' + Math.sin(3.14159) + 'abc'");
  }

  public static String eval(String expression) throws ScriptException {
    System.out.println("eval(" + expression + ")");
    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("JavaScript");
    Object obj = engine.eval(expression);
    System.out.println(obj);
    return obj.toString();
  }

}
