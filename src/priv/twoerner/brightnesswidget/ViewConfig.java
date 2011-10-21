package priv.twoerner.brightnesswidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;

public final class ViewConfig {

    private final static String TAG = "priv.twoerner.brightnesswidget.ViewConfig";
    public static final String ACTION_1 = "ACTION_1";
    public static final String ACTION_2 = "ACTION_2";
    public static final String ACTION_3 = "ACTION_3";
    public static final String ACTION_4 = "ACTION_4";
    public static final String ACTION_5 = "ACTION_5";
    public static final String ACTION_AUTO = "ACTION_AUTO";

    private ViewConfig() {
    };

    public static RemoteViews configView(RemoteViews views, Context context, int appWidgetId, Class<?> providerClass) {

	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	List<String> preferenceKeys = new ArrayList<String>();

	int mTextColor = Integer.parseInt(prefs.getString("widget_text_color_" + appWidgetId, Integer.toString(Color.WHITE)));

	// TODO: read number of buttons and their configuration from the
	// preferences
	Map<String, ?> preferenceMap = prefs.getAll();
	for (String key : preferenceMap.keySet()) {
	    if (key.endsWith("_" + appWidgetId)) {
		preferenceKeys.add(key);
		Log.d(TAG, "Key for widget ID " + appWidgetId + ": " + key);
	    }
	}

	View button = LayoutInflater.from(context).inflate(R.layout.brightness_widget, null);
	// views.addView(R.id.rootLinearLayout, button);

	// 20 percent button
	Intent intent = new Intent(context, providerClass);
	intent.setAction(ACTION_1);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to
	// the button
	views.setOnClickPendingIntent(R.id.button_1, pendingIntent);
	views.setInt(R.id.button_1, "setTextColor", mTextColor);
	views.setTextViewText(R.id.button_1, prefs.getString("button1_" + appWidgetId, "20") + "%");

	// 40 percent button
	intent = new Intent(context, providerClass);
	intent.setAction(ACTION_2);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to
	// the button
	views.setOnClickPendingIntent(R.id.button_2, pendingIntent);
	views.setInt(R.id.button_2, "setTextColor", mTextColor);
	views.setTextViewText(R.id.button_2, prefs.getString("button2_" + appWidgetId, "40") + "%");

	// 60 percent button
	intent = new Intent(context, providerClass);
	intent.setAction(ACTION_3);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to
	// the button
	views.setOnClickPendingIntent(R.id.button_3, pendingIntent);
	views.setInt(R.id.button_3, "setTextColor", mTextColor);
	views.setTextViewText(R.id.button_3, prefs.getString("button3_" + appWidgetId, "60") + "%");

	// 80 percent button
	intent = new Intent(context, providerClass);
	intent.setAction(ACTION_4);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to
	// the button
	views.setOnClickPendingIntent(R.id.button_4, pendingIntent);
	views.setInt(R.id.button_4, "setTextColor", mTextColor);
	views.setTextViewText(R.id.button_4, prefs.getString("button4_" + appWidgetId, "80") + "%");

	// 100 percent button
	intent = new Intent(context, providerClass);
	intent.setAction(ACTION_5);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to
	// the button
	views.setOnClickPendingIntent(R.id.button_5, pendingIntent);
	views.setInt(R.id.button_5, "setTextColor", mTextColor);
	views.setTextViewText(R.id.button_5, prefs.getString("button5_" + appWidgetId, "100") + "%");

	// auto button
	intent = new Intent(context, providerClass);
	intent.setAction(ACTION_AUTO);
	intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
	// Get the layout for the App Widget and attach an on-click listener to
	// the button
	views.setOnClickPendingIntent(R.id.button_auto, pendingIntent);
	views.setInt(R.id.button_auto, "setTextColor", mTextColor);
	views.setTextViewText(R.id.button_auto, prefs.getString("buttonauto_" + appWidgetId, "Auto"));

	return views;
    }
}
