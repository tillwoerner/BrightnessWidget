package priv.twoerner.brightnesswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

public final class ViewConfig {

    public static final String ACTION_1 = "ACTION_1";
    public static final String ACTION_2 = "ACTION_2";
    public static final String ACTION_3 = "ACTION_3";
    public static final String ACTION_4 = "ACTION_4";
    public static final String ACTION_5 = "ACTION_5";

    private ViewConfig() {
    };

    public static RemoteViews configView(RemoteViews views, Context context, int appWidgetId) {

	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

	boolean controlTouchBrightness = prefs.getBoolean("control_touch_brightness_" + appWidgetId, false);
	boolean showControlTouchBrightness = prefs.getBoolean("show_touch_brightness_value_" + appWidgetId, false);
	int mTextColor = Integer.parseInt(prefs.getString("widget_text_color_" + appWidgetId, Integer.toString(Color.WHITE)));

	// 20 percent button
	// Create an Intent to launch ExampleActivity
	Intent intent = new Intent(context, BrightnessWidgetProvider.class);
	intent.setAction(ACTION_1);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to the button
	views.setOnClickPendingIntent(R.id.button_1, pendingIntent);
	// TODO: Set configured color on all buttons
	views.setInt(R.id.button_1, "setTextColor", mTextColor);
	if (controlTouchBrightness && showControlTouchBrightness) {
	    views.setTextViewText(R.id.button_1,
		    prefs.getString("button1_" + appWidgetId, "20") + "%\n" + prefs.getString("button1_touchbrightness_" + appWidgetId, "10")
			    + "00uA");
	} else {
	    views.setTextViewText(R.id.button_1, prefs.getString("button1_" + appWidgetId, "20") + "%");
	}

	// 40 percent button
	// Create an Intent to launch ExampleActivity
	intent = new Intent(context, BrightnessWidgetProvider.class);
	intent.setAction(ACTION_2);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to the button
	views.setOnClickPendingIntent(R.id.button_2, pendingIntent);
	views.setInt(R.id.button_2, "setTextColor", mTextColor);
	if (controlTouchBrightness && showControlTouchBrightness) {
	    views.setTextViewText(R.id.button_2,
		    prefs.getString("button2_" + appWidgetId, "40") + "%\n" + prefs.getString("button2_touchbrightness_" + appWidgetId, "10")
			    + "00uA");
	} else {
	    views.setTextViewText(R.id.button_2, prefs.getString("button2_" + appWidgetId, "40") + "%");
	}

	// 60 percent button
	// Create an Intent to launch ExampleActivity
	intent = new Intent(context, BrightnessWidgetProvider.class);
	intent.setAction(ACTION_3);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to the button
	views.setOnClickPendingIntent(R.id.button_3, pendingIntent);
	views.setInt(R.id.button_3, "setTextColor", mTextColor);
	if (controlTouchBrightness && showControlTouchBrightness) {
	    views.setTextViewText(R.id.button_3,
		    prefs.getString("button3_" + appWidgetId, "60") + "%\n" + prefs.getString("button3_touchbrightness_" + appWidgetId, "10")
			    + "00uA");
	} else {
	    views.setTextViewText(R.id.button_3, prefs.getString("button3_" + appWidgetId, "60") + "%");
	}

	// 80 percent button
	// Create an Intent to launch ExampleActivity
	intent = new Intent(context, BrightnessWidgetProvider.class);
	intent.setAction(ACTION_4);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to the button
	views.setOnClickPendingIntent(R.id.button_4, pendingIntent);
	views.setInt(R.id.button_4, "setTextColor", mTextColor);
	if (controlTouchBrightness && showControlTouchBrightness) {
	    views.setTextViewText(R.id.button_4,
		    prefs.getString("button4_" + appWidgetId, "80") + "%\n" + prefs.getString("button4_touchbrightness_" + appWidgetId, "10")
			    + "00uA");
	} else {
	    views.setTextViewText(R.id.button_4, prefs.getString("button4_" + appWidgetId, "80") + "%");
	}

	// 100 percent button
	// Create an Intent to launch ExampleActivity
	intent = new Intent(context, BrightnessWidgetProvider.class);
	intent.setAction(ACTION_5);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to the button
	views.setOnClickPendingIntent(R.id.button_5, pendingIntent);
	views.setInt(R.id.button_5, "setTextColor", mTextColor);
	if (controlTouchBrightness && showControlTouchBrightness) {
	    views.setTextViewText(R.id.button_5,
		    prefs.getString("button5_" + appWidgetId, "100") + "%\n" + prefs.getString("button5_touchbrightness_" + appWidgetId, "10")
			    + "00uA");
	} else {
	    views.setTextViewText(R.id.button_5, prefs.getString("button5_" + appWidgetId, "100") + "%");
	}

	return views;
    }
}
