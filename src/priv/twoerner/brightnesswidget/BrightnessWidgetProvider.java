package priv.twoerner.brightnesswidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

public class BrightnessWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "BrightnessWidgetProvider";

    private static final String ACTION_20 = "ACTION_20";
    private static final String ACTION_40 = "ACTION_40";
    private static final String ACTION_60 = "ACTION_60";
    private static final String ACTION_80 = "ACTION_80";
    private static final String ACTION_100 = "ACTION_100";

    @Override
    public void onReceive(Context context, Intent intent) {
	super.onReceive(context, intent);

	Log.d(TAG, "onReceive");
	Log.d(TAG, intent.getAction());

	if (intent != null && intent.getAction() != null) {
	    int sysBackLightValue = 255;
	    float factor = 1.0f;
	    if (intent.getAction().equals(ACTION_20)) {
		factor = 20f / 100f;
	    } else if (intent.getAction().equals(ACTION_40)) {
		factor = 40f / 100f;
	    } else if (intent.getAction().equals(ACTION_60)) {
		factor = 60f / 100f;
	    } else if (intent.getAction().equals(ACTION_80)) {
		factor = 80f / 100f;
	    } else if (intent.getAction().equals(ACTION_100)) {
		factor = 100f / 100f;
	    }
	    sysBackLightValue = (int) (factor * 255);
	    Log.d(TAG, Integer.toString(sysBackLightValue));
	    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, 0);
	    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, sysBackLightValue);

	    Intent updateIntent = new Intent();
	    updateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    updateIntent.putExtra("factor", factor);
	    updateIntent.setClass(context, UpdateBrightnessActivity.class);
	    context.startActivity(updateIntent);

	}

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
	// TODO Auto-generated method stub
	Log.d(TAG, "onUpdate");

	final int N = appWidgetIds.length;

	// Perform this loop procedure for each App Widget that belongs to this provider
	for (int i = 0; i < N; i++) {
	    int appWidgetId = appWidgetIds[i];

	    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.brightness_widget);

	    // 20 percent button
	    // Create an Intent to launch ExampleActivity
	    Intent intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_20);
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_20, pendingIntent);

	    // 40 percent button
	    // Create an Intent to launch ExampleActivity
	    intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_40);
	    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_40, pendingIntent);

	    // 60 percent button
	    // Create an Intent to launch ExampleActivity
	    intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_60);
	    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_60, pendingIntent);

	    // 80 percent button
	    // Create an Intent to launch ExampleActivity
	    intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_80);
	    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_80, pendingIntent);

	    // 100 percent button
	    // Create an Intent to launch ExampleActivity
	    intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_100);
	    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_100, pendingIntent);

	    // Tell the AppWidgetManager to perform an update on the current App Widget
	    appWidgetManager.updateAppWidget(appWidgetId, views);
	}
    }

}
