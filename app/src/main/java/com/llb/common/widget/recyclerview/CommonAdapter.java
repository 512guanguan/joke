package com.llb.common.widget.recyclerview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import com.llb.joke.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llb on 2017-07-03.
 */

public class CommonAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{
    private Context mContext;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<T> mDatas;
    private OnItemClickListener onItemClickListener;
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
        Log.i("llb", "onCreateViewHolder viewType=" + viewType);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_ITEM){
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
            BaseViewHolder viewHolder = new BaseViewHolder(binding.getRoot());
            viewHolder.setBinding(binding);
            return viewHolder;
        }else if(viewType == TYPE_FOOTER){
            View view = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
            return new BaseViewHolder(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if(holder.getBinding() != null) {
            holder.getBinding().setVariable(brId,mDatas.get(position));
            holder.getBinding().executePendingBindings();
            if(this.onItemClickListener != null) {
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(view,position);
                    }
                });
                holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(view, position);
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position + '1' == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
