package radio86java.uiswing;

//import radio86java.basic.Basic;

import radio86java.ComputerModelImpl;
import radio86java.ComputerModelIntf;


public class Radio86rkMain {

  public static void main(String[] argv) {

    ComputerModelIntf computerModel = new ComputerModelImpl();

    final UserInterfaceImpl screen = new UserInterfaceImpl(computerModel);

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
