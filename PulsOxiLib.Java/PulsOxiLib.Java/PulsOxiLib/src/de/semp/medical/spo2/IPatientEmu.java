/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.semp.medical.spo2;

/**
 * Emulates a virtual patient. 
 * @author dubies
 */
public interface IPatientEmu {
    /**
     * Set parameters of the virtual patient
     * @param param the param name
     * @param value the value of the parameter to set
     * @return true, if parameter exists, false otherwise.
     */
    boolean setParam(String param, String value);
}
