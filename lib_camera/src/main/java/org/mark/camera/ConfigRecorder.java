package org.mark.camera;

import android.app.Activity;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.Surface;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class ConfigRecorder {
    WeakReference<Activity> weakReference;
    MediaRecorder mMediaRecorder;
    File file;
    ConfigTexture configTexture;

    public ConfigRecorder(ConfigTexture configTexture, Activity activity) {
        this.configTexture = configTexture;
        mMediaRecorder = new MediaRecorder();
        weakReference = new WeakReference<>(activity);
        file = new File(activity.getFilesDir(), "temp.mp4");
    }

    public void setUpMediaRecorder() {
        Activity activity = weakReference.get();
        if (activity == null) {
            Log.d("MainActivity", "act null");
            return;
        }

        if (file.exists()) {
            Log.d("MainActivity", "temp.mp4 length:" + file.length());
            file.deleteOnExit();
        }

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        mMediaRecorder.setOutputFile(file.getAbsolutePath());
        mMediaRecorder.setVideoEncodingBitRate(10000000);
        mMediaRecorder.setMaxFileSize(10000000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(configTexture.getWidth(), configTexture.getHeight());
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        mMediaRecorder.setOrientationHint(rotation);
        mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {

            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                Log.d("MainActivity", "MediaRecorder.OnErrorListener what:" + what + ", extra:" + extra);
            }
        });

        mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED) {
                    Log.v("MainActivity", "Maximum Filesize Reached");
                    stop();
                }
            }
        });

/*        switch (mSensorOrientation) {
            case SENSOR_ORIENTATION_DEFAULT_DEGREES:
                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
                break;
            case SENSOR_ORIENTATION_INVERSE_DEGREES:
                mMediaRecorder.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation));
                break;
        }*/
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("MainActivity", "MediaRecorder.prepare", e);
            e.printStackTrace();
        }
    }


    public Surface getSurface() {
        return mMediaRecorder.getSurface();
    }

    protected void stop() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    public File getFile() {
        return file;
    }
}
