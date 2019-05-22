package vit.com.mui.widget.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vit.com.mui.R;
import vit.com.mui.alpha.MUIAlphaImageButton;
import vit.com.mui.alpha.MUIAlphaTextView;
import vit.com.mui.utils.MUIAttrsHelper;
import vit.com.mui.utils.MUIDisplayHelper;
import vit.com.mui.utils.MUIDrawableHelper;
import vit.com.mui.utils.MUIViewHelper;

/**
 * @author kewz
 * @date 2019/5/4
 */
public class MUITopBar extends RelativeLayout {

    private static final String TAG =
            MUITopBar.class.getSimpleName();

    private int mTopBarSeparatorColor;
    private int mTopBarBgColor;
    private int mTopBarSeparatorHeight;
    private boolean mTopBarNeedSeparator;
    private Drawable mTopBarBgWithSeparatorDrawableCache;

    /**
     * title & subtitle
     */
    private LinearLayout mTitleContainerLl;
    private TextView mTitleTv;
    private TextView mSubTitleTv;
    private int mTitleGravity;
    private int mTitleContainerPaddingHor;
    /**
     * title & subtitle property value
     */
    private int mTitleTextSize;
    private int mTitleWithSubTitleTextSize;
    private int mTitleTextColor;
    private int mSubTitleTextSize;
    private int mSubTitleTextColor;

    /**
     * left right side
     */
    private LinearLayout mLeftContainerLl;
    private LinearLayout mRightContainerLl;
    private ImageButton mLeftBackIb;
    /**
     * left right side property value
     */
    private int mLeftBackDrawableId;
    private int mSideContainerPaddingHor;
    private int mSideImageButtonWidth;
    private int mSideImageButtonHeight;
    private int mSideImageButtonPaddingHor;
    private int mSideImageButtonMarginHor;
    private int mSideTextViewTextSize;
    private int mSideTextViewTextColor;
    private int mSideTextViewPaddingHor;

    /**
     * custom center view
     */
    private View mCenterView;


    public MUITopBar(Context context) {
        super(context);
        initAttrs(context, null, R.attr.MUITopBarStyle);
    }

