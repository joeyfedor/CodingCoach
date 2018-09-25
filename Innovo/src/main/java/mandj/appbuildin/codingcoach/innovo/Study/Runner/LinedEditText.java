package mandj.appbuildin.codingcoach.innovo.Study.Runner;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.EditText;

/**
 * Created by Monica on 7/3/2014.
 */
public class LinedEditText extends EditText {
    private Rect mRect;
    private Paint mPaint;

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        // define the style of line
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // define the color of line
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        // Gets the number of lines of text in the View.
        int count = getLineCount();
        Rect r = mRect;
        Paint paintLine = mPaint;

        Canvas canvas1 = canvas;


        //if the prefrences is set to false the lines are not created for the textview.
        //Is in edit mode makes it so that the ide will display it correctly on the editScreen
        if (!isInEditMode()) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            if (!preferences.getBoolean("lined_checkbox", true)) {
                count = 0;
            }
        }
        paintLine.setColor(Color.LTGRAY);
        /*
         * Draws one line in the rectangle for every line of text in the EditText
         */
        for (int i = 0; i < count; i++) {

            // Gets the baseline coordinates for the current line of text
            int baseline = getLineBounds(i, r);

            /*
             * Draws a line in the background from the left of the rectangle to the right,
             * at a vertical position one dip below the baseline, using the "paint" object
             * for details.
             */
            canvas.drawLine(r.left, baseline + 4, r.right, baseline + 4, paintLine);
        }

        // Finishes up by calling the parent method
        super.onDraw(canvas);

        paintLine.setColor(Color.RED);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int px = 18 * (metrics.densityDpi / 160);
        paintLine.setTextSize(px);

        for (int i = 0; i < count; i++) {

            // Gets the baseline coordinates for the current line of text
            int baseline = getLineBounds(i, r);

            /*
             * Draws a line in the background from the left of the rectangle to the right,
             * at a vertical position one dip below the baseline, using the "paint" object
             * for details.
             */
            canvas.drawText(String.valueOf(i) + ".", 0 , baseline, paintLine);

        }

        super.onDraw(canvas1);
    }
}
