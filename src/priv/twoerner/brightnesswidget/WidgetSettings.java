package priv.twoerner.brightnesswidget;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import priv.twoerner.brightnesswidget.customctrls.CustomNumberEditTextPreference;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.RemoteViews;

public class WidgetSettings extends PreferenceActivity {

    private final static String TAG = "priv.twoerner.brightnesswidget.WidgetSettings";

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private int numberOfButtons = 0;
    private boolean contextMenuIsShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	addPreferencesFromResource(R.xml.widget_settings);

	setResult(RESULT_CANCELED);

	Bundle extras = getIntent().getExtras();
	if (extras != null) {
	    mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	Log.d(TAG, "Widget ID = " + mAppWidgetId);

	changePreferencesKeys(getPreferenceScreen());
	registerForContextMenu(getListView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

	if (super.onCreateOptionsMenu(menu)) {
	    getMenuInflater().inflate(R.menu.widget_settings_menu, menu);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	switch (item.getItemId()) {
	    case (R.id.add):
		Log.d(TAG, "Add menu item selected");
		addButton();
		return true;
	    case (R.id.remove):
		Log.d(TAG, "Remove menu item selected");
		removeButton();
		return true;
	    case (R.id.addAutoButton):
		Log.d(TAG, "Add auto button menu item selected");
		addAutoButton();
		return true;
	}

	return super.onOptionsItemSelected(item);
    }

    // TODO: Implement
    @Override
    public boolean onContextItemSelected(MenuItem item) {
	if (!(item.getMenuInfo() instanceof AdapterContextMenuInfo)) {
	    return false;
	}

	switch (item.getItemId()) {
	    case (R.id.ctxRemove):
		AdapterContextMenuInfo adapterContextMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		Log.d(TAG, "onContextItemSelected -> menuInfo -> position: " + adapterContextMenuInfo.position);
		Preference pref = (Preference) getListView().getItemAtPosition(adapterContextMenuInfo.position);
		removeButton(pref);
		break;
	}

	return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	if (!(menuInfo instanceof AdapterContextMenuInfo)) {
	    return;
	}

	AdapterContextMenuInfo adapterContextMenuInfo = (AdapterContextMenuInfo) menuInfo;
	Log.d(TAG, "onCreateContextMenu -> menuInfo -> position: " + adapterContextMenuInfo.position);
	Preference pref = (Preference) getListView().getItemAtPosition(adapterContextMenuInfo.position);

	// if (pref != null) {
	// pref.setTitle(pref.getTitle() + "_");
	// }
	if (pref == null || pref.getKey() == null || !pref.getKey().startsWith("button")) {
	    return;
	}
	getMenuInflater().inflate(R.menu.widget_settings_button_ctxmenu, menu);
	super.onCreateContextMenu(menu, v, menuInfo);
	contextMenuIsShowing = true;
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
	super.onContextMenuClosed(menu);
	contextMenuIsShowing = false;
    }

    private boolean addButton() {

	XmlResourceParser parser = getResources().getXml(R.xml.widget_button_setting);
	AttributeSet attributeSet = null;
	int eventType = 0;
	try {
	    while ((eventType = parser.next()) != XmlResourceParser.END_DOCUMENT) {
		if (eventType == XmlResourceParser.START_TAG) {
		    if (parser.getName().equals("priv.twoerner.brightnesswidget.customctrls.CustomNumberEditTextPreference")) {
			attributeSet = Xml.asAttributeSet(parser);
			break;
		    }
		}
	    }
	    Preference tmpPreference = new CustomNumberEditTextPreference(this, attributeSet);
	    for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
		Preference cPreference = getPreferenceScreen().getPreference(i);
		if (cPreference != null && cPreference instanceof PreferenceCategory && cPreference.getTitle() != null
			&& cPreference.getTitle().equals(getString(R.string.settings_button_category_title))) {
		    ((PreferenceCategory) cPreference).addItemFromInflater(tmpPreference);
		    numberOfButtons++;
		    return true;
		}
	    }
	    Log.e(TAG, "Could not find parent preference category");
	} catch (XmlPullParserException e) {
	    Log.e(TAG, e.getMessage(), e);
	} catch (IOException e) {
	    Log.e(TAG, e.getMessage(), e);
	} finally {
	    parser.close();
	}

	return false;
    }

    private boolean addAutoButton() {

	XmlResourceParser parser = getResources().getXml(R.xml.widget_auto_button_setting);
	AttributeSet attributeSet = null;
	int eventType = 0;
	try {
	    while ((eventType = parser.next()) != XmlResourceParser.END_DOCUMENT) {
		if (eventType == XmlResourceParser.START_TAG) {
		    if (parser.getName().equals("EditTextPreference")) {
			attributeSet = Xml.asAttributeSet(parser);
			break;
		    }
		}
	    }
	    Preference tmpPreference = new EditTextPreference(this, attributeSet);
	    for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
		Preference cPreference = getPreferenceScreen().getPreference(i);
		if (cPreference != null && cPreference instanceof PreferenceCategory && cPreference.getTitle() != null
			&& cPreference.getTitle().equals(getString(R.string.settings_button_category_title))) {
		    ((PreferenceCategory) cPreference).addItemFromInflater(tmpPreference);
		    numberOfButtons++;
		    return true;
		}
	    }
	    Log.e(TAG, "Could not find parent preference category");
	} catch (XmlPullParserException e) {
	    Log.e(TAG, e.getMessage(), e);
	} catch (IOException e) {
	    Log.e(TAG, e.getMessage(), e);
	} finally {
	    parser.close();
	}

	return false;
    }

    private boolean removeButton() {
	for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
	    Preference cPreference = getPreferenceScreen().getPreference(i);
	    if (cPreference != null && cPreference instanceof PreferenceCategory && cPreference.getTitle() != null
		    && cPreference.getTitle().equals(getString(R.string.settings_button_category_title))) {

		int childCount = ((PreferenceCategory) cPreference).getPreferenceCount();
		if (childCount > 0) {
		    Preference removePref = ((PreferenceCategory) cPreference).getPreference(childCount - 1);
		    if (((PreferenceCategory) cPreference).removePreference(removePref)) {
			numberOfButtons--;
			return true;
		    }
		}
	    }
	}
	return false;
    }

    private boolean removeButton(Preference preference) {

	for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
	    Preference cPreference = getPreferenceScreen().getPreference(i);
	    if (cPreference != null && cPreference instanceof PreferenceCategory && cPreference.getTitle() != null
		    && cPreference.getTitle().equals(getString(R.string.settings_button_category_title))) {

		if (((PreferenceCategory) cPreference).removePreference(preference)) {
		    numberOfButtons--;
		    return true;
		}

	    }
	}
	return false;
    }

    @Override
    public void onBackPressed() {

	// Do not close the activity if context menu is showing
	if (contextMenuIsShowing) {
	    return;
	}

	Intent intent = getIntent();
	Bundle extras = intent.getExtras();
	if (extras != null) {
	    mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
	}
	if (numberOfButtons > 0) {
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
	}
	super.onBackPressed();
    }

    /**
     * Changes the keys of all referenced preference keys below the given root
     * and appends the current widget id. Pease note that the method dows not
     * remove a widget id first, it is always appended!
     * 
     * @param preference
     *            The root preference to start with
     */
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
