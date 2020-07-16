package de.semp.medical.spo2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import static javax.swing.text.StyleConstants.Background;



public class GUI_Controler implements ISpO2Observer {

    Surveillance surveillance = Surveillance.getInstance( this );

    Alarm_Error_Controller Errormeldung = new Alarm_Error_Controller();
    private boolean UpperSpO2Alarm;
    private boolean LowerSpO2Alarm;
    private boolean UpperPulseAlarm;
    private boolean LowerPulseAlarm;

    private int UpperSpO2Border = 99;
    private int LowerSpO2Border = 90;
    private int UpperPulseBorder = 120;
    private int LowerPulseBorder = 50;

    @FXML private Label lSpO2Wert;
    @FXML private Label lPulseWert;
    @FXML private TextField TfUpperBorderSpO2;
    @FXML private TextField TfLowerBorderSpO2;
    @FXML private TextField TfUpperBorderPulse;
    @FXML private TextField TfLowerBorderPulse;
    @FXML private Label lErrorLabel;
    @FXML private AnchorPane ApAnchorPane;
    @FXML private RadioButton rUseEmulator ;
    @FXML private RadioButton rUseSensor ;

    final ToggleGroup group = new ToggleGroup();

    SensorControl sensorControl = new SensorControl();

    public GUI_Main GUI_Main;
    public void setGUI_Main(GUI_Main gui_main) {
        this.GUI_Main = gui_main;
    }

    public void setAlarm( boolean alarm ) {
        if ( alarm )
            lErrorLabel.setVisible( true );
        else
            lErrorLabel.setVisible( false );
    }

    @FXML
    private void initialize()
    {
        rUseEmulator.setToggleGroup(group);
        rUseEmulator.setSelected(true);
        rUseSensor.setToggleGroup(group);
        sensorControl.setGuiInputToEmulater();
        sensorControl.getSensor().addObserver(this);
        sensorControl.getSensor().start();
        surveillance.guiControler = this;
        lErrorLabel.setVisible( false );
    }


    @FXML
    public void handleStart(){
        sensorControl.getSensor().addObserver(this);
        sensorControl.getSensor().start();
        surveillance.guiControler = this;
    }

    @FXML
    public void handleSetThreshold() {
        try {
            surveillance.setAlarmPulseLower( Integer.parseInt(TfLowerBorderPulse.getText()) );
            surveillance.setAlarmPulseUpper( Integer.parseInt(TfUpperBorderPulse.getText()) );
            surveillance.setAlarmSP02( Integer.parseInt(TfLowerBorderSpO2.getText()) );

        } catch (Exception e) {
            Errormeldung.alarmerror();
        }
    }
    @Override
    public void onNewPulse(int pulse, int spo2 ) {
        Platform.runLater(new Runnable() {
            public void run() {
                lSpO2Wert.setText("" + spo2);
                lPulseWert.setText("" + pulse);

            }
        });
    }
    @Override
    public void onErrorState(ErrorState oxiError) {

}

    public void handleAlarmPause(ActionEvent actionEvent) {

        sensorControl.getSensor().deleteObserver( Surveillance.getInstance( this ) );
    }

    public void handleAlarmStart(ActionEvent actionEvent) {

        sensorControl.getSensor().addObserver( Surveillance.getInstance( this ) );
    }


    public void UseEmulator(ActionEvent actionEvent) {
        sensorControl.setGuiInputToEmulater();
    }

    public void UseSensor(ActionEvent actionEvent) {
        sensorControl.setGuiInputToRealSensor();
    }
}
