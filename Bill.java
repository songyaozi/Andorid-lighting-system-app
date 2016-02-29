package yao.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class Bill extends AppCompatActivity {
    LEDLocalStore ledLocalStore;
    UserLocalStore userLocalStore;
    Button get_consumption,get_savings,back;
    String LED_user_local,idle,motor,light;
    Float idle_float,motor_float,light_float,consumption_float;
    TextView show_consumption, show_motor,show_light;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        show_consumption = (TextView)findViewById(R.id.total_consumed);
        show_motor = (TextView)findViewById(R.id.show_motor);
        show_light = (TextView)findViewById(R.id.show_light);

        get_consumption = (Button)findViewById(R.id.button_get_consumption);
        get_savings = (Button)findViewById(R.id.button_get_savings);
        /**back = (Button)findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(Bill.this,CurrentSetting.class);
                startActivity(intent);
                finish();
            }
        });


        userLocalStore = new UserLocalStore(this);
        ledLocalStore = new LEDLocalStore(this);
        get_consumption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LED_user_local = userLocalStore.getLoggedInUser().username;
                LED all_units = new LED(LED_user_local);

                getConsumption(all_units);
            }
        });

       get_savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent piintent = getInent(Bill.this);
                    startActivity(piintent);
                    //openChart();
                } catch (Exception e) {
                    e.getStackTrace();
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Bill.this);
                    dialogBuilder.setMessage("Please first get consumption details");
                    dialogBuilder.setPositiveButton("OK", null);
                    dialogBuilder.show();
                }
            }
        });
    }


    private void getConsumption(LED all_units) {
        ServerRequests serverRequests = new ServerRequests(this);
        Log.d("Check", "start to fetch LED data");

        serverRequests.GetConsumptionDataInBackground(all_units, new GetLEDCallback() {
            @Override

            public void done(LED returnedLED) {

                if (returnedLED == null) {
                    showErroMessage();
                } else {
                    setAllUnits(returnedLED);


                }
            }
        });
        //return LED1value;
    }
    private void showErroMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Bill.this);
        dialogBuilder.setMessage("Get units values error, check the connection");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void setAllUnits(LED returnedLED) {
        ledLocalStore.storedLEDData(returnedLED);
        ledLocalStore.setLEDValueGot(true);


        idle = returnedLED.Saved_setting_name;
        motor = returnedLED.LED1_value_string;
        light = returnedLED.LED2_value_string;

        consumption_float = Float.valueOf(motor) + Float.valueOf(light);
        //show_consumption.setText(consumption);
        show_motor.setText(motor);
        show_light.setText(light);
        show_consumption.setText(Float.toString(consumption_float));
    }



    public Intent getInent(Context context) {
        //protected void openChart(){

        idle_float = Float.valueOf(idle);
        motor_float = Float.valueOf(motor);
        light_float = Float.valueOf(light);


        // Pie Chart Section Names
        String[] code = new String[] {
                "Savings", "Motor", "Light"
        };

        // Pie Chart Section Value
        double[] distribution = {idle_float, motor_float,light_float} ;

        // Color of each Pie Chart Sections
        int[] colors = { Color.YELLOW, Color.MAGENTA, Color.GREEN};

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
        for(int i=0 ;i < distribution.length;i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        for(int i = 0 ;i<distribution.length;i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            seriesRenderer.setDisplayBoundingPoints(true);
            seriesRenderer.setChartValuesTextSize(50);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("Chart of the consumption proportion");
        defaultRenderer.setChartTitleTextSize(40);
        //defaultRenderer.setZoomButtonsVisible(true);
        defaultRenderer.setBackgroundColor(Color.BLACK);
        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setLabelsTextSize(20);
        defaultRenderer.setLabelsColor(Color.CYAN);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setStartAngle(90);
        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");

        // Start Activity
        return intent;
    }


}
