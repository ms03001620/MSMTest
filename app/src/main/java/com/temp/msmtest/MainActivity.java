package com.temp.msmtest;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import org.mark.camera.CameraUtils;
import org.mark.camera.ConfigFactory;
import org.mark.camera.ConfigRecorder;
import org.mark.camera.DoorbellCamera;

import java.util.Arrays;

public class MainActivity extends Activity {
    DoorbellCamera mDoorbellCamera;
    TextureView mTextureView;
    MediaRecorder mediaRecorder;
    ConfigFactory iConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextureView = findViewById(R.id.texture);
        mDoorbellCamera = DoorbellCamera.getInstance();


        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iConfig = new ConfigFactory(320, 240, mTextureView, MainActivity.this);
                mDoorbellCamera.initializeCamera(getApplicationContext(), iConfig);
            }
        });

        findViewById(R.id.btnFileInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigRecorder configRecorder = iConfig.getMediaRecorder();
                if (configRecorder != null) {
                    Log.d("MainActivity", "temp.mp4 length:" + configRecorder.getFile().length());
                }
            }
        });

        findViewById(R.id.btnInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printCameraInfo();
            }
        });
    }

    private void printCameraInfo() {
        try {
            CameraUtils.CameraInfo info = CameraUtils.makeCameraInfo(getApplicationContext());
            Log.d("MainActivity", "level:" + info.getHardwareLevel());
            Log.d("MainActivity", "size:" + Arrays.toString(info.getSizes()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mDoorbellCamera.shutDown();
        super.onDestroy();
    }
}
