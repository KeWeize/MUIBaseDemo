package vit.com.mui.widget.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kewz
 * @date 2019/5/19
 */
public class MUIObservableScrollView extends ScrollView {

    private int mScrollOffset = 0;

    private List<OnScrollChangedListener> mOnScrollChangedListeners;


    public MUIObservableScrollView(Context context) {
        super(context);
    }

    public MUIObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MUIObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 添加滚动回调
     *
     * @param listener
     */
    public void addOnScrollChangedListener(OnScrollChangedListener listener) {
        if (mOnScrollChangedListeners == null) {
            mOnScrollChangedListeners = new ArrayList<>();
        }
        if (mOnScrollChangedListeners.contains(listener)) {
            return;
        }
        mOnScrollChangedListeners.add(listener);
    }

    /**
     * 移除滚动回调
     *
     * @param listener
     */
    public void removeOnScrollChangedListener(OnScrollChangedListener listener) {
        if (mOnScrollChangedListeners == null) {
            return;
        }
        if (mOnScrollChangedListeners.contains(listener)) {
            mOnScrollChangedListeners.remove(listener);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollOffset = t;
        if (mOnScrollChangedListeners != null && !mOnScrollChangedListeners.isEmpty()) {
            for (OnScrollChangedListener listener : mOnScrollChangedListeners) {
                listener.onScrollChanged(this, l, t, oldl, oldt);
            }
        }
    }

    /**
     * 获取偏移值
     *
     * @return
     */
    public int getScrollOffset() {
        return mScrollOffset;
    }

    /**
     * 滚动回调接口
     */
    public interface OnScrollChangedListener {
        void onScrollChanged(MUIObservableScrollView scrollView, int l, int t, int oldl, int oldt);
    }


}
