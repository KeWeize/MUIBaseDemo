package vit.com.mui.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;

import vit.com.mui.MUILog;

/**
 * @author kewz
 * @date 2019/5/4
 */
public class MUIDrawableHelper {

    private static final String TAG
            = MUIDrawableHelper.class.getSimpleName();

    /**
     * 创建一个层级 Drawable.带上分割线或下分割线
     *
     * @param separatorColor  分割线颜色
     * @param bgColor         Drawable 背景颜色
     * @param separatorHeight 分割线高度
     * @return
     */
    public static LayerDrawable createItemSeparatorBg(@ColorInt int separatorColor,
                                                      @ColorInt int bgColor, int separatorHeight,
                                                      boolean top) {
        ShapeDrawable separator = new ShapeDrawable();
        separator.getPaint().setStyle(Paint.Style.FILL);
        separator.getPaint().setColor(separatorColor);

        ShapeDrawable bg = new ShapeDrawable();
        bg.getPaint().setStyle(Paint.Style.FILL);
        bg.getPaint().setColor(bgColor);

        Drawable[] layers = {separator, bg};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        layerDrawable.setLayerInset(1, 0, top ? separatorHeight : 0,
                0, top ? 0 : separatorHeight);

        return layerDrawable;
    }


    /////////////// VectorDrawable /////////////////////

    public static
    @Nullable
    Drawable getVectorDrawable(Context context, @DrawableRes int resVector) {
        try {
            return AppCompatResources.getDrawable(context, resVector);
        } catch (Exception e) {
            MUILog.d(TAG, "Error in getVectorDrawable. resVector=" + resVector + ", resName=" + context.getResources().getResourceName(resVector) + e.getMessage());
            return null;
        }
    }

}
