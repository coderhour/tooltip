package com.coderhour.tooltip;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
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

    /**
     * {@value #POS_LEFT} The tooltip will show to the left of the anchor view / RectF / Rect.
     */
    public static final int POS_LEFT = 0;

    /**
     * {@value #POS_RIGHT} The tooltip will show to the right of the anchor view / RectF / Rect.
     */
    public static final int POS_RIGHT = 1;

    /**
     * {@value #POS_ABOVE} The tooltip will show above of the anchor view / RectF / Rect.
     */
    public static final int POS_ABOVE = 2;

    /**
     * {@value #POS_BELOW} The tooltip will show below of the anchor view / RectF / Rect.
     */
    public static final int POS_BELOW = 3;

    /**
     * {@value #POS_AUTO} The tooltip will show automatically according to the anchor view / RectF / Rect.
     */
    public static final int POS_AUTO = 4;

    private LinearLayout mLinearLayout;
    private ImageView mImageView;
    private int mX, mY;
    private int mPos = -1;
    private View mOriginalView;
    private View mAnchorView;
    private Rect mRect;

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

    /**
     * Set the background color of the content view.
     *
     * @param color Color code
     * @return The current window
     */
    public TooltipPopupWindow setBackgroundColor(int color) {
        LayerDrawable drawableTriangle = (LayerDrawable) mImageView.getBackground();
        GradientDrawable shapeTriangle = (GradientDrawable) (((RotateDrawable) drawableTriangle.findDrawableByLayerId(R.id.shape_id)).getDrawable());
        if (shapeTriangle != null) {
            shapeTriangle.setColor(color);
        }
        GradientDrawable drawableRound = (GradientDrawable) mOriginalView.getBackground();
        if (drawableRound != null) {
            drawableRound.setColor(color);
        }
        return this;
    }

    /**
     * Set the background color of the content view with color resource ID.
     *
     * @param colorRes The color resource ID
     * @return The current window
     */
    public TooltipPopupWindow setBackgroundColorResource(int colorRes) {
        setBackgroundColor(ContextCompat.getColor(getContentView().getContext(), colorRes));
        return this;
    }

    /**
     * Set the window position.
     * <p>
     * This method should be called before show.
     * </p>
     *
     * @param pos The position of the tooltip related to the anchor view. Please see {@link #POS_LEFT}, {@link #POS_RIGHT}, {@link #POS_ABOVE} and {@link #POS_BELOW}.
     * @return The current window
     */
    public TooltipPopupWindow setWindowPosition(int pos) {
        mPos = pos;
        return this;
    }

    /**
     * Set the anchor view.
     * <p>
     * The popup window will be show related to the position of the anchor view.
     * </p>
     *
     * @param anchorView The anchor view
     * @return The current window.
     */
    public TooltipPopupWindow setAnchorView(View anchorView) {
        mAnchorView = anchorView;
        if (mRect != null) {
            throw new IllegalStateException("Can't call setAnchorView and setRectF at the same time.");
        }
        int[] loc = new int[2];
        anchorView.getLocationOnScreen(loc);
        int left = loc[0] + anchorView.getPaddingLeft();
        int top = loc[1] + anchorView.getPaddingTop();
        int right = loc[0] + anchorView.getWidth() - anchorView.getPaddingRight();
        int bottom = loc[1] + anchorView.getHeight() - anchorView.getPaddingBottom();
        relocation(new Rect(left, top, right, bottom));
        return this;
    }

    /**
     * Set the RectF of the position.
     * <p>
     * The popup window will be show related to the RectF.
     * </p>
     *
     * @param rectF The RectF
     * @return The current window
     */
    public TooltipPopupWindow setRectF(RectF rectF) {
        if (mAnchorView != null) {
            throw new IllegalStateException("Can't call setAnchorView and setRectF at the same time.");
        }
        mRect = new Rect();
        rectF.round(mRect);
        relocation(mRect);
        return this;
    }

    /**
     * Set the Rect of the position.
     * <p>
     * The popup window will be show related to the Rect.
     * </p>
     *
     * @param rect The Rect
     * @return The current window
     */
    public TooltipPopupWindow setRect(Rect rect) {
        mRect = rect;
        relocation(mRect);
        return this;
    }

    /**
     * Set outside touchable.
     * <p>
     * The same with {@link #setOutsideTouchable(boolean)} but it's chainable.
     * </p>
     *
     * @param touchable the window will dismiss when user click outside if set to true.
     * @return The current window
     */
    public TooltipPopupWindow setOutsideTouchableChain(boolean touchable) {
        super.setOutsideTouchable(touchable);
        return this;
    }

    /**
     * Show the popup window.
     *
     * @param parent Parent of the popup window.
     */
    public void show(View parent) {
        super.showAtLocation(parent, Gravity.TOP | Gravity.LEFT, mX, mY);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mLinearLayout = null;
        mImageView = null;
        mOriginalView = null;
        mAnchorView = null;
        mRect = null;
    }

    private void relocation(Rect rect) {
        mOriginalView = getContentView();
        int screenWidth = mOriginalView.getContext().getResources().getDisplayMetrics().widthPixels;
        int screenHeight = mOriginalView.getContext().getResources().getDisplayMetrics().heightPixels;
        relayout(rect, screenWidth, screenHeight);
        mLinearLayout.setPadding(0, 0, 0, 0);
        mLinearLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        if (mPos == -1) {
            throw new IllegalStateException("Pleas call setWindowPosition first.");
        }
        if (mPos == POS_LEFT) {
            mX = rect.left - mLinearLayout.getMeasuredWidth();
            mY = rect.centerY() - mLinearLayout.getMeasuredHeight() / 2;
            if (mY < 0) {
                mY = 0;
            } else if (screenHeight - mY < mLinearLayout.getMeasuredHeight()) {
                mY = screenHeight - mLinearLayout.getMeasuredHeight();
            }
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.topMargin = rect.centerY() - mY - mImageView.getMeasuredHeight() / 2;
        } else if (mPos == POS_ABOVE) {
            mX = rect.centerX() - mLinearLayout.getMeasuredWidth() / 2;
            if (mX < 0) {
                mX = 0;
            } else if (screenWidth - mX < mLinearLayout.getMeasuredWidth()) {
                mX = screenWidth - mLinearLayout.getMeasuredWidth();
            }
            mY = rect.top - mLinearLayout.getMeasuredHeight();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.leftMargin = rect.centerX() - mX - mImageView.getMeasuredWidth() / 2;
        } else if (mPos == POS_RIGHT) {
            mX = rect.right;
            mY = rect.centerY() - mLinearLayout.getMeasuredHeight() / 2;
            if (mY < 0) {
                mY = 0;
            } else if (screenHeight - mY < mLinearLayout.getMeasuredHeight()) {
                mY = screenHeight - mLinearLayout.getMeasuredHeight();
            }
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.topMargin = rect.centerY() - mY - mImageView.getMeasuredHeight() / 2;
        } else if (mPos == POS_BELOW) {
            mX = rect.centerX() - mLinearLayout.getMeasuredWidth() / 2;
            mY = rect.bottom;
            if (mX < 0) {
                mX = 0;
            } else if (screenWidth - mX < mLinearLayout.getMeasuredWidth()) {
                mX = screenWidth - mLinearLayout.getMeasuredWidth();
            }
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mImageView.getLayoutParams();
            params.leftMargin = rect.centerX() - mX - mImageView.getMeasuredWidth() / 2;
        }
    }

    private void relayout(Rect rect, int screenWidth, int screenHeight) {
        if (mPos == POS_AUTO) {
            mOriginalView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int originalViewWidth = mOriginalView.getMeasuredWidth();
            int originalViewHeight = mOriginalView.getMeasuredHeight();

            int upSpace = rect.top - originalViewHeight;
            int downSpace = screenHeight - rect.bottom - originalViewHeight;
            if (upSpace > 0 || downSpace > 0) {
                if (upSpace > downSpace) {
                    mPos = POS_ABOVE;
                } else {
                    mPos = POS_BELOW;
                }
            } else {
                int leftSpace = rect.left - originalViewWidth;
                int rightSpace = screenWidth - rect.right - originalViewWidth;
                if (leftSpace > 0 || rightSpace > 0) {
                    if (leftSpace > rightSpace) {
                        mPos = POS_LEFT;
                    } else {
                        mPos = POS_RIGHT;
                    }
                }
            }
        }
        if (mPos == POS_AUTO) {
            mPos = POS_ABOVE;
        }
        mLinearLayout = new LinearLayout(mOriginalView.getContext());
        mImageView = new ImageView(mOriginalView.getContext());
        mOriginalView.setBackgroundResource(R.drawable.tooltip_round_corner_bg);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(
                64, 64);
        if (mOriginalView.getParent() != null) {
            ((LinearLayout) mOriginalView.getParent()).removeAllViews();
        }
        if (mPos == POS_LEFT) {
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            mLinearLayout.addView(mOriginalView);

            mImageView.setBackgroundResource(R.drawable.tooltip_triangle_left);
            mLinearLayout.addView(mImageView, ivParams);
        } else if (mPos == POS_ABOVE) {
            // W/A Start
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            mLinearLayout.addView(mOriginalView);

            mLinearLayout.removeAllViews();
            // W/A End

            mLinearLayout.setOrientation(LinearLayout.VERTICAL);
            mLinearLayout.addView(mOriginalView);

            mImageView.setBackgroundResource(R.drawable.tooltip_triangle_top);
            mLinearLayout.addView(mImageView, ivParams);
        } else if (mPos == POS_RIGHT) {
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            mImageView.setBackgroundResource(R.drawable.tooltip_triangle_right);
            mLinearLayout.addView(mImageView, ivParams);

            mLinearLayout.addView(mOriginalView, params);
        } else if (mPos == POS_BELOW) {
            mLinearLayout.setOrientation(LinearLayout.VERTICAL);

            mImageView.setBackgroundResource(R.drawable.tooltip_triangle_bottom);
            mLinearLayout.addView(mImageView, ivParams);

            mLinearLayout.addView(mOriginalView, params);
        } else if (mPos == POS_AUTO) {

        }
        setContentView(mLinearLayout);
    }

}
