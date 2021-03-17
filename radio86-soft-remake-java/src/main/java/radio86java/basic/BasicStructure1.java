package radio86java.basic;

import java.util.ArrayList;
import static radio86java.basic.Basic.type2name;

public class BasicStructure1 {

  ArrayList<Integer> types = new ArrayList<Integer>();
  ArrayList<String> tokens = new ArrayList<String>();

  public ArrayList<String> getTokens() {
    return tokens;
  }

  public ArrayList<Integer> getTypes() {
    return types;
  }

  void print() {
    for (int i = 0; i < types.size(); i++) {
      System.out.println("Type=" + types.get(i) + "	Token=[" + tokens.get(i) + "]" + "	" + type2name.get(types.get(i)));
    }
  }

}
