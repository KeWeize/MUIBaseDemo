package vit.com.mui.widget.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import vit.com.mui.R;
import vit.com.mui.alpha.MUIAlphaButton;
import vit.com.mui.layout.MUIButton;
import vit.com.mui.layout.MUILinearLayout;
import vit.com.mui.utils.MUIAttrsHelper;
import vit.com.mui.utils.MUIDisplayHelper;

/**
 * @author kewz
 * @date 2019/5/19
 */
public abstract class MUIDialogBuilder<T extends MUIDialogBuilder> {

    private String TAG = "MUIDialogBuilder";

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionOrientation {
    }

    /**
     * A global theme provider, use to distinguish theme from different builder type
     */
    private static OnProvideDefaultTheme sOnProvideDefaultTheme = null;

    public static void setOnProvideDefaultTheme(OnProvideDefaultTheme onProvideDefaultTheme) {
        MUIDialogBuilder.sOnProvideDefaultTheme = onProvideDefaultTheme;
    }

    private Context mContext;
    protected MUIDialog mDialog;
    protected CharSequence mTitle;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;

    protected View mRootView;
    protected MUIDialogView mDialogView;
    protected View mAnchorTopView;
    protected View mAnchorBottomView;
    protected TextView mTitleView;
    protected MUILinearLayout mActionContainer;

    protected List<MUIDialogAction> mActions = new ArrayList<>();
    private MUIDialogView.OnDecorationListener mOnDecorationListener;
    private int mContentAreaMaxHeight = -1;

    private boolean mChangeAlphaForPressOrDisable = true;
    private int mActionDividerThickness = 0;
    private int mActionDividerColorRes = R.color.mui_config_color_separator;
    private int mActionDividerInsetStart = 0;
    private int mActionDividerInsetEnd = 0;

    @ActionOrientation
    private int mActionContainerOrientation = HORIZONTAL;

    public MUIDialogBuilder(Context mContext) {
        this.mContext = mContext;
    }

    protected int getContentAreaMaxHeight() {
        if (mContentAreaMaxHeight == -1) {
            // 未设置内容区域高度，默认为屏幕高度的0.85 - 预估的 title 和 action 高度
            return (int) ((MUIDisplayHelper.getScreenHeight(mContext) * 0.85)
                    - MUIDisplayHelper.dp2px(mContext, 100));
        }
        return mContentAreaMaxHeight;
    }

    public Context getBaseContext() {
        return mContext;
    }

    /**
     * 设置内容区域最大高度
     *
     * @param contentAreaMaxHeight
     * @return
     */
    public T setContentAreaMaxHeight(int contentAreaMaxHeight) {
        mContentAreaMaxHeight = contentAreaMaxHeight;
        return (T) this;
    }

    /**
     * 设置 Dialog title 内容
     *
     * @param title
     * @return
     */
    public T setTitle(CharSequence title) {
        if (title != null && title.length() > 0) {
            this.mTitle = title
                    + mContext.getString(R.string.mui_tool_fixellipsize);
        }
        return (T) this;
    }

    /**
     * 设置 Dialog title 内容
     *
     * @param stringResId
     * @return
     */
    public T setTitle(@StringRes int stringResId) {
        return setTitle(mContext.getString(stringResId));
    }

