package radio86java;

public class ComputerModel implements ComputerModelIntf {

  private final TerminalModel terminalModel = new TerminalModel();

  public ComputerModel() {
  }

  @Override
  public TerminalModel getTerminalModel() {
    return terminalModel;
  }

}
