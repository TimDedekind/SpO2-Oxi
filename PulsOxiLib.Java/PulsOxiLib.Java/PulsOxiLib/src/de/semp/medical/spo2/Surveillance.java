package de.semp.medical.spo2;

public class Surveillance implements ISpO2Observer {
    int LowerSpO2Border;
    int UpperPulseBorder;
    int LowerPulseBorder;
    static public GUI_Controler guiControler;
    // wird threshold geprüft
    // dann wenn auserhalb norm schickt an Alarm
    // Wenn wieder unterhalb dann stoppt Alarm



    // Singleton Pattern
    private static Surveillance instance = new Surveillance();
    private Surveillance() { }
    public static Surveillance getInstance( GUI_Controler gui ){
        guiControler = gui;
        return instance;
    }




    public void setAlarmSP02(int SP02Threeshold) {
        LowerSpO2Border = SP02Threeshold;
    }

    public void setAlarmPulseLower(int lowerPulseThreeshold) {
        LowerPulseBorder = lowerPulseThreeshold;
    }

    public void setAlarmPulseUpper(int upperPulseTrhesshold) {
        UpperPulseBorder = upperPulseTrhesshold;
    }

    @Override
    public void onNewPulse(int pulse, int spo2) {



        if ( pulse >= UpperPulseBorder || pulse <= LowerPulseBorder || spo2 <= LowerSpO2Border ) {
            Alarm alarm = new Alarm( guiControler );
            alarm.issueAlarm();
        }
        else
        {
            Alarm alarm = new Alarm( guiControler );
            alarm.stopAlarm();
        }
        // TODO schlauer Alarmalgorithmus ( ausreißerwerte usw.?)
        //Alarm sol nicht auslösen
    }

    @Override
    public void onErrorState(ErrorState oxiError) {
        //TODO keine Ahnung was hier passieren soll
    }
}
