package de.semp.medical.spo2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GUI_Controler implements ISpO2Observer {

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
    @FXML private Label lAlarm;

    SpO2Manager manager = SpO2Manager.getInstance();
    SpO2Sensor sensor = null;
    SpO2Manager.SensorType type = SpO2Manager.SensorType.emulated;


    public GUI_Main GUI_Main;
    public void setGUI_Main(GUI_Main gui_main) {
        this.GUI_Main = gui_main;
    }

    @FXML
    public void handleStart(){
        type = SpO2Manager.SensorType.emulated;
        sensor = manager.getSensor(type);
        sensor.addObserver(this);
        sensor.start();
    }

    @FXML
    public void handleSetAlarm() {
        try {
            UpperSpO2Border = Integer.parseInt(TfUpperBorderSpO2.getText());
            LowerSpO2Border = Integer.parseInt(TfLowerBorderSpO2.getText());
            UpperPulseBorder = Integer.parseInt(TfUpperBorderPulse.getText());
            LowerPulseBorder = Integer.parseInt(TfLowerBorderPulse.getText());
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

                UpperSpO2Alarm = UpperSpO2Border <= spo2;
                LowerSpO2Alarm = LowerSpO2Border >= spo2;
                UpperPulseAlarm = UpperPulseBorder <= pulse;
                LowerPulseAlarm = LowerPulseBorder >= pulse;
                if ((UpperSpO2Alarm || LowerSpO2Alarm || UpperPulseAlarm || LowerPulseAlarm) ){
                    lAlarm.setText("Werte im kritischen Bereich");
                }
            }
        });
    }
    @Override
    public void onErrorState(ErrorState oxiError) {

}

}
