package com.example.adrin.sm_p4_practica1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Message clientMess = null, serverMess = null;
    Server server = null;
    //some variables for the login authentication
    EditText etUser, etPass, etDomain, etPort;
    String s_user, s_pass, s_domain, s_port;
    int d_port;
    String serverResponse, serverReceivedMessage;
    String serverReceivedUser, serverReceivedPass, serverReceivedDomain;
    String clientMessage, serverMessage;
    int serverReceivedPort, receivedId, receivedSequence;
    int clientReceivedResponse, clientReceivedId, clientReceivedSequence;
    //communication
    byte[] buffer_in, buffer_out;

    //user and password
    String userLogin = "grijando";
    String passLogin = "grijando";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         **/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendLogin(View view)
    {
        Intent intent = new Intent(this, ActivitySecond.class);
        //getting values from the edit texts fields
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        etDomain = (EditText) findViewById(R.id.etDomain);
        etPort = (EditText) findViewById(R.id.etPort);

        s_user = etUser.getText().toString();
        s_pass = etPass.getText().toString();
        s_domain = etDomain.getText().toString();
        s_port = etPort.getText().toString();
        d_port = Integer.parseInt(s_port);

        intent.putExtra("user", s_user);
        intent.putExtra("pass", s_pass);
        intent.putExtra("domain", s_domain);
        intent.putExtra("port", s_port);

        //creating the message
        clientMess = new Message(s_user, s_pass, s_domain, d_port);

        //communication simulator
        //client side
        //buffer_out = clientMess.toByteArray();    //for create the message in bytes that will be send to the server
        clientMessage = clientMess.getMessage();
        //to implement a method for sending data to the server

        //buffer_in = buffer_out;

        //server simulator
        //receiving the data
        server = new Server(clientMessage);
        //as it will receive an id = 1 (login request), now it's necessary to call to
        //the getMessage method on the server class
        server.automaticRequestedAction();
        //getting the message
        serverReceivedMessage = server.getMessage();
        //split the received message
        String[] fields = serverReceivedMessage.split(" ");
        receivedId = Integer.parseInt(fields[0]);
        receivedSequence = Integer.parseInt(fields[1]);
        serverReceivedUser = fields[2];
        serverReceivedPass = fields[3];
        serverReceivedDomain = fields[4];
        serverReceivedPort = Integer.parseInt(fields[5]);

        //checking authentication on server side
        if(serverReceivedUser.equals(userLogin) && serverReceivedPass.equals(passLogin))
        {
            serverMessage = server.correctLogin();
        }
        else
        {
            serverMessage = server.incorrectLogin();
        }

        //implement code for sending to the client

        //buffer_in = buffer_out;

        //client receiving response from the server

        clientMess = new Message(serverMessage);
        serverResponse = clientMess.getMessage();
        String[] fieldsResponse = serverResponse.split(" ");
        clientReceivedId = Integer.parseInt(fieldsResponse[0]);
        clientReceivedSequence = Integer.parseInt(fieldsResponse[1]);
        clientReceivedResponse = Integer.parseInt(fieldsResponse[2]);

        if(clientReceivedResponse == 200)
        {
            startActivity(intent);
        }
        else if(clientReceivedResponse == 404)
        {
            //if login error, it will show a message
            Context context = getApplicationContext();
            CharSequence text = "Login error";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
}