package vit.com.mui.layout;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewOutlineProvider;

import java.lang.ref.WeakReference;

import vit.com.mui.R;
import vit.com.mui.utils.MUIAttrsHelper;

/**
 * @author kewz
 * @date 2019/4/30
 */
public class MUILayoutHelper implements IMUILayout {

    private Context mContext;
    // size
    private int mWidthLimit = 0;
    private int mHeightLimit = 0;
    private int mWidthMini = 0;
    private int mHeightMini = 0;


    // divider
    private int mTopDividerHeight = 0;
    private int mTopDividerInsetLeft = 0;
    private int mTopDividerInsetRight = 0;
    private int mTopDividerColor;
    private int mTopDividerAlpha = 255;

    private int mBottomDividerHeight = 0;
    private int mBottomDividerInsetLeft = 0;
    private int mBottomDividerInsetRight = 0;
    private int mBottomDividerColor;
    private int mBottomDividerAlpha = 255;

    private int mLeftDividerWidth = 0;
    private int mLeftDividerInsetTop = 0;
    private int mLeftDividerInsetBottom = 0;
    private int mLeftDividerColor;
    private int mLeftDividerAlpha = 255;

    private int mRightDividerWidth = 0;
    private int mRightDividerInsetTop = 0;
    private int mRightDividerInsetBottom = 0;
    private int mRightDividerColor;
    private int mRightDividerAlpha = 255;
    private Paint mDividerPaint;

    // round
    private Paint mClipPaint;
    private PorterDuffXfermode mMode;
    private int mRadius;
    private @HideRadiusSide
    int mHideRadiusSide = HIDE_RADIUS_SIDE_NONE;
    private float[] mRadiusArray;
    private RectF mBorderRect;
    private int mBorderColor = 0;
    private int mBorderWidth = 1;
    private int mOuterNormalColor = 0;
    private WeakReference<View> mOwner;
    private boolean mIsOutlineExcludePadding = false;
    private Path mPath = new Path();

    // shadow
    private boolean mIsShowBorderOnlyBeforeL = true;
    private int mShadowElevation = 0;
    private float mShadowAlpha;
    private int mShadowColor = Color.BLACK;

    // outline inset
    private int mOutlineInsetLeft = 0;
    private int mOutlineInsetRight = 0;
    private int mOutlineInsetTop = 0;
    private int mOutlineInsetBottom = 0;

    private MUILayoutHelper(Context context, Builder builder, View owner) {
        this.mContext = context;
        mOwner = new WeakReference<>(owner);

        mBottomDividerColor = mTopDividerColor =
                ContextCompat.getColor(context, R.color.mui_config_color_separator);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        mClipPaint = new Paint();
        mClipPaint.setAntiAlias(true);
        mShadowAlpha = MUIAttrsHelper.getAttrFloatValue(context,
                R.attr.mui_general_shadow_alpha);
        mBorderRect = new RectF();

        int radius = 0, shadow = 0;
        boolean useThemeGeneralShadowElevation = false;

        mWidthLimit = builder.mWidthLimit;
        mHeightLimit = builder.mHeightLimit;
        mWidthMini = builder.mWidthMini;
        mHeightMini = builder.mHeightMini;
        mTopDividerColor = builder.mTopDividerColor;
        mTopDividerHeight = builder.mTopDividerHeight;
        mTopDividerInsetLeft = builder.mTopDividerInsetLeft;
        mTopDividerInsetRight = builder.mTopDividerInsetRight;
        mBottomDividerColor = builder.mBottomDividerColor;
        mBottomDividerHeight = builder.mBottomDividerHeight;
        mBottomDividerInsetLeft = builder.mBottomDividerInsetLeft;
        mBottomDividerInsetRight = builder.mBottomDividerInsetRight;
        mLeftDividerColor = builder.mLeftDividerColor;
        mLeftDividerWidth = builder.mLeftDividerWidth;
        mLeftDividerInsetTop = builder.mLeftDividerInsetTop;
        mLeftDividerInsetBottom = builder.mLeftDividerInsetBottom;
        mRightDividerColor = builder.mRightDividerColor;
        mRightDividerWidth = builder.mRightDividerWidth;
        mRightDividerInsetTop = builder.mRightDividerInsetTop;
        mRightDividerInsetBottom = builder.mRightDividerInsetBottom;
        mBorderColor = builder.mBorderColor;
        mBorderWidth = builder.mBorderWidth;
        radius = builder.radius;
        mOuterNormalColor = builder.mOuterNormalColor;
        mHideRadiusSide = builder.mHideRadiusSide;
        mIsShowBorderOnlyBeforeL = builder.mIsShowBorderOnlyBeforeL;
        shadow = builder.shadow;
        mShadowAlpha = builder.mShadowAlpha;
        useThemeGeneralShadowElevation = builder.useThemeGeneralShadowElevation;
        mOutlineInsetLeft = builder.mOutlineInsetLeft;
        mOutlineInsetRight = builder.mOutlineInsetRight;
        mOutlineInsetTop = builder.mOutlineInsetTop;
        mOutlineInsetBottom = builder.mOutlineInsetBottom;
        mIsOutlineExcludePadding = builder.mIsOutlineExcludePadding;

        if (shadow == 0 && useThemeGeneralShadowElevation) {
            shadow = MUIAttrsHelper.getAttrDimen(context, R.attr.mui_general_shadow_elevation);
        }
        setRadiusAndShadow(radius, mHideRadiusSide, shadow, mShadowAlpha);
    }

