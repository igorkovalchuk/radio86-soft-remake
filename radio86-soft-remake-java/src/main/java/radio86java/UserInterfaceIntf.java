package radio86java;

public interface UserInterfaceIntf {

  TerminalModel getTerminalModel();

  void updateScreen();

  void setFreeze(boolean value);

  String showInputDialog(String message);

}
