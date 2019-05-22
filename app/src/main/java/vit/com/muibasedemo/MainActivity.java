package vit.com.muibasedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import vit.com.arch.base.VBaseA;
import vit.com.mui.widget.dialog.MUIDialog;

public class MainActivity extends VBaseA {

    @BindView(R.id.tv_text_span)
    TextView mTextSpanTv;

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        SpannableString ss = new SpannableString("Java一次编译到处运行并不适用Android");
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(MainActivity.this, "点击了Java", Toast.LENGTH_SHORT).show();
            }
        }, 0, 4, 0);

        mTextSpanTv.setText(ss);
        mTextSpanTv.setMovementMethod(LinkMovementMethod.getInstance());
        mTextSpanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击了TextView", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.btn_layout, R.id.btn_topbar, R.id.btn_dialog})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_layout:
                startActivity(new Intent(this, MUILayoutActivity.class));
                break;

            case R.id.btn_topbar:
                startActivity(new Intent(this, MUITopBarActivity.class));
                break;

            case R.id.btn_dialog:
                startActivity(new Intent(this, MUIDialogActivity.class));
                break;

            default:
                break;
        }
    }
}
