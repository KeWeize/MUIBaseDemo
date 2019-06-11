package vit.com.muibasedemo;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import vit.com.arch.base.VBaseA;
import vit.com.mui.widget.dialog.MUIDialog;
import vit.com.mui.widget.dialog.MUIDialogAction;
import vit.com.mui.widget.dialog.MUIDialogBuilder;

/**
 * @author kewz
 * @date 2019/5/19
 */
public class MUIDialogActivity extends VBaseA {

    @Override
    protected int getContentId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.tv_message_dialog, R.id.tv_checkbox_message_dialog, R.id.tv_edittext_dialog,
            R.id.tv_simple_menu_dialog, R.id.tv_checkable_menu_dialog, R.id.tv_multi_check_menu_dialog})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_message_dialog:
                showMessageDialog();
                break;

            case R.id.tv_checkbox_message_dialog:
                showCheckBoxMessageDialog();
                break;

            case R.id.tv_edittext_dialog:
                showEditTextDialog();
                break;

            case R.id.tv_simple_menu_dialog:
                showSimpleMenuDialog();
                break;

            case R.id.tv_checkable_menu_dialog:
                showCheckableMenuDialog();
                break;

            case R.id.tv_multi_check_menu_dialog:
                showMultiCheckableDialogBuilder();
                break;

            default:
                break;
        }
    }

    private void showMessageDialog() {
        new MUIDialog.MessageDialogBuilder(this)
                .setTitle("Title")
                .setMessage("i am message long message message long message message " +
                        "long message message long message" +
                        "long message message long message" +
                        "long message message long message" +
                        "long message message long message")
                .addAction(R.drawable.icon_arrow_left_lined, "CANCEL", MUIDialogAction.ACTION_PROP_NEGATIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "OK", MUIDialogAction.ACTION_PROP_POSITIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        Toast.makeText(MUIDialogActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void showCheckBoxMessageDialog() {
        new MUIDialog.CheckBoxMessageDialogBuilder(this)
                .setTitle("Title")
                .setMessage("Delete All The Message")
                .addAction(R.drawable.icon_arrow_left_lined, "CANCEL", MUIDialogAction.ACTION_PROP_NEGATIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "OK", MUIDialogAction.ACTION_PROP_POSITIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        Toast.makeText(MUIDialogActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void showEditTextDialog() {
        new MUIDialog.EditTextDialogBuilder(this)
                .setTitle("Title")
                .setDefaultText("Vitar5")
                .addAction(R.drawable.icon_arrow_left_lined, "CANCEL", MUIDialogAction.ACTION_PROP_NEGATIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "OK", MUIDialogAction.ACTION_PROP_POSITIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        Toast.makeText(MUIDialogActivity.this, "点击了确定", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void showSimpleMenuDialog() {
        String[] datas = new String[]{"Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Hanzhou",
                "Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Hanzhou", "Beijing", "Shanghai",
                "Guangzhou", "Shenzhen", "Hanzhou"};
        new MUIDialog.SimpleMenuDialogBuilder(this)
                .setTitle("Title")
                .addItems(datas, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MUIDialogActivity.this, "select " + datas[i], Toast.LENGTH_SHORT).show();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "CANCEL", MUIDialogAction.ACTION_PROP_NEGATIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "OK", MUIDialogAction.ACTION_PROP_POSITIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        Toast.makeText(MUIDialogActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showCheckableMenuDialog() {
        String[] datas = new String[]{"Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Hanzhou",
                "Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Hanzhou", "Beijing", "Shanghai",
                "Guangzhou", "Shenzhen", "Hanzhou"};
        new MUIDialog.CheckableDialogBuilder(this)
                .setTitle("Title")
                .addItems(datas, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MUIDialogActivity.this, "select " + datas[i], Toast.LENGTH_SHORT).show();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "CANCEL", MUIDialogAction.ACTION_PROP_NEGATIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "OK", MUIDialogAction.ACTION_PROP_POSITIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        Toast.makeText(MUIDialogActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showMultiCheckableDialogBuilder() {
        String[] datas = new String[]{"Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Hanzhou",
                "Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Hanzhou", "Beijing", "Shanghai",
                "Guangzhou", "Shenzhen", "Hanzhou"};
        new MUIDialog.MultiCheckableDialogBuilder(this)
                .setTitle("Title")
                .addItems(datas, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MUIDialogActivity.this, "select " + datas[i], Toast.LENGTH_SHORT).show();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "CANCEL", MUIDialogAction.ACTION_PROP_NEGATIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.drawable.icon_arrow_left_lined, "OK", MUIDialogAction.ACTION_PROP_POSITIVE, new MUIDialogAction.OnActionListener() {
                    @Override
                    public void onClick(MUIDialog dialog, int index) {
                        Toast.makeText(MUIDialogActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
