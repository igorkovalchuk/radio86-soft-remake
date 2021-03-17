package radio86java;

public class ComputerModel implements ComputerModelIntf {

  private TerminalModel terminalModel = new TerminalModel();

  public ComputerModel() {
  }

  @Override
  public TerminalModel getTerminalModel() {
    return terminalModel;
  }

}