    /**
     * 设置是否可以取消
     *
     * @param cancelable
     * @return
     */
    public T setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return (T) this;
    }

    /**
     * 设置是否可以点击外部区域取消
     *
     * @param canceledOnTouchOutside
     * @return
     */
    public T setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
        return (T) this;
    }

    /**
     * 设置 DialogView 绘制回调
     *
     * @param listener
     * @return
     */
    public T setOnDecorationListener(MUIDialogView.OnDecorationListener listener) {
        mOnDecorationListener = listener;
        return (T) this;
    }

    /**
     * 设置底部按钮 gravity
     *
     * @param actionContainerOrientation
     * @return
     */
    public T setActionContainerOrientation(@ActionOrientation int actionContainerOrientation) {
        mActionContainerOrientation = actionContainerOrientation;
        return (T) this;
    }

    /**
     * 添加操作视图的分割线
     *
     * @param thickness
     * @param colorRes
     * @param startInset
     * @param endInset
     * @return
     */
    public T setActionDivider(int thickness, int colorRes, int startInset, int endInset) {
        mActionDividerThickness = thickness;
        mActionDividerColorRes = colorRes;
        mActionDividerInsetStart = startInset;
        mActionDividerInsetEnd = endInset;
        return (T) this;
    }

    /**
     * 添加操作按钮
     *
     * @param iconRes  图标
     * @param str      文案
     * @param prop     属性
     * @param listener 点击回调事件
     */
    @SuppressWarnings("unchecked")
    public T addAction(int iconRes, CharSequence str, @MUIDialogAction.ActionProp int prop, MUIDialogAction.OnActionListener listener) {
        MUIDialogAction action = new MUIDialogAction(mContext, str, prop, listener);
        mActions.add(action);
        return (T) this;
    }

    /**
     * 判断对话框是否需要显示title
     *
     * @return 是否有title
     */
    protected boolean hasTitle() {
        return mTitle != null && mTitle.length() != 0;
    }

    /**
     * 产生一个 dialog 并显示出来
     *
     * @return
     */
    public MUIDialog show() {
        final MUIDialog dialog = create();
        dialog.show();
        return dialog;
    }

    public MUIDialog create() {
        if (sOnProvideDefaultTheme != null) {
            int theme = sOnProvideDefaultTheme.getThemeForBuilder(this);
            if (theme > 0) {
                return create(theme);
            }
        }
        return create(R.style.MUI_Dialog);
    }

    public MUIDialog create(@StyleRes int style) {
        mDialog = new MUIDialog(mContext, style);
        Context dialogContext = mDialog.getContext();

        mRootView = LayoutInflater.from(dialogContext)
                .inflate(R.layout.mui_dialog_layout, null);
        mDialogView = mRootView.findViewById(R.id.dialog);
        mAnchorTopView = mRootView.findViewById(R.id.v_anchor_top);
        mAnchorBottomView = mRootView.findViewById(R.id.v_anchor_bottom);

        // 创建title
        onCreateTitle(mDialog, mDialogView, dialogContext);

        // 创建中间内容区域
        onCreateContent(mDialog, mDialogView, dialogContext);

        // 底部操作按钮栏
        onCreateHandleBar(mDialog, mDialogView, dialogContext);

        mDialog.addContentView(mRootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        onAfter(mDialog, mRootView, dialogContext);
        return mDialog;
    }


    /**
     * 创建顶部 title
     *
     * @param dialog
     * @param parent
     * @param context
     */
    protected void onCreateTitle(MUIDialog dialog, ViewGroup parent, Context context) {
        if (hasTitle()) {
            mTitleView = new TextView(context);
            mTitleView.setText(mTitle);
            // 设置 textView 属性
            MUIAttrsHelper.assignTextViewWithAttr(mTitleView, R.attr.mui_dialog_title_style);
            onConfigTitleView(mTitleView);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mTitleView.setLayoutParams(llp);
            parent.addView(mTitleView);
        }
    }

    /**
     * 提供给子类配置 titleView
     *
     * @param mTitleView
     */
    protected void onConfigTitleView(TextView mTitleView) {
    }

    /**
     * 获取titleView
     *
     * @return
     */
    public TextView getTitleView() {
        return mTitleView;
    }

    /**
     * 创建中间内容区域。由子类实现
     */
    protected abstract void onCreateContent(MUIDialog dialog, ViewGroup parent, Context context);

    /**
     * 创建底部的操作栏区域
     */
    protected void onCreateHandleBar(final MUIDialog dialog, ViewGroup parent, Context context) {
        int size = mActions.size();
        if (size <= 0) {
            return;
        }
        int mJustifyContent = 1;
        // weigit = 1 的 视图位置
        int mSpaceCustomIndex = 0;
        int mActionHeight = -1;
        int mActionSpace = 0;
        TypedArray ta = context.obtainStyledAttributes(null, R.styleable.MUIDialogActionContainer,
                R.attr.mui_dialog_action_container_style, R.style.MUI_Dialog_ActionContainerDefStyle);
        mJustifyContent = ta.getInteger(R.styleable
                .MUIDialogActionContainer_mui_dialog_action_container_justify_content, -1);
        mSpaceCustomIndex = ta.getInteger(R.styleable
                .MUIDialogActionContainer_mui_dialog_action_container_custom_space_index, 0);
        mActionSpace = ta.getDimensionPixelSize(R.styleable.MUIDialogActionContainer_mui_dialog_action_space, 0);
        mActionHeight = ta.getDimensionPixelSize(R.styleable.MUIDialogActionContainer_mui_dialog_action_height, 0);
        ta.recycle();

        int mSpaceInsertPos = -1;
        if (mActionContainerOrientation == VERTICAL) {
            // Action 竖直方向，Space 不能插入
            mSpaceInsertPos = -1;
        } else if (mJustifyContent == 0) {
            mSpaceInsertPos = size;
        } else if (mJustifyContent == 1) {
            mSpaceInsertPos = 0;
        } else if (mJustifyContent == 3) {
            mSpaceInsertPos = mSpaceCustomIndex;
        }

        mActionContainer = new MUILinearLayout(context, null, R.attr.mui_dialog_action_container_style);
        mActionContainer.setOrientation(mActionContainerOrientation == VERTICAL ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
        mActionContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < size; i++) {
            if (mSpaceInsertPos == i) {
                mActionContainer.addView(generateActionContainerSpace(context));
            }
            MUIDialogAction mAction = mActions.get(i);

            LinearLayout.LayoutParams mActionLlp;
            if (mActionContainerOrientation == VERTICAL) {
                mActionLlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mActionHeight);
            } else {
                mActionLlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mActionHeight);
                if (mSpaceInsertPos >= 0) {
                    if (i >= mSpaceInsertPos) {
                        mActionLlp.leftMargin = mActionSpace;
                    } else {
                        mActionLlp.rightMargin = mActionSpace;
                    }
                }
                if (mJustifyContent == 2) {
                    // 平铺，所有action 权重都为1
                    mActionLlp.weight = 1;
                }
            }
            MUIButton mActionView = mAction.buildActionView(dialog, i);
            // add divider
            if (mActionDividerThickness > 0 && i > 0 && mSpaceInsertPos != i) {
                if (mActionContainerOrientation == VERTICAL) {
                    mActionView.onlyShowTopDivider(mActionDividerInsetStart, mActionDividerInsetEnd,
                            mActionDividerThickness, ContextCompat.getColor(context, mActionDividerColorRes));
                } else {
                    mActionView.onlyShowLeftDivider(mActionDividerInsetStart, mActionDividerInsetEnd,
                            mActionDividerThickness, ContextCompat.getColor(context, mActionDividerColorRes));
                }
            }

            mActionView.setChangeAlphaWhenDisable(mChangeAlphaForPressOrDisable);
            mActionView.setChangeAlphaWhenPress(mChangeAlphaForPressOrDisable);
            mActionContainer.addView(mActionView, mActionLlp);
        }

        if (mSpaceInsertPos == size) {
            mActionContainer.addView(generateActionContainerSpace(context));
        }
        if (mActionContainerOrientation == HORIZONTAL) {
            mActionContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    int width = right - left;
                    int childCount = mActionContainer.getChildCount();
                    if (childCount > 0) {
                        View lastChild = mActionContainer.getChildAt(childCount - 1);
                        // 如果ActionButton的宽度过宽，则减小padding
                        if (lastChild.getRight() > width) {
                            int childPaddingHor = Math.max(0, lastChild.getPaddingLeft() - MUIDisplayHelper.dp2px(mContext, 3));
                            for (int i = 0; i < childCount; i++) {
                                mActionContainer.getChildAt(i).setPadding(childPaddingHor, 0, childPaddingHor, 0);
                            }
                        }
                    }

                }
            });
        }
        parent.addView(mActionContainer);
    }

    /**
     * 生成权重为 1 的View。 只在 HOR
     *
     * @param context
     * @return
     */
    private View generateActionContainerSpace(Context context) {
        Space space = new Space(context);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, 0);
        llp.weight = 1;
        space.setLayoutParams(llp);
        return space;
    }

    protected void onAfter(MUIDialog dialog, View parent, Context context) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancelOutSide();
            }
        };
        mAnchorBottomView.setOnClickListener(listener);
        mAnchorTopView.setOnClickListener(listener);
        mRootView.setOnClickListener(listener);
    }

    /**
     * 回调接口，用于提供一个默认主题
     */
    public interface OnProvideDefaultTheme {
        int getThemeForBuilder(MUIDialogBuilder builder);
    }

}
