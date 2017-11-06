package com.llb.subway.view.home_fragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llb.joke.R;
import com.llb.subway.common.BaseViewHolder;
import com.llb.subway.common.CommonAdapter;
import com.llb.subway.model.bean.ForumListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derrick on 2017/11/6.
 */

public class HomeAdapter extends CommonAdapter {
    protected List<ForumListItem> mDatas;
    public HomeAdapter(Context mContext, @LayoutRes int layoutId) {
        super(mContext, layoutId);
        mDatas = new ArrayList<>();
        setData(mDatas);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        return viewHolder;
    }

    @Override
    public void setData(List mDatas) {
        super.setData(mDatas);
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(holder!=null){

            //用过Picasso框架对图片处理并显示到iv上
            //用with()方法初始化，,
//        Picasso.with(mContext)
//                //load()下载图片
//                .load(mDatas.get(position).iconUrl)
////                .resize(60,120)
//                //下载中显示的图片
//                .placeholder(R.mipmap.ic_launcher)
//                //下载失败显示的图片
//                .error(R.mipmap.ic_launcher)
//                //init()显示到指定控件
//                .into((ImageView) holder.getView(R.id.icon_iv));
            ((TextView)holder.getView(R.id.forum_name_tv)).setText(mDatas.get(position).subjectName);
            ((TextView)holder.getView(R.id.forum_description_tv)).setText(mDatas.get(position).description);
//        ((TextView)holder.getView(R.id.new_post_number_tv)).setText(mDatas.get(position).subjectNewPost);
        }
    }
}
