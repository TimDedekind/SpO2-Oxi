/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.semp.medical.spo2;

import de.semp.medical.spo2.ISpO2Observer.ErrorState;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a abstract SpO2 sensor. Sensor values are provided using the "Observer Pattern".
 * Observers have to be registered prior to starting the sensor data aquisition process.
 * @author dubies
 */
public abstract class SpO2Sensor {
    /** the current hr value */
    protected int hr;
    
    /** the current spo2 value */
    protected int spo2;

    /** list of all registerd observers */
    protected List<ISpO2Observer> observers;
    /** Indicatees if the sensor is running or not. */
    protected boolean run = false;

    /**
     * Creates an default sensor
     */
    public SpO2Sensor() {
        hr = 0;
        spo2 = 0;
        observers = new ArrayList<>();
    }

    /**
     * Register a observer
     * @param obs the observer to register
     */
    public void addObserver(ISpO2Observer obs){
        observers.add(obs);
    }

    /**
     * Unregister a observer
     * @param obs the observer to unregister
     */
    public void deleteObserver(ISpO2Observer obs){
         observers.remove(obs);
    }

    /**
     *
     * @return all registrated observers
     */
    public List<ISpO2Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<ISpO2Observer> obs){
        observers=obs;
    }

    protected void notifyObservers() {
        for (ISpO2Observer spo : observers) {
            spo.onNewPulse(hr, spo2);
        }
    }
     
    protected void notifyStateChange(ErrorState state){
          for (ISpO2Observer spo : observers) {
            spo.onErrorState(state);
        }
     }
    
    /**
     * Starts the sensor data aquisition. Afterwards, all registered observers are notified about new data.
     */
    public abstract void start();
    /**
     * Stops data aquisition.
     */
    public abstract void stop();
  
    /**
     * Checks if the sensor has been started.
     * @return true, if sensor has been started, false otherwise.
     */
    public boolean isRun(){
        return run;
    }    
}
