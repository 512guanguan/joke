package com.llb.common.recycler_view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by llb on 2017-07-03.
 */

public class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder>{
    private List<T> mDatas;

    private int layoutId;

    private int brId;

    public CommonAdapter(List<T> mDatas, int layoutId, int brId){
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
    }
    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        CommonViewHolder viewHolder = new CommonViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        holder.getBinding().setVariable(brId,mDatas.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();

    }
}
