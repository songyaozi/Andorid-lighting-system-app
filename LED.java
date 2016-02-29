package yao.myapplication;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yao on 11/17/15.
 */



public class LED {
    int LED1_value, LED2_value,usage_history;
    String LED_user,LEDList,LED1List,Saved_setting_name,LED1_value_string,LED2_value_string;
    //two way of creating a new user, the first way is given his name, username and password
    public LED(int LED1_value, int LED2_value, String LED_user){
        this.LED1_value = LED1_value;
        this.LED2_value = LED2_value;
        this.LED_user = LED_user;
    }
    public LED(int LED1_value,int LED2_value){
        this.LED1_value = LED1_value;
        this.LED2_value = LED2_value;
    }
    public LED(String LED_user,int usage_history){
        this.LED_user = LED_user;
        this.usage_history = usage_history;
    }
    public LED(String LEDList,String LED1List)
    {
        this.LEDList = LEDList;
        this.LED1List = LED1List;
    }

    public LED(String LED_user){
        this.LED_user = LED_user;
    }
    public LED( String LED_user,String Saved_setting_name,int LED1_value, int LED2_value){
        this.LED1_value = LED1_value;
        this.LED2_value = LED2_value;
        this.LED_user = LED_user;
        this.Saved_setting_name = Saved_setting_name;
    }

    public LED( String Saved_setting_name,String LED1_value, String LED2_value){
        this.LED1_value_string = LED1_value;
        this.LED2_value_string = LED2_value;
        //this.LED_user = LED_user;
        this.Saved_setting_name = Saved_setting_name;
    }

}
/**public class LED {
 int LED_ID, LED1_value,LED2_value,LED3_value;
 public LED(){}

 public LED(int id, int LED1_value,int LED2_value,int LED3_value){
 this.LED_ID = id;
 this.LED1_value = LED1_value;
 this.LED2_value = LED2_value;
 this.LED3_value = LED3_value;
 }
 public LED( int LED1_value,int LED2_value,int LED3_value) {

 this.LED1_value = LED1_value;
 this.LED2_value = LED2_value;
 this.LED3_value = LED3_value;
 }
 public int getLED_ID(){
 return this.LED_ID;
 }
 public void setLED_ID(int id){
 this.LED_ID = id;
 }
 public int getLED1_value(){
 return this.LED1_value;
 }
 public void setLED1_value(int value1){
 this.LED1_value = value1;
 }
 public int getLED2_value(){
 return this.LED2_value;
 }
 public void setLED2_value(int value2){
 this.LED2_value = value2;
 }
 public int getLED3_value(){
 return this.LED1_value;
 }
 public void setLED3_value(int value3){
 this.LED3_value = value3;
 }
 }*/