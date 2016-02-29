package yao.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

public class SavedSetting extends AppCompatActivity {

    SeekBar seekBar_LED1, seekBar_LED2;
    TextView seekBar_message1,seekBar_message2,saved_setting;
    String saved_setting_name;
    int LED1_progress,LED2_progress;
    //Get the bundle
    DatabasesHandler db =new DatabasesHandler(this);
    List<LED> led;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();

        seekBar_LED1 = (SeekBar)findViewById(R.id.seekBar_LED1);
        seekBar_LED2 = (SeekBar)findViewById(R.id.seekBar_LED2);
        ;

        seekBar_message1 = (TextView)findViewById(R.id.seekbar_message1);
        seekBar_message2 = (TextView)findViewById(R.id.seekbar_message2);
        //seekBar_message3 = (TextView)findViewById(R.id.seekbar_message3);
        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saved_setting = (TextView)findViewById(R.id.saved_setting);

        Button button_back_to_current = (Button)findViewById(R.id.button_back_saved);
        button_back_to_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Log.d("Check", "onBackPressed");

        if (extras != null){
            LED1_progress = Integer.parseInt(extras.getString("LED1"));
            LED2_progress = Integer.parseInt(extras.getString("LED2"));
            saved_setting_name = extras.getString("name");
            Log.d("check","name is "+saved_setting_name);
        }
        Log.d("Check","get LED progress");


            saved_setting.setText(saved_setting_name);

            seekBar_LED1.setKeyProgressIncrement(1);
            seekBar_LED1.setMax(100);
            seekBar_LED1.setProgress(LED1_progress);
            seekBar_message1.setText("LED1 " + Integer.toString(LED1_progress) + "/" + seekBar_LED1.getMax());
            Log.d("check", "LED1 progress set");

            seekBar_LED2.setKeyProgressIncrement(1);
            seekBar_LED2.setMax(100);
            seekBar_LED2.setProgress(LED2_progress);
            seekBar_message2.setText("LED2 " + Integer.toString(LED2_progress) + "/" + seekBar_LED2.getMax());
            Log.d("check", "LED2 progress set");


            Log.d("Reading: ", "Reading all contacts..");


    }
}
