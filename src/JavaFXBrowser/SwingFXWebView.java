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
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
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

        swingButton = new JButton();
        swingButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        webEngine.reload();
                    }

                });
            }
        });
        swingButton.setText("Reload");

        add(swingButton, BorderLayout.SOUTH);
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

                stage.setTitle("Hello Java FX");
                stage.setResizable(true);

                Group root = new Group();
                Scene scene = new Scene(root,80,20);
                stage.setScene(scene);

                // Set up the embedded browser:
                browser = new WebView();
                webEngine = browser.getEngine();
                webEngine.load(CurrentURL);

                ObservableList<Node> children = root.getChildren();
                children.add(browser);

                jfxPanel.setScene(scene);
            }
        });
    }

}