package com.example.eddymalou.iut_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private UserDataSource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        datasource = new UserDataSource(this);
        setTitle("Register");
    }

    public void registerUser(View v)
    {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String confirmPass = ((EditText) findViewById(R.id.confirmpassword)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        Log.d("register",password);
        Log.d("register",confirmPass);
        if (!confirmPass.equals(password))
        {
            Toast t = Toast.makeText(this, "Password and confirm Password don't match", Toast.LENGTH_SHORT);
            t.show();
        }
        else
        {
            datasource.open();
            if(datasource.ifUserExists(username) == 0)
            {
                datasource.createUser(username, password);
                Toast t = Toast.makeText(this, "Your account is created !", Toast.LENGTH_SHORT);
                t.show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
            else
            {
                Toast t = Toast.makeText(this, "Username already used !", Toast.LENGTH_SHORT);
                t.show();
            }
            datasource.close();
        }


    }
}
