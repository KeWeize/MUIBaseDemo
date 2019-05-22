package vit.com.mui.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import vit.com.mui.R;
import vit.com.mui.alpha.MUIAlphaRelativeLayout;

/**
 * @author kewz
 * @date 2019/5/4
 */
public class MUIRelativeLayout extends MUIAlphaRelativeLayout implements IMUILayout {

    private MUILayoutHelper mLayoutHelper;

    public MUIRelativeLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MUIRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MUIRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        MUILayoutHelper.Builder builder = new MUILayoutHelper.Builder(context, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MUIRelativeLayout,
                defStyleAttr, 0);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int index = ta.getIndex(i);
            if (index == R.styleable.MUIRelativeLayout_android_maxWidth) {
                builder.mWidthLimit = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_android_maxHeight) {
                builder.mHeightLimit = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_android_minWidth) {
                builder.mWidthMini = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_android_minHeight) {
                builder.mHeightMini = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_topDividerColor) {
                builder.mTopDividerColor = ta.getColor(index,
                        ContextCompat.getColor(context, R.color.mui_config_color_separator));
            } else if (index == R.styleable.MUIRelativeLayout_mui_topDividerHeight) {
                builder.mTopDividerHeight = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_topDividerInsetLeft) {
                builder.mTopDividerInsetLeft = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_topDividerInsetRight) {
                builder.mTopDividerInsetRight = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_bottomDividerColor) {
                builder.mBottomDividerColor = ta.getColor(index,
                        ContextCompat.getColor(context, R.color.mui_config_color_separator));
            } else if (index == R.styleable.MUIRelativeLayout_mui_bottomDividerHeight) {
                builder.mBottomDividerHeight = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_bottomDividerInsetLeft) {
                builder.mBottomDividerInsetLeft = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_bottomDividerInsetRight) {
                builder.mBottomDividerInsetRight = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_leftDividerColor) {
                builder.mLeftDividerColor = ta.getColor(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_leftDividerWidth) {
                builder.mLeftDividerWidth = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_leftDividerInsetTop) {
                builder.mLeftDividerInsetTop = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_leftDividerInsetBottom) {
                builder.mLeftDividerInsetBottom = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_rightDividerColor) {
                builder.mRightDividerColor = ta.getColor(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_rightDividerWidth) {
                builder.mRightDividerWidth = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_rightDividerInsetTop) {
                builder.mRightDividerInsetTop = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_rightDividerInsetBottom) {
                builder.mRightDividerInsetBottom = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_borderColor) {
                builder.mBorderColor = ta.getColor(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_borderWidth) {
                builder.mBorderWidth = ta.getDimensionPixelSize(index, 1);
            } else if (index == R.styleable.MUIRelativeLayout_mui_radius) {
                builder.radius = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_outerNormalColor) {
                builder.mOuterNormalColor = ta.getColor(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_hideRadiusSide) {
                builder.mHideRadiusSide = ta.getColor(index, HIDE_RADIUS_SIDE_NONE);
            } else if (index == R.styleable.MUIRelativeLayout_mui_showBorderOnlyBeforeL) {
                builder.mIsShowBorderOnlyBeforeL = ta.getBoolean(index, true);
            } else if (index == R.styleable.MUIRelativeLayout_mui_shadowElevation) {
                builder.shadow = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_shadowAlpha) {
                builder.mShadowAlpha = ta.getFloat(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_useThemeGeneralShadowElevation) {
                builder.useThemeGeneralShadowElevation = ta.getBoolean(index, false);
            } else if (index == R.styleable.MUIRelativeLayout_mui_outlineInsetLeft) {
                builder.mOutlineInsetLeft = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_outlineInsetRight) {
                builder.mOutlineInsetRight = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_outlineInsetTop) {
                builder.mOutlineInsetTop = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_outlineInsetBottom) {
                builder.mOutlineInsetBottom = ta.getDimensionPixelSize(index, 0);
            } else if (index == R.styleable.MUIRelativeLayout_mui_outlineExcludePadding) {
                builder.mIsOutlineExcludePadding = ta.getBoolean(index, false);
            }
        }
        ta.recycle();

        mLayoutHelper = builder.build();
        setChangeAlphaWhenPress(false);
        setChangeAlphaWhenDisable(false);
    }


    @Override
    public void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        mLayoutHelper.updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        invalidate();
    }

    @Override
    public void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        mLayoutHelper.updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        invalidate();
    }

    @Override
    public void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        mLayoutHelper.updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        invalidate();
    }

