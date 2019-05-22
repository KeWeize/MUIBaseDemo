package vit.com.mui.widget.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import vit.com.mui.R;
import vit.com.mui.layout.MUILinearLayout;
import vit.com.mui.utils.MUIAttrsHelper;

/**
 * @author kewz
 * @date 2019/5/19
 */
public class MUIDialogView extends MUILinearLayout {

    private int mMinWidth;
    private int mMaxWidth;
    private OnDecorationListener mOnDecorationListener;

    public MUIDialogView(Context context) {
        this(context, null, 0);
    }

    public MUIDialogView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MUIDialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMinWidth = MUIAttrsHelper.getAttrDimen(context, R.attr.mui_dialog_min_width);
        mMaxWidth = MUIAttrsHelper.getAttrDimen(context, R.attr.mui_dialog_max_width);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (mMaxWidth > 0 && widthSize > mMaxWidth) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, widthMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            int measureWidth = getMeasuredWidth();
            if (measureWidth < mMinWidth && mMinWidth < widthSize) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMinWidth,
                        MeasureSpec.EXACTLY);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mOnDecorationListener != null) {
            mOnDecorationListener.onDraw(canvas, this);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mOnDecorationListener != null) {
            mOnDecorationListener.onDrawOver(canvas, this);
        }
    }

    public void setOnDecorationListener(OnDecorationListener onDecorationListener) {
        mOnDecorationListener = onDecorationListener;
    }

    public void setMinWidth(int minWidth) {
        mMinWidth = minWidth;
    }

    public void setMaxWidth(int maxWidth) {
        mMaxWidth = maxWidth;
    }

    public interface OnDecorationListener {
        void onDraw(Canvas canvas, MUIDialogView view);

        void onDrawOver(Canvas canvas, MUIDialogView view);
    }

}
