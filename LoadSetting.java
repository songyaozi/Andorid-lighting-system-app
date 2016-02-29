package yao.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadSetting extends AppCompatActivity {
    String LED_user_local,LED1String,LED2String,setting_name,LED_user_load;
    int LED1_load_value,LED2_load_value;
    Button load_setting,set_setting,button_to_current_setting;
    EditText Which_setting;
    LEDLocalStore ledLocalStore;
    UserLocalStore userLocalStore;
    ArrayList<String>arrayList;
    ArrayList<Integer>LEDList1,LEDList2,IDList;
    ArrayAdapter<String>arrayAdapter;
    ListView listView_in_load;
    TextView loaded_setting,LEDvalueshow,setting_name_show,LED1_show,LED2_show;
    Intent intent_set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);
        ledLocalStore = new LEDLocalStore(this);

        loaded_setting = (TextView)findViewById(R.id.textView_loaded_setting);
        LEDvalueshow = (TextView)findViewById(R.id.textView_LEDvalue_show);

        Which_setting = (EditText)findViewById(R.id.editText_which_setting);
        set_setting = (Button)findViewById(R.id.button_set);

        /**button_to_current_setting = (Button)findViewById(R.id.button_back_in_load_setting);
        button_to_current_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "go back", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent go_to_current = new Intent(LoadSetting.this,CurrentSetting.class);
                startActivity(go_to_current);
                finish();
            }
        });

        load_setting = (Button)findViewById(R.id.button_load);
        load_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LED_user_local = userLocalStore.getLoggedInUser().username;
                LED led = new LED(LED_user_local);
                getSetting(led);
                /**try{
                 Log.d("check","new button start to create");
                 myButton = new Button(LoadSetting.this);
                 Log.d("check", "new button created");
                 myButton.setText("Add Me");
                 Log.d("check", "new layout start to create");
                 LinearLayout ll = (LinearLayout)findViewById(R.id.buttonlayout);
                 Log.d("check","new layoutparams start to create");
                 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(40,40);
                 lp.setMargins(5,3,0,0);
                 myButton.setLayoutParams(lp);
                 Log.d("check", "new button added to view");
                 ll.addView(myButton);


                 }
                 catch(Exception e){
                 e.getStackTrace();
                 }*/

                Log.d("Check", "start to store local LED data");
                ledLocalStore.storedLEDData(led);
                Log.d("check", "storedLEDData = " + led);
                ledLocalStore.setLEDValueGot(true);
            }
        });

        /**Log.d("check", "start to find listview id ");
        listView_in_load= (ListView)findViewById(R.id.listView_saved_setting);

        String[] saved_setting_name = {};
        //Integer[] integers1 = {};
        //Integer[] integers2 = {};
        //Integer[] id = {};
        //String [] LEDListitem = {};
        arrayList = new ArrayList<>(Arrays.asList(saved_setting_name));
        //LEDList = new ArrayList<>(Arrays.asList(LEDListitem));
        //LEDList1 = new ArrayList<>(Arrays.asList(integers1));
        //LEDList2 = new ArrayList<>(Arrays.asList(integers2));
        //IDList = new ArrayList<>(Arrays.asList(id));

        Log.d("check","start to define adapter ");

        arrayAdapter= new ArrayAdapter<String>(this, R.layout.load_listview,R.id.item_in_load, arrayList);
        Log.d("check","start to set adapter");

        listView_in_load.setAdapter(arrayAdapter);
        Log.d("check", "adapter set ");*/

         //set_setting = (Button)findViewById(R.id.button_set_setting);
         set_setting.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String id_typed = Which_setting.getText().toString();
                 try {
                     Integer.parseInt(id_typed);

                     applySetting();
                 } catch (Exception e) {
                     AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoadSetting.this);
                     dialogBuilder.setMessage("Please first load settings");
                     dialogBuilder.setPositiveButton("OK", null);
                     dialogBuilder.show();
                 }

             }
         });


    }

    public void applySetting(){
        String delims = "[\\r?\\n\\t]+";
        //String delims = ""
        String[] LED1tokens = LED1String.split(delims);
        //String[] LED2tokens = LED2String.split(delims);
        String[] Setting_name_tokens = setting_name.split(delims);
        Log.d("check","LED1,LED2,Setting name are "+LED1tokens[0]+" "+Setting_name_tokens[0]);

        String LED1load=null,LED2load=null,settingname_load=null;
        LED led = new LED(settingname_load,LED1load,LED2load);
        //Integer id_int = null;
        String id_typed = Which_setting.getText().toString();

            Log.d("check", "id_typed is " + id_typed);
            for (int i = 0; i < Setting_name_tokens.length; i = i + 2) {
                Log.d("check", "ids are " + Setting_name_tokens[i]);
                if (Integer.parseInt(id_typed) == Integer.parseInt(Setting_name_tokens[i])) {
                    Log.d("check", "id_typed match");
                    intent_set = new Intent(getApplicationContext(), CurrentSetting.class);
                    intent_set.putExtra("LED1", LED1tokens[i]);
                    Log.d("check", "LED1 putExtra is " + LED1tokens[i]);
                    intent_set.putExtra("LED2", LED1tokens[i + 1]);
                    Log.d("check", "LED2 putExtra is " + LED1tokens[i + 1]);
                    //intent_save.putExtra("LED3", Integer.toString(progress_value3));
                    intent_set.putExtra("name", Setting_name_tokens[i + 1]);
                    Log.d("check", "name putExtra is " + Setting_name_tokens[i + 1]);

                    LED1load = LED1tokens[i].toString();
                    Log.d("check", "LED1load is " + LED1load);
                    LED2load = LED1tokens[i + 1].toString();
                    Log.d("check", "LED2load is " + LED2load);

                    settingname_load = Setting_name_tokens[i + 1].toString();
                    led = new LED(settingname_load, LED1load, LED2load);

                    Log.d("check", "saved setting finish");
                }


        }



        send_Saved_Setting_to_Server(led);

        //Intent go_to_current_setting = new Intent(LoadSetting.this,CurrentSetting.class);
        //startActivity(go_to_current_setting);

    }

    private void send_Saved_Setting_to_Server(LED led){
        ServerRequests serverRequests = new ServerRequests(this);
        Log.d("Check", "start to send saved setting");

        serverRequests.StoreSavedSettingDataInBackground(led, new GetLEDCallback() {
            @Override

            public void done(LED returnedLED) {
                    startActivity(intent_set);
                    finish();
            }
        });
        //return LED1value;
    }

    private void showErroMessage1() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoadSetting.this);
        dialogBuilder.setMessage("Set saved setting error");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void getSetting(LED led){
        ServerRequests serverRequests = new ServerRequests(this);
        Log.d("Check", "start to fetch setting");

        serverRequests.LoadSettingDataInBackground(led, new GetLEDCallback() {
            @Override

            public void done(LED returnedLED) {

                if (returnedLED == null) {
                    showErroMessage();
                } else {
                    setLEDValue(returnedLED);


                }
            }
        });
        //return LED1value;
    }

    private void showErroMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoadSetting.this);
        dialogBuilder.setMessage("Get setting error");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void setLEDValue(LED returnedLED){
        ledLocalStore.storedLEDData(returnedLED);
        ledLocalStore.setLEDValueGot(true);
        Log.d("Check", "start to get setting");


        //return saved setting details
        setting_name = returnedLED.LEDList;
        LED1String = returnedLED.LED1List;

        //LED1String = returnedLED.LED1_value_string;
        //LED2String = returnedLED.LED2_value_string;


        loaded_setting.setText(setting_name);
        String delims = "[\\r?\\n\\t]+";
        //String delims = ""
        //String[] LED2tokens = LED2String.split(delims);
        String[] Setting_name_tokens = setting_name.split(delims);
        LEDvalueshow.setText(LED1String);

        if(Setting_name_tokens.length == 10){
            Context context = getApplicationContext();
            CharSequence text = "Maximum 5 settings can show";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
        //LED1_show.setText(LED1String);
        //LED2_show.setText(LED2String);
        //setting_name_show.setText(setting_name);
        //arrayList.add(setting_name);
        //arrayList.add("LED2: "+Integer.toString(LED2value));
        //LEDList1.add(LED1value);
        //LEDList2.add(LED2value);
        //arrayAdapter.notifyDataSetChanged();

        //count = LED1tokens.length/2;
        //textView_history.setText("You get " + Integer.toString(count) + " usage history");
        //count = count + 1;
        Log.d("Check", "got setting");

    }



}
