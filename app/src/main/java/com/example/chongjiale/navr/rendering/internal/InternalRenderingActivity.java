package com.example.chongjiale.navr.rendering.internal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.chongjiale.navr.WikitudeConstant;
import com.wikitude.NativeStartupConfiguration;
import com.wikitude.WikitudeSDK;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.InternalRendering;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.common.rendering.RenderSettings;

import com.wikitude.tracker.ImageTarget;
import com.wikitude.tracker.ImageTracker;
import com.wikitude.tracker.ImageTrackerListener;
import com.wikitude.tracker.TargetCollectionResource;
import com.wikitude.tracker.TargetCollectionResourceLoadingCallback;

public class InternalRenderingActivity extends Activity implements InternalRendering, ImageTrackerListener {

    private static final String TAG = "InternalRendering";
    private WikitudeSDK wikitudeSDK;
    private CustomRenderExtension mRenderExtension;

    private TargetCollectionResource mTargetCollectionResource;
//    private DropDownAlert mDropDownAlert;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wikitudeSDK = new WikitudeSDK(this);
        NativeStartupConfiguration startupConfiguration = new NativeStartupConfiguration();
        startupConfiguration.setLicenseKey(WikitudeConstant.WIKITUDE_SDK_KEY);
        startupConfiguration.setCameraPosition(CameraSettings.CameraPosition.BACK);
        startupConfiguration.setCameraResolution(CameraSettings.CameraResolution.AUTO);
        startupConfiguration.setRenderingAPI(RenderSettings.RenderingAPI.OPENGL_ES_3, RenderSettings.RenderingAPI.OPENGL_ES_2);

        wikitudeSDK.onCreate(getApplicationContext(), this, startupConfiguration);
        mTargetCollectionResource = wikitudeSDK.getTrackerManager().createTargetCollectionResource("file:///android_asset/magazine.wtc", new TargetCollectionResourceLoadingCallback() {
            @Override
            public void onError(int errorCode, String errorMessage) {
                Log.v(TAG, "Failed to load target collection resource. Reason: " + errorMessage);
            }

            @Override
            public void onFinish() {
                wikitudeSDK.getTrackerManager().createImageTracker(mTargetCollectionResource, InternalRenderingActivity.this, null);
            }
        });
        setContentView(wikitudeSDK.setupWikitudeGLSurfaceView());

//        mDropDownAlert = new DropDownAlert(this);
//        mDropDownAlert.setText("Scan Target #1 (surfer):");
//        mDropDownAlert.addImages("surfer.png");
//        mDropDownAlert.setTextWeight(0.5f);
//        mDropDownAlert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wikitudeSDK.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wikitudeSDK.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wikitudeSDK.clearCache();
        wikitudeSDK.onDestroy();
    }

    @Override
    public RenderExtension provideRenderExtension() {
        mRenderExtension = new CustomRenderExtension();
        return mRenderExtension;
    }

    @Override
    public void onRenderingApiInstanceCreated(RenderSettings.RenderingAPI renderingAPI) {
        String renderingAPIName = renderingAPI == RenderSettings.RenderingAPI.OPENGL_ES_3 ?
                "OpenGL ES 3.0" : "OpenGL ES 2.0";
        Log.v(TAG, "Rendering connection was created with rendering API " + renderingAPIName);
    }

    @Override
    public void onErrorLoadingTargets(ImageTracker tracker, int errorCode, final String errorMessage) {
        Log.v(TAG, "Unable to load image tracker. Reason: " + errorMessage);
    }

    @Override
    public void onTargetsLoaded(ImageTracker tracker) {
        Log.v(TAG, "Image tracker loaded");
    }

    @Override
    public void onImageRecognized(ImageTracker tracker, final ImageTarget target) {
        Log.v(TAG, "Recognized target " + target.getName());
//        mDropDownAlert.dismiss();
    }

    @Override
    public void onImageTracked(ImageTracker tracker, final ImageTarget target) {
        mRenderExtension.setCurrentlyRecognizedTarget(target);
    }

    @Override
    public void onImageLost(ImageTracker tracker, final ImageTarget target) {
        Log.v(TAG, "Lost target " + target.getName());
        mRenderExtension.setCurrentlyRecognizedTarget(null);
    }

    @Override
    public void onExtendedTrackingQualityChanged(ImageTracker tracker, final ImageTarget target, final int oldTrackingQuality, final int newTrackingQuality) {

    }
}
