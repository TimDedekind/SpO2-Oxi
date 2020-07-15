package de.semp.medical.spo2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Observer interface to be notified when sensor values have changed or an error occured.
 * @author dubies
 */

public interface ISpO2Observer {
    /**
     * Different error states
     *
     */
    public enum ErrorState {
        /** sensor ok*/
        OK, 
        /** sensor disconnected*/
        SensorDisconnect, 
        /** finger is out*/
        FingerOut, 
        /** perfusion is low*/
        LowPerfusion, 
        /** selftest was not completeted successfully*/
        SelftestError}


    
    /**
     * Is called when a new sensor value was received.
     * @param pulse the current pulse value
     * @param spo2 the spo2 value
     */
    void onNewPulse(int pulse, int spo2 );
    /**
     * Is called when a error occured
     * @param oxiError the error
     */
    void onErrorState(ErrorState oxiError);
}
