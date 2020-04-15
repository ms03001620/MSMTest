package org.mark.camera;

import android.media.MediaRecorder;
import android.view.Surface;

/**
 * Created by mark on 2019/3/16
 */
public interface IConfig {
    int getWidth();
    int getHeight();
    Surface getSurface();
    ConfigRecorder getMediaRecorder();
    void release();
}
