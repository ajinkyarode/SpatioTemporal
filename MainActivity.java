package com.example.ajinkyarode.chatapp;

/**
 * Created by ajinkyarode on 7/16/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static android.widget.Toast.makeText;

/**
 *
 * Login activity for the application which includes EditTexts, Spinners
 * and Buttons to login to the application
 *
 */
public class MainActivity extends Activity {

    public static String uname;
    private Button send;
    private int counter = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        send = ( Button ) findViewById(R.id.btn_join);
        send.setOnClickListener(new View.OnClickListener() {
            EditText username = (EditText) findViewById(R.id.fld_username);
            EditText password = (EditText) findViewById(R.id.fld_password);
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("") ||username.getText().toString().equals("One")||username.getText().toString().equals("Two") && password.getText().toString().equals(""))
                {
                    uname=username.getText().toString();
                    Intent intent = new Intent(MainActivity.this, PeerDetails.class);
                    startActivity(intent);
                }
                else {
                    makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    counter--;
                    if (counter == 0) {
                        send.setEnabled(false);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}/* End of Class MyLocationListener */

