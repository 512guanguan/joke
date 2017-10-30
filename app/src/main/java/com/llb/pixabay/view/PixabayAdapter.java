package com.llb.pixabay.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.llb.common.utils.ViewUtil;
import com.llb.common.widget.recyclerview.BaseViewHolder;
import com.llb.joke.R;
import com.llb.pixabay.model.bean.SearchImagesResponse.HitImages;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derrick on 2017/7/13.
 */

public class PixabayAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<HitImages> mDatas;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private OnItemClickListener onItemClickListener;
    private int layoutId;
    private int brId;
    private boolean isLoadingMore = false;

    public PixabayAdapter(Context mContext, @LayoutRes int layoutId, int brId) {
        this.mContext = mContext;
        this.mDatas = new ArrayList<>();
        this.layoutId = layoutId;
        this.brId = brId;
    }

    public PixabayAdapter(List<HitImages> mDatas, @LayoutRes int layoutId, int brId) {
        this.mDatas = mDatas;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
            return new BaseViewHolder(view);
        }
        else {
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
            BaseViewHolder viewHolder = new BaseViewHolder(binding.getRoot());
            viewHolder.setBinding(binding);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder.getBinding() != null) {
            holder.getBinding().setVariable(brId, mDatas.get(position));
            holder.getBinding().executePendingBindings();
            if (this.onItemClickListener != null) {
                if(getItemViewType(position) == TYPE_ITEM){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            int position = holder.getLayoutPosition();
                            onItemClickListener.onItemClick(view, position);
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            int position = holder.getLayoutPosition();
                            onItemClickListener.onItemLongClick(view, position);
                            return false;
                        }
                    });
                }else {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            int position = holder.getLayoutPosition();
                            onItemClickListener.onItemClick(view, position);
                        }
                    });
                }
            }
        }
        //通过itemview得到每个图片的pararms对象
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();

        //将高度修改为传入的随机高度
        params.height = Integer.valueOf(mDatas.get(position).webformatHeight);
        double width = ViewUtil.getWindowDisplayMetrics(mContext).widthPixels * 0.49;
        params.width = new Double(width).intValue();

        //设置修改参数
        holder.itemView.setLayoutParams(params);
//        holder.iv.setImageResource(R.mipmap.ic_launcher);

        //用过Picasso框架对图片处理并显示到iv上
        //用with()方法初始化，,
//        Picasso.with(mContext)
//                //load()下载图片
//                .load(mDatas.get(position).webformatURL)
////                .resize(60,120)
//                //下载中显示的图片
//                .placeholder(R.mipmap.ic_launcher)
//                //下载失败显示的图片
//                .error(R.mipmap.ic_launcher)
//                //init()显示到指定控件
//                .into((ImageView) holder.itemView.findViewById(R.id.pixabay_imageview));
    }

    public void setImageData(List<HitImages> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + '1' == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    public void setData(List<HitImages> mDatas){
        this.mDatas = mDatas;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
