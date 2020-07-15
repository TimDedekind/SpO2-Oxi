package de.semp.medical.spo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Alarm_Error_Controller {

    @FXML private Button bOk;
    

    Stage secondStage = new Stage();
    

    public void alarmerror(){

    try {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Alarm_Error.fxml"));
        Parent rootl = (Parent) fxmlloader.load();
        secondStage.setTitle("Error");
        secondStage.setScene(new Scene(rootl));
        secondStage.show();
    }catch(IOException ex){
        Logger.getLogger(GUI_Controler.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    @FXML
    public void handleOk(){
        Stage Secondstage = (Stage) bOk.getScene().getWindow();
        secondStage.close();
    }
}
