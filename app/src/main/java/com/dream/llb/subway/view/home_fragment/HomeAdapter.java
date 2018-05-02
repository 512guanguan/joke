package com.dream.llb.subway.view.home_fragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.BaseViewHolder;
import com.dream.llb.subway.common.OnItemClickListener;
import com.dream.llb.subway.model.api.SubwayURL;
import com.dream.llb.subway.model.bean.HomePageResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derrick on 2017/11/6.
 */

public class HomeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;
    protected List<HomePageResponse.ForumInformation> mDatas;
    protected OnItemClickListener onItemClickListener;
    protected int layoutId;
    protected boolean isLoadingMore = false;

    public HomeAdapter(Context mContext, @LayoutRes int layoutId) {
        this.mContext = mContext;
        this.mDatas = new ArrayList<>();
        this.layoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i("llb", "onCreateViewHolder viewType=" + viewType);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            View itemView = inflater.inflate(layoutId, parent, false);
            BaseViewHolder viewHolder = new BaseViewHolder(mContext, itemView);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View itemView = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
            return new BaseViewHolder(mContext, itemView);
//            to_left null;
        } else {
            return null;
        }
    }

    public void setData(List mDatas) {
        this.mDatas.clear();
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //TODO 初始化化页面
        if (this.onItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(view, position);
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
        if (holder != null) {
            if (getItemViewType(position) == TYPE_ITEM) {
                //用过Picasso框架对图片处理并显示到iv上
                //用with()方法初始化，,
                Picasso.with(mContext)
//                        .load("http://g.hiphotos.baidu.com/image/pic/item/c9fcc3cec3fdfc03e426845ed03f8794a5c226fd.jpg")//下载图片
                        .load(SubwayURL.SUBWAY_BASE + mDatas.get(position).iconUrl)
        //                .resize(60,120)
                        //下载中显示的图片
                        .placeholder(R.mipmap.ic_launcher)
                        //下载失败显示的图片
                        .error(R.mipmap.ic_launcher)
                        //init()显示到指定控件
                        .into((ImageView) holder.getView(R.id.icon_iv));
                ((TextView) holder.getView(R.id.forum_name_tv)).setText(mDatas.get(position).subjectName);
                ((TextView) holder.getView(R.id.forum_description_tv)).setText(mDatas.get(position).description);
                ((TextView) holder.getView(R.id.new_post_number_tv)).setText(mDatas.get(position).subjectNewPost);
            } else if (getItemViewType(position) == TYPE_FOOTER) {
            }
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position== mDatas.size()) {//TODO 有问题
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

//    public static boolean isBottom(RecyclerView recyclerView){
//        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//        int visibleItemCount = layoutManager.getChildCount();
//        //当前RecyclerView的所有子项个数
//        int totalItemCount = layoutManager.getItemCount();
//        //RecyclerView的滑动状态
//        int state = recyclerView.getScrollState();
//        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
//            to_left true;
//        }else {
//            to_left false;
//        }
//    }

    @Override
    public int getItemCount() {
        return mDatas == null ? -1 : mDatas.size();
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
}
