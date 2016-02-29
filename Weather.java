package yao.myapplication;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Weather extends AppCompatActivity {

    //EditText editText_name, editText_username, editText_password;
    TextView temperature_show,weather_show,place;
    String user_name,weather,dtime,temperature;
    LEDLocalStore ledLocalStore;
    UserLocalStore userLocalStore;
    //Button set_weather;
    ImageView weather_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("check", "checkpoint0");
        setContentView(R.layout.activity_weather);
        Log.d("check", "checkpoint01");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        userLocalStore = new UserLocalStore(this);
        ledLocalStore = new LEDLocalStore(this);

        Log.d("check","checkpoint1");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setImageResource(R.drawable.light1);
        Log.d("check","checkpoint2");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Getting weather...", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                user_name = userLocalStore.getLoggedInUser().username;

                LED weather = new LED(user_name);
                GetWeather(weather);

            }
        });
        Log.d("check", "checkpoint3");
        weather_view = (ImageView)findViewById(R.id.imageView);
        place = (TextView)findViewById(R.id.textView_place);
        Log.d("check","checkpoint4");
        temperature_show =(TextView)findViewById(R.id.temperature_show);
        weather_show = (TextView)findViewById(R.id.textview_weather);


        //set_weather = (TextView)findViewById(R.id.button_weather);

        /**set_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });*/
    }

    private void GetWeather(LED weather){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.GetWeatherDataInBackground(weather, new GetLEDCallback() {
            @Override
            public void done(LED returnedLED) {


                if (returnedLED == null) {
                    showErroMessage();
                } else {

                    setWeather(returnedLED);


                }
            }
        });
    }
    private void showErroMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Weather.this);
        dialogBuilder.setMessage("Get LED value error, check the connection or try a smaller number");
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void setWeather(LED returnedLED) {
        ledLocalStore.storedLEDData(returnedLED);
        ledLocalStore.setLEDValueGot(true);
        Log.d("Check", "start to get LED value");
        //LED1value = returnedLED.LED1_value;
        //LED2value = returnedLED.LED2_value;
        weather = returnedLED.Saved_setting_name;
        dtime = returnedLED.LED1_value_string;
        temperature = returnedLED.LED2_value_string;
        place.setText("Columbia University");
        temperature_show.setText(Html.fromHtml("<b>"+temperature.toString() + "°C</b>"));
        Log.d("check", "weather1 is " + weather);
        //weather_view.setImageResource(R.drawable.clear);
        Log.d("check","image set");
        if(weather.equals("Sunny")||weather.equals("Fair")){
            Log.d("check", "weather2 is " + weather);
            Log.d("check", "weather2 is " + dtime);
            //weather_show.setText(Html.fromHtml("<font color=\"#ffffe0\">Sunny</font>"));
            if(dtime.equals("day")){
                Log.d("check","dtime is "+dtime);
                weather_view.setImageResource(R.drawable.clear);
            }
            else if(dtime.equals("night")){
                weather_view.setImageResource(R.drawable.clearnight);
            }
        }
        else if (weather.equals("Cloudy")){
            if(dtime.equals("day")){
                weather_view.setImageResource(R.drawable.cloudy);
            }
            else if(dtime.equals("night")){
                weather_view.setImageResource(R.drawable.cloudynight);
            }
        }
        else if (weather.equals("Partly Cloudy")){
            //weather_show.setText(Html.fromHtml("<font color=\"white\">Partly Cloudy</font>"));
            if(dtime.equals("day")){
                weather_view.setImageResource(R.drawable.partlycloudy);
            }
            else if(dtime.equals("night")){
                weather_view.setImageResource(R.drawable.partlycloundynight);
            }
        }
        else if (weather.equals("Rainy")){
            if(dtime.equals("day")){
                weather_view.setImageResource(R.drawable.rain03);
            }
            else if(dtime.equals("night")){
                weather_view.setImageResource(R.drawable.rainnight);
            }
        }
        else if (weather.toString() =="Snowy"){
            if(dtime.equals("day")){
                weather_view.setImageResource(R.drawable.snow);
            }
            else if(dtime.equals("night")){
                weather_view.setImageResource(R.drawable.snownight);
            }
        }
    }

}


