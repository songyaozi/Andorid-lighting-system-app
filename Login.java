package yao.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button button_login, button_exit, button_changeIp;
    EditText login_username, login_password, changeIPAddress;
    TextView register_here,changed_ip,show_http_response;
    UserLocalStore userLocalStore;
    //ChangeIp changeIp;
    //String ip;
    //HttpResponseResult httpResponseResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ChangeIp changeIp;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "exiting", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                finish();
                System.exit(0);
            }
        });
        userLocalStore = new UserLocalStore(this);



        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = login_username.getText().toString();
                String password = login_password.getText().toString();
                User user = new User(username, password);

                Log.d("Check","start to authenticate");
                authenticate(user);
                Log.d("Check", "authenticated");
                Log.d("Check","start to store local data");
                userLocalStore.storedUserData(user);
                userLocalStore.setUserLoggedIn(true);
                //Intent intent = new Intent(Login.this, CurrentSetting.class);
                //startActivity(intent);

            }
        });
       /** button_exit = (Button)findViewById(R.id.button_exit);
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }

        });*/

        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);

        register_here = (TextView) findViewById(R.id.textView_register);
        register_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
    /**@Override
    protected void onDestroy(){
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }*/
    private void authenticate(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        Log.d("Check","start to fetch user data");
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                /**AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
                dialogBuilder.setMessage(returnedUser + " :is the message");
                dialogBuilder.setPositiveButton("OK", null);
                dialogBuilder.show();
                Log.d("check","returned user="+returnedUser);*/

                //show_http_response.setText(httpResponseResult.getResponseResult());

                if (returnedUser == null) {
                    showErroMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErroMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        //dialogBuilder.setMessage("Incorrect Userdetails");
        dialogBuilder.setMessage(Html.fromHtml("<font color='#FF7F27'>Incorrect user details</font>"));
        dialogBuilder.setPositiveButton("OK", null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser){
        userLocalStore.storedUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        Log.d("Check", "start to log user in");
        startActivity(new Intent(this, CurrentSetting.class));
        Log.d("Check","Log user in");

    }


}
