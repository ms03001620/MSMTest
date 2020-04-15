package org.mark.camera;


import android.app.Activity;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.view.Surface;
import android.view.TextureView;

public class ConfigFactory implements IConfig {

    private IConfig config;

    public ConfigFactory(int width, int height, ImageReader.OnImageAvailableListener listenerImageAvailable) {
        config = new ConfigImageReader(width, height, listenerImageAvailable);
    }

    public ConfigFactory(int width, int height, TextureView textureView, Activity activity) {
        config = new ConfigTexture(width, height, textureView, activity);
    }

    public int getWidth() {
        return config.getWidth();
    }

    public int getHeight() {
        return config.getHeight();
    }


    public Surface getSurface() {
        return config.getSurface();
    }

    @Override
    public ConfigRecorder getMediaRecorder() {
        return config.getMediaRecorder();
    }

    @Override
    public void release() {
        config.release();
    }
}
