package com.dbulgakov.entitycounterview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Task 1
 *
 * Custom view group that partly implements LinearLayout logic,
 * making it more convenient to use in case necessity to fill
 * empty space between views with fixed size with one
 * having match_parent width.
 */

public class MatchParentViewGroup extends ViewGroup {

    private final Rect tmpChildRect = new Rect();
    private final ArrayList<View> matchParentChildren = new ArrayList<>();

    private int totalAvailableWidth;
    private int totalChildWidth;
    private int totalChildHeight;

    private boolean firstMeasure = true;

    /**
     * Class constructor taking only a context. Used to create
     * {@link MatchParentViewGroup} objects from code.
     *
     * @param context current context
     */

    public MatchParentViewGroup(Context context) {
        super(context);
    }

    /**
     * Class constructor taking a context and an attribute set. This constructor
     * is used by the layout engine to construct a {@link MatchParentViewGroup} from a set of
     * XML attributes.
     *
     * @param context current context
     * @param attrs XML attrs
     */

    public MatchParentViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Called to determine the size requirements for this view and all of its children.
     *
     * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
     * @param heightMeasureSpec vertical space requirements as imposed by the parent.
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Log.d("Measuring info", "onMeasure in CustomViewGroup call");
        if (!firstMeasure) {
            totalAvailableWidth = MeasureSpec.getSize(widthMeasureSpec);

            measureChildViews(widthMeasureSpec, heightMeasureSpec);

            if (hasMatchParentViews()) {
                measureMatchParentViews();
                matchParentChildren.clear();
            }
        } else {
            firstMeasure = false;
        }

        setMeasuredDimension(resolveSize(totalChildWidth, widthMeasureSpec),
                resolveSize(totalChildHeight, heightMeasureSpec));
    }

    private void measureChildViews(int widthMeasureSpec, int heightMeasureSpec) {
        totalChildWidth = getHorizontalPadding();
        totalChildHeight = getVerticalPadding();

        for (int childNumber = 0; childNumber < getChildCount(); childNumber++) {

            final View childView = getChildAt(childNumber);

            final LayoutParams lp = (LayoutParams) childView.getLayoutParams();

            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);

            if (lp.width != LayoutParams.MATCH_PARENT) {

                totalChildHeight = updateMaxChildHeight(childView, totalChildHeight);

                // calculating total width of views, except for one with match_parent width one
                totalChildWidth += (childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);

            } else {
                // processing this elements later
                matchParentChildren.add(childView);

                // calculating only margins of children
                totalChildWidth += lp.leftMargin + lp.rightMargin;
            }
        }
    }

    private void measureMatchParentViews(){
        int matchChildWidth = (totalAvailableWidth - totalChildWidth) / matchParentChildren.size();

        for (View childView : matchParentChildren) {
            childView.measure(MeasureSpec.makeMeasureSpec(matchChildWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(childView.getMeasuredHeight(), MeasureSpec.EXACTLY));

            totalChildHeight = updateMaxChildHeight(childView, totalChildHeight);

            totalChildWidth += childView.getMeasuredWidth();
        }
    }

    /**
     * Called when this view should assign a size and position to all of its children.
     *
     * @param changed Is thia a new size or position for this view
     * @param left Left position, relative to parent
     * @param top Top position, relative to parent
     * @param right Right position, relative to parent
     * @param bottom Bottom position, relative to parent
     */

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // filling our view group left to right, taking padding into account
        int currentPosition = getPaddingLeft();

        int paddingVertical = getPaddingTop();

        for (int childNumber = 0; childNumber < getChildCount(); childNumber++) {

            final View childView = getChildAt(childNumber);

            // ignoring views that are gone
            if (childView.getVisibility() != GONE) {

                final LayoutParams lp = (LayoutParams) childView.getLayoutParams();

                final int childWidth = childView.getMeasuredWidth();
                final int childHeight = childView.getMeasuredHeight();

                // calculating position of a new element
                tmpChildRect.left = currentPosition + lp.leftMargin;
                tmpChildRect.right = tmpChildRect.left + childWidth;

                tmpChildRect.top = paddingVertical + lp.topMargin;

                // making sure that we are not going into negative values here
                tmpChildRect.bottom = Math.max((tmpChildRect.top + childHeight), 0);

                // calculating new item start position
                currentPosition = tmpChildRect.right + lp.rightMargin;

                childView.layout(tmpChildRect.left, tmpChildRect.top,
                        tmpChildRect.right, tmpChildRect.bottom);
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MatchParentViewGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * Custom per-child layout information.
     */
    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            // Pull the layout param values from the layout XML during
            // inflation.
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.MatchParentViewGroup);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    private boolean hasMatchParentViews() {
        return matchParentChildren.size() > 0;
    }


    /**
     * Simple method to get total horizontal padding for a {@link MatchParentViewGroup}
     *
     * @return total horizontal padding for view group
     */

    private int getHorizontalPadding() {
        return getPaddingLeft() + getPaddingRight();
    }

    /**
     * Simple method to get total vertical padding for a {@link MatchParentViewGroup}
     *
     * @return total vertical padding for view group
     */

    private int getVerticalPadding() {
        return getPaddingTop() + getPaddingBottom();
    }

    /**
     * Method to compare current max child height value with new view group child
     *
     * @param childView child to compare with
     * @param currentMaxChildHeight current max height value
     *
     * @return updated child max value
     */


    private int updateMaxChildHeight(View childView, int currentMaxChildHeight) {
        final LayoutParams lp = (LayoutParams) childView.getLayoutParams();

        return Math.max(currentMaxChildHeight, childView.getHeight()
                + lp.topMargin + lp.bottomMargin);
    }
}
