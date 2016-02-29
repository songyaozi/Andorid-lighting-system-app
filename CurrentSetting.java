package yao.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Handler;

public class CurrentSetting extends AppCompatActivity {
    SeekBar seekBar1, seekBar2, seekBar3;
    TextView seekbar_message1, seekbar_message2, seekbar_message3,
                            loggedin_name, loggedin_username,welcome;
    UserLocalStore userLocalStore;
    int progress_value1, progress_value2, progress_value3,LED1_progress,LED2_progress;
    Button button_logout, button_save;
    //DatabasesHandler db = new DatabasesHandler(this);
    final Context context = this;
    //private EditText result;
    Intent intent_save,pass_value_to_history,pass_value_to_save;
    String LED_user,LED1String,LED2String;
    LEDLocalStore ledLocalStore;
    String setting_name,set_setting_name;
    Integer LED1_value,LED2_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras1 = getIntent().getExtras();
        Bundle extras2 = getIntent().getExtras();

        userLocalStore = new UserLocalStore(this);
        ledLocalStore = new LEDLocalStore(this);
        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loggedin_name = (TextView) findViewById(R.id.textView_loggedin_name);
        loggedin_username = (TextView) findViewById(R.id.textView_loggedin_username);

        seekBar1 = (SeekBar) findViewById(R.id.seekBar_LED1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar_LED2);
        seekbar_message1 = (TextView) findViewById(R.id.seekbar_message1);
        seekbar_message2 = (TextView) findViewById(R.id.seekbar_message2);
        button_logout = (Button) findViewById(R.id.button_logout);

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent_logout = new Intent(CurrentSetting.this, Login.class);
                //startActivity(intent_logout);
                //finish();

                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                onBackPressed();
                finish();
            }
        });

        LED_user = userLocalStore.getLoggedInUser().username;

        button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







                Log.d("Check", "try to add LED value");


                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);


                //LED1_value = progress_value1;
                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        //result.setText(userInput.getText());
                        setting_name = userInput.getText().toString();
                        intent_save = new Intent(getApplicationContext(), SavedSetting.class);
                        intent_save.putExtra("LED1", Integer.toString(progress_value1));
                        intent_save.putExtra("LED2", Integer.toString(progress_value2));
                        //intent_save.putExtra("LED3", Integer.toString(progress_value3));
                        intent_save.putExtra("name", setting_name);
                        Log.d("check", "saved setting finish");

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CurrentSetting.this);
                        dialogBuilder.setMessage("Setting saved");
                        dialogBuilder.show();

                        LED saved_LED_setting= new LED(LED_user,setting_name,progress_value1,progress_value2);
                        save_setting(saved_LED_setting);

                        //startActivity(intent_save);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                Log.d("Check", "start save setting");

            }
        });

        welcome = (TextView)findViewById(R.id.textView_welcome);


        if (extras1 != null){
            LED1_progress = Integer.parseInt(extras1.getString("LED1"));
            LED2_progress = Integer.parseInt(extras1.getString("LED2"));
            set_setting_name = extras1.getString("name");
            Log.d("check","set setting name is "+set_setting_name);
            Log.d("check", "set LEd1 is " + LED1_progress);
            Log.d("check", "set LED2 is " + LED2_progress);

            welcome.setText("This is your saved setting: " + set_setting_name.toString());
            Log.d("check", "welcome text set");

            //seekBar1 = (SeekBar) findViewById(R.id.seekBar_LED1);
            seekBar1.setKeyProgressIncrement(1);
            seekBar1.setMax(100);
            Log.d("check", "seekbar1 progress set start");
            seekBar1.setProgress(LED1_progress);
            Log.d("check", "seekbar1 progress set");
            //seekbar_message1.setText("Brightness1 " + Integer.toString(LED1_progress) + "/" + seekBar1.getMax());
            Log.d("check", "LED1 progress set");

            seekBar2.setKeyProgressIncrement(1);
            seekBar2.setMax(100);
            seekBar2.setProgress(LED2_progress);
            Log.d("check", "seekbar1 progress set");
            //seekbar_message2.setText("Brightness2 " + Integer.toString(LED2_progress) + "/" + seekBar2.getMax());
            Log.d("check", "LED2 progress set");

        }
        Log.d("Check", "Extras1 = null!");


        /**if (extras2 != null){
            LED1_progress = Integer.parseInt(extras2.getString("value1_from_history"));
            LED2_progress = Integer.parseInt(extras2.getString("value2_from_history"));
            //set_setting_name = extras2.getString("name");
            //Log.d("check","set setting name is "+set_setting_name);
            Log.d("check", "set LEd1 is " + LED1_progress);
            Log.d("check", "set LED2 is " + LED2_progress);

            //welcome.setText("This is your saved setting: " + set_setting_name.toString());
            //Log.d("check", "welcome text set");

            //seekBar1 = (SeekBar) findViewById(R.id.seekBar_LED1);
            seekBar1.setKeyProgressIncrement(1);
            seekBar1.setMax(100);
            Log.d("check", "seekbar1 progress set start");
            seekBar1.setProgress(LED1_progress);
            Log.d("check", "seekbar1 progress set");
            //seekbar_message1.setText("Brightness1 " + Integer.toString(LED1_progress) + "/" + seekBar1.getMax());
            Log.d("check", "LED1 progress set");

            seekBar2.setKeyProgressIncrement(1);
            seekBar2.setMax(100);
            seekBar2.setProgress(LED2_progress);
            Log.d("check", "seekbar1 progress set");
            //seekbar_message2.setText("Brightness2 " + Integer.toString(LED2_progress) + "/" + seekBar2.getMax());
            Log.d("check", "LED2 progress set");

        }
        Log.d("Check", "extras2 = null!");*/



        seekbar_adjust1();
        seekbar_adjust2();
        //seekbar_adjust3();


        final Button updateLED = (Button) findViewById(R.id.button_update);
        updateLED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                int LED1_value = seekBar1.getProgress();
                int LED2_value = seekBar2.getProgress();
                String LED_user = userLocalStore.getLoggedInUser().username;
                Log.d("check","LED_user= "+LED_user);
                //int LED3_value = seekBar3.getProgress();

                LED led = new LED(LED1_value, LED2_value,LED_user);
                UpdateLED(led);

            }
        });

    }


    private void save_setting(LED led){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeSettingInBackground(led, new GetLEDCallback() {
            @Override
            public void done(LED returnedLED) {
                startActivity(intent_save);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {
            displayUserDetails();
        } else {
            onBackPressed();
        }
    }

    private boolean authenticate() {
        return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        //loggedin_name.setText(user.name);
        loggedin_username.setText(user.username);
    }

    public void seekbar_adjust1() {
        //seekBar1 = (SeekBar) findViewById(R.id.seekBar_LED1);
        seekBar1.setKeyProgressIncrement(1);
        seekBar1.setMax(100);

        seekbar_message1.setText("Brightness" + seekBar1.getProgress() + " / " + seekBar1.getMax());

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value1 = progress;
                seekbar_message1.setText("Brightness" + progress + " / " + seekBar.getMax()+"   Adjusting...");
                //Toast.makeText(CurrentSetting.this, "Adjusting LED1", Toast.LENGTH_SHORT).show();
                //Toast.makeText(CurrentSetting.this, "Adjusting LED1", Toast.LENGTH_SHORT).cancel();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekbar_message1.setText("Brightness" + progress_value1 + " / " + seekBar.getMax()+"   Start...");
                //Toast.makeText(CurrentSetting.this, "Start adjusting LED1", Toast.LENGTH_SHORT).show();

                //Toast.makeText(CurrentSetting.this, "Start adjusting LED1", Toast.LENGTH_SHORT).cancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbar_message1.setText("Brightness" + progress_value1 + " / " + seekBar.getMax()+"   Stop...");
                //Toast.makeText(CurrentSetting.this, "Stop adjusting LED1", Toast.LENGTH_LONG).show();
                //Toast.makeText(CurrentSetting.this, "Stop adjusting LED1", Toast.LENGTH_SHORT).cancel();
            }
        });
    }

    public void seekbar_adjust2() {
        //seekBar2 = (SeekBar) findViewById(R.id.seekBar_LED2);
        seekBar2.setKeyProgressIncrement(1);
        seekBar2.setMax(100);

        seekbar_message2.setText("Brightness" + seekBar2.getProgress() + " / " + seekBar2.getMax());

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value2 = progress;
                seekbar_message2.setText("Brightness" + progress + " / " + seekBar.getMax()+"   Adjusting...");
                //Toast.makeText(CurrentSetting.this, "Adjusting LED2", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekbar_message2.setText("Brightness" + progress_value2 + " / " + seekBar.getMax()+"   Start...");
                //Toast.makeText(CurrentSetting.this, "Start adjusting LED2", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekbar_message2.setText("Brightness" + progress_value2 + " / " + seekBar.getMax()+"   Stop...");
                //Toast.makeText(CurrentSetting.this, "Stop adjusting LED2", Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_usage_history) {
            Intent intent_usage_history = new Intent(this, UsageHistory.class);
            //pass_value_to_history = new Intent(getApplicationContext(), UsageHistory.class);
            //pass_value_to_history.putExtra("LED1", Integer.toString(progress_value1));
            //pass_value_to_history.putExtra("LED2", Integer.toString(progress_value2));
            //intent_save.putExtra("LED3", Integer.toString(progress_value3));
            //pass_value_to_history.putExtra("name", setting_name);
           // Log.d("check", "pass LED to history finish");

            startActivity(intent_usage_history);
            finish();
            return true;
        }

        if (id == R.id.menu_saved_setting) {
            Intent intent_saved_setting = new Intent(this, LoadSetting.class);
            //LED led = new LED(setting_name,LED1String,LED2String);
            //getSetting(led);
            pass_value_to_save = new Intent(getApplicationContext(), SavedSetting.class);
            pass_value_to_save.putExtra("LED1", Integer.toString(progress_value1));
            pass_value_to_save.putExtra("LED2", Integer.toString(progress_value2));
            //intent_save.putExtra("LED3", Integer.toString(progress_value3));
            //pass_value_to_history.putExtra("name", setting_name);
            Log.d("check", "pass LED to history finish");



            Log.d("check","go to load activity");
                startActivity(intent_saved_setting);
            finish();
            return true;
        }


        if (id == R.id.menu_bill) {
            Intent intent_other_settings = new Intent(this, Bill.class);
            startActivity(intent_other_settings);
            finish();
            return true;
        }
        if (id == R.id.menu_alarm) {
            Intent intent_alarm = new Intent(this, Alarm.class);
            startActivity(intent_alarm);
            finish();
            return true;
        }
        if (id == R.id.menu_weather) {
            Intent intent_weather = new Intent(this, Weather.class);
            startActivity(intent_weather);
            //finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void UpdateLED(LED led) {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeLEDDataInBackground(led, new GetLEDCallback() {
            @Override
            public void done(LED returnedLED) {

                Toast.makeText(CurrentSetting.this, "Setting updated", Toast.LENGTH_LONG).show();


            }
        });
    }
}