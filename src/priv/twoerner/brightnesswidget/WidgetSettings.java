package priv.twoerner.brightnesswidget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.RemoteViews;

public class WidgetSettings extends PreferenceActivity {
    
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.xml.widget_settings);
	setResult(RESULT_CANCELED);
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
