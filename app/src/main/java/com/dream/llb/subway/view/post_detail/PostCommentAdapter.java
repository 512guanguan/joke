package com.dream.llb.subway.view.post_detail;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
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
import com.dream.llb.subway.common.htmlspanner.handlers.MyLinkHandler;
import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse.CommentInformation;
import com.squareup.picasso.Picasso;

import net.nightwhistler.htmlspanner.HtmlSpanner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Derrick on 2017/11/6.
 */

public class PostCommentAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_ITEM = 1;
    protected static final int TYPE_FOOTER = 2;
    protected List<CommentInformation> commentData;
    protected PostDetailResponse response;
    protected View headerView, footerView;
    protected OnItemClickListener onItemClickListener;
    protected int layoutId;
    protected boolean isLoadingMore = false;
    protected HtmlSpanner htmlSpanner;

    public PostCommentAdapter(Context mContext, @LayoutRes int layoutId) {
        this.mContext = mContext;
        this.commentData = new ArrayList<>();
        this.layoutId = layoutId;
        htmlSpanner = new HtmlSpanner();
        htmlSpanner.registerHandler("a",new MyLinkHandler());
        htmlSpanner.setStripExtraWhiteSpace(true);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("llb", "onCreateViewHolder viewType=" + viewType);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        BaseViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEADER:
//                if (headerView == null) {
                headerView = inflater.inflate(R.layout.item_post_detail_header, parent, false);
//                }
                holder = new BaseViewHolder(mContext, headerView);
                break;
            case TYPE_ITEM:
                itemView = inflater.inflate(layoutId, parent, false);
                holder = new BaseViewHolder(mContext, itemView);
                break;
            case TYPE_FOOTER:
//                if (footerView == null) {
                footerView = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
//                }
                holder = new BaseViewHolder(mContext, footerView);
                break;
            default:
                break;
        }
        return holder;
    }

    /**
     * 初次刷新加载数据
     * @param response
     */
    public void setData(PostDetailResponse response) {
        this.commentData.addAll(response.commentList);
//        response.commentList.clear();
        this.response = response;
        notifyDataSetChanged();
    }

    /**
     * 上拉加载更多数据合并
     * @param response
     */
    public void setMoreData(PostDetailResponse response){
        this.commentData.addAll(response.commentList);
        notifyDataSetChanged();
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return headerView;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        int realPosition = getRealPosition(holder);
        //TODO 初始化化页面
        if (this.onItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(view, holder.getLayoutPosition());
                    return false;
                }
            });
        }
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((TextView) holder.getView(R.id.post_author_tv)).setText(new HtmlSpanner().fromHtml(response.author));
                ((TextView) holder.getView(R.id.post_time_tv)).setText(new HtmlSpanner().fromHtml(response.postTime));
                ((TextView) holder.getView(R.id.post_title_tv)).setText(new HtmlSpanner().fromHtml(response.postTitle));
//                ((TextView) holder.getView(R.id.post_content_tv)).setText(new HtmlSpanner().fromHtml(response.postContent));
                Observable.just(response.postContent)
                        .subscribeOn(Schedulers.io())
                        .flatMap((String content)->{
                            Spannable spannable = htmlSpanner.fromHtml(content);
                            return Observable.just(spannable);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((Spannable content)->{
                            ((TextView) holder.getView(R.id.post_content_tv)).setText(content);
                            ((TextView) holder.getView(R.id.post_content_tv)).setMovementMethod(LinkMovementMethod.getInstance());
                        },(error)->{

                        },()->{

                        });

                break;
            case TYPE_ITEM:
//                if (TextUtils.isEmpty(commentData.get(realPosition).headShotUrl)) {
//                    holder.getView(R.id.head_shortcut_iv).setVisibility(View.GONE);
//                } else {
//                    holder.getView(R.id.head_shortcut_iv).setVisibility(View.VISIBLE);
                //用过Picasso框架对图片处理并显示到iv上
                //用with()方法初始化，,
                Picasso.with(mContext)
//                        .load("http://g.hiphotos.baidu.com/image/pic/item/c9fcc3cec3fdfc03e426845ed03f8794a5c226fd.jpg")//下载图片
                        .load(commentData.get(realPosition).headShotUrl)
                        //                .resize(60,120)
                        //下载中显示的图片
                        .placeholder(R.mipmap.ic_launcher)
                        //下载失败显示的图片
                        .error(R.mipmap.ic_launcher)
                        //init()显示到指定控件
                        .into((ImageView) holder.getView(R.id.head_shortcut_iv));
//                }
//
                ((TextView) holder.getView(R.id.comment_author_tv)).setText(commentData.get(realPosition).author);
                ((TextView) holder.getView(R.id.comment_time_tv)).setText(commentData.get(realPosition).commentTime);
                ((TextView) holder.getView(R.id.floor_title_tv)).setText(commentData.get(realPosition).floor);
                ((TextView) holder.getView(R.id.comment_content_tv)).setText(new HtmlSpanner().fromHtml(commentData.get(realPosition).commentContent));

                Observable.just(commentData.get(realPosition).commentContent)
                        .subscribeOn(Schedulers.io())
                        .flatMap((String content)->{
                            Spannable spannable = htmlSpanner.fromHtml(content);
                            return Observable.just(spannable);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((Spannable content)->{
                            ((TextView) holder.getView(R.id.comment_content_tv)).setText(content);
                            ((TextView) holder.getView(R.id.comment_content_tv)).setMovementMethod(LinkMovementMethod.getInstance());
                        },(error)->{

                        },()->{

                        });


                break;
            case TYPE_FOOTER:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //TODO 有问题
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
//        } else if (headerView != null && commentData.size()>0 && position>= commentData.size()) {
        } else if (position < getItemCount() && position >= getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {//http://blog.csdn.net/qibin0506/article/details/49716795
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    public int getItemCount() {
        return commentData.size() + getHeadersCount() + getFootersCount();
    }
    public int getHeadersCount() {
        return 1;
    }

    public int getFootersCount() {
        return 1;
    }
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
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
