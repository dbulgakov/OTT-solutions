package com.dbulgakov.entitycounterview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Custom View, that inherits from {@link TextView}.
 * Created to debug onMeasure method for custom ViewGroups.
 */


public class CustomTextView extends TextView{

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * Called to determine the size requirements for this view and all of its children.
     * Does same work, as onMeasure method in {@link TextView},
     * but also writes into log when called.
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("Measuring info", String.format("onMeasure call for %s", getText().toString()));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
