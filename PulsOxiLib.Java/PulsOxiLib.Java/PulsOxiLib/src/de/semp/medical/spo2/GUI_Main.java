package de.semp.medical.spo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI_Main extends Application {

    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    public void mainWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(GUI_Main.class.getResource("GUI.fxml"));
            AnchorPane pane = loader.load();

            primaryStage.setMinHeight(400.00);
            primaryStage.setMinWidth(500.00);

            GUI_Controler GUIControler = loader.getController();
            GUIControler.setGUI_Main(this);

            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(GUI_Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        launch(args);

    }


}
