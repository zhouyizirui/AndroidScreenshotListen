package joey.com.androidscreenshotlisten;


import android.net.Uri;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.util.ArrayList;

public class ScreenCaptureObserver  {

    ArrayList<MediaFileObserver> mScreenShotObserver = new ArrayList<>();

    private OnScreenShotTakenListener mListener;

    public ScreenCaptureObserver(OnScreenShotTakenListener listener) {
        mListener = listener;
    }

    public void addObserver(String path) {
        System.out.println(path);
        if (mScreenShotObserver != null) {
            MediaFileObserver observer = new MediaFileObserver(path);
            mScreenShotObserver.add(observer);
            observer.startWatching();
        }
    }

    public void cleanObservers() {
        if (mScreenShotObserver != null) {
            for (MediaFileObserver mediaFileObserver : mScreenShotObserver) {
                mediaFileObserver.stopWatching();
            }
            mScreenShotObserver.clear();
        }
    }

    private void onScreenEvent(final Uri uri) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.onScreenshotTaken(uri);
            }
        }, 300);
    }

    public class MediaFileObserver extends FileObserver {
        private String mPath;

        public MediaFileObserver(String path) {
            super(path, FileObserver.ALL_EVENTS);
            mPath = path;
        }

        @Override
        public void onEvent(int event, String path) {
            if (path != null) {
                File file = new File(mPath + path);
                onScreenEvent(Uri.fromFile(file));
            }
        }

        @Override
        public void startWatching() {
            super.startWatching();
        }

        @Override
        public void stopWatching() {
            super.stopWatching();
        }
    }

}
