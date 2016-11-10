package com.dbulgakov.entitycounterview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Custom View to implement logic of integer selection in a view with increase/decrease value buttons
 * Based on {@link MatchParentViewGroup}.
 */

public class EntityCounterView extends MatchParentViewGroup{

    private final int DEFAULT_START_NUMBER = 2;
    private final int DEFAULT_MIN_NUMBER = 1;
    private final int DEFAULT_MAX_NUMBER = 4;

    private static final int INCREASE_VALUE = 1;
    private static final int DECREASE_VALUE = -1;
    private final static long REPEAT_INTERVAL_MS = 100L;

    private FlipNumberView entityCountView;
    private TextView entityTypeTextView;

    private boolean buttonPressed;
    private final Handler handler = new Handler();

    private int minValue;
    private int maxValue;
    private int currentValue;

    /**
     * Class constructor taking only a context. Used to create
     * {@link EntityCounterView} objects from code.
     *
     * @param context current context
     */

    public EntityCounterView(Context context) {
        super(context);
        initialize(context);
    }


    /**
     * Class constructor taking a context and an attribute set. This constructor
     * is used by the layout engine to construct a {@link EntityCounterView} from a set of
     * XML attributes.
     *
     * @param context current context
     * @param attrs xml attributes
     */

    public EntityCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
        getAttrsFromXml(context, attrs);
    }

    /**
     * Method used to inflate layout and set onClickListeners for
     * plus/minus {@link Button}.
     *
     * @param context current context
     */

    private void initialize(Context context) {
        View rootView = inflate(context, R.layout.entity_counter_layout, this);

        entityTypeTextView = (TextView) rootView.findViewById(R.id.string_description_textview);
        entityCountView = (FlipNumberView) rootView.findViewById(R.id.current_count_view);

        Button decreaseValueButton = (Button) rootView.findViewById(R.id.decrease_value_button);
        Button increaseValueButton = (Button) rootView.findViewById(R.id.increase_value_button);

        decreaseValueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseValueByOne();
            }
        });

        decreaseValueButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                buttonPressed = true;
                handler.post(new ValueUpdater(DECREASE_VALUE));
                return false;
            }
        });

        decreaseValueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    buttonPressed = false;
                }
                return false;
            }
        });

        increaseValueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseValueByOne();
            }
        });

        increaseValueButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                buttonPressed = true;
                handler.post(new ValueUpdater(INCREASE_VALUE));
                return false;
            }
        });

        increaseValueButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    buttonPressed = false;
                }
                return false;
            }
        });

        setDefaultValues();
    }


    /**
     * Simple method do decrement current value by 1.
     */

    public void decreaseValueByOne() {
        if (currentValue != minValue) {
            setCurrentValue(--currentValue);
        }
    }


    /**
     * Simple method do increment current value by 1.
     */

    public void increaseValueByOne(){
        if (currentValue != maxValue) {
            setCurrentValue(++currentValue);
        }
    }


    /**
     * Method to read attributes set in XML of the {@link EntityCounterView}
     *
     * @param context current context
     * @param attrs xml attributes
     */

    private void getAttrsFromXml(Context context, AttributeSet attrs) {
        // getting all xml attrs and iterating over each one
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EntityCounterView);

        for (int i = 0; i < a.getIndexCount(); i++) {
            int attrId = a.getIndex(i);

            if (attrId == R.styleable.EntityCounterView_start_value) {
                currentValue = a.getInteger(attrId, DEFAULT_START_NUMBER);
                setCurrentValue(currentValue);
            } else if (attrId == R.styleable.EntityCounterView_min_value) {
                minValue = a.getInteger(attrId, DEFAULT_MIN_NUMBER);
            } else if (attrId == R.styleable.EntityCounterView_max_value) {
                maxValue = a.getInteger(attrId, DEFAULT_MAX_NUMBER);
            } else if (attrId == R.styleable.EntityCounterView_description_string) {
                setDescriptionString(a.getString(attrId));
            }
        }

        a.recycle();

        // checking input values to be correct
        checkInputValues(currentValue, minValue, maxValue);
    }

    /**
     * Dummy method to check if values from XML attributes are correct.
     *
     * @param startValue start number, initial value for {@link FlipNumberView}
     * @param minValue minimum possible number
     * @param maxValue maximum possible number
     */

    private void checkInputValues(int startValue, int minValue, int maxValue) {
        if (startValue < minValue) {
            throw new IllegalArgumentException("start_value cannot be less then min_value!");
        }

        if (maxValue < startValue) {
            throw new IllegalArgumentException("max_value cannot be less then start_value!");
        }

        if (minValue > maxValue) {
            throw new IllegalArgumentException("max_value cannot be less then min_value!");
        }
    }


    /**
     * Method to set default values if custom are not present in XML
     */

    private void setDefaultValues(){
        minValue = DEFAULT_MIN_NUMBER;
        maxValue = DEFAULT_MAX_NUMBER;
        currentValue = DEFAULT_START_NUMBER;
        setCurrentValue(currentValue);
    }


    /**
     * Getter for counter description string.
     *
     * @return current description string
     */

    public String getDescriptionString() {
        return entityTypeTextView.getText().toString();
    }


    /**
     * Setter for counter description string.
     *
     * @param descriptionString new description string
     */

    public void setDescriptionString(String descriptionString) {
        entityTypeTextView.setText(descriptionString);
    }


    /**
     * Getter for minimum number
     *
     * @return current guest number minimum
     */

    public int getMinValue() {
        return minValue;
    }


    /**
     * Setter for minimum number
     *
     * @param minValue new allowed minimum
     */

    public void setMinValue(int minValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException("Min value cannot be larger then max value");
        }
        this.minValue = minValue;
    }


    /**
     * Getter for maximum number
     *
     * @return current maximum number
     */

    public int getMaxValue(){
        return maxValue;
    }


    /**
     * Setter for maximum number
     *
     * @param maxValue new allowed maximum
     */

    public void setMaxValue(int maxValue) {
        if (maxValue < minValue) {
            throw new IllegalArgumentException("Max guest count cannot be less then min value!");
        }
        this.maxValue = maxValue;
    }


    /**
     * Getter for current number
     *
     * @return current guest number
     */

    public int getCurrentValue() {
        return currentValue;
    }


    /**
     * Setter for current number
     *
     * @param currentValue sets current number of guests
     */

    public void setCurrentValue(int currentValue) {
        if (currentValue >= minValue && currentValue <= maxValue) {
            entityCountView.setValue(currentValue);
            this.currentValue = currentValue;
        } else {
            throw new IllegalArgumentException("Value is not in selected min/max range!");
        }
    }


    /**
     * Simple {@link Runnable} used to implement auto increment/decrement logic
     */

    private class ValueUpdater implements Runnable {
        final int direction;

        public ValueUpdater(int direction) {
            this.direction = direction;
        }

        @Override
        public void run() {
            if(isButtonPressed()){
                if (direction == INCREASE_VALUE) {
                    increaseValueByOne();
                } else {
                    decreaseValueByOne();
                }
                handler.postDelayed(new ValueUpdater(direction), REPEAT_INTERVAL_MS);
            }
        }
    }

    private boolean isButtonPressed() {
        return buttonPressed;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.savedValue = currentValue;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if(!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        currentValue = ss.savedValue;
        entityCountView.setValue(currentValue);
    }


    /**
     * Static class, that extends {@link android.view.View.BaseSavedState}
     * used to save {@link EntityCounterView} state in a correct way
     */

    static class SavedState extends BaseSavedState {
        int savedValue;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.savedValue = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.savedValue);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