    @Override
    public void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        mLayoutHelper.updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        invalidate();
    }

    @Override
    public void onlyShowTopDivider(int topInsetLeft, int topInsetRight,
                                   int topDividerHeight, int topDividerColor) {
        mLayoutHelper.onlyShowTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        invalidate();
    }

    @Override
    public void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight,
                                      int bottomDividerHeight, int bottomDividerColor) {
        mLayoutHelper.onlyShowBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        invalidate();
    }

    @Override
    public void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        mLayoutHelper.onlyShowLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        invalidate();
    }

    @Override
    public void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        mLayoutHelper.onlyShowRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        invalidate();
    }

    @Override
    public void setTopDividerAlpha(int dividerAlpha) {
        mLayoutHelper.setTopDividerAlpha(dividerAlpha);
        invalidate();
    }

    @Override
    public void setBottomDividerAlpha(int dividerAlpha) {
        mLayoutHelper.setBottomDividerAlpha(dividerAlpha);
        invalidate();
    }

    @Override
    public void setLeftDividerAlpha(int dividerAlpha) {
        mLayoutHelper.setLeftDividerAlpha(dividerAlpha);
        invalidate();
    }

    @Override
    public void setRightDividerAlpha(int dividerAlpha) {
        mLayoutHelper.setRightDividerAlpha(dividerAlpha);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = mLayoutHelper.getMeasuredWidthSpec(widthMeasureSpec);
        heightMeasureSpec = mLayoutHelper.getMeasuredHeightSpec(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minW = mLayoutHelper.handleMiniWidth(widthMeasureSpec, getMeasuredWidth());
        int minH = mLayoutHelper.handleMiniHeight(heightMeasureSpec, getMeasuredHeight());
        if (widthMeasureSpec != minW || heightMeasureSpec != minH) {
            super.onMeasure(minW, minH);
        }
    }

    @Override
    public void setRadiusAndShadow(int radius, int shadowElevation, final float shadowAlpha) {
        mLayoutHelper.setRadiusAndShadow(radius, shadowElevation, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, @HideRadiusSide int hideRadiusSide, int shadowElevation, final float shadowAlpha) {
        mLayoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha) {
        mLayoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowColor, shadowAlpha);
    }

    @Override
    public void setRadius(int radius) {
        mLayoutHelper.setRadius(radius);
    }

    @Override
    public void setRadius(int radius, @HideRadiusSide int hideRadiusSide) {
        mLayoutHelper.setRadius(radius, hideRadiusSide);
    }

    @Override
    public int getRadius() {
        return mLayoutHelper.getRadius();
    }

    @Override
    public void setOutlineInset(int left, int top, int right, int bottom) {
        mLayoutHelper.setOutlineInset(left, top, right, bottom);
    }

    @Override
    public void setBorderColor(@ColorInt int borderColor) {
        mLayoutHelper.setBorderColor(borderColor);
        invalidate();
    }

    @Override
    public void setBorderWidth(int borderWidth) {
        mLayoutHelper.setBorderWidth(borderWidth);
        invalidate();
    }

    @Override
    public void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL) {
        mLayoutHelper.setShowBorderOnlyBeforeL(showBorderOnlyBeforeL);
        invalidate();
    }

    @Override
    public void setHideRadiusSide(int hideRadiusSide) {
        mLayoutHelper.setHideRadiusSide(hideRadiusSide);
    }

    @Override
    public int getHideRadiusSide() {
        return mLayoutHelper.getHideRadiusSide();
    }

    @Override
    public boolean setWidthLimit(int widthLimit) {
        if (mLayoutHelper.setWidthLimit(widthLimit)) {
            requestLayout();
            invalidate();
        }
        return true;
    }

    @Override
    public boolean setHeightLimit(int heightLimit) {
        if (mLayoutHelper.setHeightLimit(heightLimit)) {
            requestLayout();
            invalidate();
        }
        return true;
    }

    @Override
    public void setUseThemeGeneralShadowElevation() {
        mLayoutHelper.setUseThemeGeneralShadowElevation();
    }

    @Override
    public void setOutlineExcludePadding(boolean outlineExcludePadding) {
        mLayoutHelper.setOutlineExcludePadding(outlineExcludePadding);
    }

    @Override
    public void setShadowElevation(int elevation) {
        mLayoutHelper.setShadowElevation(elevation);
    }

    @Override
    public int getShadowElevation() {
        return mLayoutHelper.getShadowElevation();
    }

    @Override
    public void setShadowAlpha(float shadowAlpha) {
        mLayoutHelper.setShadowAlpha(shadowAlpha);
    }

    @Override
    public void setShadowColor(int shadowColor) {
        mLayoutHelper.setShadowColor(shadowColor);
    }

    @Override
    public int getShadowColor() {
        return mLayoutHelper.getShadowColor();
    }

    @Override
    public void setOuterNormalColor(int color) {
        mLayoutHelper.setOuterNormalColor(color);
    }

    @Override
    public float getShadowAlpha() {
        return mLayoutHelper.getShadowAlpha();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mLayoutHelper.drawDividers(canvas, getWidth(), getHeight());
        mLayoutHelper.dispatchRoundBorderDraw(canvas);
    }

}
