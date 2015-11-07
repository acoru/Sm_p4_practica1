package com.example.adrin.sm_p4_practica1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ActivitySecond extends AppCompatActivity {

    TextView tvUser, tvPass, tvDomain, tvPort;
    String user, password, domain, port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_second);
        Intent intent = getIntent();

        //taking the data send by the other activity

        user = intent.getStringExtra("user");
        password = intent.getStringExtra("pass");
        domain = intent.getStringExtra("domain");
        port = intent.getStringExtra("port");

        //putting the values into the text view

        tvUser = (TextView) findViewById(R.id.tvUser);
        tvPass = (TextView) findViewById(R.id.tvPass);
        tvDomain = (TextView) findViewById(R.id.tvDomain);
        tvPort = (TextView) findViewById(R.id.tvPort);

        tvUser.setText(user);
        tvPass.setText(password);
        tvDomain.setText(domain);
        tvPort.setText(port);

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

}