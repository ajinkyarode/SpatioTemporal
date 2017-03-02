package com.example.ajinkyarode.chatapp;

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
 * Created by ajinkyarode on 11/20/16.
 */
public class PeerDetails extends Activity {

    public static String ctr;
    public static String ctr1;
    private Button go;
    private Spinner sp;
    private Spinner sp1;
    private TextView tv1;
    private TextView tv2;
    private View v1;
    private View v2;
    private View v3;
    private View v4;
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peer_details);

        tv1 = (TextView) findViewById(R.id.header2);
        tv2 = (TextView) findViewById(R.id.header3);
        v1 = findViewById(R.id.l2);
        v2 = findViewById(R.id.l3);
        v3 = findViewById(R.id.l4);
        v4 = findViewById(R.id.l5);

        /**
         *
         * Spinner used for drop down menu items
         *
         */
        spin = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this,
                R.array.option_array, android.R.layout.simple_spinner_item);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapt);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (spin.getSelectedItem().toString().equals("Peer")) {
                    sp.setVisibility(View.INVISIBLE);
                    sp1.setVisibility(View.INVISIBLE);
                    tv1.setVisibility(View.INVISIBLE);
                    tv2.setVisibility(View.INVISIBLE);
                    v1.setVisibility(View.INVISIBLE);
                    v2.setVisibility(View.INVISIBLE);
                    v3.setVisibility(View.INVISIBLE);
                    v4.setVisibility(View.INVISIBLE);
                    ctr = "NA";
                    ctr1 = "NA";
                } else if (spin.getSelectedItem().toString().equals("Initiator")) {
                    sp.setVisibility(View.VISIBLE);
                    sp1.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                    v1.setVisibility(View.VISIBLE);
                    v2.setVisibility(View.VISIBLE);
                    v3.setVisibility(View.VISIBLE);
                    v4.setVisibility(View.VISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /**
         *
         * Spinner used for drop down menu items
         *
         */
        sp = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                ctr = sp.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        /**
         *
         * Spinner used for drop down menu items
         *
         */
        sp1 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.space_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                ctr1 = sp1.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


    go=(Button)findViewById(R.id.btn_go);

    go.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick (View v){
        Intent intent = new Intent(PeerDetails.this, MessageAll.class);
        startActivity(intent);
    }
    });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
