package com.example.chongjiale.navr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RoomSearching extends AppCompatActivity {

    EditText from;
    EditText destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_searching);

        String roomName= getIntent().getStringExtra("Room_Name");

        from= (EditText)findViewById(R.id.from);
        from.setText(roomName);

        destination=(EditText)findViewById(R.id.destination);
        destination.setText("test");

    }
}
