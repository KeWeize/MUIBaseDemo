package vit.com.mui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import vit.com.mui.R;
import vit.com.mui.alpha.MUIAlphaTextView;
import vit.com.mui.utils.MUIAttrsHelper;
import vit.com.mui.widget.viewgroup.MUIWrapContentScrollView;

/**
 * @author kewz
 * @date 2019/5/19
 */
public class MUIDialog extends Dialog {

    boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private boolean mCanceledOnTouchOutsideSet;
    private Context mContext;

    public MUIDialog(@NonNull Context context) {
        super(context);
    }

    public MUIDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        mCancelable = flag;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !mCancelable) {
            mCancelable = true;
        }
        mCanceledOnTouchOutside = cancel;
        mCanceledOnTouchOutsideSet = true;
    }

    @Override
    public void show() {
        super.show();
    }

    /**
     * 初始化 Dialog WindowManager.LayoutParams
     */
    private void initDialog() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams wmlp = window.getAttributes();
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.gravity = Gravity.CENTER;
        window.setAttributes(wmlp);
    }


    boolean shouldWindowCloseOnTouchOutside() {
        if (!mCanceledOnTouchOutsideSet) {
            if (Build.VERSION.SDK_INT < 11) {
                mCanceledOnTouchOutside = true;
            } else {
                TypedArray a = getContext().obtainStyledAttributes(
                        new int[]{android.R.attr.windowCloseOnTouchOutside});
                mCanceledOnTouchOutside = a.getBoolean(0, true);
                a.recycle();
            }
            mCanceledOnTouchOutsideSet = true;
        }
        return mCanceledOnTouchOutside;
    }

    void cancelOutSide() {
        if (mCancelable && isShowing() && shouldWindowCloseOnTouchOutside()) {
            cancel();
        }
    }


    /***##################### MessageDialogBuilder ######################*/
    public static class MessageDialogBuilder extends MUIDialogBuilder<MessageDialogBuilder> {

        protected CharSequence mMessage;
        private MUIWrapContentScrollView mScrollContainer;
        protected AppCompatTextView mTextView;

        public MessageDialogBuilder(Context mContext) {
            super(mContext);
        }

        /**
         * 设置消息内容
         *
         * @param stringResId
         * @return
         */
        public MessageDialogBuilder setMessage(@StringRes int stringResId) {
            return setMessage(getBaseContext().getString(stringResId));
        }

        /**
         * 设置消息内容
         *
         * @param message
         * @return
         */
        public MessageDialogBuilder setMessage(CharSequence message) {
            this.mMessage = message;
            return this;
        }

        /**
         * 获取消息视图
         *
         * @return
         */
        protected TextView getMessageTextView() {
            return mTextView;
        }

        @Override
        protected void onCreateContent(MUIDialog dialog, ViewGroup parent, Context context) {
            if (mMessage != null && mMessage.length() > 0) {
                mTextView = new AppCompatTextView(context);
                assignMessageTvWithAttr(mTextView, hasTitle(),
                        R.attr.mui_dialog_message_content_style);
                mTextView.setText(mMessage);
                mScrollContainer = new MUIWrapContentScrollView(context);
                mScrollContainer.setMaxHeight(getContentAreaMaxHeight());
                mScrollContainer.setVerticalScrollBarEnabled(false);
                mScrollContainer.addView(mTextView);
                parent.addView(mScrollContainer);
            }
        }

        @Override
        protected void onConfigTitleView(TextView mTitleView) {
            super.onConfigTitleView(mTitleView);
            if (mMessage == null || mMessage.length() == 0) {
                // 没有内容，给titleView 设置 paddingBotom
                TypedArray a = mTitleView.getContext().obtainStyledAttributes(null,
                        R.styleable.MUIDialogTitleTvCustom, R.attr.mui_dialog_title_style, 0);
                int count = a.getIndexCount();
                for (int i = 0; i < count; i++) {
                    int attr = a.getIndex(i);
                    if (attr == R.styleable.MUIDialogTitleTvCustom_mui_paddingBottomWhenNotContent) {
                        mTitleView.setPadding(
                                mTitleView.getPaddingLeft(),
                                mTitleView.getPaddingTop(),
                                mTitleView.getPaddingRight(),
                                a.getDimensionPixelSize(attr, mTitleView.getPaddingBottom())
                        );
                    }
                }
                a.recycle();
            }
        }

        /**
         * 设置 TextView 属性
         *
         * @param mMessageTv
         * @param hasTitle
         * @param defAttr
         */
        private static void assignMessageTvWithAttr(TextView mMessageTv, boolean hasTitle, int defAttr) {
            MUIAttrsHelper.assignTextViewWithAttr(mMessageTv, defAttr);
            if (!hasTitle) {
                TypedArray a = mMessageTv.getContext().obtainStyledAttributes(null,
                        R.styleable.MUIDialogMessageTvCustom, defAttr, 0);
                int count = a.getIndexCount();
                for (int i = 0; i < count; i++) {
                    int attr = a.getIndex(i);
                    if (attr == R.styleable.MUIDialogMessageTvCustom_mui_paddingTopWhenNotTitle) {
                        mMessageTv.setPadding(
                                mMessageTv.getPaddingLeft(),
                                a.getDimensionPixelSize(attr, mMessageTv.getPaddingTop()),
                                mMessageTv.getPaddingRight(),
                                mMessageTv.getPaddingBottom()
                        );
                    }
                }
                a.recycle();
            }
        }
    }


    /***##################### CheckBoxMessageDialogBuilder ######################*/
    public static class CheckBoxMessageDialogBuilder extends MessageDialogBuilder {

        private boolean mIsChecked = false;
        private Drawable mCheckMarkDrawable;

        public CheckBoxMessageDialogBuilder(Context mContext) {
            super(mContext);
            mCheckMarkDrawable = MUIAttrsHelper.getAttrDrawable(mContext, R.attr.mui_s_checkbox);
        }

        /**
         * CheckBox 是否处于勾选状态
         */
        public boolean isChecked() {
            return mIsChecked;
        }

        /**
         * 设置 CheckBox 的勾选状态
         */
        public CheckBoxMessageDialogBuilder setChecked(boolean checked) {
            if (mIsChecked != checked) {
                mIsChecked = checked;
                if (mTextView != null) {
                    mTextView.setSelected(checked);
                }
            }
            return this;
        }

        @Override
        protected void onCreateContent(MUIDialog dialog, ViewGroup parent, Context context) {
            super.onCreateContent(dialog, parent, context);
            mCheckMarkDrawable.setBounds(0, 0, mCheckMarkDrawable.getIntrinsicWidth(), mCheckMarkDrawable.getIntrinsicHeight());
            mTextView.setCompoundDrawables(mCheckMarkDrawable, null, null, null);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChecked(!mIsChecked);
                }
            });
            mTextView.setSelected(mIsChecked);
        }
    }

}