    public MUITopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, R.attr.MUITopBarStyle);
    }

    public MUITopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, R.attr.MUITopBarStyle);
    }

    /**
     * 初始化参数
     *
     * @param context
     * @param attrs
     * @param defStyleAttrs
     */
    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MUITopBar,
                defStyleAttrs, R.style.MUITopBarDefStyle);
        mLeftBackDrawableId = ta.getResourceId(R.styleable.MUITopBar_mui_topbar_left_back_drawable_id,
                R.drawable.mui_default_left_back_drawable);
        // 背景
        mTopBarBgColor = ta.getColor(R.styleable.MUITopBar_mui_topbar_bg_color,
                ContextCompat.getColor(context, R.color.mui_config_color_white));
        mTopBarSeparatorColor = ta.getColor(R.styleable.MUITopBar_mui_topbar_separator_color,
                ContextCompat.getColor(context, R.color.mui_config_color_separator));
        mTopBarSeparatorHeight = ta.getDimensionPixelOffset(R.styleable.MUITopBar_mui_topbar_separator_height,
                MUIDisplayHelper.dp2px(context, 1));
        mTopBarNeedSeparator = ta.getBoolean(R.styleable.MUITopBar_mui_topbar_need_separator,
                true);
        // 标题
        mTitleContainerPaddingHor = ta.getDimensionPixelSize(R.styleable
                .MUITopBar_mui_topbar_title_container_padding_hor, MUIDisplayHelper.dp2px(context, 5));
        mTitleTextSize = ta.getDimensionPixelSize(R.styleable
                .MUITopBar_mui_topbar_title_text_size, MUIDisplayHelper.sp2px(context, 17));
        mTitleWithSubTitleTextSize = ta.getDimensionPixelSize(R.styleable
                .MUITopBar_mui_topbar_title_with_subtitle_text_size, MUIDisplayHelper.sp2px(context, 15));
        mTitleTextColor = ta.getColor(R.styleable.MUITopBar_mui_topbar_title_text_color,
                ContextCompat.getColor(context, R.color.mui_config_color_gray_1));
        mSubTitleTextSize = ta.getDimensionPixelSize(R.styleable.MUITopBar_mui_topbar_subtitle_text_size,
                MUIDisplayHelper.sp2px(context, 12));
        mSubTitleTextColor = ta.getColor(R.styleable.MUITopBar_mui_topbar_subtitle_text_color,
                ContextCompat.getColor(context, R.color.mui_config_color_gray_3));
        mTitleGravity = ta.getInt(R.styleable.MUITopBar_mui_topbar_title_gravity, Gravity.CENTER);
        // 两侧视图
        mSideImageButtonWidth =
                ta.getDimensionPixelSize(R.styleable.MUITopBar_mui_topbar_side_imagebutton_width,
                        MUIDisplayHelper.dp2px(context, 35));
        mSideImageButtonHeight =
                ta.getDimensionPixelSize(R.styleable.MUITopBar_mui_topbar_side_imagebutton_height,
                        MUIDisplayHelper.dp2px(context, 35));
        mSideImageButtonPaddingHor =
                ta.getDimensionPixelSize(R.styleable.MUITopBar_mui_topbar_side_imagebutton_padding_hor,
                        MUIDisplayHelper.dp2px(context, 5));
        mSideImageButtonMarginHor =
                ta.getDimensionPixelSize(R.styleable.MUITopBar_mui_topbar_side_imagebutton_margin_hor,
                        MUIDisplayHelper.dp2px(context, 3));
        mSideContainerPaddingHor =
                ta.getDimensionPixelSize(R.styleable.MUITopBar_mui_topbar_side_container_padding_hor,
                        MUIDisplayHelper.dp2px(context, 5));
        mSideTextViewTextSize =
                ta.getDimensionPixelSize(R.styleable.MUITopBar_mui_topbar_side_textview_text_size,
                        MUIDisplayHelper.sp2px(getContext(), 16));
        mSideTextViewTextColor =
                ta.getColor(R.styleable.MUITopBar_mui_topbar_side_textview_text_color,
                        ContextCompat.getColor(context, R.color.mui_config_color_gray_3));
        mSideTextViewPaddingHor =
                ta.getDimensionPixelSize(R.styleable.MUITopBar_mui_topbar_side_textview_padding_hor,
                        MUIDisplayHelper.dp2px(getContext(), 3));

        ta.recycle();

        //todo  DEBUG
        Log.d(TAG, "initAttrs: mSideImageButtonWidth" + mSideImageButtonWidth);
        Log.d(TAG, "initAttrs: mSideImageButtonHeight" + mSideImageButtonHeight);

        setBackgroundDividerEnable(mTopBarNeedSeparator);
    }

    /**
     * 设置背景
     *
     * @param enable 是否带分割线
     */
    private void setBackgroundDividerEnable(boolean enable) {
        if (enable) {
            if (mTopBarBgWithSeparatorDrawableCache == null) {
                mTopBarBgWithSeparatorDrawableCache = MUIDrawableHelper
                        .createItemSeparatorBg(mTopBarSeparatorColor, mTopBarBgColor,
                                mTopBarSeparatorHeight, false);
            }
            MUIViewHelper.setBackgroundKeepingPadding(this,
                    mTopBarBgWithSeparatorDrawableCache);
        } else {
            MUIViewHelper.setBackgroundColorKeepPadding(this, mTopBarBgColor);
        }
    }

    /************************** title & subtitle *****************************/

    /**
     * 添加标题
     *
     * @param resId
     */
    public TextView setTitle(@StringRes int resId) {
        return setTitle(getContext().getString(resId));
    }

    /**
     * 添加标题
     *
     * @param charSequence
     */
    public TextView setTitle(CharSequence charSequence) {
        TextView mTextView = getTitleTv();
        mTextView.setText(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            mTextView.setVisibility(GONE);
        } else {
            mTextView.setVisibility(VISIBLE);
        }
        return mTextView;
    }

    /**
     * 添加副标题
     *
     * @param resId
     * @return
     */
    public TextView setSubTitle(@StringRes int resId) {
        return setSubTitle(getContext().getString(resId));
    }

    /**
     * 添加副标题
     *
     * @param charSequence
     * @return
     */
    public TextView setSubTitle(CharSequence charSequence) {
        TextView mTextView = getSubTitleTv();
        mTextView.setText(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            mTextView.setVisibility(GONE);
        } else {
            mTextView.setVisibility(VISIBLE);
        }
        updateTitleTvStyle();
        return mTextView;
    }


    /**
     * 获取大标题视图
     *
     * @return
     */
    private TextView getTitleTv() {
        if (mTitleTv == null) {
            mTitleTv = new TextView(getContext());
            mTitleTv.setGravity(Gravity.CENTER);
            mTitleTv.setSingleLine(true);
            mTitleTv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            mTitleTv.setTextColor(mTitleTextColor);
            // 更新字体尺寸样式
            updateTitleTvStyle();
            LinearLayout.LayoutParams llp
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.gravity = mTitleGravity;
            makeSureTitleContainerLl().addView(mTitleTv, llp);
        }
        return mTitleTv;
    }

    /**
     * 获取副标题视图
     *
     * @return
     */
    private TextView getSubTitleTv() {
        if (mSubTitleTv == null) {
            mSubTitleTv = new TextView(getContext());
            mSubTitleTv.setGravity(Gravity.CENTER);
            mSubTitleTv.setSingleLine(true);
            mSubTitleTv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            mSubTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSubTitleTextSize);
            mSubTitleTv.setTextColor(mSubTitleTextColor);
            LinearLayout.LayoutParams llp =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.topMargin = MUIDisplayHelper.dp2px(getContext(), 1);
            llp.gravity = mTitleGravity;
            makeSureTitleContainerLl().addView(mSubTitleTv, llp);
        }
        return mSubTitleTv;
    }

    /**
     * 获取标题布局
     *
     * @return
     */
    private LinearLayout makeSureTitleContainerLl() {
        if (mTitleContainerLl == null) {
            mTitleContainerLl = new LinearLayout(getContext());
            mTitleContainerLl.setOrientation(LinearLayout.VERTICAL);
            mTitleContainerLl.setGravity(Gravity.CENTER);
            mTitleContainerLl.setPadding(mTitleContainerPaddingHor, 0,
                    mTitleContainerPaddingHor, 0);
            int height = MUIAttrsHelper.getAttrDimen(getContext(), R.attr.mui_topbar_height);
            if (height == 0) {
                height = LayoutParams.WRAP_CONTENT;
            }
            addView(mTitleContainerLl, new LayoutParams(LayoutParams.MATCH_PARENT, height));
        }
        return mTitleContainerLl;
    }

    /**
     * 更新 titleTv Size（副标题影响这标题样式）
     */
    private void updateTitleTvStyle() {
        if (mTitleTv != null) {
            if (mSubTitleTv == null || TextUtils.isEmpty(mSubTitleTv.getText().toString())) {
                // 副标题视图为空或者标题内容为空
                mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
            } else {
                mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleWithSubTitleTextSize);
            }
        }
    }

    /*************************** side view **************************/

    /**
     * 添加左侧 TextView
     *
     * @param resId
     * @return
     */
    public TextView addLeftTextView(@StringRes int resId) {
        return addLeftTextView(getContext().getString(resId));
    }

    /**
     * 添加左侧 TextView
     *
     * @param charSequence
     * @return
     */
    public TextView addLeftTextView(CharSequence charSequence) {
        TextView mTextView = generateSideTextView(charSequence);
        addLeftView(mTextView);
        return mTextView;
    }

    /**
     * 添加左侧返回按钮
     *
     * @return
     */
    public ImageButton addLeftBackImageButton() {
        if (mLeftBackIb == null) {
            mLeftBackIb = generateSideImageButton(mLeftBackDrawableId);
        }
        addLeftView(mLeftBackIb);
        return mLeftBackIb;
    }

    /**
     * 添加左侧 ImageButton
     *
     * @param resId
     */
    public ImageButton addLeftImageButton(@DrawableRes int resId) {
        ImageButton mImageButton = generateSideImageButton(resId);
        addLeftView(mImageButton);
        return mImageButton;
    }

    /**
     * 添加左边视图
     *
     * @param view
     */
    public void addLeftView(View view) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        LinearLayout.LayoutParams llp;
        if (vlp != null && vlp instanceof LinearLayout.LayoutParams) {
            llp = (LinearLayout.LayoutParams) vlp;
        } else {
            llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
        }
        if (view == mLeftBackIb) {
            if (view.getParent() == null) {
                makeSureLeftContainerLl().addView(view, 0);
            }
        } else {
            makeSureLeftContainerLl().addView(view, llp);
        }
    }

    /**
     * 添加左侧 TextView
     *
     * @param charSequence
     * @return
     */
    public TextView addRightTextView(CharSequence charSequence) {
        TextView mTextView = generateSideTextView(charSequence);
        addRightView(mTextView);
        return mTextView;
    }

    /**
     * 添加右侧 ImageButton
     *
     * @param resId
     */
    public ImageButton addRightImageButton(@DrawableRes int resId) {
        ImageButton mImageButton = generateSideImageButton(resId);
        addRightView(mImageButton);
        return mImageButton;
    }

    /**
     * 添加右边视图
     *
     * @param view
     */
    public void addRightView(View view) {
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        LinearLayout.LayoutParams llp;
        if (vlp != null && vlp instanceof LinearLayout.LayoutParams) {
            llp = (LinearLayout.LayoutParams) vlp;
        } else {
            llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
        }
        makeSureRightContainerLl().addView(view, 0, llp);
    }

    /**
     * 获取 left 容器布局
     *
     * @return
     */
    private LinearLayout makeSureLeftContainerLl() {
        if (mLeftContainerLl == null) {
            mLeftContainerLl = new LinearLayout(getContext());
            mLeftContainerLl.setGravity(Gravity.CENTER_VERTICAL);
            mLeftContainerLl.setOrientation(LinearLayout.HORIZONTAL);
            mLeftContainerLl.setPadding(mSideContainerPaddingHor, 0, 0, 0);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            rlp.addRule(ALIGN_PARENT_LEFT);
            rlp.addRule(CENTER_VERTICAL);
            addView(mLeftContainerLl, rlp);
        }
        return mLeftContainerLl;
    }

    /**
     * 获取 right 容器布局
     *
     * @return
     */
    private LinearLayout makeSureRightContainerLl() {
        if (mRightContainerLl == null) {
            mRightContainerLl = new LinearLayout(getContext());
            mRightContainerLl.setGravity(CENTER_VERTICAL);
            mRightContainerLl.setOrientation(LinearLayout.HORIZONTAL);
            mRightContainerLl.setPadding(0, 0, mSideContainerPaddingHor, 0);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            rlp.addRule(ALIGN_PARENT_RIGHT);
            rlp.addRule(CENTER_VERTICAL);
            addView(mRightContainerLl, rlp);
        }
        return mRightContainerLl;
    }

    /**
     * 生成两侧 ImageButton
     *
     * @param resId
     * @return
     */
    private ImageButton generateSideImageButton(@DrawableRes int resId) {
        MUIAlphaImageButton mImageButton = new MUIAlphaImageButton(getContext());
        mImageButton.setBackgroundColor(Color.TRANSPARENT);
        mImageButton.setImageResource(resId);
        mImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(mSideImageButtonWidth,
                mSideImageButtonHeight);
        llp.leftMargin = mSideImageButtonMarginHor;
        llp.rightMargin = mSideImageButtonMarginHor;
        mImageButton.setLayoutParams(llp);
        mImageButton.setPadding(mSideImageButtonPaddingHor, 0, mSideImageButtonPaddingHor, 0);
        return mImageButton;
    }

    /**
     * 生成两侧 TextView
     *
     * @param charSequence
     * @return
     */
    private TextView generateSideTextView(CharSequence charSequence) {
        MUIAlphaTextView mTextView = new MUIAlphaTextView(getContext());
        mTextView.setText(charSequence);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSideTextViewTextSize);
        mTextView.setTextColor(mSideTextViewTextColor);
        mTextView.setPadding(mSideTextViewPaddingHor, 0, mSideTextViewPaddingHor, 0);
        mTextView.setSingleLine(true);
        mTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mTextView.setLayoutParams(llp);
        return mTextView;
    }

    /***************** custom center view ********************/
    public void setCenterView(View view) {
        if (mCenterView == view) {
            return;
        }
        if (mCenterView != null) {
            removeView(mCenterView);
        }
        mCenterView = view;
        LayoutParams params;
        ViewGroup.LayoutParams vlp = mCenterView.getLayoutParams();
        if (vlp == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        } else if (!(vlp instanceof LayoutParams)) {
            params = new LayoutParams(vlp.width, vlp.height);
        } else {
            params = (LayoutParams) vlp;
        }
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mCenterView, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTitleContainerLl != null) {
            boolean isleftEmpty = mLeftContainerLl == null
                    || mLeftContainerLl.getChildCount() == 0;
            boolean isRightEmpty = mRightContainerLl == null
                    || mRightContainerLl.getChildCount() == 0;
            // 因为两侧容器有 paddingHor 存在，所以就算不存在子View也会有宽度。
            int leftViewWidth = isleftEmpty ? 0 : mLeftContainerLl.getMeasuredWidth();
            int rightViewWidth = isRightEmpty ? 0 : mRightContainerLl.getMeasuredWidth();
            // 计算 titleContainer 的最大宽度
            int titleContainerWidth;
            // 标题非水平居中，左右两侧的占位按实际计算即可
            titleContainerWidth = MeasureSpec.getSize(widthMeasureSpec)
                    - leftViewWidth - rightViewWidth - getPaddingLeft() - getPaddingRight();

            int titleContainerWidthMeasureSpec
                    = MeasureSpec.makeMeasureSpec(titleContainerWidth, MeasureSpec.EXACTLY);
            mTitleContainerLl.measure(titleContainerWidthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mTitleContainerLl != null) {
            int titleContainerViewWidth = mTitleContainerLl.getMeasuredWidth();
            int titleContainerViewHeight = mTitleContainerLl.getMeasuredHeight();
            int titleContainerViewTop = (b - t - mTitleContainerLl.getMeasuredHeight()) / 2;
            int titleContainerViewLeft = getPaddingLeft();

            if ((mTitleGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL) {
                // 标题水平居中
                titleContainerViewLeft = (r - l - mTitleContainerLl.getMeasuredWidth()) / 2;
            } else {
                // 标题非水平居中
                // 计算左侧 View 的总宽度
                boolean isLeftEmpty = mLeftContainerLl == null
                        || mLeftContainerLl.getChildCount() == 0;
                int left = isLeftEmpty ? 0 : mLeftContainerLl.getMeasuredWidth();
                titleContainerViewLeft += left;
            }
            mTitleContainerLl.layout(titleContainerViewLeft, titleContainerViewTop,
                    titleContainerViewLeft + titleContainerViewWidth,
                    titleContainerViewTop + titleContainerViewHeight);
        }
    }

}
