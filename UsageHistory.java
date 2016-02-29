package yao.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsageHistory extends AppCompatActivity {

    int LED1value, LED2value;
    TextView LED1value_show, LED2value_show, textView_history,LED_string_show;
    EditText how_many_usage_history;
    //ListView listView;
    LEDLocalStore ledLocalStore;
    UserLocalStore userLocalStore;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;


    ArrayList<Integer> LEDList1, LEDList2;
    Button button_back_to_current, button_get_chart;
    int count = 1, LEDInteger;
    String LEDString;
    String LED1String;
    String delims;
    String how_many_history;
    int how_many_history_int,LED1_progress,LED2_progress;
    String[] LEDtokens, LED1tokens;
    int[] LEDintegershow;
    StringBuilder LED_string = new StringBuilder();

    //Bundle extras = getIntent().getExtras();
    //Intent pass_value_to_current;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**button_back_to_current = (Button) findViewById(R.id.button_back_usage);
        button_back_to_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent go_to_current = new Intent(UsageHistory.this, CurrentSetting.class);

                //pass_value_to_current = new Intent(getApplicationContext(), UsageHistory.class);
                //pass_value_to_current.putExtra("value1_from_history", Integer.toString(LED1_progress));
               // pass_value_to_current.putExtra("value2_from_history", Integer.toString(LED2_progress));
                //intent_save.putExtra("LED3", Integer.toString(progress_value3));
                //pass_value_to_history.putExtra("name", setting_name);
                //Log.d("check", "pass LED to current finish");

                startActivity(go_to_current);
                finish();
            }
        });*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "go back", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent go_to_current = new Intent(UsageHistory.this, CurrentSetting.class);

                //pass_value_to_current = new Intent(getApplicationContext(), UsageHistory.class);
                //pass_value_to_current.putExtra("value1_from_history", Integer.toString(LED1_progress));
                // pass_value_to_current.putExtra("value2_from_history", Integer.toString(LED2_progress));
                //intent_save.putExtra("LED3", Integer.toString(progress_value3));
                //pass_value_to_history.putExtra("name", setting_name);
                //Log.d("check", "pass LED to current finish");

                startActivity(go_to_current);
                finish();
            }
        });
        textView_history = (TextView) findViewById(R.id.textView_history);

        LED1value_show = (TextView) findViewById(R.id.textView_LED1Value);
        LED2value_show = (TextView) findViewById(R.id.textView_LED2Value);

        LED_string_show = (TextView)findViewById(R.id.LED_string_show);

        userLocalStore = new UserLocalStore(this);
        ledLocalStore = new LEDLocalStore(this);

        //how_many_usage_history = (EditText) findViewById(R.id.how_many_history);


        Log.d("check", "history ok");
        final Button get_usage_history = (Button) findViewById(R.id.button_get_usage_history);
        get_usage_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("check", "get userlocalstore.username");
                Log.d("Check", "here is userlocalstore.username" + userLocalStore.getLoggedInUser().username);
                Log.d("check", "get..");
                //how to get username/？/
                String LED_user = userLocalStore.getLoggedInUser().username;
                Log.d("check", "LED_user=" + LED_user);


                Log.d("check", "how many history is ");
                how_many_history_int =5;
                LED led = new LED(LED_user, how_many_history_int);
                getUsage(led);
                //how_many_history = how_many_usage_history.getText().toString();
                /**Log.d("check", "how many history is " + how_many_history);
                try{
                how_many_history_int = Integer.parseInt(how_many_history);}
                catch (Exception e){
                    e.getStackTrace();
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UsageHistory.this);
                    dialogBuilder.setMessage("Please input a number");
                    dialogBuilder.setPositiveButton("OK", null);
                    dialogBuilder.show();
                }

                if (how_many_history_int >0) {
                    LED led = new LED(LED_user, how_many_history_int);

                    Log.d("Check", "start to getusage");
                    getUsage(led);

                    //textView_history.setText("You get " + Integer.toString(count) + "usage history");
                    Log.d("Check", "usage got");
                    Log.d("Check", "start to store local LED data");
                    ledLocalStore.storedLEDData(led);
                    Log.d("check", "storedLEDData = " + led);
                    ledLocalStore.setLEDValueGot(true);
                }*/
            }
        });


        //listView = (ListView) findViewById(R.id.listView);

        String[] item = {};
        Integer[] integers1 = {};
        Integer[] integers2 = {};
        //String [] LEDListitem = {};
        arrayList = new ArrayList<>(Arrays.asList(item));
        //LEDList = new ArrayList<>(Arrays.asList(LEDListitem));
        LEDList1 = new ArrayList<>(Arrays.asList(integers1));
        LEDList2 = new ArrayList<>(Arrays.asList(integers2));
        //arrayAdapter = new ArrayAdapter<String>(this, R.layout.listiem, R.id.item, arrayList);
        //
        // listView.setAdapter(arrayAdapter);


        button_get_chart = (Button) findViewById(R.id.button_get_chart);
        button_get_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent lineintent = getInent(UsageHistory.this);
                    startActivity(lineintent);
                    //openChart();
                } catch (Exception e) {
                    e.getStackTrace();
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UsageHistory.this);
                    dialogBuilder.setMessage("Please first get history");
                    dialogBuilder.setPositiveButton("OK", null);
                    dialogBuilder.show();
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }


    private void getUsage(LED led) {
        ServerRequests serverRequests = new ServerRequests(this);
        Log.d("Check", "start to fetch LED data");

        serverRequests.getLEDDataInBackground(led, new GetLEDCallback() {
            @Override

            public void done(LED returnedLED) {

                if (returnedLED == null) {
                    showErroMessage();
                } else {
                    arrayList.clear();
                    setLEDValue(returnedLED);


                }
            }
        });
        //return LED1value;
    }

    private void showErroMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UsageHistory.this);
        dialogBuilder.setMessage("Get LED value error, check the connection or try a smaller number");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void setLEDValue(LED returnedLED) {
        ledLocalStore.storedLEDData(returnedLED);
        ledLocalStore.setLEDValueGot(true);
        Log.d("Check", "start to get LED value");
        //LED1value = returnedLED.LED1_value;
        //LED2value = returnedLED.LED2_value;
        LEDString = returnedLED.LEDList;
        LED1String = returnedLED.LED1List;


        //LED1value_show.setText("LED1: " + LED1value);
        //LED2value_show.setText("LED2: " + LED2value);
        //LED2value_show.setText(LEDString);

        //arrayList.add("LED2: "+Integer.toString(LED2value));
        //LEDList1.add(LED1value);
        //LEDList2.add(LED2value);
        //arrayAdapter.notifyDataSetChanged();
        delims = "[\\r?\\n\\t]+";
        LED1tokens = LED1String.split(delims);


        LEDtokens = LEDString.split(delims);
        //String[] fields = s.split("\t", -1);  // in your case s.split("\t", 11) might also do
        //for (int i = 0; i < fields.length; ++i) {
        //    if ("".equals(fields[i])) fields[i] = null;
        //}
        Log.d("check", "LED tokens are " + LEDtokens[0]);
        try {
            int j;
            for (j = 0; j < LED1tokens.length; j++) {
                Log.d("check", "LED lenght " + LED1tokens.length);
                LEDInteger = Integer.parseInt(LED1tokens[j]);
                Log.d("check", "LED integer" + LEDInteger);
                if (LEDInteger >= 75 && j%2 !=0) {

                    LED_string.append("\t\t\t\t\t\t\t\t\t<font color=\"red\">" + Integer.toString(LEDInteger) + "</font><BR>");
                    Log.d("check", "LED2list add");
                }
                else if(LEDInteger >= 75 && j%2 ==0){
                    LED_string.append("<font color=\"red\">" + Integer.toString(LEDInteger) + "</font>");
                    //Log.d("check", "LED2list add");
                }
                else if(LEDInteger < 50 &&LEDInteger>9&& j%2 ==0){
                    LED_string.append("<font color=\"black\">" + Integer.toString(LEDInteger) + "</font>");
                    //Log.d("check", "LED2list add");
                }
                else if(LEDInteger < 50 &&LEDInteger>9&& j%2 !=0){
                    LED_string.append("\t\t\t\t\t\t\t\t\t<font color=\"black\">" + Integer.toString(LEDInteger) + "</font><BR>");
                    //Log.d("check", "LED2list add");
                }
                else if(LEDInteger < 75 &&LEDInteger>49&& j%2 ==0){
                    LED_string.append("<font color=\"blue\">" + Integer.toString(LEDInteger) + "</font>");
                    //Log.d("check", "LED2list add");
                }
                else if(LEDInteger < 75 &&LEDInteger>49&& j%2 !=0){
                    LED_string.append("\t\t\t\t\t\t\t\t\t<font color=\"blue\">" + Integer.toString(LEDInteger) + "</font><BR>");
                    //Log.d("check", "LED2list add");
                }
                else if(LEDInteger < 10 && j%2 !=0){
                    LED_string.append("\t\t\t\t\t\t\t\t\t\t<font color=\"black\">" + Integer.toString(LEDInteger) + "</font><BR>");
                    //Log.d("check", "LED2list add");
                }
                else if(LEDInteger < 10 && j%2 ==0){
                    LED_string.append("\t<font color=\"black\">" + Integer.toString(LEDInteger) + "</font>");
                    //Log.d("check", "LED2list add");
                }
                Log.d("check", "x list finished");
                Log.d("check", "LEDlist1 " + LEDList1);
                Log.d("check", "LEDlist2 " + LEDList2);
                //LEDintegershow[i-1]=LEDInteger;

            }

            //arrayList.add(": <font color=\"red\">"+LED1String+"</font>");
            Log.d("check", "show arraylist");
            LED_string_show.setText(Html.fromHtml(LED_string.toString()));
            //count = LED1tokens.length / 2;
            //textView_history.setText("You get " + Integer.toString(count) + " usage history");
            textView_history.setText("You got recent usage history");
            //count = count + 1;
            Log.d("Check", "got LED value");
        }catch(Exception e){
            e.getStackTrace();
        }

    }

    public Intent getInent(Context context) {
        //protected void openChart(){
        //Integer[] xvals = {};
        List<Integer> xvals = new ArrayList<Integer>();
        Log.d("check", "start to make list, arraylist size is  " + arrayList.size());

        delims = "[\\r?\\n\\t]+";
        LEDtokens = LEDString.split(delims);
        //String[] fields = s.split("\t", -1);  // in your case s.split("\t", 11) might also do
        //for (int i = 0; i < fields.length; ++i) {
        //    if ("".equals(fields[i])) fields[i] = null;
        //}
        Log.d("check", "LED tokens are " + LEDtokens[0]);
        int j;
        for (j = 0; j < LEDtokens.length; j++) {
            Log.d("check", "LED lenght " + LEDtokens.length);
            LEDInteger = Integer.parseInt(LEDtokens[j]);
            Log.d("check", "LED integer" + LEDInteger);
            if (j % 2 == 0) {
                Log.d("check", "i is " + j / 2);
                xvals.add(j / 2 + 1);
                Log.d("check", "xval add");
                LEDList2.add(LEDInteger);
                Log.d("check", "LED2list add");
            } else {

                LEDList1.add(LEDInteger);
                Log.d("check", "LED1list add");
            }
            Log.d("check", "x list finished");
            Log.d("check", "LEDlist1 " + LEDList1);
            Log.d("check", "LEDlist2 " + LEDList2);
            //LEDintegershow[i-1]=LEDInteger;
            Log.d("check", "xvals is " + xvals);

        }


        Log.d("check", "list finished");

        XYSeries series1 = new XYSeries("LED1");
        XYSeries series2 = new XYSeries("LED2");
        Log.d("check", "start series add");
        for (int i = 0; i < xvals.size(); i++)

        {
            series1.add(xvals.get(i), LEDList1.get(i));
            series2.add(xvals.get(i), LEDList2.get(i));
        }
        Log.d("check", "series finished");
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        XYMultipleSeriesRenderer multipleSeriesRenderer = new XYMultipleSeriesRenderer();
        SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
        XYSeriesRenderer renderer1 = new XYSeriesRenderer();
        renderer1.setLineWidth(3);
        renderer1.setPointStrokeWidth(10);
        renderer1.setPointStyle(PointStyle.SQUARE);
        renderer1.setDisplayChartValues(true);
        renderer1.setColor(Color.CYAN);
        renderer1.setChartValuesTextSize(30);

        XYSeriesRenderer renderer2 = new XYSeriesRenderer();
        renderer2.setLineWidth(3);
        renderer2.setPointStrokeWidth(10);
        renderer2.setPointStyle(PointStyle.CIRCLE);
        renderer2.setDisplayChartValues(true);
        renderer2.setColor(Color.GREEN);
        renderer2.setChartValuesTextSize(30);

        multipleSeriesRenderer.setChartTitle("LED value in the usage history");
        multipleSeriesRenderer.setXTitle("Times");
        multipleSeriesRenderer.setYTitle("LED brightness");
        multipleSeriesRenderer.setAxisTitleTextSize(30);
        multipleSeriesRenderer.setYLabels(5);
        multipleSeriesRenderer.setLabelsTextSize(30);
        multipleSeriesRenderer.setChartTitleTextSize(30);
        multipleSeriesRenderer.setLegendTextSize(30);
        //simpleSeriesRenderer.setChartValuesTextSize(50);
        multipleSeriesRenderer.setApplyBackgroundColor(true);
        multipleSeriesRenderer.setBackgroundColor(Color.BLACK);
        multipleSeriesRenderer.setMarginsColor(Color.BLACK);
        multipleSeriesRenderer.setMargins(new int[]{80, 60, 80, 80});
        multipleSeriesRenderer.setXAxisMin(0);
        multipleSeriesRenderer.setYAxisMin(0);
        multipleSeriesRenderer.setYAxisMax(100);
        //multipleSeriesRenderer.setPanEnabled(false,false);
        multipleSeriesRenderer.isZoomEnabled();

        multipleSeriesRenderer.addSeriesRenderer(renderer1);
        multipleSeriesRenderer.addSeriesRenderer(renderer2);

        for (int i = 1; i <= xvals.size(); i++) {
            multipleSeriesRenderer.addXTextLabel(i, String.valueOf(i));
            multipleSeriesRenderer.setXAxisMax(i + 1);
        }
        Intent intent = ChartFactory.getLineChartIntent(context, dataset, multipleSeriesRenderer, "Linear Graph");
        return intent;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UsageHistory Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://yao.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "UsageHistory Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://yao.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
