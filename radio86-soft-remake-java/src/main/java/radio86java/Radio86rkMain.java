package radio86java;

//import radio86java.basic.Basic;
import radio86java.uiswing.UserInterfaceImpl;

public class Radio86rkMain {

  public static void main(String[] argv) {

    final UserInterfaceImpl screen = new UserInterfaceImpl();

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
