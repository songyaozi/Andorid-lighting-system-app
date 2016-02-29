package yao.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class Alarm extends Activity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static Alarm inst;
    private TextView alarmTextView;
    String time;

    public static Alarm instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_alarm);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        Log.d("check","checkpoint0");

        //ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        Button back = (Button)findViewById(R.id.button_back_in_alarm);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Alarm.this,CurrentSetting.class);
                startActivity(intent);
                finish();

            }
        });
        Log.d("check","checkpoint1");
        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "go back", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/
        Log.d("check", "checkpoint2");

        Button set_alarm = (Button)findViewById(R.id.button_set_alarm);
        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Check", "Alarm On");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                Log.d("check", "hour  is " + alarmTimePicker.getCurrentHour());
                // Log.d("check", "calendar date is " + Calendar.HOUR_OF_DAY);
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                Log.d("check", "minute is " + alarmTimePicker.getCurrentMinute());
                Log.d("check", "calendar is " + Calendar.MINUTE);
                //Log.d("check","calendar year is "+Calendar.YEAR);
                Log.d("check", "calendar date is " + "2015-" + Calendar.MINUTE + "-" + Calendar.HOUR_OF_DAY + " " + alarmTimePicker.getCurrentHour() + ":" + alarmTimePicker.getCurrentMinute());
                /**if (Integer.parseInt(Calendar.HOUR_OF_DAY)<10&&Calendar.MINUTE>=10){
                    time = "2015-" + Calendar.MINUTE + "-0" + Calendar.HOUR_OF_DAY + " " + alarmTimePicker.getCurrentHour() + ":" + alarmTimePicker.getCurrentMinute();
                }
                else if(Calendar.MINUTE<10&&Calendar.HOUR_OF_DAY>=10){
                    time = "2015-0" + Calendar.MINUTE + "-" + Calendar.HOUR_OF_DAY + " " + alarmTimePicker.getCurrentHour() + ":" + alarmTimePicker.getCurrentMinute();
                }
                else if(Calendar.MINUTE<10&&Calendar.HOUR_OF_DAY<10){*/
                    time=alarmTimePicker.getCurrentHour() + ":" + alarmTimePicker.getCurrentMinute();
                //}

               // time = "2015-" + Calendar.MINUTE + "-" + Calendar.HOUR_OF_DAY + " " + alarmTimePicker.getCurrentHour() + ":" + alarmTimePicker.getCurrentMinute();

                              Log.d("check", "time is " + time);
                Context context = getApplicationContext();
                CharSequence text = "Alarm set: " + alarmTimePicker.getCurrentHour() + ":" + alarmTimePicker.getCurrentMinute();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                LED alarm = new LED(time);
                postAlarm(alarm);

                Intent myIntent = new Intent(Alarm.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, myIntent, 0);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                Log.d("check", "alarm is " + calendar.getTimeInMillis());
            }
        });
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private void postAlarm(LED alarm){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeAlarmDataInBackground(alarm, new GetLEDCallback() {
            @Override
            public void done(LED returnedLED) {

                //startActivity(new Intent(Register.this, Login.class));

            }
        });
    }

   /** public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("Check", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            Log.d("check", "hour  is " + alarmTimePicker.getCurrentHour());
           // Log.d("check", "calendar date is " + Calendar.HOUR_OF_DAY);
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Log.d("check", "minute is " + alarmTimePicker.getCurrentMinute());
            Log.d("check","calendar is "+Calendar.MINUTE);
            //Log.d("check","calendar year is "+Calendar.YEAR);
            Log.d("check","calendar date is "+"2015-"+Calendar.MINUTE+"-"+Calendar.HOUR_OF_DAY+" "+alarmTimePicker.getCurrentHour()+":"+alarmTimePicker.getCurrentMinute());
            time = "2015-"+Calendar.MINUTE+"-"+Calendar.HOUR_OF_DAY+" "+alarmTimePicker.getCurrentHour()+":"+alarmTimePicker.getCurrentMinute();
            Log.d("check","time is "+time);
            Intent myIntent = new Intent(Alarm.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
            Log.d("check","alarm is "+calendar.getTimeInMillis());
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("Check", "Alarm Off");
        }
    }*/

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }
}