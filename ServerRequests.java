package yao.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by yao on 11/7/15.
 */
public class ServerRequests extends AppCompatActivity{
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    String changedIP ;
    HttpResponseResult httpResponseResult;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        /**public ChangeIp getChangeIp() {
         return changeIp.getIp();
         }*/

    /**    if (intent != null)
            if (intent.getExtras() != null)
                if (intent.getExtras().getString("Key") != null) {
                    String recivedString= intent.getExtras().getString("Key");
    */
        Log.d("Check","receive ip :....");

        if (extras != null)

        {
            changedIP= extras.getString("ipAddress");
            Log.d("Check","changedip :"  + changedIP);
        }
    }
    public  String SERVER_ADDRESS = "http://bagriaditya.cloudapp.net:1234/";
    //public String SERVER_ADDRESS = "http://"+changedIP.toString()+"/";



    //public  String SERVER_ADDRESS = "http://songyao.net16.net/";

    /**ChangeIp changeIp;
    public class GetChangeIp(ChangeIp changeIp){
        this.changeIp = changeIp;
    }
    if GetChangeIp.*/


    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");

       // SERVER_ADDRESS = "http://"+changeIp.getIp()+"/";
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallback) {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallback).execute();
    }
    public void storeLEDDataInBackground(LED led, GetLEDCallback ledCallback) {
        progressDialog.show();
        new StoreLEDDataAsyncTask(led, ledCallback).execute();
    }

    public void storeAlarmDataInBackground(LED led, GetLEDCallback ledCallback) {
        progressDialog.show();
        new StoreAlarmDataAsyncTask(led, ledCallback).execute();
    }
    public void fetchUserDataInBackground(User user, GetUserCallback callback) {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, callback).execute();
    }

    public void GetWeatherDataInBackground(LED led, GetLEDCallback ledcallback) {
        progressDialog.show();
        new GetWeatherDataAsyncTask(led, ledcallback).execute();
    }
    public void getLEDDataInBackground(LED led, GetLEDCallback ledcallback) {
        progressDialog.show();
        new fetchLEDDataAsyncTask(led, ledcallback).execute();
    }

    public void LoadSettingDataInBackground(LED led, GetLEDCallback callback) {
        progressDialog.show();
        new LoadSettingDataAsyncTask(led, callback).execute();
    }
    public void GetConsumptionDataInBackground(LED led, GetLEDCallback callback) {
        progressDialog.show();
        new GetConsumptionDataAsyncTask(led, callback).execute();
    }

    public void StoreSavedSettingDataInBackground(LED led, GetLEDCallback callback) {
        progressDialog.show();
        new StoreSavedSettingDataAsyncTask(led, callback).execute();
    }

    public void storeSettingInBackground(LED led, GetLEDCallback ledCallback) {
        progressDialog.show();
        new StoreSettingDataAsyncTask(led, ledCallback).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.d("Check", "doinbackground");
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("name", user.name));
            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");
            Log.d("Check","Start to http post");
            HttpPost post = new HttpPost(SERVER_ADDRESS);
            //HttpPost post_update = new HttpPost(SERVER_ADDRESS + "Update.php");
            Log.d("Check","http posted");
                try {
                Log.d("Check","start to set Entity");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "post.setEntity finished");
                Log.d("Check","start client.execute(post)");
                client.execute(post);
                Log.d("Check", "client.execute(post) finished");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check", "post error");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.username));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            Log.d("check", "user dataToSend:" + dataToSend);
            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS );

            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");
            //HttpPost post_update = new HttpPost(SERVER_ADDRESS + "Update.html");
            Log.d("Check","fetch data httppost end ");

            User returnedUser = null;
            Log.d("check","initialized returnedUser="+returnedUser);
            try {
                Log.d("Check","try post.setEntity ");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "post.setEntity ends ");
                Log.d("check","user post.setEntity = "+post);
                Log.d("Check", "try httpRespnse ");
                HttpResponse httpResponse = client.execute(post);
                Log.d("Check: ", "Http response" + httpResponse.toString());
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                /**AlertDialog.Builder dialogBuilder;
                dialogBuilder = new AlertDialog.Builder(this,Login.class);
                dialogBuilder.setMessage(result + " :is the message");
                dialogBuilder.show();*/


                //httpResponseResult(result);



                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.length() == 0 || jsonObject.getBoolean("status")==false) {
                    returnedUser = null;
                    Log.d("check","not user");
                } else {
                    String name = jsonObject.getString("username");
                    Boolean status = jsonObject.getBoolean("status");
                    Log.d("check","status:" + status);
                    if (status==false) {
                        returnedUser = null;
                    }
                    else if (status==true)
                    {
                        returnedUser = new User(user.username, user.password, name);

                    }
                    Log.d("check","returneduser="+returnedUser);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check","returned user error");
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }

    public class GetWeatherDataAsyncTask extends AsyncTask<Void, Void, LED> {
        LED weather;
        GetLEDCallback ledCallback;

        public GetWeatherDataAsyncTask(LED weather, GetLEDCallback ledCallback) {
            this.weather = weather;
            this.ledCallback = ledCallback;
        }

        @Override
        protected LED doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", weather.LED_user));
            //dataToSend.add(new BasicNameValuePair("password", user.password));
            Log.d("check", "user dataToSend:" + dataToSend);
            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS );

            HttpPost post = new HttpPost(SERVER_ADDRESS + "Weather.html");
            //HttpPost post_update = new HttpPost(SERVER_ADDRESS + "Update.html");
            Log.d("Check","fetch data httppost end ");

            LED returnedLED = null;
            Log.d("check","initialized returnedUser="+returnedLED);
            try {
                Log.d("Check", "try get LED post.setEntity ");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "get LED post.setEntity ends,post setEntity="+post);
                Log.d("Check","try GET LED httpRespnse ");
                HttpResponse httpResponse = client.execute(post);
                Log.d("Check: ", "GET LED Http response" + httpResponse.toString());
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                /**AlertDialog.Builder dialogBuilder;
                 dialogBuilder = new AlertDialog.Builder(this,Login.class);
                 dialogBuilder.setMessage(result + " :is the message");
                 dialogBuilder.show();*/


                //httpResponseResult(result);



                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.length() == 0 ) {
                    returnedLED = null;
                    Log.d("check","not LED");
                } else {
                    //JSONArray LED1 = jsonObject.getJSONArray("LED1");
                    //JSONArray LED2 = jsonObject.getJSONArray("LED2");
                    //String LED1 = jsonObject.getString("LED1");
                    //String LED2 = jsonObject.getString("LED2");
                    String weather = jsonObject.getString("weather");
                    String dtime = jsonObject.getString("dtime");
                    String temperature = jsonObject.getString("temperature");
                    Log.d("check","weather: " +weather+"  "+dtime);

                    /**for(int i=0;i<LED1.length();i++){
                     returnedLED = new LED(Integer.parseInt(LED1.getString(i)), Integer.parseInt(LED2.getString(i)));
                     }*/
                    returnedLED = new LED(weather,dtime,temperature);

                }
                Log.d("check","returned led=" + returnedLED);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check","returned led error");
            }

            return returnedLED;
        }

        @Override
        protected void onPostExecute(LED returnedLED) {
            progressDialog.dismiss();
            ledCallback.done(returnedLED);
            super.onPostExecute(returnedLED);
        }
    }


    public class StoreLEDDataAsyncTask extends AsyncTask<Void, Void, Void> {
        LED led;
        GetLEDCallback ledCallback;

        public StoreLEDDataAsyncTask(LED led, GetLEDCallback ledCallback) {
            this.led = led;
            this.ledCallback = ledCallback;
        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.d("Check", "doinbackground");
            dataToSend.add(new BasicNameValuePair("LED1", Integer.toString(led.LED1_value)));
            dataToSend.add(new BasicNameValuePair("LED2", Integer.toString(led.LED2_value)));
            dataToSend.add(new BasicNameValuePair("name", led.LED_user));

            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");
            Log.d("Check","Start to http post LED");
            //HttpPost post = new HttpPost(SERVER_ADDRESS);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Update.html");
            Log.d("Check","http LED posted");
            try {
                Log.d("Check","start to set Entity");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "post.setEntity finished");
                Log.d("Check","start client.execute(post)LED");
                client.execute(post);
                Log.d("Check", "client.execute(post) LED finished");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check", "post error");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            ledCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }





        public class fetchLEDDataAsyncTask extends AsyncTask<Void, Void, LED> {
        LED led;
        GetLEDCallback ledCallback;

        public fetchLEDDataAsyncTask(LED led, GetLEDCallback ledCallback) {
            this.led = led;
            this.ledCallback = ledCallback;
        }

        @Override
        protected LED doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.d("check","Start to send LED user to server");
            Log.d("check", "LED_user=" + led.LED_user);
            dataToSend.add(new BasicNameValuePair("username", led.LED_user));
            dataToSend.add(new BasicNameValuePair("history", Integer.toString(led.usage_history)));
            Log.d("check", "dataToSend LED has:" + dataToSend);
            //dataToSend.add(new BasicNameValuePair("LED2", Integer.toString(led.LED2_value)));

            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS );

            HttpPost post = new HttpPost(SERVER_ADDRESS + "Getusage.html");
            //HttpPost post_update = new HttpPost(SERVER_ADDRESS + "Update.html");
            Log.d("Check","fetch LED httppost end ");

            LED returnedLED = null;
            Log.d("check","initialized returnedLED="+returnedLED);
            try {
                Log.d("Check","try get LED post.setEntity ");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "get LED post.setEntity ends,post setEntity="+post);
                Log.d("Check","try GET LED httpRespnse ");
                HttpResponse httpResponse = client.execute(post);
                Log.d("Check: ", "GET LED Http response" + httpResponse.toString());
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                /**AlertDialog.Builder dialogBuilder;
                 dialogBuilder = new AlertDialog.Builder(this,Login.class);
                 dialogBuilder.setMessage(result + " :is the message");
                 dialogBuilder.show();*/


                //httpResponseResult(result);



                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.length() == 0 ) {
                    returnedLED = null;
                    Log.d("check","not LED");
                } else {
                    //JSONArray LED1 = jsonObject.getJSONArray("LED1");
                    //JSONArray LED2 = jsonObject.getJSONArray("LED2");
                    //String LED1 = jsonObject.getString("LED1");
                    //String LED2 = jsonObject.getString("LED2");
                    String LEDstring = jsonObject.getString("LED_string");
                    String LED1string = jsonObject.getString("LED1_string");
                    Log.d("check","LED list: " +LEDstring+"  "+LED1string);

                    /**for(int i=0;i<LED1.length();i++){
                        returnedLED = new LED(Integer.parseInt(LED1.getString(i)), Integer.parseInt(LED2.getString(i)));
                    }*/
                    returnedLED = new LED(LEDstring,LED1string);

                }
                Log.d("check","returned led=" + returnedLED);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check","returned led error");
            }

            return returnedLED;
        }

        @Override
        protected void onPostExecute(LED returnedLED) {
            progressDialog.dismiss();
            ledCallback.done(returnedLED);
            super.onPostExecute(returnedLED);
        }
    }


    ///store  setting
    public class StoreSettingDataAsyncTask extends AsyncTask<Void, Void, Void> {
        LED led;
        GetLEDCallback ledCallback;

        public StoreSettingDataAsyncTask(LED led, GetLEDCallback ledCallback) {
            this.led = led;
            this.ledCallback = ledCallback;
        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.d("Check", "doinbackground");
            dataToSend.add(new BasicNameValuePair("username", led.LED_user));
            dataToSend.add(new BasicNameValuePair("setting_name", led.Saved_setting_name));
            dataToSend.add(new BasicNameValuePair("LED1", Integer.toString(led.LED1_value)));
            dataToSend.add(new BasicNameValuePair("LED2", Integer.toString(led.LED2_value)));



            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");
            Log.d("Check","Start to http post setting");
            //HttpPost post = new HttpPost(SERVER_ADDRESS);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Setting.html");
            Log.d("Check","http setting posted");
            try {
                Log.d("Check","start to set Entity");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "post.setEntity finished");
                Log.d("Check","start client.execute(post)setting");
                client.execute(post);
                Log.d("Check", "client.execute(post) setting finished");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check", "post error");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            ledCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }




    public class LoadSettingDataAsyncTask extends AsyncTask<Void, Void, LED> {
        LED led;
        GetLEDCallback ledCallback;

        public LoadSettingDataAsyncTask(LED led, GetLEDCallback ledCallback) {
            this.led = led;
            this.ledCallback = ledCallback;
        }

        @Override
        protected LED doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.d("check","Start to send LED user to server");
            Log.d("check", "LED_user=" + led.LED_user);
            dataToSend.add(new BasicNameValuePair("username", led.LED_user));
            //dataToSend.add(new BasicNameValuePair("history", Integer.toString(led.usage_history)));
            Log.d("check", "dataToSend LED has:" + dataToSend);
            //dataToSend.add(new BasicNameValuePair("LED2", Integer.toString(led.LED2_value)));

            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS );

            HttpPost post = new HttpPost(SERVER_ADDRESS + "Load.html");
            //HttpPost post_update = new HttpPost(SERVER_ADDRESS + "Update.html");
            Log.d("Check","load setting httppost end ");

            LED returnedLED = null;
            Log.d("check","initialized returnedLED="+returnedLED);
            try {
                Log.d("Check","try get setting post.setEntity ");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "get setting post.setEntity ends,post setEntity="+post);
                Log.d("Check","try load setting httpRespnse ");
                HttpResponse httpResponse = client.execute(post);
                Log.d("Check: ", "load setting Http response" + httpResponse.toString());
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                /**AlertDialog.Builder dialogBuilder;
                 dialogBuilder = new AlertDialog.Builder(this,Login.class);
                 dialogBuilder.setMessage(result + " :is the message");
                 dialogBuilder.show();*/


                //httpResponseResult(result);



                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.length() == 0 ) {
                    returnedLED = null;
                    Log.d("check","not setting");
                } else {
                    //JSONArray LED1 = jsonObject.getJSONArray("LED1");
                    //JSONArray LED2 = jsonObject.getJSONArray("LED2");
                    //String LED1 = jsonObject.getString("LED1");
                    //String LED2 = jsonObject.getString("LED2");
                    String LED1string = jsonObject.getString("LEDString");
                    //String LED2string = jsonObject.getString("LED2_string");
                    //String LED_user = jsonObject.getString("LED_user");
                    String saved_setting = jsonObject.getString("Saved_setting");
                    //Log.d("check","LED list: " +LED1string+"  "+LED2string);
                    Log.d("check","setting list: " +saved_setting);

                    /**for(int i=0;i<LED1.length();i++){
                     returnedLED = new LED(Integer.parseInt(LED1.getString(i)), Integer.parseInt(LED2.getString(i)));
                     }*/
                    //returnedLED = new LED(saved_setting,LED1string,LED2string);
                    returnedLED = new LED(saved_setting,LED1string);
                }
                Log.d("check","returned led=" + returnedLED);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check","returned led error");
            }

            return returnedLED;
        }

        @Override
        protected void onPostExecute(LED returnedLED) {
            progressDialog.dismiss();
            ledCallback.done(returnedLED);
            super.onPostExecute(returnedLED);
        }
    }


    ///store  setting
    public class StoreSavedSettingDataAsyncTask extends AsyncTask<Void, Void, Void> {
        LED led;
        GetLEDCallback ledCallback;

        public StoreSavedSettingDataAsyncTask(LED led, GetLEDCallback ledCallback) {
            this.led = led;
            this.ledCallback = ledCallback;
        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.d("Check", "doinbackground");
            //dataToSend.add(new BasicNameValuePair("username", led.LED_user));
            dataToSend.add(new BasicNameValuePair("setting_name", led.Saved_setting_name));
            dataToSend.add(new BasicNameValuePair("LED1", led.LED1_value_string));
            dataToSend.add(new BasicNameValuePair("LED2", led.LED2_value_string));



            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");
            Log.d("Check","Start to http post set savedsetting");
            //HttpPost post = new HttpPost(SERVER_ADDRESS);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "SetSavedSetting.html");
            Log.d("Check","http set savedsetting posted");
            try {
                Log.d("Check","start to set Entity");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "post.setEntity finished");
                Log.d("Check","start client.execute(post)set savedsetting");
                client.execute(post);
                Log.d("Check", "client.execute(post) set savedsetting finished");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check", "set savedsetting post error");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            ledCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }


//get all units consumption: consumption,motor, light

    public class GetConsumptionDataAsyncTask extends AsyncTask<Void, Void, LED> {
        LED all_units;
        GetLEDCallback ledCallback;

        public GetConsumptionDataAsyncTask(LED all_units, GetLEDCallback ledCallback) {
            this.all_units = all_units;
            this.ledCallback = ledCallback;
        }

        @Override
        protected LED doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.d("check","Start to send LED user to server");
            Log.d("check", "LED_user=" + all_units.LED_user);
            dataToSend.add(new BasicNameValuePair("username", all_units.LED_user));
            //dataToSend.add(new BasicNameValuePair("history", Integer.toString(led.usage_history)));
            Log.d("check", "dataToSend LED has:" + dataToSend);
            //dataToSend.add(new BasicNameValuePair("LED2", Integer.toString(led.LED2_value)));

            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS );

            HttpPost post = new HttpPost(SERVER_ADDRESS + "Consumption.html");
            //HttpPost post_update = new HttpPost(SERVER_ADDRESS + "Update.html");
            Log.d("Check","load setting httppost end ");

            LED returnedLED = null;
            Log.d("check","initialized returnedLED="+returnedLED);
            try {
                Log.d("Check","try get setting post.setEntity ");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "get setting post.setEntity ends,post setEntity="+post);
                Log.d("Check","try load setting httpRespnse ");
                HttpResponse httpResponse = client.execute(post);
                Log.d("Check: ", "load setting Http response" + httpResponse.toString());
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                /**AlertDialog.Builder dialogBuilder;
                 dialogBuilder = new AlertDialog.Builder(this,Login.class);
                 dialogBuilder.setMessage(result + " :is the message");
                 dialogBuilder.show();*/


                //httpResponseResult(result);



                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.length() == 0 ) {
                    returnedLED = null;
                    Log.d("check","not setting");
                } else {
                    //JSONArray LED1 = jsonObject.getJSONArray("LED1");
                    //JSONArray LED2 = jsonObject.getJSONArray("LED2");
                    //String LED1 = jsonObject.getString("LED1");
                    //String LED2 = jsonObject.getString("LED2");
                    String consumption = jsonObject.getString("idle");
                    String light = jsonObject.getString("light");
                    //String LED_user = jsonObject.getString("LED_user");
                    String motor = jsonObject.getString("motor");
                    //Log.d("check","LED list: " +LED1string+"  "+LED2string);
                    //Log.d("check","setting list: " +saved_setting);

                    /**for(int i=0;i<LED1.length();i++){
                     returnedLED = new LED(Integer.parseInt(LED1.getString(i)), Integer.parseInt(LED2.getString(i)));
                     }*/
                    //returnedLED = new LED(saved_setting,LED1string,LED2string);
                    returnedLED = new LED(consumption,motor,light);
                }
                Log.d("check","returned led=" + returnedLED);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check","returned led error");
            }

            return returnedLED;
        }

        @Override
        protected void onPostExecute(LED returnedLED) {
            progressDialog.dismiss();
            ledCallback.done(returnedLED);
            super.onPostExecute(returnedLED);
        }
    }


    //post alarm
    public class StoreAlarmDataAsyncTask extends AsyncTask<Void, Void, Void> {
        LED alarm;
        GetLEDCallback ledCallback;

        public StoreAlarmDataAsyncTask(LED alarm, GetLEDCallback ledCallback) {
            this.alarm = alarm;
            this.ledCallback = ledCallback;
        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            Log.d("Check", "doinbackground");
            dataToSend.add(new BasicNameValuePair("Time", alarm.LED_user));
            //dataToSend.add(new BasicNameValuePair("LED2", Integer.toString(led.LED2_value)));
            //dataToSend.add(new BasicNameValuePair("name", led.LED_user));

            //dataToSend.add(new BasicNameValuePair("ip", ip));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            //HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");
            Log.d("Check","Start to http post alarm");
            //HttpPost post = new HttpPost(SERVER_ADDRESS);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Alarm.html");
            Log.d("Check","http alarm posted");
            try {
                Log.d("Check","start to set Entity");
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                Log.d("Check", "post.setEntity finished");
                Log.d("Check","start client.execute(post)alarm");
                client.execute(post);
                Log.d("Check", "client.execute(post) alarm finished");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Check", "post error");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            ledCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }


}


