package de.semp.medical.spo2;


import java.io.IOException;
import java.util.List;

public class SpO2Manager {

    public enum SensorType { emulated, CADT }

    private SpO2Sensor sensor;
    private SensorType type;

    // Singleton Pattern
    private static SpO2Manager instance = new SpO2Manager();
    private SpO2Manager() { }
    public static SpO2Manager getInstance(){
        return instance;
    }

    /**
     *
     * produce a new sensor instance
     * @param type
     * @return new sensor instance
     */
    public SpO2Sensor getSensor(SensorType type){
        if(sensor!=null){
            sensor.stop();
            sensor = null;
        }
        this.type = type;
        switch (type){
            case emulated:  sensor = new SpO2emulated();
                break;
            case CADT:
                try {
                    sensor = new SpO2_CADT();
                } catch (IOException e) {
                    sensor=null;
                }
                break;
            default: sensor=null; break;
        }
        return sensor;
    }

    /**
     * switch sensor to selected sensor type
     * @param type
     * @return
     */
    public boolean switchSensor(SensorType type){
        if(sensor==null || this.type==type) return false;  // nothing to do
        boolean running = sensor.isRun();
        sensor.stop();
        List<ISpO2Observer> observers = sensor.getObservers();
        SpO2Sensor oldSensor = sensor;
        SensorType oldType = this.type;
        sensor=getSensor(type);

        if(sensor==null){
            sensor = oldSensor;
            return false;
        }
        sensor.setObservers(observers);
        if(running)
            sensor.start();
        return true;
    }

}
