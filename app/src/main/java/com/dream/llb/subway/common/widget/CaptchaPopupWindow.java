package com.dream.llb.subway.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.helper.ImageHelper;

/**
 * Created by llb on 2018-01-07.
 */

public class CaptchaPopupWindow extends PopupWindow{
    private View mainView;
    private Context mContext;
    private View.OnClickListener onClickListener;
    private Button sendBtn;
    private ImageView captchaIV;
    private EditText captchaEdit;
    public CaptchaPopupWindow(Context context,View.OnClickListener onClickListener,int width, int height, boolean focusable) {
        super(context);
        this.onClickListener = onClickListener;
        this.mContext = context;
        this.mainView = LayoutInflater.from(context).inflate(R.layout.captcha_popup,null);
        sendBtn = (Button) mainView.findViewById(R.id.popup_send_btn);
        sendBtn.setOnClickListener(onClickListener);
        captchaIV = (ImageView) mainView.findViewById(R.id.popup_captcha_iv);
        captchaIV.setOnClickListener(onClickListener);
        captchaEdit = (EditText) mainView.findViewById(R.id.popup_captcha_edit);
        captchaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>3){
                    sendBtn.setEnabled(true);
                }else {
                    sendBtn.setEnabled(false);
                }
            }
        });
        setContentView(mainView);
        //设置宽度
        setWidth(width);
        //设置高度
        setHeight(height);
        //设置显示隐藏动画
//        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
//        setBackgroundDrawable();
        // 设置动画 commentPopup.setAnimationStyle(R.style.popWindow_animation_in2out);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        setFocusable(true);
        setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        setBackgroundDrawable(new ColorDrawable(0x11000000));
        //必须加这两行，不然不会显示在键盘上方
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // PopupWindow的显示及位置设置
//        showAtLocation(mainView, Gravity.CENTER, 0, 0);
    }

    public CaptchaPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    /**
     * 获取用户输入的验证码
     * @return
     */
    public String getCaptcha(){
        return captchaEdit.getText().toString();
    }

    /**
     * 设置验证码图片
     * @param path
     */
    public void setCaptchaImage(String path) {
        if(!TextUtils.isEmpty(path)){
            Bitmap bitmap = ImageHelper.getBitmap(mContext, path);
            captchaIV.setImageBitmap(bitmap);
        }
    }

}
