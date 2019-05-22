package vit.com.mui.widget.roundwidget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import vit.com.mui.R;
import vit.com.mui.layout.MUILinearLayout;
import vit.com.mui.utils.MUIViewHelper;

/**
 * @author kewz
 * @date 2019/5/13
 */
public class MUIRoundLinearLayout extends MUILinearLayout {

    public MUIRoundLinearLayout(Context context) {
        super(context);
        initAttrs(context, null, 0);
    }

    public MUIRoundLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public MUIRoundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MUIRoundLinearLayout,
                defStyleAttr, 0);
        MUIRoundDrawable.Builder builder = new MUIRoundDrawable.Builder();
        ColorStateList colorBg = ta.getColorStateList(R.styleable.MUIRoundLinearLayout_mui_backgroundColor);
        builder.backgroundColor(colorBg);
        ColorStateList colorBorder = ta.getColorStateList(R.styleable.MUIRoundLinearLayout_mui_borderColor);
        builder.borderColor(colorBorder);
        int borderWidth = ta.getDimensionPixelSize(R.styleable.MUIRoundLinearLayout_mui_borderWidth, 0);
        builder.borderWidth(borderWidth);
        boolean isAdjust = ta.getBoolean(R.styleable.MUIRoundLinearLayout_mui_isRadiusAdjustBounds, true);
        builder.isRadiusAdjustBounds(isAdjust);
        int radius = ta.getDimensionPixelSize(R.styleable.MUIRoundLinearLayout_mui_radius, 0);
        builder.radius(radius);
        int mRadiusTopLeft = ta.getDimensionPixelSize(R.styleable.MUIRoundLinearLayout_mui_radiusTopLeft, 0);
        builder.radiusTopLeft(mRadiusTopLeft);
        int mRadiusTopRight = ta.getDimensionPixelSize(R.styleable.MUIRoundLinearLayout_mui_radiusTopRight, 0);
        builder.radiusTopRight(mRadiusTopRight);
        int mRadiusBottomLeft = ta.getDimensionPixelSize(R.styleable.MUIRoundLinearLayout_mui_radiusBottomLeft, 0);
        builder.radiusBottomLeft(mRadiusBottomLeft);
        int mRadiusBottomRight = ta.getDimensionPixelSize(R.styleable.MUIRoundLinearLayout_mui_radiusBottomRight, 0);
        builder.radiusBottomRight(mRadiusBottomRight);
        ta.recycle();
        // 生成并设置 bg drawable
        MUIRoundDrawable drawable = builder.build();
        MUIViewHelper.setBackgroundKeepingPadding(this, drawable);
        setChangeAlphaWhenDisable(false);
        setChangeAlphaWhenPress(false);
    }

}
