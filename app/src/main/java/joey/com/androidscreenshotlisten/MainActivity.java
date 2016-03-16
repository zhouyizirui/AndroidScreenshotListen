package joey.com.androidscreenshotlisten;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnScreenShotTakenListener {

    private ImageView mScreenShotView;

    private ScreenCaptureObserver mScreenCaptureObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScreenShotView = (ImageView) findViewById(R.id.screenshot);

        mScreenCaptureObserver = new ScreenCaptureObserver(this);
        mScreenCaptureObserver.addObserver("/sdcard/Pictures/Screenshots/");
        mScreenCaptureObserver.addObserver("/storage/sdcard0/Pictures/Screenshots/");
        mScreenCaptureObserver.addObserver("/storage/sdcard0/DCIM/Screenshots/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScreenCaptureObserver != null) {
            mScreenCaptureObserver.cleanObservers();
            mScreenCaptureObserver = null;
        }
    }

    @Override
    public void onScreenshotTaken(Uri fileUri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), fileUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            mScreenShotView.setImageBitmap(bitmap);
        }
    }
}
