package vit.com.mui.widget.roundwidget;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;

/**
 * @author kewz
 * @date 2019/5/13
 */
public class MUIRoundDrawable extends GradientDrawable {

    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private boolean mIsRadiusAdjustBounds = true;
    private ColorStateList mFillColors;
    private ColorStateList mStrokeColors;
    private int mStrokeWidth = 0;

    private MUIRoundDrawable(Builder builder) {
        ColorStateList colorBg = builder.colorBg;
        ColorStateList colorBorder = builder.colorBorder;
        int borderWidth = builder.borderWidth;
        boolean isRadiusAdjustBounds = builder.isRadiusAdjustBounds;
        int mRadius = builder.mRadius;
        int mRadiusTopLeft = builder.mRadiusTopLeft;
        int mRadiusTopRight = builder.mRadiusTopRight;
        int mRadiusBottomLeft = builder.mRadiusBottomLeft;
        int mRadiusBottomRight = builder.mRadiusBottomRight;

        // 填充背景、描边
        setBgData(colorBg);
        setStrokeData(borderWidth, colorBorder);
        // 设置圆角
        if (mRadiusTopLeft > 0 || mRadiusTopRight > 0 || mRadiusBottomLeft > 0
                || mRadiusBottomRight > 0) {
            // radii 是长度为 8 的数组，代表 4 个角的 x y 圆角半径
            float[] radii = new float[]{
                    mRadiusTopLeft, mRadiusTopLeft,
                    mRadiusTopRight, mRadiusTopRight,
                    mRadiusBottomRight, mRadiusBottomRight,
                    mRadiusBottomLeft, mRadiusBottomLeft
            };
            setCornerRadii(radii);
            // 设置了各个圆角值，isRadiusAdjustBounds 重置为 false
            isRadiusAdjustBounds = false;
        } else {
            setCornerRadius(mRadius);
            if (mRadius > 0) {
                // 圆角值大于0，isRadiusAdjustBounds 重置为 false
                isRadiusAdjustBounds = false;
            }
        }
        setIsRadiusAdjustBounds(isRadiusAdjustBounds);

    }

    /**
     * 设置背景颜色
     *
     * @param colors
     */
    private void setBgData(@Nullable ColorStateList colors) {
        if (hasNativeStateListApi()) {
            super.setColor(colors);
        } else {
            mFillColors = colors;
            // 当前状态对应的颜色
            final int currentColor;
            if (colors == null) {
                currentColor = Color.TRANSPARENT;
            } else {
                currentColor = colors.getColorForState(getState(), 0);
            }
            setColor(currentColor);
        }
    }

    /**
     * 设置按钮的描边和颜色
     *
     * @param width
     * @param colors
     */
    private void setStrokeData(int width, @Nullable ColorStateList colors) {
        if (hasNativeStateListApi()) {
            super.setStroke(width, colors);
        } else {
            mStrokeWidth = width;
            mStrokeColors = colors;
            final int mCurrentStateColor;
            if (colors == null) {
                mCurrentStateColor = Color.TRANSPARENT;
            } else {
                mCurrentStateColor = colors.getColorForState(getState(), 0);
            }
            setStroke(width, mCurrentStateColor);
        }
    }

    /**
     * 设置圆角是否适应宽度/高度
     *
     * @param adjust
     */
    private void setIsRadiusAdjustBounds(boolean adjust) {
        this.mIsRadiusAdjustBounds = adjust;
    }

    private boolean hasNativeStateListApi() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        boolean superRet = super.onStateChange(stateSet);
        if (mFillColors != null) {
            int color = mFillColors.getColorForState(stateSet, 0);
            setColor(color);
            superRet = true;
        }
        if (mStrokeColors != null) {
            int color = mStrokeColors.getColorForState(stateSet, 0);
            setStroke(mStrokeWidth, color);
            superRet = true;
        }
        return superRet;
    }

    @Override
    public boolean isStateful() {
        return (mFillColors != null && mFillColors.isStateful())
                || (mStrokeColors != null && mStrokeColors.isStateful())
                || super.isStateful();
    }

    @Override
    protected void onBoundsChange(Rect r) {
        super.onBoundsChange(r);
        if (mIsRadiusAdjustBounds) {
            // 修改圆角自适应短边
            setCornerRadius(Math.min(r.width(), r.height()) / 2);
        }
    }

    public static class Builder {

        private ColorStateList colorBg;
        private ColorStateList colorBorder;
        private int borderWidth;
        private boolean isRadiusAdjustBounds;
        private int mRadius;
        private int mRadiusTopLeft;
        private int mRadiusTopRight;
        private int mRadiusBottomLeft;
        private int mRadiusBottomRight;

        public Builder() {
        }

        public Builder backgroundColor(ColorStateList color) {
            this.colorBg = color;
            return this;
        }

        public Builder borderColor(ColorStateList color) {
            this.colorBorder = color;
            return this;
        }

        public Builder borderWidth(int width) {
            this.borderWidth = width;
            return this;
        }

        public Builder isRadiusAdjustBounds(boolean isAdjustBounds) {
            this.isRadiusAdjustBounds = isAdjustBounds;
            return this;
        }

        public Builder radius(int radius) {
            this.mRadius = radius;
            return this;
        }

        public Builder radiusTopLeft(int radius) {
            this.mRadiusTopLeft = radius;
            return this;
        }

        public Builder radiusTopRight(int radius) {
            this.mRadiusTopRight = radius;
            return this;
        }

        public Builder radiusBottomLeft(int radius) {
            this.mRadiusBottomLeft = radius;
            return this;
        }

        public Builder radiusBottomRight(int radius) {
            this.mRadiusBottomRight = radius;
            return this;
        }

        public MUIRoundDrawable build() {
            return new MUIRoundDrawable(this);
        }

    }

}
