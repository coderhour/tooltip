package com.coderhour.tooltip;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * @author coderhour
 * @version 1.0: 4/1/16
 */
public class TooltipPopupWindow extends PopupWindow {

    public static final int POS_LEFT = 0;
    public static final int POS_RIGHT = 1;
    public static final int POS_TOP = 2;
    public static final int POS_BOTTOM = 3;

    private LinearLayout mLinearLayout;
    private ImageView mImageView;
    private int mX, mY;
    private int mPos = -1;
    private View mOriginalView;

    public TooltipPopupWindow(Context context) {
        super(context);
    }

    public TooltipPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TooltipPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TooltipPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TooltipPopupWindow() {
    }

    public TooltipPopupWindow(View contentView) {
        super(contentView);
    }

    public TooltipPopupWindow(int width, int height) {
        super(width, height);
    }

    public TooltipPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public TooltipPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public TooltipPopupWindow setBackgroundColor(int color) {
        LayerDrawable drawableTriangle = (LayerDrawable) mImageView.getBackground();
        GradientDrawable shapeTriangle = (GradientDrawable) (((RotateDrawable) drawableTriangle.findDrawableByLayerId(R.id.shape_id)).getDrawable());
        if (shapeTriangle != null)
        {
            shapeTriangle.setColor(color);
        }
        GradientDrawable drawableRound = (GradientDrawable) mOriginalView.getBackground();
        if (drawableRound != null)
        {
            drawableRound.setColor(color);
        }
        return this;
    }

    public TooltipPopupWindow setBackgroundColorResource(int colorRes) {
        setBackgroundColor(ContextCompat.getColor(getContentView().getContext(), colorRes));
        return this;
    }

    public TooltipPopupWindow setWindowPosition(int pos) {
        mPos = pos;
        mOriginalView = getContentView();
        mLinearLayout = new LinearLayout(mOriginalView.getContext());
        mImageView = new ImageView(mOriginalView.getContext());
        mOriginalView.setBackgroundResource(R.drawable.tooltip_round_corner_bg);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(
                64, 64);
        if (mOriginalView.getParent() != null) {
            ((LinearLayout)mOriginalView.getParent()).removeAllViews();
        }
        if (pos == POS_LEFT) {
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            mLinearLayout.addView(mOriginalView);

            mImageView.setBackgroundResource(R.drawable.tooltip_triangle_left);
            mLinearLayout.addView(mImageView, ivParams);
        } else if (pos == POS_TOP) {
            // W/A Start
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            mLinearLayout.addView(mOriginalView);

            mLinearLayout.removeAllViews();
            // W/A End

            mLinearLayout.setOrientation(LinearLayout.VERTICAL);
            mLinearLayout.addView(mOriginalView);

            mImageView.setBackgroundResource(R.drawable.tooltip_triangle_top);
            mLinearLayout.addView(mImageView, ivParams);
        } else if (pos == POS_RIGHT) {
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            mImageView.setBackgroundResource(R.drawable.tooltip_triangle_right);
            mLinearLayout.addView(mImageView, ivParams);

            mLinearLayout.addView(mOriginalView, params);
        } else if (pos == POS_BOTTOM) {
            mLinearLayout.setOrientation(LinearLayout.VERTICAL);

            mImageView.setBackgroundResource(R.drawable.tooltip_triangle_bottom);
            mLinearLayout.addView(mImageView, ivParams);

            mLinearLayout.addView(mOriginalView, params);
        }
        setContentView(mLinearLayout);
        return this;
    }

    public TooltipPopupWindow setAnchorView(View anchorView) {
        mLinearLayout.setPadding(0, 0, 0, 0);
        mLinearLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        if (mPos == -1) {
            throw new IllegalStateException("Pleas call setWindowPosition first.");
        }
        int[] loc = new int[2];
        anchorView.getLocationOnScreen(loc);
        if (mPos == POS_LEFT) {
            mX = loc[0] - mLinearLayout.getMeasuredWidth() + anchorView.getPaddingLeft();
            mY = loc[1] + anchorView.getPaddingTop() - mLinearLayout.getMeasuredHeight() / 2 + anchorView.getHeight() / 2;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.topMargin = loc[1] - mY + anchorView.getHeight() / 2 - mImageView.getMeasuredHeight() / 2;
        } else if (mPos == POS_TOP) {
            mX = loc[0] + anchorView.getPaddingLeft() - mLinearLayout.getMeasuredWidth() / 2 + anchorView.getWidth() / 2;
            mY = loc[1] - mLinearLayout.getMeasuredHeight() - anchorView.getPaddingTop();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.leftMargin = loc[0] - mX + anchorView.getWidth() / 2 - mImageView.getMeasuredWidth() / 2;
        } else if (mPos == POS_RIGHT) {
            mX = loc[0] + anchorView.getWidth() - anchorView.getPaddingRight();
            mY = loc[1] + anchorView.getPaddingTop() - mLinearLayout.getMeasuredHeight() / 2 + anchorView.getHeight() / 2;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.topMargin = loc[1] - mY + anchorView.getHeight() / 2 - mImageView.getMeasuredHeight() / 2;
        } else if (mPos == POS_BOTTOM) {
            mX = loc[0] + anchorView.getPaddingLeft() - mLinearLayout.getMeasuredWidth() / 2 + anchorView.getWidth() / 2;
            mY = loc[1] + anchorView.getHeight() - anchorView.getPaddingBottom();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.leftMargin = loc[0] - mX + anchorView.getWidth() / 2 - mImageView.getMeasuredWidth() / 2;
        }
        return this;
    }

    public TooltipPopupWindow setOutsideTouchableChain(boolean touchable) {
        super.setOutsideTouchable(touchable);
        return this;
    }

    public void show(View parent) {
        super.showAtLocation(parent, Gravity.TOP | Gravity.LEFT, mX, mY);
    }

}
