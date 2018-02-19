package com.example.chongjiale.navr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RoomSearching extends AppCompatActivity implements View.OnClickListener{

    EditText from;
    EditText destination;
    Button btnStartNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_searching);

        String roomName= getIntent().getStringExtra("Room_Name");

        from= (EditText)findViewById(R.id.from);
        from.setText(roomName);

        destination=(EditText)findViewById(R.id.destination);
        destination.setText("test");

        //Initialize Button
        btnStartNav= (Button)findViewById(R.id.nav_button);
        btnStartNav.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(RoomSearching.this,  ArNavigateActivity.class);
        startActivity(intent);
    }
}
