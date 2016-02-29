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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText editText_name, editText_username, editText_password;
    TextView ipshowinregister;
    String changedIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText_name = (EditText)findViewById(R.id.register_name);
        editText_username = (EditText)findViewById(R.id.register_username);
        editText_password = (EditText)findViewById(R.id.register_password);

        final Button register = (Button)findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText_name.getText().toString();
                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();

                User user= new User(username,password,name);
                registerUser(user);

            }
        });
    }

  private void registerUser(User user){
        ServerRequests serverRequests = new ServerRequests(this);
       serverRequests.storeUserDataInBackground(user, new GetUserCallback() {
           @Override
            public void done(User returnedUser) {




                   //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Register.this);
                   //dialogBuilder.setMessage("Register succeeded!");
                   //dialogBuilder.setPositiveButton("OK", null);
                   //dialogBuilder.show();

               Context context = getApplicationContext();
               CharSequence text = "Register succeeded!";
               int duration = Toast.LENGTH_SHORT;

               Toast toast = Toast.makeText(context, text, duration);
               toast.show();

               onBackPressed();


               //startActivity(new Intent(Register.this, Login.class));

            }
        });
    }
}
