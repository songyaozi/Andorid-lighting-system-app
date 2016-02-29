package yao.myapplication;

/**
 * Created by yao on 11/7/15.
 */
public class User {
    String name, username, password;
    //two way of creating a new user, the first way is given his name, username and password
    public User(String username, String password,String name){
        this.username = username;
        this.password = password;
        this.name = name;
    }

    //the second way is only given his username and password
    public User(String username, String password){
        this.username = username;
        this.password = password;
        //this.name = "";
    }
}



