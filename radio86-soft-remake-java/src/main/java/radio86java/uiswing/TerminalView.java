package radio86java.uiswing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import radio86java.ComputerModelIntf;
import radio86java.TerminalModelIntf;

public class TerminalView extends JPanel {

  private static final long serialVersionUID = 1L;
  private final Charset charset = new Charset();
  private boolean previousIsColoredCharset = false;

  private final ComputerModelIntf computerModel;

  private final FontSizeMultiplier multiplier;

  private final int pixelsX;
  private final int pixelsY;

  private boolean freezeJPanel = false;

  /**
   * @param computerModel
   * @param multiplier 1 for 8*8 pixels or 2 for 16*16 pixels;
   * @param space some space around chars
   */
  TerminalView(ComputerModelIntf computerModel,
          FontSizeMultiplier multiplier, int space) {
    this.computerModel = computerModel;
    this.multiplier = multiplier;
    this.pixelsX = 8 * multiplier.asNumber() + space;
    this.pixelsY = 8 * multiplier.asNumber() + space;
  }

  void init() {

    TerminalModelIntf console = getTerminalModel();

    setBackground(Color.BLACK);
    Dimension d = new Dimension(console.getMaxX() * pixelsX, console.getMaxY() * pixelsY);
    setMaximumSize(d);
    setMinimumSize(d);
    setPreferredSize(d);
    setSize(d);
    charset.init();
  }

  private TerminalModelIntf getTerminalModel() {
    return computerModel.getTerminalModel();
  }

  //private boolean partial = false;

  // TODO: implement partial repaint
  void setPartialRepaint(boolean value) {
    //this.partial = value;
  }

  boolean isFreezeJPanel() {
    return freezeJPanel;
  }

  void setFreezeJPanel(boolean freezeJPanel) {
    this.freezeJPanel = freezeJPanel;
  }

  @Override
  public void paintComponent(Graphics g) {

    if (freezeJPanel) {
      return;
    }

    super.paintComponent(g);

    TerminalModelIntf console = getTerminalModel();

    Graphics2D g2d = (Graphics2D) g;

    fullRepaint(g2d);

    // Cursor;
    g2d.setColor(Color.YELLOW);
    int x = console.getCursorX();
    int y = console.getCursorY();
    drawCursor(g2d, x, y);
  }

  private void drawCursor(Graphics2D g2d, int x, int y) {

    TerminalModelIntf console = getTerminalModel();

    if (console.getDirectionUp() > 0) {
      y = (console.getMaxY() - 1 - console.getCursorY());
    }
    if (x >= 0 && x < console.getMaxX() && y >= 0 && y < console.getMaxY()) {
      x = x * pixelsX;
      y = y * pixelsY + pixelsY;
      g2d.drawLine(x, y, x + pixelsX, y);
    }
  }

  private void fullRepaint(Graphics2D g2d) {

    int screenY = 0;
    int screenX = 0;

    TerminalModelIntf console = getTerminalModel();

    if (console.getDirectionUp() > 0) {
      for (int y = (console.getMaxY() - 1); y >= 0; y--) {
        for (int x = 0; x < console.getMaxX(); x++) {
          char c = console.get(x, y);
          render(g2d, screenX, screenY, c, console);
          screenX++;
        }
        screenY++;
        screenX = 0;
      }
    } else {
      for (int y = 0; y < console.getMaxY(); y++) {
        for (int x = 0; x < console.getMaxX(); x++) {
          char c = console.get(x, y);
          render(g2d, screenX, screenY, c, console);
          screenX++;
        }
        screenY++;
        screenX = 0;
      }
    }

  }

  private void render(Graphics2D g2d, int screenX, int screenY, char c, TerminalModelIntf console) {
    ImageIcon imageIcon;
    BufferedImage bi;

    if (((int) c == 32) || ((int) c == 0)) {
      return;
    }

    boolean isColoredCharset = console.isColoredCharset();
    if (!isColoredCharset && (isColoredCharset != previousIsColoredCharset)) {
      // reset all characters to white
      charset.init();
    }
    previousIsColoredCharset = isColoredCharset;

    if (multiplier == FontSizeMultiplier.ONE) {
      imageIcon = charset.getImageIcon((int) c);
    } else {
      imageIcon = charset.getImageIcon16((int) c);
    }

    if (imageIcon == null) {
      return;
    }

    if (console.isColoredCharset()) {
      if (multiplier == FontSizeMultiplier.ONE) {
        bi = charset.getBufferedImage((int) c);
      } else {
        bi = charset.getBufferedImage16((int) c);
      }

      for (int xx = 0; xx < bi.getWidth(); xx++) {
        for (int yy = 0; yy < bi.getHeight(); yy++) {
          int rgb = bi.getRGB(xx, yy);
          if (rgb != -16777216) {
            rgb -= 3;
          }
          bi.setRGB(xx, yy, rgb);
        }
      }
      imageIcon = new ImageIcon(bi);
    }

    g2d.drawImage(imageIcon.getImage(), screenX * pixelsX, screenY * pixelsY, this);
  }

}
