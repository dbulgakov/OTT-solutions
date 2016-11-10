package com.dbulgakov.entitycounterview;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Custom View, that inherits from {@link FrameLayout}.
 * Created to implement flip animation for {@link TextView}
 */

public class FlipNumberView extends FrameLayout{

    private static final int ANIMATION_DURATION = 75;
    private static final int DOWNWARD_DIRECTION = 1;
    private static final int UPWARD_DIRECTION = -1;


    private TextView currentTextView;
    private TextView nextTextView;
    private int currentValue;


    /**
     * Class constructor taking only a context. Used to create
     * {@link FlipNumberView} objects from code.
     *
     * @param context current context
     */

    public FlipNumberView(Context context) {
        super(context);
        initialize(context);
    }


    /**
     * Class constructor taking a context and an attribute set. This constructor
     * is used by the layout engine to construct a {@link FlipNumberView} from a set of
     * XML attributes.
     *
     * @param context current context
     * @param attrs xml attributes
     */

    public FlipNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }


    /**
     * Method used to inflate layout
     *
     * @param context current context
     */

    private void initialize(Context context) {
        View rootView = inflate(context, R.layout.flip_number_layout, this);
        currentTextView = (TextView) rootView.findViewById(R.id.first_text_view);
        nextTextView = (TextView) rootView.findViewById(R.id.second_text_view);
    }


    /**
     * Method to set new value on child {@link TextView} with
     * flip animation
     *
     * @param newValue new value to be set on child {@link TextView}
     */

    public void setValue(final int newValue){

        // if TextView is empty, setting value without animation
        if (checkIfEmpty()) {
            currentTextView.setText(String.valueOf(newValue));
            currentValue = newValue;
            return;
        }

        nextTextView.setText(String.valueOf(newValue));

        // Getting the direction of animation
        // if current value is greater than the new one
        // than getting downward animation, and upward otherwise

        if (currentValue > newValue) {
            updateTextViewValue(DOWNWARD_DIRECTION, newValue);
        } else if (currentValue < newValue) {
            updateTextViewValue(UPWARD_DIRECTION, newValue);
        }

        currentValue = newValue;
    }


    /**
     * Internal method to implement animation logic for {@link #setValue(int)} method
     *
     * @param direction direction of animation (upward/downward)
     * @param newValue new value to be set
     */

    private void updateTextViewValue(int direction, final int newValue) {
        currentTextView.animate().translationY(makeNegative(direction) * currentTextView.getHeight()).setDuration(ANIMATION_DURATION).start();
        nextTextView.setTranslationY(direction * nextTextView.getHeight());
        nextTextView.animate().translationY(0).setDuration(ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                currentTextView.setText(String.valueOf(newValue));
                currentTextView.setTranslationY(0);
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        }).start();
    }


    /**
     * Simple method to check if main {@link TextView} is empty
     *
     * @return returns true in case if main {@link TextView}  is empty
     */

    public boolean checkIfEmpty() {
        return currentTextView.getText() == null || currentTextView.getText().length() == 0;
    }


    /**
     * Dummy method to invert integer sign
     *
     * @param value value to invert
     * @return inverted value
     */

    private int makeNegative(int value) {
        return -1 * value;
    }
}
