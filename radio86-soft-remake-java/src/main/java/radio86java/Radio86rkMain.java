package radio86java;

//import radio86java.basic.Basic;

import java.io.InputStream;
import java.util.Scanner;


public class Radio86rkMain {

    public static void main(String[] argv) {

        final Radio86rk screen = new Radio86rk();

        //String listing = Utils.loadResource("ohota_na_lis.js");
        //screen.getEditor().setText(listing.toString());

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                screen.setVisible(true);
                screen.getCanvas().requestFocus();
            }
        });

        //Basic basic = new Basic();
        //basic.run(listing, screen);
    }

}
