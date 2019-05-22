package vit.com.mui.widget.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import vit.com.mui.R;
import vit.com.mui.alpha.MUIAlphaButton;
import vit.com.mui.layout.MUIButton;
import vit.com.mui.utils.MUIViewHelper;

/**
 * @author kewz
 * @date 2019/5/22
 */
public class MUIDialogAction {

    @IntDef({ACTION_PROP_POSITIVE, ACTION_PROP_NEUTRAL, ACTION_PROP_NEGATIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionProp {
    }

    /**
     * 用于标记操作类型
     */
    public static final int ACTION_PROP_POSITIVE = 0;
    public static final int ACTION_PROP_NEUTRAL = 1;
    public static final int ACTION_PROP_NEGATIVE = 2;

    private Context mContext;
    private CharSequence mCharSequence;
    private @DrawableRes
    int mIconDrawableRes;
    private @ActionProp
    int mActionProp;
    // 点击回调
    private OnActionListener mOnActionListener;
    private MUIButton mButton;
    private boolean mIsEnabled = true;

    public MUIDialogAction(Context context, @StringRes int strResId, OnActionListener onActionListener) {
        this(context, context.getString(strResId), ACTION_PROP_NEUTRAL, onActionListener);
    }

    public MUIDialogAction(Context context, CharSequence charSequence,
                           OnActionListener onActionListener) {
        this(context, charSequence, ACTION_PROP_NEUTRAL, onActionListener);
    }

    public MUIDialogAction(Context context, @StringRes int strResId,
                           @ActionProp int actionProp, OnActionListener onActionListener) {
        this(context, context.getString(strResId), actionProp, onActionListener);
    }

    public MUIDialogAction(Context context, CharSequence charSequence,
                           @ActionProp int actionProp, OnActionListener onActionListener) {
        this.mContext = context;
        this.mCharSequence = charSequence;
        this.mActionProp = actionProp;
        this.mOnActionListener = onActionListener;
    }

    public void setOnClickListener(OnActionListener mOnActionListener) {
        this.mOnActionListener = mOnActionListener;
    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;
        if (mButton != null) {
            mButton.setEnabled(mIsEnabled);
        }
    }

    /**
     * 为外部生成一个 Action View
     *
     * @param dialog
     * @param index
     * @return
     */
    public MUIButton buildActionView(final MUIDialog dialog, final int index) {
        mButton = generateActionView(dialog.getContext(), mCharSequence, index);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnActionListener != null) {
                    mOnActionListener.onClick(dialog, index);
                }
            }
        });
        return mButton;
    }

    /**
     * 生成一个 Action View
     *
     * @param context
     * @param text
     * @param resId
     * @return
     */
    private MUIButton generateActionView(Context context, CharSequence text, @DrawableRes int resId) {
        MUIButton mButton = new MUIButton(context);
        MUIViewHelper.setBackground(mButton, null);
        mButton.setMinHeight(0);
        mButton.setMinimumHeight(0);
        mButton.setChangeAlphaWhenDisable(true);
        mButton.setChangeAlphaWhenPress(true);
        int mPaddingHor = 0;
        int mIconSpace = 0;
        ColorStateList mNegativeTextColor = null;
        ColorStateList mPositiveTextColor = null;
        TypedArray ta = context.obtainStyledAttributes(null, R.styleable.MUIDialogActionView,
                R.attr.mui_dialog_action_view_style, R.style.MUI_Dialog_ActionViewDefStyle);

        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.MUIDialogActionView_android_gravity) {
                mButton.setGravity(ta.getInt(attr, -1));
            } else if (attr == R.styleable.MUIDialogActionView_android_textColor) {
                mButton.setTextColor(ta.getColorStateList(attr));
            } else if (attr == R.styleable.MUIDialogActionView_android_textSize) {
                mButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.MUIDialogActionView_mui_dialog_action_button_padding_horizontal) {
                mPaddingHor = ta.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.MUIDialogActionView_android_background) {
                MUIViewHelper.setBackground(mButton, ta.getDrawable(attr));
            } else if (attr == R.styleable.MUIDialogActionView_android_minWidth) {
                int miniWidth = ta.getDimensionPixelSize(attr, 0);
                mButton.setMinWidth(miniWidth);
                mButton.setMinimumWidth(miniWidth);
            } else if (attr == R.styleable.MUIDialogActionView_mui_dialog_positive_action_text_color) {
                mPositiveTextColor = ta.getColorStateList(attr);
            } else if (attr == R.styleable.MUIDialogActionView_mui_dialog_negative_action_text_color) {
                mNegativeTextColor = ta.getColorStateList(attr);
            } else if (attr == R.styleable.MUIDialogActionView_mui_dialog_action_icon_space) {
                mIconSpace = ta.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.MUIDialogActionView_android_textStyle) {
                int styleIndex = ta.getInt(attr, -1);
                mButton.setTypeface(null, styleIndex);
            }
        }
        ta.recycle();
        mButton.setPadding(mPaddingHor, 0, mPaddingHor, 0);
        if (mIconDrawableRes <= 0) {
            mButton.setText(text);
        } else {
            // todo setText with icon
        }
        mButton.setClickable(true);
        mButton.setEnabled(mIsEnabled);

        if (mActionProp == ACTION_PROP_POSITIVE) {
            mButton.setTextColor(mPositiveTextColor);
        } else if (mActionProp == ACTION_PROP_NEGATIVE) {
            mButton.setTextColor(mNegativeTextColor);
        }
        return mButton;
    }

    /**
     * 操作点击回调
     */
    public interface OnActionListener {
        void onClick(MUIDialog dialog, int index);
    }

}
