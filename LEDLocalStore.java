package yao.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yao on 11/7/15.
 */
public class LEDLocalStore {
    public static final String SP_NAME = "ledDetails";
    SharedPreferences ledLocalDatabases;

    public LEDLocalStore(Context context){
        ledLocalDatabases = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storedLEDData(LED led){
        SharedPreferences.Editor spEditor = ledLocalDatabases.edit();
        //spEditor.putString("LED", Integer.toString(led.LED1_value));
        //spEditor.putString("LED2", Integer.toString(led.LED2_value));
        //spEditor.putString("name", led.LED_user);
        spEditor.putString("LED_string",led.LED_user);
        spEditor.putString("LED_string",Integer.toString(led.usage_history));
        spEditor.commit();
    }

    //get an already stored LED's data
    public LED getStoredLED(){

        String LED1value = ledLocalDatabases.getString("LED1","");
        String LED2value = ledLocalDatabases.getString("LED2", "");
        String LEDusername = ledLocalDatabases.getString("username", "");

        LED storedLED = new LED(Integer.parseInt(LED1value), Integer.parseInt(LED2value),LEDusername);
        return storedLED;
    }

    //set a LED Got boolean
    public void setLEDValueGot(boolean setLEDvalue){
        SharedPreferences.Editor spEditor = ledLocalDatabases.edit();
        spEditor.putBoolean("setLEDvalue", setLEDvalue);
        spEditor.commit();

    }

    public boolean getLEDValue(){
        if (ledLocalDatabases.getBoolean("setLEDvalue", false)==true) {
            return true;
        }
        else{
            return false;
        }
    }
    public void clearLEDData(){
        SharedPreferences.Editor spEditor = ledLocalDatabases.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
