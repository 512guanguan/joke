package com.dream.llb.subway.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.llb.subway.R;

public class CustomResultDialog extends Dialog {
    private TextView title_text, notic_text;
    private Button confirmBtn, confirmBtn_1;
    private View confirm_line;
    private TextView closeTxt;
    /**
     * 标记弹框类型 4-高端理财购买的合格投资者认定
     **/
    private int id = 0;
    private boolean canback = true;

    public CustomResultDialog(Context mContext) {
        super(mContext, R.style.CustomDialog);
        setCustomDialog();
    }

    public CustomResultDialog(Context mContext, int id) {
        super(mContext, R.style.CustomDialog);
        this.id = id;
        setCustomDialog();
    }

    public void canback(boolean canback) {
        this.canback = canback;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // System.out.println("onBackPressed");
        if (canback) {
            super.onBackPressed();
        }
    }

    public void showD() {
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(android.view.WindowManager.LayoutParams.FILL_PARENT,
                android.view.WindowManager.LayoutParams.WRAP_CONTENT);
        show();
    }

    private void setCustomDialog() {
        View mView;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.custom_result_dialog, null);
        confirmBtn = (Button) mView.findViewById(R.id.confirm_btn);
        confirmBtn_1 = (Button) mView.findViewById(R.id.confirm_btn_1);
        confirm_line = mView.findViewById(R.id.confirm_line);
        title_text = (TextView) mView.findViewById(R.id.hint_txt);
        notic_text = (TextView) mView.findViewById(R.id.notic_txt);
        closeTxt = (TextView) mView.findViewById(R.id.close_txt);
        if (id == 4) {
            notic_text.getLayoutParams().height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;// setHeight();
            notic_text.setGravity(android.view.Gravity.LEFT);
            closeTxt.setVisibility(View.GONE);
        }
        closeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });

        super.setContentView(mView);
    }

    /**
     * 确定键监听器
     *
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener) {
        confirmBtn.setOnClickListener(listener);
    }

    public void setOnNegativeListener(View.OnClickListener listener) {
        confirmBtn_1.setOnClickListener(listener);
    }

    public void setTitle(CharSequence title) {
        if (title != null) {
            title_text.setText(title);
        } else {
            title_text.setVisibility(View.GONE);
        }
    }

    public void setNotic(String notic) {
        if (notic != null) {
            notic = notic.replace("\\n", "\n");
            notic_text.setGravity(Gravity.LEFT);
            notic_text.setText(notic);
        } else {
            notic_text.setVisibility(View.GONE);
        }
    }

    public void setNotic(CharSequence notic) {
        if (notic != null) {
            notic_text.setText(notic);
        } else {
            notic_text.setVisibility(View.GONE);
        }
    }

    public void setX() {
        closeTxt.setVisibility(View.GONE);
    }

    public void setX(int visibility) {
        closeTxt.setVisibility(visibility);
    }

    public void setBtnText(String btn1, String btn2) {
        if (btn1 != null) {
            confirmBtn.setText(btn1);
        } else {
            if (confirm_line != null){
                confirm_line.setVisibility(View.GONE);
            }
            confirmBtn.setVisibility(View.GONE);
        }
        if (btn2 != null) {
            confirmBtn_1.setText(btn2);
        } else {
            if (confirm_line != null){
                confirm_line.setVisibility(View.GONE);
            }
            confirmBtn_1.setVisibility(View.GONE);
        }
    }

    public void setBtnText(String btn1) {

        confirmBtn_1.setText(btn1);

    }

    public void setBtnClible(boolean btn1, boolean btn2) {
        confirmBtn.setEnabled(btn1);
        confirmBtn_1.setEnabled(btn2);
    }

}
