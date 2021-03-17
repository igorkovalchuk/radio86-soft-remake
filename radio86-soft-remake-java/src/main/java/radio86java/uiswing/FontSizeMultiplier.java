package radio86java.uiswing;

public enum FontSizeMultiplier {
  ONE(1),
  TWO(2);

  private final int number;

  private FontSizeMultiplier(int number) {
    this.number = number;
  }

  public int asNumber() {
    return number;
  }
}
