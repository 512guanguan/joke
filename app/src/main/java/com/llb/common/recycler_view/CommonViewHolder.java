package com.llb.common.recycler_view;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by llb on 2017-07-03.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder{
    public CommonViewHolder(View itemView) {
        super(itemView);
    }
    private ViewDataBinding binding;

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}
