package radio86java;

public class ComputerModelImpl implements ComputerModelIntf {

  private TerminalModel terminalModel = new TerminalModel();

  public ComputerModelImpl() {
  }

  @Override
  public TerminalModel getTerminalModel() {
    return terminalModel;
  }

}
