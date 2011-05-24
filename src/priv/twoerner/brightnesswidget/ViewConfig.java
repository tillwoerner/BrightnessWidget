package priv.twoerner.brightnesswidget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

public final class ViewConfig {

    public static final String ACTION_1 = "ACTION_1";
    public static final String ACTION_2 = "ACTION_2";
    public static final String ACTION_3 = "ACTION_3";
    public static final String ACTION_4 = "ACTION_4";
    public static final String ACTION_5 = "ACTION_5";

    private ViewConfig(){};
    
    public static RemoteViews configView(RemoteViews views, Context context){
	
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

	    // 20 percent button
	    // Create an Intent to launch ExampleActivity
	    Intent intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_1);
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_1, pendingIntent);
	    // TODO: Set configured color on all buttons
	    //views.setInt(R.id.button_20, "setTextColor", Color.rgb(255, 0, 0));
	    //views.setInt(R.id.button_1, "setTextColor", Color.BLACK);
	    views.setTextViewText(R.id.button_1, prefs.getString("button1", "20") + "%");

	    // 40 percent button
	    // Create an Intent to launch ExampleActivity
	    intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_2);
	    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_2, pendingIntent);
	    views.setTextViewText(R.id.button_2, prefs.getString("button2", "40") + "%");

	    // 60 percent button
	    // Create an Intent to launch ExampleActivity
	    intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_3);
	    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_3, pendingIntent);
	    views.setTextViewText(R.id.button_3, prefs.getString("button3", "60") + "%");

	    // 80 percent button
	    // Create an Intent to launch ExampleActivity
	    intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_4);
	    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_4, pendingIntent);
	    views.setTextViewText(R.id.button_4, prefs.getString("button4", "80") + "%");

	    // 100 percent button
	    // Create an Intent to launch ExampleActivity
	    intent = new Intent(context, BrightnessWidgetProvider.class);
	    intent.setAction(ACTION_5);
	    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
	    // Get the layout for the App Widget and attach an on-click listener to the button
	    views.setOnClickPendingIntent(R.id.button_5, pendingIntent);
	    views.setTextViewText(R.id.button_5, prefs.getString("button5", "100") + "%");
	
	
	
	return views;
    }
}
