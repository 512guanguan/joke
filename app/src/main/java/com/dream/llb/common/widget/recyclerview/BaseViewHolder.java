package com.dream.llb.common.widget.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by llb on 2017-07-03.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{
    private ViewDataBinding binding;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}
