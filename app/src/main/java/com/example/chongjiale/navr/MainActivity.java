package com.example.chongjiale.navr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.wikitude.WikitudeSDK;
import com.wikitude.common.permission.PermissionManager;
import com.wikitude.common.util.SDKBuildInformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnTrack;
    private final int CODE_PERMISSIONS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Button
        btnTrack= (Button)findViewById(R.id.tracking_button);
        btnTrack.setOnClickListener(this);

        //permission for location
        String[] neededPermissions={
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this,neededPermissions,CODE_PERMISSIONS);

//        double distance=calculateDistance(53.98192369, -6.39274222,53.98127213, -6.39178755);
//        Toast.makeText(MainActivity.this,"Distance: "+distance,Toast.LENGTH_SHORT).show();


    }


//
    @Override
    public void onClick(View view) {
        WikitudeSDK.getPermissionManager().checkPermissions(this, new String[]{Manifest.permission.CAMERA}, PermissionManager.WIKITUDE_PERMISSION_REQUEST, new PermissionManager.PermissionManagerCallback() {
            @Override
            public void permissionsGranted(int requestCode) {

                final Intent intent = new Intent(MainActivity.this,  MarkerTracker.class);
                startActivity(intent);
            }

            @Override
            public void permissionsDenied(String[] deniedPermissions) {
                Toast.makeText(MainActivity.this, "The Wikitude SDK needs the following permissions to enable an AR experience: " + Arrays.toString(deniedPermissions), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showPermissionRationale(final int requestCode, final String[] permissions) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Wikitude Permissions");
                alertBuilder.setMessage("The Wikitude SDK needs the following permissions to enable an AR experience: " + Arrays.toString(permissions));
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WikitudeSDK.getPermissionManager().positiveRationaleResult(requestCode, permissions);
                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        WikitudeSDK.getPermissionManager().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //calculate distance based on lat long
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public double calculateDistance(double lat1, double lon1,double lat2,double lon2) {
        double latDistance = Math.toRadians(lat1 - lat2);
        double lngDistance = Math.toRadians(lon1 - lon2);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        //return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));

        double meter=(AVERAGE_RADIUS_OF_EARTH_KM *c)*1000;
        return meter;
    }


}


