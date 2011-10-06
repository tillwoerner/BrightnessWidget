package priv.twoerner.brightnesswidget;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetSettings extends PreferenceActivity {

    private final static String TAG = "priv.twoerner.brightnesswidget.WidgetSettings";

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.xml.widget_settings);
	setResult(RESULT_CANCELED);

	// TODO: Save preferences for each widget instance?
	Bundle extras = getIntent().getExtras();
	if (extras != null) {
	    mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	Log.d(TAG, "Widget ID = " + mAppWidgetId);

	changePreferencesKeys(getPreferenceScreen());
	findPreference("show_touch_brightness_value_" + mAppWidgetId).setDependency("control_touch_brightness_" + mAppWidgetId);

	Intent touchBrightnessIntent = new Intent();
	touchBrightnessIntent.setAction(BrightnessWidgetProvider.ACTION_CHANGE_TOUCH_BRIGHTNESS);

	PackageManager packageManager = getPackageManager();
	List<ResolveInfo> list = packageManager.queryBroadcastReceivers(touchBrightnessIntent, 0);
	if (list != null && list.size() > 0) {
	    // TouchBrightness seems to be installed, enable additional settings
	    findPreference("button1_touchbrightness_" + mAppWidgetId).setEnabled(true);
	    findPreference("button2_touchbrightness_" + mAppWidgetId).setEnabled(true);
	    findPreference("button3_touchbrightness_" + mAppWidgetId).setEnabled(true);
	    findPreference("button4_touchbrightness_" + mAppWidgetId).setEnabled(true);
	    findPreference("button5_touchbrightness_" + mAppWidgetId).setEnabled(true);
	} else {
	    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("control_touch_brightness_" + mAppWidgetId);
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
	try {
	    if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

		RemoteViews views = new RemoteViews(getPackageName(), R.layout.brightness_widget);

		ComponentName cName = appWidgetManager.getAppWidgetInfo(mAppWidgetId).provider;
		views = ViewConfig.configView(views, this, mAppWidgetId, Class.forName(cName.getClassName()));

		appWidgetManager.updateAppWidget(mAppWidgetId, views);

		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, resultValue);
	    }
	} catch (ClassNotFoundException cnfe) {
	    Log.e(TAG, cnfe.getMessage(), cnfe);
	}
	super.onBackPressed();
    }

    private void changePreferencesKeys(Preference preference) {
	if (preference == null) {
	    return;
	}

	String mOldKey = preference.getKey();
	String mNewKey;
	if (mOldKey != null) {
	    mNewKey = mOldKey + "_" + mAppWidgetId;
	    preference.setKey(mNewKey);
	    Log.d(TAG, mOldKey + " --> " + mNewKey);
	} else {
	    Log.d(TAG, "- Key is null -");
	}

	if (preference instanceof PreferenceGroup) {

	    int mChildCount = ((PreferenceGroup) preference).getPreferenceCount();
	    for (int index = 0; index < mChildCount; index++) {
		changePreferencesKeys(((PreferenceGroup) preference).getPreference(index));
	    }
	}
    }
}
