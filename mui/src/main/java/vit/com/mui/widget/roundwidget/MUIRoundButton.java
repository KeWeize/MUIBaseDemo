package vit.com.mui.widget.roundwidget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import vit.com.mui.R;
import vit.com.mui.alpha.MUIAlphaButton;
import vit.com.mui.utils.MUIViewHelper;

/**
 * @author kewz
 * @date 2019/5/13
 */
public class MUIRoundButton extends MUIAlphaButton {

    public MUIRoundButton(Context context) {
        super(context);
        initAttrs(context, null, 0);
    }

    public MUIRoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public MUIRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MUIRoundButton,
                defStyleAttr, 0);
        MUIRoundDrawable.Builder builder = new MUIRoundDrawable.Builder();
        ColorStateList colorBg = ta.getColorStateList(R.styleable.MUIRoundButton_mui_backgroundColor);
        builder.backgroundColor(colorBg);
        ColorStateList colorBorder = ta.getColorStateList(R.styleable.MUIRoundButton_mui_borderColor);
        builder.borderColor(colorBorder);
        int borderWidth = ta.getDimensionPixelSize(R.styleable.MUIRoundButton_mui_borderWidth, 0);
        builder.borderWidth(borderWidth);
        boolean isAdjust = ta.getBoolean(R.styleable.MUIRoundButton_mui_isRadiusAdjustBounds, true);
        builder.isRadiusAdjustBounds(isAdjust);
        int radius = ta.getDimensionPixelSize(R.styleable.MUIRoundButton_mui_radius, 0);
        builder.radius(radius);
        int mRadiusTopLeft = ta.getDimensionPixelSize(R.styleable.MUIRoundButton_mui_radiusTopLeft, 0);
        builder.radiusTopLeft(mRadiusTopLeft);
        int mRadiusTopRight = ta.getDimensionPixelSize(R.styleable.MUIRoundButton_mui_radiusTopRight, 0);
        builder.radiusTopRight(mRadiusTopRight);
        int mRadiusBottomLeft = ta.getDimensionPixelSize(R.styleable.MUIRoundButton_mui_radiusBottomLeft, 0);
        builder.radiusBottomLeft(mRadiusBottomLeft);
        int mRadiusBottomRight = ta.getDimensionPixelSize(R.styleable.MUIRoundButton_mui_radiusBottomRight, 0);
        builder.radiusBottomRight(mRadiusBottomRight);
        ta.recycle();
        // 生成并设置 bg drawable
        MUIRoundDrawable drawable = builder.build();
        MUIViewHelper.setBackgroundKeepingPadding(this, drawable);
        setChangeAlphaWhenDisable(false);
        setChangeAlphaWhenPress(false);
    }

}
