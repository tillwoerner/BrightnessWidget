package priv.twoerner.brightnesswidget.customctrls;

import priv.twoerner.brightnesswidget.R;
import yuku.ambilwarna.ColorPicker;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class CustomColorPreference extends DialogPreference {

    /**
     * The edit text shown in the dialog.
     */
    private ColorPicker mColorPicker;

    private String mText;

    public CustomColorPreference(Context context, AttributeSet attrs, int defStyle) {
	// super(context, attrs, defStyle);
	super(context, attrs);
	setDialogLayoutResource(R.layout.preference_dialog_colorpicker);
	mColorPicker = new ColorPicker(context, attrs);

	// Give it an ID so it can be saved/restored
	mColorPicker.setId(R.id.colorpicker);

	/*
	 * The preference framework and view framework both have an 'enabled' attribute. Most likely, the 'enabled'
	 * specified in this XML is for the preference framework, but it was also given to the view framework. We reset
	 * the enabled state.
	 */
	mColorPicker.setEnabled(true);

    }

    public CustomColorPreference(Context context, AttributeSet attrs) {
	this(context, attrs, 0);
    }

    public CustomColorPreference(Context context) {
	this(context, null);
    }

    /**
     * Saves the text to the {@link SharedPreferences}.
     * 
     * @param text
     *            The text to save
     */
    public void setText(String text) {
	final boolean wasBlocking = shouldDisableDependents();

	mText = text;

	persistString(text);

	final boolean isBlocking = shouldDisableDependents();
	if (isBlocking != wasBlocking) {
	    notifyDependencyChange(isBlocking);
	}
    }

    /**
     * Gets the text from the {@link SharedPreferences}.
     * 
     * @return The current preference value.
     */
    public String getText() {
	return mText;
    }

    @Override
    protected void onBindDialogView(View view) {
	super.onBindDialogView(view);

	ColorPicker colorPicker = mColorPicker;
	colorPicker.setColor(getText() == null ? Color.WHITE : Integer.parseInt(getText()));

	ViewParent oldParent = colorPicker.getParent();
	if (oldParent != view) {
	    if (oldParent != null) {
		((ViewGroup) oldParent).removeView(colorPicker);
	    }
	    onAddColorPickerToDialogView(view, colorPicker);
	}
    }

    /**
     * Adds the EditText widget of this preference to the dialog's view.
     * 
     * @param dialogView
     *            The dialog view.
     */
    protected void onAddColorPickerToDialogView(View dialogView, ColorPicker colorPicker) {
	ViewGroup container = (ViewGroup) dialogView.findViewById(R.id.colorpicker_container);
	if (container != null) {
	    container.addView(colorPicker, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	}
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
	super.onDialogClosed(positiveResult);

	if (positiveResult) {
	    String value = Integer.toString(mColorPicker.getColor());
	    if (callChangeListener(value)) {
		setText(value);
	    }
	    notifyChanged();
	}
    }

    // @Override
    // public CharSequence getSummary() {
    // String text = getText();
    // if (text != null && text.length() > 0) {
    // return text;
    // }
    // return super.getSummary();
    // }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
	return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
	setText(restoreValue ? getPersistedString(mText) : (String) defaultValue);
    }

    @Override
    public boolean shouldDisableDependents() {
	return TextUtils.isEmpty(mText) || super.shouldDisableDependents();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
	final Parcelable superState = super.onSaveInstanceState();
	if (isPersistent()) {
	    // No need to save instance state since it's persistent
	    return superState;
	}

	final SavedState myState = new SavedState(superState);
	myState.text = getText();
	return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
	if (state == null || !state.getClass().equals(SavedState.class)) {
	    // Didn't save state for us in onSaveInstanceState
	    super.onRestoreInstanceState(state);
	    return;
	}

	SavedState myState = (SavedState) state;
	super.onRestoreInstanceState(myState.getSuperState());
	setText(myState.text);
    }

    private static class SavedState extends BaseSavedState {
	String text;

	public SavedState(Parcel source) {
	    super(source);
	    text = source.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	    super.writeToParcel(dest, flags);
	    dest.writeString(text);
	}

	public SavedState(Parcelable superState) {
	    super(superState);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
	    public SavedState createFromParcel(Parcel in) {
		return new SavedState(in);
	    }

	    public SavedState[] newArray(int size) {
		return new SavedState[size];
	    }
	};
    }

}
