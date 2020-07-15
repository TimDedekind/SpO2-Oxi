package de.semp.medical.spo2;


import javax.management.loading.MLetContent;
import java.io.Console;
import java.util.Scanner;
import java.awt.event.KeyEvent;

import static javafx.scene.input.KeyCode.ESCAPE;

public class TestMain implements ISpO2Observer{

    public static void main(String[] args) {
        char c = 0;
        SpO2Manager manager = SpO2Manager.getInstance();
        SpO2Sensor sensor = null;

        System.out.println("'e' - emulated Patient");
        System.out.println("'h' - hardware (CADT - Pulsoximeter)");
        System.out.println("'s' - start / stop measurement");
        System.out.println("'t' - terminate program");

        Scanner sc = new Scanner(System.in);


        SpO2Manager.SensorType type = SpO2Manager.SensorType.emulated;

        do {
            c = sc.next().charAt(0);
            if (c == 'e') {
                type = SpO2Manager.SensorType.emulated;
            } else if (c == 'h') {
                type = SpO2Manager.SensorType.CADT;
            }

            if (sensor == null) {
                sensor = manager.getSensor(type);
                sensor.addObserver(new TestMain());
                sensor.start();
            }
            // a sensor allready exists
            else {
                manager.switchSensor(type);
            }
            if (c == 's') {
                if (sensor.isRun()) {
                    sensor.stop();
                    System.out.println("will stoppen!");
                } else {
                    sensor.start();
                    System.out.println("will starten!");
                }
            }
            if(sensor instanceof SpO2emulated)
                editEmulationOptions(c, (SpO2emulated)sensor);

        }while (c != 't');

        System.exit(0);
    }

    private static void editEmulationOptions(char key, SpO2emulated sensor) {
        String value = "";
        String param = "";

        System.out.println("Commands to control the simulated patient:");
        System.out.println(" 'b': set bpm to standard level");
        System.out.println(" 'u': set bpm up to a high level");
        System.out.println(" 'd': set bpm down to a low level");
        System.out.println(" 'n': set spo2 to normal saturation level");
        System.out.println(" 'm': set spo2 to more saturation");
        System.out.println(" 'l': set spo2 to less saturation level");
        System.out.println(" 'c': ErrorCheck");
        System.out.println("");

        switch (key)
        {
            case 'b': param = "bpm"; value = "norm"; break;
            case 'u': param = "bpm"; value = "high"; break;
            case 'd': param = "bpm"; value = "low"; break;
            case 'n': param = "spo2"; value = "norm"; break;
            case 'm': param = "spo2"; value = "high"; break;
            case 'l': param = "spo2"; value = "low"; break;
            case 'c': param = "ErrorCheck"; value = ErrorState.FingerOut.toString(); break;
            default: param = ""; value = ""; break;
        }
        if (param!="")
            if (sensor.setParam(param, value))
                System.out.println(String.format("Set %s on %s", param, value));
            else
                System.out.println("Error - Parameter setting");
    }

    @Override
    public void onNewPulse(int pulse, int spo2) {
        System.out.println("New measurement - pulse: "+pulse+", spo2: "+spo2);
    }

    @Override
    public void onErrorState(ErrorState oxiError) {
        System.err.println("Errorstate: "+oxiError);
    }
}
