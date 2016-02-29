package yao.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yao on 11/7/15.
 */
public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabases;

    public UserLocalStore(Context context){
        userLocalDatabases = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storedUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("name", user.name);
        spEditor.commit();
    }

    //get an already loggined user's data
    public User getLoggedInUser(){

        String username = userLocalDatabases.getString("username","");
        String password = userLocalDatabases.getString("password", "");
        String name = userLocalDatabases.getString("name", "");

        User storedUser = new User(username, password,name);
        return storedUser;
    }

    //set a user loggedin boolean
    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();

    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabases.getBoolean("loggedIn", false)==true) {
            return true;
        }
        else{
            return false;
        }
    }
    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
