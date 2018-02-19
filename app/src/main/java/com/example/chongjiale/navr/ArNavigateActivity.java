package com.example.chongjiale.navr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class ArNavigateActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        FrameLayout arViewPane = (FrameLayout) findViewById(R.id.ar_view_pane);

        ArNavigateView arDisplay = new ArNavigateView(this,this);
        arViewPane.addView(arDisplay);

        OverlayView arContent = new OverlayView(getApplicationContext());
        arViewPane.addView(arContent);
    }
}
