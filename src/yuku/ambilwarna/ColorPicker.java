package yuku.ambilwarna;

import priv.twoerner.brightnesswidget.R;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

@SuppressWarnings("deprecation")
public class ColorPicker extends LinearLayout {
    private static final String TAG = ColorPicker.class.getSimpleName();

    private static final int DEFAULT_COLOR = Color.WHITE;

    View viewHue;
    AmbilWarnaKotak viewKotak;
    ImageView panah;
    View viewOldColor;
    View viewNewColor;
    ImageView viewContainer;

    float satudp;
    int oldColor;
    int newColor;
    float hue;
    float sat;
    float val;
    float ukuranUiDp = 240.f;
    float ukuranUiPx; // diset di constructor

    public ColorPicker(Context context) {
	this(context, null);
    }

    public ColorPicker(Context context, AttributeSet attrs) {
	this(context, attrs, 0);
    }

    public ColorPicker(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs);
	setOrientation(VERTICAL);
	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	inflater.inflate(R.layout.ambilwarna_preference, this, true);

	satudp = context.getResources().getDimension(R.dimen.ambilwarna_satudp);
	ukuranUiPx = ukuranUiDp * satudp;
	Log.d(TAG, "satudp = " + satudp + ", ukuranUiPx=" + ukuranUiPx); //$NON-NLS-1$//$NON-NLS-2$

	viewHue = findViewById(R.id.ambilwarna_viewHue);
	viewKotak = (AmbilWarnaKotak) findViewById(R.id.ambilwarna_viewKotak);
	panah = (ImageView) findViewById(R.id.ambilwarna_panah);
	viewOldColor = findViewById(R.id.ambilwarna_warnaLama);
	viewNewColor = findViewById(R.id.ambilwarna_warnaBaru);
	viewContainer = (ImageView) findViewById(R.id.ambilwarna_keker);

	setColor(DEFAULT_COLOR);

	viewHue.setOnTouchListener(new View.OnTouchListener() {
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN
			|| event.getAction() == MotionEvent.ACTION_UP) {

		    float y = event.getY(); // dalam px, bukan dp
		    if (y < 0.f) {
			y = 0.f;
		    }
		    if (y > ukuranUiPx) {
			y = ukuranUiPx - 0.001f;
		    }

		    hue = 360.f - 360.f / ukuranUiPx * y;
		    if (hue == 360.f) {
			hue = 0.f;
		    }

		    newColor = getColorAsInt();
		    // update view
		    viewKotak.setHue(hue);
		    placeArrow();
		    viewNewColor.setBackgroundColor(newColor);

		    return true;
		}
		return false;
	    }
	});
	viewKotak.setOnTouchListener(new View.OnTouchListener() {
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN
			|| event.getAction() == MotionEvent.ACTION_UP) {

		    float x = event.getX(); // dalam px, bukan dp
		    float y = event.getY(); // dalam px, bukan dp

		    if (x < 0.f) {
			x = 0.f;
		    }
		    if (x > ukuranUiPx) {
			x = ukuranUiPx;
		    }
		    if (y < 0.f) {
			y = 0.f;
		    }
		    if (y > ukuranUiPx) {
			y = ukuranUiPx;
		    }

		    sat = (1.f / ukuranUiPx * x);
		    val = 1.f - (1.f / ukuranUiPx * y);

		    newColor = getColorAsInt();
		    // update view
		    placeSpyGlass();
		    viewNewColor.setBackgroundColor(newColor);

		    return true;
		}
		return false;
	    }
	});

    }

    protected void placeArrow() {
	float y = ukuranUiPx - (hue * ukuranUiPx / 360.f);
	if (y == ukuranUiPx) {
	    y = 0.f;
	}

	AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) panah.getLayoutParams();
	layoutParams.y = (int) (y + 4);
	panah.setLayoutParams(layoutParams);
    }

    protected void placeSpyGlass() {
	float x = sat * ukuranUiPx;
	float y = (1.f - val) * ukuranUiPx;

	AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) viewContainer.getLayoutParams();
	layoutParams.x = (int) (x + 3);
	layoutParams.y = (int) (y + 3);
	viewContainer.setLayoutParams(layoutParams);
    }

    float[] tmp01 = new float[3];

    private int getColorAsInt() {
	tmp01[0] = hue;
	tmp01[1] = sat;
	tmp01[2] = val;
	return Color.HSVToColor(tmp01);
    }

    public int getColor() {
	return newColor;
    }

    public void setColor(int color) {
	this.oldColor = color;
	this.newColor = color;
	Color.colorToHSV(color, tmp01);
	hue = tmp01[0];
	sat = tmp01[1];
	val = tmp01[2];
	updateViews(color);
    }

    private void updateViews(int color) {
	placeArrow();
	placeSpyGlass();
	viewKotak.setHue(hue);
	viewOldColor.setBackgroundColor(color);
	viewNewColor.setBackgroundColor(color);
    }
}
