package com.example.ajinkyarode.chatapp;

/**
 * Created by ajinkyarode on 8/4/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
 * Class to handle the multicast messages. It uses TextView, EditText and Button
 * to send/receive messages on the Activity window
 */
public class MessageAll extends Activity implements View.OnClickListener, ReceiveListener{

    private EditText msg_snd;
    private MessageBroadcast msg_broadcast;
    private Button broadcast;
    private Button exit;
    private static Set<String> store1 = new HashSet<String>();
    private ListView lv;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_message_window);

        //New Location Code
        startService(new Intent(MessageAll.this, MyLocationListener.class));
        //New code ends*/

        msg_snd = (EditText) findViewById(R.id.msg_send);
        lv = (ListView) findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);

        /*
         * Broadcasts the message to all the peers on click event of the Send button
         */
        broadcast = (Button) findViewById(R.id.btnSend);
        exit = (Button) findViewById(R.id.exit_app);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit = new Intent(Intent.ACTION_MAIN);
                exit.addCategory(Intent.CATEGORY_HOME);
                exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exit);
            }
        });
        broadcast.setOnClickListener(this);
        msg_broadcast = new MessageBroadcast((WifiManager) getSystemService(WIFI_SERVICE), 8888);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                try {
                    msg_broadcast.listen(MessageAll.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
}

    /*
     * Prints the messages on the message window
     *
     * @param message
     */
    public void print(String message) {
        final String helper = message;
        if (store1.add(message) == true) {
            lv.post(new Runnable() {
                @Override
                public void run() {
                    arrayList.add(helper);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    /*
     * Sends the respective message on Button click event
     *
     * @param arg0
     */
    @Override
    public void onClick(View arg0) {
        String message = msg_snd.getText().toString();
        msg_broadcast.sendMulticast(message);

        msg_snd.setText(null);

    }

    /*
     * Receives and displays messages
     *
     * @param arg0
     */
    @Override
    public void receive(InetAddress address, final String msg) {

        /*
         *   Uncomment for demo video
         */

       //setup(msg);
        print(msg);
        forward(msg);
    }


    public void setup(final String msg) {
        //Run code on the UI thread...
        runOnUiThread(new Runnable() {
            @ Override
            public void run() {
                //Create the alert dialog
                showSimplePopUp(msg);
            }
        });
    }

    /*
     * Multi-hop code
     */
    public void forward(String msg) {
        while (true) {
            try {
                msg_broadcast.sendMulticast(msg);
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showSimplePopUp(String message) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("New Alert");
        helpBuilder.setMessage(message);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });
        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
}