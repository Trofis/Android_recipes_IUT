package com.example.eddymalou.iut_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private UserDataSource datasource;
    public UserConnected connected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        datasource = new UserDataSource(this);

        UserDataSource user = new UserDataSource(this);


        setTitle("Login");

        connected = new UserConnected();
    }

    public void SignUp(View v)
    {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    public void SignIn(View v)
    {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        datasource.open();
        int id = datasource.ifUserExists(username,password);
        if (id != 0)
        {
            connected.setUser(new User(id,username, password));
            Toast t = Toast.makeText(this, "You are connected as "+username, Toast.LENGTH_SHORT);
            t.show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast t = Toast.makeText(this, "User or password wrong", Toast.LENGTH_SHORT);
            t.show();
        }
        datasource.close();
    }


}
