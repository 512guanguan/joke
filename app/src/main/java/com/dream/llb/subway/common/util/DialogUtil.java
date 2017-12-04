package com.dream.llb.subway.common.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.dream.llb.subway.common.widget.CustomResultDialog;

/**
 * TODO 先用着有空待梳理
 */
public class DialogUtil {
    /**
     * 确认取消弹窗
     */
    public static void showDialog(String title, String msg, Context context, final DialogListener listener) {
        showDialog(title, msg, null, null, context, listener);
    }

    /**
     * @param title    标题
     * @param msg      信息
     * @param btn1     右边按钮
     * @param btn2     左边按钮
     * @param context
     * @param listener 返回监听
     */
    public static void showDialog(String title, CharSequence msg, String btn1, String btn2, Context context,
                                  final DialogListener listener) {
        showDialog(title, msg, btn1, btn2, context, listener, true, View.INVISIBLE);
    }

    public static void showDialog(String title, CharSequence msg, String btn1, String btn2, Context context,
                                  final DialogListener listener, boolean Canceled, int visibility) {
        showDialog(title, msg, null, btn1, btn2, context, listener, Canceled, View.INVISIBLE);
    }

    /**
     * @param title    标题
     * @param msg      信息
     * @param msg1     信息(字符串类型)
     * @param btn1     右边按钮
     * @param btn2     左边按钮
     * @param context
     * @param listener 返回监听
     * @param Canceled 是否能点击屏幕消失
     */
    public static void showDialog(final String title, CharSequence msg, String msg1, String btn1, String btn2, final Context context,
                                  final DialogListener listener, boolean Canceled, int visibility) {
        final CustomResultDialog customResultDialog = new CustomResultDialog(context, 1);
        customResultDialog.setTitle(title);
        if (msg1 != null) {

            customResultDialog.setNotic(msg1);
        } else {
            customResultDialog.setNotic(msg);
        }
        customResultDialog.setX(visibility);
        if (btn1 == null) {
            btn1 = "确认";
        }
        if (btn2 == null) {
            btn2 = "取消";
        }
        customResultDialog.setBtnText(btn2, btn1);
        customResultDialog.setOnPositiveListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                customResultDialog.dismiss();
                listener.onNegativeTaskOver();
            }
        });
        customResultDialog.setOnNegativeListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                customResultDialog.dismiss();
                listener.onPositiveTaskOver();
            }
        });
        customResultDialog.getWindow().setGravity(Gravity.CENTER);
        customResultDialog.setCanceledOnTouchOutside(Canceled);
        if (!Canceled) {// 如果不能点击屏幕消失，则把点击返回按钮时消失的功能去掉
            customResultDialog.canback(false);
        }
        customResultDialog.getWindow().setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        customResultDialog.show();
        customResultDialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss();
            }
        });
    }

    public interface DialogListener {
        void onPositiveTaskOver();

        void onNegativeTaskOver();

        void onDismiss();
    }

    public static void showDialog(String title, String msg, String btn1, Context context,
                                  final DialogListener listener) {
        showDialog(title, msg, btn1, context, listener, true);
    }

    //单个按钮
    public static void showDialog(String title, String msg, String btn1, Context context,
                                  final DialogListener listener, boolean Canceled) {
        final CustomResultDialog customResultDialog = new CustomResultDialog(context, 1);
        customResultDialog.setTitle(title);
        if (msg != null && msg.contains("</font>")) {
            customResultDialog.setNotic(Html.fromHtml(msg));
        } else {
            customResultDialog.setNotic(msg);
        }
        customResultDialog.setX(View.INVISIBLE);
        if (btn1 == null) {
            btn1 = "确 " + " " + " " + " " + "  认";
        }
        customResultDialog.setBtnText(null, btn1);
        customResultDialog.setOnNegativeListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (listener != null) {//因为单个按钮改了颜色，所以这里的监听反了
                    listener.onPositiveTaskOver();
                }
                customResultDialog.dismiss();
            }
        });
        customResultDialog.setCanceledOnTouchOutside(Canceled);
        if (!Canceled) {// 如果不能点击屏幕消失，则把点击返回按钮时消失的功能去掉
            customResultDialog.canback(false);
        }
        customResultDialog.getWindow().setGravity(Gravity.CENTER);
        customResultDialog.getWindow().setLayout(android.view.WindowManager.LayoutParams.WRAP_CONTENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        customResultDialog.show();
        customResultDialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });
    }



}
