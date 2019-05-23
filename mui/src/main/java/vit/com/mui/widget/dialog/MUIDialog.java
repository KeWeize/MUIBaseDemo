package vit.com.mui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.text.method.TransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import vit.com.mui.R;
import vit.com.mui.alpha.MUIAlphaTextView;
import vit.com.mui.layout.MUILayoutHelper;
import vit.com.mui.utils.MUIAttrsHelper;
import vit.com.mui.utils.MUIDisplayHelper;
import vit.com.mui.utils.MUILangHelper;
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


    /***##################### EditTextDialogBuilder ######################*/
    public static class EditTextDialogBuilder extends MUIDialogBuilder<EditTextDialogBuilder> {

        protected CharSequence mPlaceholder;
        protected TransformationMethod mTransformationMethod;
        protected RelativeLayout mMainLayout;
        protected EditText mEditText;
        protected ImageView mRightImageView;
        private int mInputType = InputType.TYPE_CLASS_TEXT;
        private CharSequence mDefaultText = null;

        public EditTextDialogBuilder(Context mContext) {
            super(mContext);
        }

        /**
         * 设置输入框的 placeholder
         */
        public EditTextDialogBuilder setPlaceholder(String placeholder) {
            this.mPlaceholder = placeholder;
            return this;
        }

        /**
         * 设置输入框的 placeholder
         */
        public EditTextDialogBuilder setPlaceholder(int resId) {
            return setPlaceholder(getBaseContext().getResources().getString(resId));
        }

        public EditTextDialogBuilder setDefaultText(CharSequence defaultText) {
            mDefaultText = defaultText;
            return this;
        }

        /**
         * 设置 EditText 的 transformationMethod
         */
        public EditTextDialogBuilder setTransformationMethod(TransformationMethod transformationMethod) {
            mTransformationMethod = transformationMethod;
            return this;
        }

        /**
         * 设置 EditText 的 inputType
         */
        public EditTextDialogBuilder setInputType(int inputType) {
            mInputType = inputType;
            return this;
        }

        @Override
        protected void onCreateContent(MUIDialog dialog, ViewGroup parent, Context context) {
            mEditText = new EditText(context);
            MessageDialogBuilder.assignMessageTvWithAttr(mEditText, hasTitle(),
                    R.attr.mui_dialog_edit_content_style);
            mEditText.setFocusable(true);
            mEditText.setFocusableInTouchMode(true);
            mEditText.setImeOptions(EditorInfo.IME_ACTION_GO);

            if (!MUILangHelper.isNullOrEmpty(mDefaultText)) {
                mEditText.setText(mDefaultText);
            }

            mRightImageView = new ImageView(context);
            mRightImageView.setVisibility(View.GONE);

            mMainLayout = new RelativeLayout(context);
            LinearLayout.LayoutParams llp
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.leftMargin = mEditText.getPaddingLeft();
            llp.topMargin = mEditText.getPaddingTop();
            llp.rightMargin = mEditText.getPaddingRight();
            llp.bottomMargin = mEditText.getPaddingBottom();

            mMainLayout.setBackgroundResource(R.drawable.mui_edittext_bg_border_bottom);
            mMainLayout.setLayoutParams(llp);

            if (mTransformationMethod != null) {
                mEditText.setTransformationMethod(mTransformationMethod);
            } else {
                mEditText.setInputType(mInputType);
            }

            mEditText.setBackgroundResource(0);
            mEditText.setPadding(0, 0, 0, MUIDisplayHelper.dpToPx(5));
            RelativeLayout.LayoutParams editLp =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            editLp.addRule(RelativeLayout.LEFT_OF, mRightImageView.getId());
            editLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            if (mPlaceholder != null) {
                mEditText.setHint(mPlaceholder);
            }
            mMainLayout.addView(mEditText, createEditTextLayoutParams());
            mMainLayout.addView(mRightImageView, createRightIconLayoutParams());

            parent.addView(mMainLayout);
        }

        protected RelativeLayout.LayoutParams createEditTextLayoutParams() {
            RelativeLayout.LayoutParams editLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            editLp.addRule(RelativeLayout.LEFT_OF, mRightImageView.getId());
            editLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            return editLp;
        }

        protected RelativeLayout.LayoutParams createRightIconLayoutParams() {
            RelativeLayout.LayoutParams rightIconLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rightIconLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            rightIconLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            rightIconLp.leftMargin = MUIDisplayHelper.dpToPx(5);
            return rightIconLp;
        }

        @Override
        protected void onAfter(MUIDialog dialog, View parent, Context context) {
            super.onAfter(dialog, parent, context);
            final InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            dialog.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    inputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                }
            });
            mEditText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEditText.requestFocus();
                    inputMethodManager.showSoftInput(mEditText, 0);
                }
            }, 300);
        }

        /**
         * 注意该方法只在调用 {@link #create()} 或 {@link #create(int)} 或 {@link #show()} 生成 Dialog 之后
         * 才能返回对应的 EditText，在此之前将返回 null
         */
        public EditText getEditText() {
            return mEditText;
        }

        public ImageView getRightImageView() {
            return mRightImageView;
        }

    }


    /***##################### MenuBaseDialogBuilder ######################*/
    public static class MenuBaseDialogBuilder<T extends MUIDialogBuilder> extends MUIDialogBuilder<T> {

        protected ArrayList<ItemViewFactory> mMenuItemViewsFactoryList;
        protected LinearLayout mMenuItemContainer;
        protected MUIWrapContentScrollView mContentScrollView;
        protected LinearLayout.LayoutParams mMenuItemLp;
        protected ArrayList<MUIDialogMenuItemView> mMenuItemViews = new ArrayList<>();

        public MenuBaseDialogBuilder(Context mContext) {
            super(mContext);
            mMenuItemViewsFactoryList = new ArrayList<>();
        }

        public void clear() {
            mMenuItemViewsFactoryList.clear();
        }

        public T addItem(final MUIDialogMenuItemView itemView, final OnClickListener mOnClickListener) {
            itemView.setMenuIndex(mMenuItemViewsFactoryList.size());
            itemView.setListener(new MUIDialogMenuItemView.MenuItemViewListener() {
                @Override
                public void onClick(int index) {
                    onItemClick(index);
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(mDialog, index);
                    }
                }
            });
            mMenuItemViewsFactoryList.add(new ItemViewFactory() {
                @Override
                public MUIDialogMenuItemView createItemView(Context context) {
                    return itemView;
                }
            });
            return (T) this;
        }

        /**
         * 添加组件
         *
         * @param itemViewFactory
         * @param listener
         * @return
         */
        public T addItem(final ItemViewFactory itemViewFactory, final OnClickListener listener) {
            mMenuItemViewsFactoryList.add(new ItemViewFactory() {
                @Override
                public MUIDialogMenuItemView createItemView(Context context) {
                    MUIDialogMenuItemView itemView = itemViewFactory.createItemView(context);
                    itemView.setMenuIndex(mMenuItemViewsFactoryList.indexOf(this));
                    itemView.setListener(new MUIDialogMenuItemView.MenuItemViewListener() {
                        @Override
                        public void onClick(int index) {
                            onItemClick(index);
                            if (listener != null) {
                                listener.onClick(mDialog, index);
                            }
                        }
                    });
                    return itemView;
                }
            });
            return (T) this;
        }

        protected void onItemClick(int index) {
        }

        @Override
        protected void onCreateContent(MUIDialog dialog, ViewGroup parent, Context context) {

            mMenuItemContainer = new LinearLayout(context);
            mMenuItemContainer.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mMenuItemContainer.setLayoutParams(layoutParams);
            TypedArray ta = context.obtainStyledAttributes(null, R.styleable.MUIDialogMenuContainer,
                    R.attr.mui_dialog_menu_container_style, R.style.MUI_Dialog_MenuContainer);
            int count = ta.getIndexCount();
            int paddingTop = 0, paddingBottom = 0, paddingVerWhenSingle = 0,
                    paddingTopWhenTitle = 0, paddingBottomWhenAction = 0, itemHeight = -1;
            for (int i = 0; i < count; i++) {
                int attr = ta.getIndex(i);
                if (attr == R.styleable.MUIDialogMenuContainer_android_paddingTop) {
                    paddingTop = ta.getDimensionPixelSize(attr, paddingTop);
                } else if (attr == R.styleable.MUIDialogMenuContainer_android_paddingBottom) {
                    paddingBottom = ta.getDimensionPixelSize(attr, paddingBottom);
                } else if (attr == R.styleable.MUIDialogMenuContainer_mui_dialog_menu_container_single_padding_vertical) {
                    paddingVerWhenSingle = ta.getDimensionPixelSize(attr, paddingVerWhenSingle);
                } else if (attr == R.styleable.MUIDialogMenuContainer_mui_dialog_menu_container_padding_top_when_title_exist) {
                    paddingTopWhenTitle = ta.getDimensionPixelSize(attr, paddingTopWhenTitle);
                } else if (attr == R.styleable.MUIDialogMenuContainer_mui_dialog_menu_container_padding_bottom_when_action_exist) {
                    paddingBottomWhenAction = ta.getDimensionPixelSize(attr, paddingBottomWhenAction);
                } else if (attr == R.styleable.MUIDialogMenuContainer_mui_dialog_menu_item_height) {
                    itemHeight = ta.getDimensionPixelSize(attr, itemHeight);
                }
            }
            ta.recycle();

            mMenuItemLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
            mMenuItemLp.gravity = Gravity.CENTER_VERTICAL;

            if (mMenuItemViewsFactoryList.size() == 1) {
                paddingBottom = paddingTop = paddingVerWhenSingle;
            }

            if (hasTitle()) {
                paddingTop = paddingTopWhenTitle;
            }

            if (mActions.size() > 0) {
                paddingBottom = paddingBottomWhenAction;
            }
            mMenuItemContainer.setPadding(0, paddingTop, 0, paddingBottom);

            mMenuItemViews.clear();
            for (ItemViewFactory factory : mMenuItemViewsFactoryList) {
                MUIDialogMenuItemView itemView = factory.createItemView(context);
                mMenuItemContainer.addView(itemView, mMenuItemLp);
                mMenuItemViews.add(itemView);
            }

            mContentScrollView = new MUIWrapContentScrollView(context);
            mContentScrollView.setMaxHeight(getContentAreaMaxHeight());
            mContentScrollView.addView(mMenuItemContainer);
            mContentScrollView.setVerticalScrollBarEnabled(false);
            parent.addView(mContentScrollView);
        }

        /**
         * 用于提供一个 MUIDialogMenuItemView 实例。
         */
        protected interface ItemViewFactory {
            MUIDialogMenuItemView createItemView(Context context);
        }
    }


    /***##################### SimpleMenuDialogBuilder ######################*/
    public static class SimpleMenuDialogBuilder extends MenuBaseDialogBuilder<SimpleMenuDialogBuilder> {

        public SimpleMenuDialogBuilder(Context mContext) {
            super(mContext);
        }

        public SimpleMenuDialogBuilder addItems(CharSequence[] items, OnClickListener listener) {
            for (final CharSequence item : items) {
                addItem(item, listener);
            }
            return this;
        }


        /**
         * 添加单个菜单项
         *
         * @param item     菜单项的文字
         * @param listener 菜单项的点击事件
         */
        public SimpleMenuDialogBuilder addItem(final CharSequence item, OnClickListener listener) {
            addItem(new ItemViewFactory() {
                @Override
                public MUIDialogMenuItemView createItemView(Context context) {
                    return new MUIDialogMenuItemView.SimpleTextItemView(context, item);
                }
            }, listener);
            return this;
        }
    }

}
