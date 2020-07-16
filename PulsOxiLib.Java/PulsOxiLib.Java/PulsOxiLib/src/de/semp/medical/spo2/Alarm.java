package de.semp.medical.spo2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Alarm {
    GUI_Controler guiControler;

    Alarm( GUI_Controler gui ) {
        guiControler = gui;
    }

    void issueAlarm() {
        guiControler.setAlarm( true );
    }

    void stopAlarm() {
        guiControler.setAlarm( false );
    }
}
