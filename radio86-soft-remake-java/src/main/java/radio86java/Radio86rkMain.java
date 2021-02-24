package radio86java;

//import radio86java.basic.Basic;

import java.io.InputStream;
import java.util.Scanner;


public class Radio86rkMain {

    public static void main(String[] argv) {

        StringBuilder listing = new StringBuilder();

        InputStream is = Class.class.getResourceAsStream("/radio86java/samples/tv_tbl.js");
        if (is == null)
            throw new RuntimeException("Samples InputStream == null");

        Scanner s = new Scanner(is);
        while (s.hasNextLine())
            listing.append(s.nextLine()).append("\n");
        s.close();

        final Radio86rk screen = new Radio86rk();

        screen.getEditor().setText(listing.toString());

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
