package de.semp.medical.spo2;

public class SensorControl {

    SpO2Manager manager = SpO2Manager.getInstance();
    SpO2Sensor sensor = null;
    SpO2Manager.SensorType type = SpO2Manager.SensorType.emulated;

    public SpO2Sensor getSensor()
    {
        return sensor;
    }

    public void setGuiInputToEmulater(){
        sensor = manager.getSensor(SpO2Manager.SensorType.emulated);

    }


    public void setGuiInputToRealSensor(){
        sensor = manager.getSensor(SpO2Manager.SensorType.CADT);

    }

}
