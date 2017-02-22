package JavaFXBrowser;

import JavaFXBrowser.SwingFXWebView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by diederikdulfer on 21-02-17.
 */
public class main {
    public static void main(String ...args){
        // Run this later:
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame frame = new JFrame();

                frame.getContentPane().add(new SwingFXWebView());

                frame.setMinimumSize(new Dimension(640, 480));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                SwingFXWebView swingFXWebView = (SwingFXWebView) frame.getRootPane().getContentPane().getComponent(0);
                swingFXWebView.setCurrentURL("http://wetten.overheid.nl/jci1.3:c:BWBR0011173&artikel=1&z=2000-07-01&g=2000-07-01");
                swingFXWebView.load();
            }
        });
    }
}
