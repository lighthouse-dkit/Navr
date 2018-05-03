package com.example.chongjiale.navr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class WayFindingActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_finding);

        FrameLayout arViewPane = (FrameLayout) findViewById(R.id.ar_view_pane);

        ArNavigateView arDisplay = new ArNavigateView(this,this);
        arViewPane.addView(arDisplay);

        OverlayView arContent = new OverlayView(getApplicationContext());
        arViewPane.addView(arContent);
    }
}
