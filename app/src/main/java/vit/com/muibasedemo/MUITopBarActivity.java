package vit.com.muibasedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import vit.com.arch.base.VBaseA;
import vit.com.mui.widget.topbar.MUITopBar;

/**
 * @author kewz
 * @date 2019/5/7
 */
public class MUITopBarActivity extends VBaseA {

    @BindView(R.id.topbar)
    MUITopBar mTopBar;
    @BindView(R.id.topbar_01)
    MUITopBar mTopBar01;
    @BindView(R.id.topbar_02)
    MUITopBar mTopBar02;
    @BindView(R.id.topbar_03)
    MUITopBar mTopBar03;

    @Override
    protected int getContentId() {
        return R.layout.activity_topbar;
    }

    @Override
    protected void initView() {

        mTopBar.setTitle("MUITopBar");
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTopBar.addLeftBackImageButton();
        mTopBar.addRightImageButton(R.drawable.icon_share_filled_dark);
        mTopBar.addRightImageButton(R.drawable.ic_idcard);

        // keep 个人中心样式
        mTopBar01.setTitle("我");
        mTopBar01.addRightImageButton(R.drawable.icon_message_filled_dark);
        mTopBar01.addRightImageButton(R.drawable.icon_scan_lined_dark);
        mTopBar01.addRightImageButton(R.drawable.icon_setting_lined_dark);

        // keep 个人中心
        mTopBar02.setTitle("体侧列表");
        mTopBar02.addLeftImageButton(R.drawable.icon_arrow_left_lined);
        mTopBar02.addRightTextView("体侧历史");

        // keep 动态详情
        View mCustomCenterView = LayoutInflater
                .from(this).inflate(R.layout.include_topbar_custom_layout, null);
        mCustomCenterView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mTopBar03.setCenterView(mCustomCenterView);

    }
}
