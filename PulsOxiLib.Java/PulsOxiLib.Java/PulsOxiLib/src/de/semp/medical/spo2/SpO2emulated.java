/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.semp.medical.spo2;

import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A virtual sensor which emulates sensor data from a virtual patient.
 * Parameters of the patient can be modified. The actual values are simulated
 * using a random process. The variance depends on the parameters of the
 * simulated patient.
 *
 * @author dubies
 */
public class SpO2emulated extends SpO2Sensor implements IPatientEmu {
    // initial values
    private int spLow = 94;
    private int spHigh = 98;
    private int hrMittel = 66;

    private int interval;
    private boolean checkError = false;

    private Random rand = new Random();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (run) {
                try {
                    notifyObservers();
                    variance();                   
                    interval = 60000 / hr;
                    Thread.sleep(interval);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void variance() {
            int var = hr - hrMittel;
            if (Math.abs(var) > 10) {
                if (var > 0) {
                    hr -= rand.nextInt(var);
                } else {
                    hr += rand.nextInt(-var);
                }
            } else {
                hr += rand.nextInt(5) - 2;
            }

            if (var % 2 == 0 && Math.abs(var) > 3) {
                int sp_soll = (spLow + spHigh) / 2;
                if (spo2 > sp_soll) {
                    spo2 -= 1;
                } else //if (spo2 < spHigh) {
                {
                    spo2 += 1;
                }
            }
        }

        private int count = 0;
        private int index = 1;

        private void switchError() {
            if (count++ % 10 == 0) {
                notifyStateChange(ISpO2Observer.ErrorState.values()[index++]);
                if (index > ISpO2Observer.ErrorState.values().length) {
                    index = 1;
                }
            } else {
                notifyStateChange(ISpO2Observer.ErrorState.OK);
            }
        }
    };

   // Thread thread = new Thread(runnable);

    public SpO2emulated() {
        hr = hrMittel;
        spo2 = spLow + (spHigh - spLow) / 2;
    }

    @Override
    public void start() {
        run = true;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void stop() {
        run = false;
    }

    /**
     * Set parameters of the emulated patient.
     *
     * @param param the name of the parameter to be set. Available parameters are: 
     * 
     * BPM: simulated mean heart rate of the patient 
     * SpO2: simulated mean spo2 value of the patient norm: normal simulated values low: low
     * simulated values high: high simulated values
     * e.g. if(emu.setParam("BPM", "high"))
                            System.out.println("set BPM to high!");
                        else
                            System.out.println("can't set BPM to high!");
     * 
     * ErrorCheck: simulates Error/State-Changes. e.g. setParam("ErrorCheck", ISpO2Observer.ErrorState.LowPerfusion.toString());
     * 
     * @param value the value to be set for the given parameter.
     * @return true, if parameter exists, false otherwise.
     */
    @Override
    public boolean setParam(String param, String value) {
        final String bpm = "BPM";
        final String spo2 = "SpO2";
        final String error = "ErrorCheck";
        final String vNorm = "norm";
        final String vLow = "low";
        final String vHigh = "high";
       

        boolean res = false;

        if (param.equalsIgnoreCase(bpm)) {
            if (value.equalsIgnoreCase(vHigh)) {
                hrMittel = 150;
                res = true;
            } else if (value.equalsIgnoreCase(vLow)) {
                hrMittel = 40;
                res = true;
            } else {
                hrMittel = 66;
                res = true;
            }
        } else if (param.equalsIgnoreCase(spo2)) {
            if (value.equalsIgnoreCase(vHigh)) {
                spHigh = 100;
                this.spo2 = 99;
                spLow = 98;
                res = true;
            } else if (value.equalsIgnoreCase(vLow)) {
                spHigh = 90;
                this.spo2 = 85;
                spLow = 80;
                res = true;
            } else {
                spHigh = 99;
                this.spo2 = 97;
                spLow = 94;
                res = true;
            }
        } else if (param.equalsIgnoreCase(error)) {
            notifyStateChange(ISpO2Observer.ErrorState.valueOf(value));            
            res = true;
        }
        return res;
    }
}
