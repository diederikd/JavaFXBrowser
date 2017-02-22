package JavaFXBrowser;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class SwingFXWebView extends JPanel {

    private Stage stage;
    private WebView browser;
    private JFXPanel jfxPanel;
    private JButton swingButton;
    private WebEngine webEngine;
    private String CurrentURL = "http://www.wetten.nl";

    public String getCurrentURL()
        {
            return CurrentURL;
        }

    public void setCurrentURL(String url)
    {
        CurrentURL = url;
    }

    public SwingFXWebView(){
        initComponents();
    }

    public void load(){
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                webEngine.load(CurrentURL);
            }
        });
    }

    private void initComponents(){

        jfxPanel = new JFXPanel();
        createScene();

        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);
        //Dimension dimension = new Dimension(2500,1600);
        //jfxPanel.setMinimumSize(dimension);
        //this.setMinimumSize(dimension);

        //swingButton = new JButton();
        //swingButton.addActionListener(new ActionListener() {

        //    @Override
        //    public void actionPerformed(ActionEvent e) {
        //        Platform.runLater(new Runnable() {

        //            @Override
        //            public void run() {
        //              webEngine.reload();
         //           }

         //       });
         //   }
        // });
        //swingButton.setText("Reload");

        //add(swingButton, BorderLayout.SOUTH);
    }

    private void adjustHeight() {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                try {
                    Object result = webEngine.executeScript("document.getElementById('mydiv').offsetHeight");
                    if (result instanceof Integer) {
                        Integer i = (Integer) result;
                        double height = new Double(i);
                        height = height + 20;
                        browser.setPrefHeight(height);
                        browser.getPrefHeight();
                    }
                } catch (JSException e) { }
            }
        });
    }

    /**
     * createScene 
     *
     * Note: Key is that Scene needs to be created and run on "FX user thread" 
     *       NOT on the AWT-EventQueue Thread 
     *
     */
    private void createScene() {
        PlatformImpl.startup(new Runnable() {
            @Override
            public void run() {

                stage = new Stage();
                stage.setResizable(true);

                /* Group root = new Group();
                root.prefWidth(2500);
                root.prefHeight(1600);
                root.minWidth(2500);
                root.minHeight(1600);
                Scene scene = new Scene(root,2500,1600);*/

                // Set up the embedded browser:
                browser = new WebView();
                browser.setMaxWidth(2500);
                browser.setMaxHeight(1600);
                browser.prefWidth(2500);
                browser.prefHeight(1600);

                webEngine = browser.getEngine();
                webEngine.load(CurrentURL);

                StackPane pane = new StackPane();
                pane.minWidth(2500);
                pane.minHeight(1600);
                pane.getChildren().add(browser);

                //ObservableList<Node> children = root.getChildren();
                //children.add(pane);
                Scene scene = new Scene(pane,2500,1600);
                adjustHeight();
                jfxPanel.setScene(scene);
            }
        });
    }

}