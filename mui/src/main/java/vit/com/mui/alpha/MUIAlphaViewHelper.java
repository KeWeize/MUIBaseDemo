package vit.com.mui.alpha;

import android.support.annotation.NonNull;
import android.view.View;
import java.lang.ref.WeakReference;

import vit.com.mui.R;
import vit.com.mui.utils.MUIAttrsHelper;

/**
 * @author kewz
 * @date 2019/4/30
 */
public class MUIAlphaViewHelper {

    private WeakReference<View> mTarget;

    /**
     * 设置是否要在 press 时改变透明度
     */
    private boolean mChangeAlphaWhenPress = true;

    /**
     * 设置是否要在 disabled 时改变透明度
     */
    private boolean mChangeAlphaWhenDisable = true;

    private float mNormalAlpha = 1f;
    private float mPressedAlpha = .5f;
    private float mDisabledAlpha = .5f;

    public MUIAlphaViewHelper(@NonNull View target) {
        mTarget = new WeakReference<>(target);
        mPressedAlpha = MUIAttrsHelper.getAttrFloatValue(target.getContext(), R.attr.mui_alpha_pressed);
        mDisabledAlpha = MUIAttrsHelper.getAttrFloatValue(target.getContext(), R.attr.mui_alpha_disabled);
    }

    public MUIAlphaViewHelper(@NonNull View target, float pressedAlpha, float disabledAlpha) {
        mTarget = new WeakReference<>(target);
        mPressedAlpha = pressedAlpha;
        mDisabledAlpha = disabledAlpha;
    }

    /**
     * 在 {@link View#setPressed(boolean)} 中调用，通知 helper 更新
     *
     * @param current the view to be handled, maybe not equal to target view
     * @param pressed {@link View#setPressed(boolean)} 中接收到的参数
     */
    public void onPressedChanged(View current, boolean pressed) {
        View target = mTarget.get();
        if (target == null) {
            return;
        }
        if (current.isEnabled()) {
            target.setAlpha(mChangeAlphaWhenPress && pressed && current.isClickable() ? mPressedAlpha : mNormalAlpha);
        } else {
            if (mChangeAlphaWhenDisable) {
                target.setAlpha(mDisabledAlpha);
            }
        }
    }

    /**
     * 在 {@link View#setEnabled(boolean)} 中调用，通知 helper 更新
     *
     * @param current the view to be handled, maybe not  equal to target view
     * @param enabled {@link View#setEnabled(boolean)} 中接收到的参数
     */
    public void onEnabledChanged(View current, boolean enabled) {
        View target = mTarget.get();
        if (target == null) {
            return;
        }
        float alphaForIsEnable;
        if (mChangeAlphaWhenDisable) {
            alphaForIsEnable = enabled ? mNormalAlpha : mDisabledAlpha;
        } else {
            alphaForIsEnable = mNormalAlpha;
        }
        if (current != target && target.isEnabled() != enabled) {
            target.setEnabled(enabled);
        }
        target.setAlpha(alphaForIsEnable);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        mChangeAlphaWhenPress = changeAlphaWhenPress;
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        mChangeAlphaWhenDisable = changeAlphaWhenDisable;
        View target = mTarget.get();
        if (target != null) {
            onEnabledChanged(target, target.isEnabled());
        }
    }

}
