package org.mark.camera;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.media.MediaRecorder;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by mark on 2019/3/16
 */
public class ConfigTexture implements IConfig {

    private int width;
    private int height;
    private Surface surface;
    private ConfigRecorder configRecorder;
    TextureView textureView;

    public ConfigTexture(int width, int height, TextureView textureView,  Activity activity){
        this.width = width;
        this.height = height;
        this.textureView = textureView;
        configRecorder =new ConfigRecorder(this, activity);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Surface getSurface() {
        SurfaceTexture texture = textureView.getSurfaceTexture();
        texture.setDefaultBufferSize(width, height);
        return new Surface(texture);
    }

    @Override
    public ConfigRecorder getMediaRecorder() {
        return configRecorder;
    }

    @Override
    public void release() {
        configRecorder.stop();
    }
}
