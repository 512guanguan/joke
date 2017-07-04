package com.llb.common.recycler_view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llb on 2017-07-03.
 */

public class CommonAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{
    private Context mContext;
    private List<T> mDatas;

    private int layoutId;

    private int brId;

    public CommonAdapter(Context mContext, @LayoutRes int layoutId, int brId){
        this.mContext = mContext;
        this.mDatas = new ArrayList<>();
        this.layoutId = layoutId;
        this.brId = brId;
    }
    public CommonAdapter(List<T> mDatas, @LayoutRes int layoutId, int brId){
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
    }
    public void setData(List<T> mDatas){
        this.mDatas = mDatas;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.getBinding().setVariable(brId,mDatas.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();

    }
}
