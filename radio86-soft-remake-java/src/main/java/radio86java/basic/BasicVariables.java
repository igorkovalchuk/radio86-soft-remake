package radio86java.basic;

import java.util.HashMap;
import java.util.Map;

public class BasicVariables {

  private Map<String, String> numericvars = new HashMap<String, String>();
  private Map<String, String> stringvars = new HashMap<String, String>();

  public void setNumeric(String name, String value) {
    System.out.println("Set variable " + name + "=" + value);
    numericvars.put(name, value);
  }

  public void setString(String name, String value) {
    System.out.println("Set variable " + name + "=" + value);
    stringvars.put(name, value);
  }

  public void setVariable(String name, String value) {
    if (name.endsWith("$")) {
      setString(name, value);
    } else {
      setNumeric(name, value);
    }
  }

  public String getVariable(String name) {
    if (name.endsWith("$")) {
      if (stringvars.containsKey(name)) {
        return stringvars.get(name);
      }
      return "";
    } else {
      if (numericvars.containsKey(name)) {
        return numericvars.get(name);
      }
      return "0";
    }
    //throw new RuntimeException("Unknown variable: " + name);
  }

}
