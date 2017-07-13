package com.llb.pixabay.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.llb.common.widget.recyclerview.BaseViewHolder;
import com.llb.common.widget.recyclerview.CommonAdapter;
import com.llb.joke.R;
import com.llb.pixabay.model.bean.SearchImagesResponse.HitImages;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Derrick on 2017/7/13.
 */

public class PixabayAdapter extends CommonAdapter {
    private Context mContext;
    private List<HitImages> mDatas;

    public PixabayAdapter(Context mContext, @LayoutRes int layoutId, int brId) {
        super(mContext, layoutId, brId);
        this.mContext = mContext;
    }

    public PixabayAdapter(List<HitImages> mDatas, @LayoutRes int layoutId, int brId) {
        super(mDatas, layoutId, brId);
        this.mDatas = mDatas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //通过itemview得到每个图片的pararms对象
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();

        //将高度修改为传入的随机高度
        params.height = Integer.valueOf(mDatas.get(position).previewHeight);

        //设置修改参数
        holder.itemView.setLayoutParams(params);


//        holder.iv.setImageResource(R.mipmap.ic_launcher);

        //用过Picasso框架对图片处理并显示到iv上
        //用with()方法初始化，,
        Picasso.with(mContext)
                //load()下载图片
                .load(mDatas.get(position).previewURL)
                //下载中显示的图片
                .placeholder(R.mipmap.ic_launcher)
                //下载失败显示的图片
                .error(R.mipmap.ic_launcher)
                //init()显示到指定控件
                .into((ImageView) holder.itemView.findViewById(R.id.pixabay_imageview));
    }
}
