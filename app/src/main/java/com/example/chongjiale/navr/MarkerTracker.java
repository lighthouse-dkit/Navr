package com.example.chongjiale.navr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.chongjiale.navr.rendering.external.CustomSurfaceView;
import com.example.chongjiale.navr.rendering.external.Driver;
import com.example.chongjiale.navr.rendering.external.GLRenderer;
import com.example.chongjiale.navr.rendering.external.StrokedRectangle;
import com.wikitude.NativeStartupConfiguration;
import com.wikitude.WikitudeSDK;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.rendering.ExternalRendering;
import com.wikitude.tracker.ImageTarget;
import com.wikitude.tracker.ImageTracker;
import com.wikitude.tracker.ImageTrackerListener;
import com.wikitude.tracker.TargetCollectionResource;
import com.wikitude.tracker.TargetCollectionResourceLoadingCallback;


public class MarkerTracker extends Activity implements ImageTrackerListener, ExternalRendering {

    private static final String TAG = "SimpleClientTracking";

    private WikitudeSDK wikitudeSDK;
    private CustomSurfaceView customView;
    private Driver mDriver;
    private GLRenderer mGLRenderer;

    private DropDownAlert mDropDownAlert;
    private TargetCollectionResource mTargetCollectionResource;
//    private DropDownAlert mDropDownAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        wikitudeSDK = new WikitudeSDK(this);
        NativeStartupConfiguration startupConfiguration = new NativeStartupConfiguration();
        startupConfiguration.setLicenseKey(WikitudeConstant.WIKITUDE_SDK_KEY);
        startupConfiguration.setCameraPosition(CameraSettings.CameraPosition.BACK);
        startupConfiguration.setCameraResolution(CameraSettings.CameraResolution.AUTO);


        wikitudeSDK.onCreate(getApplicationContext(), this, startupConfiguration);
        mTargetCollectionResource = wikitudeSDK.getTrackerManager().createTargetCollectionResource("file:///android_asset/location.wtc", new TargetCollectionResourceLoadingCallback() {
            @Override
            public void onError(int errorCode, String errorMessage) {
                Log.v(TAG, "Failed to load target collection resource. Reason: " + errorMessage);
            }

            @Override
            public void onFinish() {
                wikitudeSDK.getTrackerManager().createImageTracker(mTargetCollectionResource, MarkerTracker.this, null);
            }
        });


        mDropDownAlert = new DropDownAlert(this);
        mDropDownAlert.setText("Scan Target Marker :");
        mDropDownAlert.addImages("sample.jpg");
        mDropDownAlert.setTextWeight(0.5f);
        mDropDownAlert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wikitudeSDK.onResume();
        customView.onResume();
        mDriver.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wikitudeSDK.onPause();
        customView.onPause();
        mDriver.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wikitudeSDK.clearCache();
    }

    @Override
    public void onRenderExtensionCreated(final RenderExtension renderExtension) {
        mGLRenderer = new GLRenderer(renderExtension);
        customView = new CustomSurfaceView(getApplicationContext(), mGLRenderer);
        mDriver = new Driver(customView, 30);
        setContentView(customView);
    }

    @Override
    public void onTargetsLoaded(ImageTracker tracker) {
        Log.v(TAG, "Image tracker loaded");
    }

    @Override
    public void onErrorLoadingTargets(ImageTracker tracker, int errorCode, final String errorMessage) {
        Log.v(TAG, "Unable to load image tracker. Reason: " + errorMessage);
    }


    /* When the image tracker first recognizes a target
        provide recognized target name
    */
    @Override
    public void onImageRecognized(ImageTracker tracker, final ImageTarget target) {
        Log.v(TAG, "Recognized target " + target.getName());
        mDropDownAlert.dismiss();
        StrokedRectangle strokedRectangle = new StrokedRectangle(StrokedRectangle.Type.STANDARD);
        mGLRenderer.setRenderablesForKey(target.getName() + target.getUniqueId(), strokedRectangle, null);
        Intent intent = new Intent(this, RoomSearching.class);
        intent.putExtra("Room_Name", target.getName());
        startActivity(intent);
    }

    /*When the image tracker starts tracking this target it will call onImageTracked continuously
     until it loses the target and finishes tracking with a call to onImageLost.*/


    @Override
    public void onImageTracked(ImageTracker tracker, final ImageTarget target) {
        StrokedRectangle strokedRectangle = (StrokedRectangle)mGLRenderer.getRenderableForKey(target.getName() + target.getUniqueId());

        if (strokedRectangle != null) {
            strokedRectangle.projectionMatrix = target.getProjectionMatrix();
            strokedRectangle.viewMatrix = target.getViewMatrix();

            strokedRectangle.setXScale(target.getTargetScale().x);
            strokedRectangle.setYScale(target.getTargetScale().y);
        }
    }

    @Override
    public void onImageLost(ImageTracker tracker, final ImageTarget target) {
        Log.v(TAG, "Lost target " + target.getName());
        mGLRenderer.removeRenderablesForKey(target.getName() + target.getUniqueId());
    }

    @Override
    public void onExtendedTrackingQualityChanged(ImageTracker tracker, final ImageTarget target, final int oldTrackingQuality, final int newTrackingQuality) {

    }
}
