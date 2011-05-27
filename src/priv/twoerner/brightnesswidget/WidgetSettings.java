package priv.twoerner.brightnesswidget;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.widget.RemoteViews;

public class WidgetSettings extends PreferenceActivity {

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.xml.widget_settings);
	setResult(RESULT_CANCELED);

	Intent touchBrightnessIntent = new Intent();
	touchBrightnessIntent.setAction(BrightnessWidgetProvider.ACTION_CHANGE_TOUCH_BRIGHTNESS);

	PackageManager packageManager = getPackageManager();
	List<ResolveInfo> list = packageManager.queryBroadcastReceivers(touchBrightnessIntent, 0);
	if (list != null && list.size() > 0) {
	    // TouchBrightness seems to be installed, enable additional settings
	    findPreference("button1_touchbrightness").setEnabled(true);
	    findPreference("button2_touchbrightness").setEnabled(true);
	    findPreference("button3_touchbrightness").setEnabled(true);
	    findPreference("button4_touchbrightness").setEnabled(true);
	    findPreference("button5_touchbrightness").setEnabled(true);
	} else {
	    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("control_touch_brightness");
	    checkBoxPreference.setSummaryOff(getString(R.string.settings_control_touch_brightness_summary_na));
	    checkBoxPreference.setChecked(false);
	    checkBoxPreference.setEnabled(false);
	}
    }

    @Override
    public void onBackPressed() {
	Intent intent = getIntent();
	Bundle extras = intent.getExtras();
	if (extras != null) {
	    mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	}
	if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

	    RemoteViews views = new RemoteViews(getPackageName(), R.layout.brightness_widget);

	    views = ViewConfig.configView(views, this);

	    appWidgetManager.updateAppWidget(mAppWidgetId, views);

	    Intent resultValue = new Intent();
	    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
	    setResult(RESULT_OK, resultValue);
	}

	super.onBackPressed();
    }
}
