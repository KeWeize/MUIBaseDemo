package vit.com.mui.widget.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vit.com.mui.R;
import vit.com.mui.layout.MUIRelativeLayout;
import vit.com.mui.utils.MUIAttrsHelper;
import vit.com.mui.utils.MUIViewHelper;

/**
 * @author kewz
 * @date 2019/5/23
 */
public class MUIDialogMenuItemView extends MUIRelativeLayout {

    private int index = -1;
    private MenuItemViewListener mMenuItemViewListener;
    private boolean mIsChecked = false;

    public MUIDialogMenuItemView(Context context) {
        super(context, null, R.attr.mui_dialog_menu_item_style);
    }

    /**
     * 创建一个 TextView
     *
     * @param context
     * @return
     */
    public static TextView createItemTextView(Context context) {
        TextView mTextView = new TextView(context);

        TypedArray at = context.obtainStyledAttributes(null, R.styleable.MUITextViewCommAttr,
                R.attr.mui_dialog_menu_item_style, 0);
        int count = at.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = at.getIndex(i);
            if (attr == R.styleable.MUITextViewCommAttr_android_gravity) {
                mTextView.setGravity(at.getInt(attr, -1));
            } else if (attr == R.styleable.MUITextViewCommAttr_android_textColor) {
                mTextView.setTextColor(at.getColorStateList(attr));
            } else if (attr == R.styleable.MUITextViewCommAttr_android_textSize) {
                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, at.getDimensionPixelSize(attr, 0));
            }
        }
        at.recycle();

        mTextView.setSingleLine(true);
        mTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        return mTextView;
    }

    public int getMenuIndex() {
        return this.index;
    }

    public void setMenuIndex(int index) {
        this.index = index;
    }

    protected void notifyCheckChange(boolean isChecked) {
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
        notifyCheckChange(mIsChecked);
    }

    public void setListener(MenuItemViewListener listener) {
        if (!isClickable()) {
            setClickable(true);
        }
        mMenuItemViewListener = listener;
    }

    @Override
    public boolean performClick() {
        if (mMenuItemViewListener != null) {
            mMenuItemViewListener.onClick(index);
        }
        return super.performClick();
    }

    /**
     * 菜单视图点击回调
     */
    public interface MenuItemViewListener {
        void onClick(int index);
    }


    /**
     * 简单 TextView 的 itemView
     */
    public static class SimpleTextItemView extends MUIDialogMenuItemView {

        protected TextView mTextView;

        public SimpleTextItemView(Context context) {
            super(context);
            init();
        }

        public SimpleTextItemView(Context context, CharSequence charSequence) {
            super(context);
            init();
            setText(charSequence);
        }

        private void init() {
            mTextView = createItemTextView(getContext());
            addView(mTextView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        public void setTextColor(int color) {
            mTextView.setTextColor(color);
        }

    }

    /**
     * Mark Item View
     */
    public static class MarkItemView extends MUIDialogMenuItemView {

        private Context mContext;
        private TextView mTextView;
        private ImageView mCheckedView;

        public MarkItemView(Context context) {
            super(context);
            mContext = context;
            mCheckedView = new ImageView(mContext);
            mCheckedView.setId(MUIViewHelper.generateViewId());

            TypedArray ta = context.obtainStyledAttributes(null, R.styleable.MUIDialogMenuMarkCheckContainer,
                    R.attr.mui_dialog_menu_item_style, 0);
            int markMarginHor = 0;
            int count = ta.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = ta.getIndex(i);
                if (attr == R.styleable.MUIDialogMenuMarkCheckContainer_mui_dialog_menu_item_check_mark_margin_hor) {
                    markMarginHor = ta.getDimensionPixelSize(attr, 0);
                } else if (attr == R.styleable.MUIDialogMenuMarkCheckContainer_mui_dialog_menu_item_mark_drawable) {
                    mCheckedView.setImageDrawable(MUIAttrsHelper.getAttrDrawable(context, ta, attr));
                }
            }
            ta.recycle();

            LayoutParams rlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            rlp.addRule(CENTER_VERTICAL, TRUE);
            rlp.addRule(ALIGN_PARENT_RIGHT, TRUE);
            rlp.leftMargin = markMarginHor;
            addView(mCheckedView, rlp);

            mTextView = createItemTextView(mContext);
            LayoutParams tvLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            tvLp.addRule(ALIGN_PARENT_LEFT, TRUE);
            tvLp.addRule(LEFT_OF, mCheckedView.getId());
            addView(mTextView, tvLp);
        }

        public MarkItemView(Context context, CharSequence text) {
            this(context);
            setText(text);
        }

        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        @Override
        protected void notifyCheckChange(boolean isChecked) {
            MUIViewHelper.safeSetImageViewSelected(mCheckedView, isChecked);
        }
    }

    /**
     * Check Item View
     */
    public static class CheckItemView extends MUIDialogMenuItemView {

        private Context mContext;
        private TextView mTextView;
        private ImageView mCheckedView;

        public CheckItemView(Context context, boolean right) {
            super(context);
            mContext = context;
            mCheckedView = new ImageView(mContext);
            mCheckedView.setId(MUIViewHelper.generateViewId());

            TypedArray a = context.obtainStyledAttributes(null, R.styleable.MUIDialogMenuMarkCheckContainer,
                    R.attr.mui_dialog_menu_item_style, 0);
            int markMarginHor = 0;
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.MUIDialogMenuMarkCheckContainer_mui_dialog_menu_item_check_mark_margin_hor) {
                    markMarginHor = a.getDimensionPixelSize(attr, 0);
                } else if (attr == R.styleable.MUIDialogMenuMarkCheckContainer_mui_dialog_menu_item_check_drawable) {
                    mCheckedView.setImageDrawable(MUIAttrsHelper.getAttrDrawable(context, a, attr));
                }
            }
            a.recycle();


            LayoutParams rlp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            rlp.addRule(CENTER_VERTICAL, TRUE);
            if (right) {
                rlp.addRule(ALIGN_PARENT_RIGHT, TRUE);
                rlp.leftMargin = markMarginHor;
            } else {
                rlp.addRule(ALIGN_PARENT_LEFT, TRUE);
                rlp.rightMargin = markMarginHor;
            }

            addView(mCheckedView, rlp);

            mTextView = createItemTextView(mContext);
            LayoutParams tvLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            if (right) {
                tvLp.addRule(LEFT_OF, mCheckedView.getId());
            } else {
                tvLp.addRule(RIGHT_OF, mCheckedView.getId());
            }

            addView(mTextView, tvLp);
        }

        public CheckItemView(Context context, boolean right, CharSequence text) {
            this(context, right);
            setText(text);
        }

        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        public CharSequence getText() {
            return mTextView.getText();
        }

        @Override
        protected void notifyCheckChange(boolean isChecked) {
            MUIViewHelper.safeSetImageViewSelected(mCheckedView, isChecked);
        }
    }

}
