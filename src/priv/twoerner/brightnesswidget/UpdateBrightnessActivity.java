package priv.twoerner.brightnesswidget;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class UpdateBrightnessActivity extends Activity {

    private static final String TAG = "UpdateBrightnessActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Log.d(TAG, "Update activity starting");

	setContentView(R.layout.update_brightness_activity);

	WindowManager.LayoutParams lp = getWindow().getAttributes();
	Intent intent = getIntent();

	lp.screenBrightness = intent.getFloatExtra("factor", 1.0f);
	getWindow().setAttributes(lp);

	Timer timer = new Timer();

	timer.schedule(new TimerTask() {
	    @Override
	    public void run() {
		Log.d(TAG, "Update activity stopping");
		this.cancel();
		finish();
	    }
	}, 1000, 5000);
    }

}