    @Override
    public boolean setWidthLimit(int widthLimit) {
        if (mWidthLimit != widthLimit) {
            mWidthLimit = widthLimit;
            return true;
        }
        return false;
    }

    @Override
    public boolean setHeightLimit(int heightLimit) {
        if (mHeightLimit != heightLimit) {
            mHeightLimit = heightLimit;
            return true;
        }
        return false;
    }

    @Override
    public void setUseThemeGeneralShadowElevation() {
        mShadowElevation = MUIAttrsHelper.getAttrDimen(mContext, R.attr.mui_general_shadow_elevation);
        setRadiusAndShadow(mRadius, mHideRadiusSide, mShadowElevation, mShadowAlpha);
    }

    @Override
    public void setOutlineExcludePadding(boolean outlineExcludePadding) {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            mIsOutlineExcludePadding = outlineExcludePadding;
            owner.invalidateOutline();
        }
    }

    @Override
    public void setShadowElevation(int elevation) {
        if (mShadowElevation == elevation) {
            return;
        }
        mShadowElevation = elevation;
        invalidate();
    }

    @Override
    public int getShadowElevation() {
        return mShadowElevation;
    }

    @Override
    public void setShadowAlpha(float shadowAlpha) {
        if (mShadowAlpha == shadowAlpha) {
            return;
        }
        mShadowAlpha = shadowAlpha;
        invalidate();
    }

    @Override
    public float getShadowAlpha() {
        return mShadowAlpha;
    }

    @Override
    public void setShadowColor(int shadowColor) {
        if (mShadowColor == shadowColor) {
            return;
        }
        mShadowColor = shadowColor;
        setShadowColorInner(mShadowColor);
    }

    @Override
    public int getShadowColor() {
        return mShadowColor;
    }

    @Override
    public void setRadius(int radius) {
        if (mRadius != radius) {
            setRadiusAndShadow(radius, mShadowElevation, mShadowAlpha);
        }
    }

    @Override
    public void setRadius(int radius, int hideRadiusSide) {
        if (mRadius == radius && hideRadiusSide == mHideRadiusSide) {
            return;
        }
        setRadiusAndShadow(radius, hideRadiusSide, mShadowElevation, mShadowAlpha);
    }

    @Override
    public int getRadius() {
        return mRadius;
    }

    @Override
    public void setOutlineInset(int left, int top, int right, int bottom) {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            mOutlineInsetLeft = left;
            mOutlineInsetRight = right;
            mOutlineInsetTop = top;
            mOutlineInsetBottom = bottom;
            owner.invalidateOutline();
        }
    }

    @Override
    public void setShowBorderOnlyBeforeL(boolean showBorderOnlyBeforeL) {
        mIsShowBorderOnlyBeforeL = showBorderOnlyBeforeL;
        invalidate();
    }

    @Override
    public void setHideRadiusSide(int hideRadiusSide) {
        if (mHideRadiusSide == hideRadiusSide) {
            return;
        }
        setRadiusAndShadow(mRadius, hideRadiusSide, mShadowElevation, mShadowAlpha);
    }

    @Override
    public int getHideRadiusSide() {
        return mHideRadiusSide;
    }

    @Override
    public void setRadiusAndShadow(int radius, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, mHideRadiusSide, shadowElevation, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, float shadowAlpha) {
        setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, mShadowColor, shadowAlpha);
    }

    @Override
    public void setRadiusAndShadow(int radius, int hideRadiusSide, int shadowElevation, int shadowColor, float shadowAlpha) {
        final View owner = mOwner.get();
        if (owner == null) {
            return;
        }

        mRadius = radius;
        mHideRadiusSide = hideRadiusSide;

        if (mRadius > 0) {
            if (hideRadiusSide == HIDE_RADIUS_SIDE_TOP) {
                mRadiusArray = new float[]{0, 0, 0, 0, mRadius, mRadius, mRadius, mRadius};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_RIGHT) {
                mRadiusArray = new float[]{mRadius, mRadius, 0, 0, 0, 0, mRadius, mRadius};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_BOTTOM) {
                mRadiusArray = new float[]{mRadius, mRadius, mRadius, mRadius, 0, 0, 0, 0};
            } else if (hideRadiusSide == HIDE_RADIUS_SIDE_LEFT) {
                mRadiusArray = new float[]{0, 0, mRadius, mRadius, mRadius, mRadius, 0, 0};
            } else {
                mRadiusArray = null;
            }
        }

        mShadowElevation = shadowElevation;
        mShadowAlpha = shadowAlpha;
        mShadowColor = shadowColor;
        if (useFeature()) {
            if (mShadowElevation == 0 || isRadiusWithSideHidden()) {
                owner.setElevation(0);
            } else {
                owner.setElevation(mShadowElevation);
            }

            setShadowColorInner(mShadowColor);

            owner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                @TargetApi(21)
                public void getOutline(View view, Outline outline) {
                    int w = view.getWidth(), h = view.getHeight();
                    if (w == 0 || h == 0) {
                        return;
                    }
                    if (isRadiusWithSideHidden()) {
                        int left = 0, top = 0, right = w, bottom = h;
                        if (mHideRadiusSide == HIDE_RADIUS_SIDE_LEFT) {
                            left -= mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_TOP) {
                            top -= mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_RIGHT) {
                            right += mRadius;
                        } else if (mHideRadiusSide == HIDE_RADIUS_SIDE_BOTTOM) {
                            bottom += mRadius;
                        }
                        outline.setRoundRect(left, top,
                                right, bottom, mRadius);
                        return;
                    }

                    int top = mOutlineInsetTop, bottom = Math.max(top + 1, h - mOutlineInsetBottom),
                            left = mOutlineInsetLeft, right = w - mOutlineInsetRight;
                    if (mIsOutlineExcludePadding) {
                        left += view.getPaddingLeft();
                        top += view.getPaddingTop();
                        right = Math.max(left + 1, right - view.getPaddingRight());
                        bottom = Math.max(top + 1, bottom - view.getPaddingBottom());
                    }

                    float shadowAlpha = mShadowAlpha;
                    if (mShadowElevation == 0) {
                        // outline.setAlpha will work even if shadowElevation == 0
                        shadowAlpha = 1f;
                    }

                    outline.setAlpha(shadowAlpha);

                    if (mRadius <= 0) {
                        outline.setRect(left, top,
                                right, bottom);
                    } else {
                        outline.setRoundRect(left, top,
                                right, bottom, mRadius);
                    }
                }
            });
            owner.setClipToOutline(mRadius > 0);

        }
        owner.invalidate();
    }

    @Override
    public void setBorderColor(int borderColor) {
        this.mBorderColor = borderColor;
    }

    @Override
    public void setBorderWidth(int borderWidth) {
        this.mBorderWidth = borderWidth;
    }

    @Override
    public void updateTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        mTopDividerInsetLeft = topInsetLeft;
        mTopDividerInsetRight = topInsetRight;
        mTopDividerHeight = topDividerHeight;
        mTopDividerColor = topDividerColor;
    }

    @Override
    public void updateBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        mBottomDividerInsetLeft = bottomInsetLeft;
        mBottomDividerInsetRight = bottomInsetRight;
        mBottomDividerColor = bottomDividerColor;
        mBottomDividerHeight = bottomDividerHeight;
    }

    @Override
    public void updateLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        mLeftDividerInsetTop = leftInsetTop;
        mLeftDividerInsetBottom = leftInsetBottom;
        mLeftDividerWidth = leftDividerWidth;
        mLeftDividerColor = leftDividerColor;
    }

    @Override
    public void updateRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        mRightDividerInsetTop = rightInsetTop;
        mRightDividerInsetBottom = rightInsetBottom;
        mRightDividerWidth = rightDividerWidth;
        mRightDividerColor = rightDividerColor;
    }

    @Override
    public void onlyShowTopDivider(int topInsetLeft, int topInsetRight, int topDividerHeight, int topDividerColor) {
        updateTopDivider(topInsetLeft, topInsetRight, topDividerHeight, topDividerColor);
        mLeftDividerWidth = 0;
        mRightDividerWidth = 0;
        mBottomDividerHeight = 0;
    }

    @Override
    public void onlyShowBottomDivider(int bottomInsetLeft, int bottomInsetRight, int bottomDividerHeight, int bottomDividerColor) {
        updateBottomDivider(bottomInsetLeft, bottomInsetRight, bottomDividerHeight, bottomDividerColor);
        mLeftDividerWidth = 0;
        mRightDividerWidth = 0;
        mTopDividerHeight = 0;
    }

    @Override
    public void onlyShowLeftDivider(int leftInsetTop, int leftInsetBottom, int leftDividerWidth, int leftDividerColor) {
        updateLeftDivider(leftInsetTop, leftInsetBottom, leftDividerWidth, leftDividerColor);
        mRightDividerWidth = 0;
        mTopDividerHeight = 0;
        mBottomDividerHeight = 0;
    }

    @Override
    public void onlyShowRightDivider(int rightInsetTop, int rightInsetBottom, int rightDividerWidth, int rightDividerColor) {
        updateRightDivider(rightInsetTop, rightInsetBottom, rightDividerWidth, rightDividerColor);
        mLeftDividerWidth = 0;
        mTopDividerHeight = 0;
        mBottomDividerHeight = 0;
    }

    @Override
    public void setTopDividerAlpha(int dividerAlpha) {
        this.mTopDividerAlpha = dividerAlpha;
    }

    @Override
    public void setBottomDividerAlpha(int dividerAlpha) {
        this.mBottomDividerAlpha = dividerAlpha;
    }

    @Override
    public void setLeftDividerAlpha(int dividerAlpha) {
        this.mLeftDividerAlpha = dividerAlpha;
    }

    @Override
    public void setRightDividerAlpha(int dividerAlpha) {
        this.mRightDividerAlpha = dividerAlpha;
    }

    @Override
    public void setOuterNormalColor(int color) {
        mOuterNormalColor = color;
        View owner = mOwner.get();
        if (owner != null) {
            owner.invalidate();
        }
    }

    /**
     * 有radius, 但是有一边不显示radius。
     *
     * @return
     */
    public boolean isRadiusWithSideHidden() {
        return mRadius > 0 && mHideRadiusSide != HIDE_RADIUS_SIDE_NONE;
    }

    private void setShadowColorInner(int shadowColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            owner.setOutlineAmbientShadowColor(shadowColor);
            owner.setOutlineSpotShadowColor(shadowColor);
        }
    }

    /**
     * 重绘制
     */
    private void invalidate() {
        if (useFeature()) {
            View owner = mOwner.get();
            if (owner == null) {
                return;
            }
            if (mShadowElevation == 0) {
                owner.setElevation(0);
            } else {
                owner.setElevation(mShadowElevation);
            }
            owner.invalidateOutline();
        }
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     * @param w
     * @param h
     */
    public void drawDividers(Canvas canvas, int w, int h) {
        if (mDividerPaint == null &&
                (mTopDividerHeight > 0 || mBottomDividerHeight > 0 || mLeftDividerWidth > 0 || mRightDividerWidth > 0)) {
            mDividerPaint = new Paint();
        }
        if (mTopDividerHeight > 0) {
            mDividerPaint.setStrokeWidth(mTopDividerHeight);
            mDividerPaint.setColor(mTopDividerColor);
            if (mTopDividerAlpha < 255) {
                mDividerPaint.setAlpha(mTopDividerAlpha);
            }
            float y = mTopDividerHeight * 1f / 2;
            canvas.drawLine(mTopDividerInsetLeft, y, w - mTopDividerInsetRight, y, mDividerPaint);
        }

        if (mBottomDividerHeight > 0) {
            mDividerPaint.setStrokeWidth(mBottomDividerHeight);
            mDividerPaint.setColor(mBottomDividerColor);
            if (mBottomDividerAlpha < 255) {
                mDividerPaint.setAlpha(mBottomDividerAlpha);
            }
            float y = (float) Math.floor(h - mBottomDividerHeight * 1f / 2);
            canvas.drawLine(mBottomDividerInsetLeft, y, w - mBottomDividerInsetRight, y, mDividerPaint);
        }

        if (mLeftDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mLeftDividerWidth);
            mDividerPaint.setColor(mLeftDividerColor);
            if (mLeftDividerAlpha < 255) {
                mDividerPaint.setAlpha(mLeftDividerAlpha);
            }
            canvas.drawLine(0, mLeftDividerInsetTop, 0, h - mLeftDividerInsetBottom, mDividerPaint);
        }

        if (mRightDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mRightDividerWidth);
            mDividerPaint.setColor(mRightDividerColor);
            if (mRightDividerAlpha < 255) {
                mDividerPaint.setAlpha(mRightDividerAlpha);
            }
            canvas.drawLine(w, mRightDividerInsetTop, w, h - mRightDividerInsetBottom, mDividerPaint);
        }
    }

    /**
     * 绘制圆角
     *
     * @param canvas
     */
    public void dispatchRoundBorderDraw(Canvas canvas) {
        View owner = mOwner.get();
        if (owner == null) {
            return;
        }
        if (mBorderColor == 0 && (mRadius == 0 || mOuterNormalColor == 0)) {
            return;
        }

        if (mIsShowBorderOnlyBeforeL && useFeature() && mShadowElevation != 0) {
            return;
        }

        int width = canvas.getWidth(), height = canvas.getHeight();

        // react
        if (mIsOutlineExcludePadding) {
            mBorderRect.set(1 + owner.getPaddingLeft(), 1 + owner.getPaddingTop(),
                    width - 1 - owner.getPaddingRight(), height - 1 - owner.getPaddingBottom());
        } else {
            mBorderRect.set(1, 1, width - 1, height - 1);
        }

        if (mRadius == 0 || (!useFeature() && mOuterNormalColor == 0)) {
            mClipPaint.setStyle(Paint.Style.STROKE);
            mClipPaint.setColor(mBorderColor);
            canvas.drawRect(mBorderRect, mClipPaint);
            return;
        }

        // 圆角矩形
        if (!useFeature()) {
            int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawColor(mOuterNormalColor);
            mClipPaint.setColor(mOuterNormalColor);
            mClipPaint.setStyle(Paint.Style.FILL);
            mClipPaint.setXfermode(mMode);
            if (mRadiusArray == null) {
                canvas.drawRoundRect(mBorderRect, mRadius, mRadius, mClipPaint);
            } else {
                drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint);
            }
            mClipPaint.setXfermode(null);
            canvas.restoreToCount(layerId);
        }

        mClipPaint.setColor(mBorderColor);
        mClipPaint.setStrokeWidth(mBorderWidth);
        mClipPaint.setStyle(Paint.Style.STROKE);
        if (mRadiusArray == null) {
            canvas.drawRoundRect(mBorderRect, mRadius, mRadius, mClipPaint);
        } else {
            drawRoundRect(canvas, mBorderRect, mRadiusArray, mClipPaint);
        }
    }

    private void drawRoundRect(Canvas canvas, RectF rect, float[] radiusArray, Paint paint) {
        mPath.reset();
        mPath.addRoundRect(rect, radiusArray, Path.Direction.CW);
        canvas.drawPath(mPath, paint);
    }

    public static boolean useFeature() {
        return Build.VERSION.SDK_INT >= 21;
    }


    public static class Builder {
        private Context context;
        private View owner;
        int mWidthLimit;
        int mWidthMini;
        int mHeightLimit;
        int mHeightMini;
        int mTopDividerColor;
        int mTopDividerHeight;
        int mTopDividerInsetLeft;
        int mTopDividerInsetRight;
        int mBottomDividerColor;
        int mBottomDividerHeight;
        int mBottomDividerInsetLeft;
        int mBottomDividerInsetRight;
        int mLeftDividerColor;
        int mLeftDividerWidth;
        int mLeftDividerInsetTop;
        int mLeftDividerInsetBottom;
        int mRightDividerColor;
        int mRightDividerWidth;
        int mRightDividerInsetTop;
        int mRightDividerInsetBottom;
        int mBorderColor;
        int mBorderWidth;
        int radius;
        int mOuterNormalColor;
        int mHideRadiusSide;
        boolean mIsShowBorderOnlyBeforeL;
        int shadow;
        float mShadowAlpha;
        boolean useThemeGeneralShadowElevation;
        int mOutlineInsetLeft;
        int mOutlineInsetRight;
        int mOutlineInsetTop;
        int mOutlineInsetBottom;
        boolean mIsOutlineExcludePadding;

        public Builder(Context context, View owner) {
            this.context = context;
            this.owner = owner;
        }

        public MUILayoutHelper build() {
            return new MUILayoutHelper(context, this, owner);
        }
    }

    public int getMeasuredWidthSpec(int widthMeasureSpec) {
        if (mWidthLimit > 0) {
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            if (size > mWidthLimit) {
                int mode = View.MeasureSpec.getMode(widthMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY);
                }

            }
        }
        return widthMeasureSpec;
    }

    public int getMeasuredHeightSpec(int heightMeasureSpec) {
        if (mHeightLimit > 0) {
            int size = View.MeasureSpec.getSize(heightMeasureSpec);
            if (size > mHeightLimit) {
                int mode = View.MeasureSpec.getMode(heightMeasureSpec);
                if (mode == View.MeasureSpec.AT_MOST) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.AT_MOST);
                } else {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mWidthLimit, View.MeasureSpec.EXACTLY);
                }
            }
        }
        return heightMeasureSpec;
    }

    public int handleMiniWidth(int widthMeasureSpec, int measuredWidth) {
        if (View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.EXACTLY
                && measuredWidth < mWidthMini) {
            return View.MeasureSpec.makeMeasureSpec(mWidthMini, View.MeasureSpec.EXACTLY);
        }
        return widthMeasureSpec;
    }

    public int handleMiniHeight(int heightMeasureSpec, int measuredHeight) {
        if (View.MeasureSpec.getMode(heightMeasureSpec) != View.MeasureSpec.EXACTLY
                && measuredHeight < mHeightMini) {
            return View.MeasureSpec.makeMeasureSpec(mHeightMini, View.MeasureSpec.EXACTLY);
        }
        return heightMeasureSpec;
    }
}
