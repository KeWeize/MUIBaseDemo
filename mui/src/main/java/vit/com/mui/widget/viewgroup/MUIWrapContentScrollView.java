package vit.com.mui.widget.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @author kewz
 * @date 2019/5/19
 */
public class MUIWrapContentScrollView extends MUIObservableScrollView {

    private int mMaxHeight = Integer.MAX_VALUE >> 2;

    public MUIWrapContentScrollView(Context context) {
        super(context);
    }

    public MUIWrapContentScrollView(Context context, int mMaxHeight) {
        super(context);
        this.mMaxHeight = mMaxHeight;
    }

    public MUIWrapContentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MUIWrapContentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMaxHeight(int maxHeight) {
        if (mMaxHeight != maxHeight) {
            mMaxHeight = maxHeight;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams vlp = getLayoutParams();
        int expandSpec;
        if (vlp.height > 0 && vlp.height <= mMaxHeight) {
            // 如果高度小于最大高度，按实际高度测量
            expandSpec = MeasureSpec.makeMeasureSpec(vlp.height, MeasureSpec.EXACTLY);
        } else {
            // 如果高度大于最大高度，按最大高度测量
            expandSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
