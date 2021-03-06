package radio86java.uiswing;

import radio86java.ComputerModel;
import radio86java.ComputerModelIntf;


public class Radio86rkMain {

  public static void main(String[] argv) {

    ComputerModelIntf computerModel = new ComputerModel();

    final UserInterfaceImpl screen = new UserInterfaceImpl(computerModel);
    screen.setLocationByPlatform(true);
    screen.setTitle("Симулятор Радио 86РК");

    java.awt.EventQueue.invokeLater(() -> {
      screen.setVisible(true);
      screen.getCanvas().requestFocus();
    });

  }

  //private static void setDefaultListing(UserInterfaceImpl screen) {
    //String listing = Utils.loadResource("ohota_na_lis.js");
    //screen.getEditor().setText(listing.toString());
  //}

  //private static void runBasicCode(String listing, UserInterfaceImpl screen) {
    //Basic basic = new Basic();
    //basic.run(listing, screen);
  //}

}
