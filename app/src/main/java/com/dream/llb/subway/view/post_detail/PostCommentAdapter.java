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
import com.dream.llb.subway.common.htmlspanner.handlers.MyLinkHandler;
import com.dream.llb.subway.model.bean.PostDetailResponse;
import com.dream.llb.subway.model.bean.PostDetailResponse.CommentInformation;
import com.dream.llb.subway.view.base.BaseApplication;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
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
    protected static final int TYPE_FOOTER_NO_MORE_DATA = 3;
    protected static final int BANNER_AD_VIEW_TYPE = 4;
    protected boolean hasAd = false;
    protected int adIndex = 15;
    protected List<Object> commentData;
    protected PostDetailResponse response;
    protected View headerView, footerView, noMoreDataView;
    protected OnItemClickListener onItemClickListener;
    protected int layoutId;
    protected boolean isLoadingMore = false;
    protected HtmlSpanner htmlSpanner;
    public boolean isLastPage = true;//标记最后一页
    protected static final int ITEMS_PER_AD = 25;
    protected String adUnitId = "ca-app-pub-1863836438957183/6535261332";// "ca-app-pub-3940256099942544/6300978111";//TODO　test

    public PostCommentAdapter(Context mContext, @LayoutRes int layoutId) {
        this.mContext = mContext;
        this.commentData = new ArrayList<>();
        this.layoutId = layoutId;
        htmlSpanner = new HtmlSpanner();
        htmlSpanner.registerHandler("a", new MyLinkHandler());
        htmlSpanner.setStripExtraWhiteSpace(true);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.i("llb", "onCreateViewHolder viewType=" + viewType);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        BaseViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEADER:
                if (headerView == null) {
                    headerView = inflater.inflate(R.layout.item_post_detail_header, parent, false);
                }
                holder = new BaseViewHolder(mContext, headerView);
                break;
            case TYPE_ITEM:
                itemView = inflater.inflate(layoutId, parent, false);
                holder = new BaseViewHolder(mContext, itemView);
                break;
            case TYPE_FOOTER:
                if (footerView == null) {
                    footerView = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
                }
                holder = new BaseViewHolder(mContext, footerView);
                break;
            case TYPE_FOOTER_NO_MORE_DATA:
                if (noMoreDataView == null) {
                    noMoreDataView = inflater.inflate(R.layout.item_recyclerview_footer_no_more_data, parent, false);
                }
                holder = new BaseViewHolder(mContext, noMoreDataView);
                break;
            case BANNER_AD_VIEW_TYPE:
                // fall through
                View bannerLayoutView = LayoutInflater.from(mContext).inflate(R.layout.banner_ad_container, parent, false);
                holder = new BaseViewHolder(mContext, bannerLayoutView);
                break;
            default:
                break;
        }
        return holder;
    }

    /**
     * 初次刷新加载数据
     *
     * @param response
     */
    public void setData(PostDetailResponse response) {
        this.commentData.clear();
        this.commentData.addAll(response.commentList);
//        response.commentList.clear();
        // TODO admod
        this.response = response;
        if (this.commentData.size() == Integer.valueOf(this.response.commentNum)) {
            isLastPage = true;
        } else {
            isLastPage = false;
        }
        notifyDataSetChanged();
    }

    /**
     * 上拉加载更多数据合并
     *
     * @param response
     */
    public void setMoreData(PostDetailResponse response) {
        this.commentData.addAll(response.commentList);
        if(!hasAd){
            addBannerAds();
        }
        if (this.commentData.size() == Integer.valueOf(this.response.commentNum)) {
            isLastPage = true;
        } else {
            isLastPage = false;
        }
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
        if (holder == null || headerView == null) {
            return;
        }
        int realPosition = getRealPosition(holder);
//        Log.i("llb", "realPostion="+realPosition+"===postion="+position+"===totalCount="+getItemCount()+"====Commentsize="+commentData.size());
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
                        .flatMap((String content) -> {
                            Spannable spannable = htmlSpanner.fromHtml(content);
                            return Observable.just(spannable);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((Spannable content) -> {
                            ((TextView) holder.getView(R.id.post_content_tv)).setText(content);
                            ((TextView) holder.getView(R.id.post_content_tv)).setMovementMethod(LinkMovementMethod.getInstance());
                        }, (error) -> {

                        }, () -> {

                        });

                break;
            case TYPE_ITEM:
                if (commentData.get(realPosition) instanceof CommentInformation) {
                    CommentInformation information = (CommentInformation) commentData.get(realPosition);
                    //用with()方法初始化，,//用过Picasso框架对图片处理并显示到iv上
                    Picasso.with(mContext)
//                        .load("http://g.hiphotos.baidu.com/image/pic/item/c9fcc3cec3fdfc03e426845ed03f8794a5c226fd.jpg")//下载图片
                            .load(information.headShotUrl)
                            //                .resize(60,120)
                            //下载中显示的图片
                            .placeholder(R.drawable.default_head_icon)
                            //下载失败显示的图片
                            .error(R.drawable.default_head_icon)
                            //init()显示到指定控件
                            .into((ImageView) holder.getView(R.id.head_shortcut_iv));
//                }
//
                    ((TextView) holder.getView(R.id.comment_author_tv)).setText(information.author);
                    ((TextView) holder.getView(R.id.comment_time_tv)).setText(information.commentTime);
                    ((TextView) holder.getView(R.id.floor_title_tv)).setText(information.floor.replace("#", "楼"));
                    //下面这句话可能在主线程loadimage，闪退
//                ((TextView) holder.getView(R.id.comment_content_tv)).setText(new HtmlSpanner().fromHtml(commentData.get(realPosition).commentContent));
                    if (BaseApplication.isLogin) {
                        holder.getView(R.id.reply_tv).setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO 只有登陆态才能跳转哟，登录态的判定待完善
                                onItemClickListener.onReplyButtonClick(information.replyUrl, information.author);
                            }
                        });
                    } else {
                        holder.getView(R.id.reply_tv).setVisibility(View.GONE);
                    }

                    Observable.just(information.commentContent)
                            .subscribeOn(Schedulers.io())
                            .flatMap((String content) -> {
                                Spannable spannable = htmlSpanner.fromHtml(content);
                                return Observable.just(spannable);
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((Spannable content) -> {
                                ((TextView) holder.getView(R.id.comment_content_tv)).setText(content);
                                ((TextView) holder.getView(R.id.comment_content_tv)).setMovementMethod(LinkMovementMethod.getInstance());
                            }, (error) -> {
                            }, () -> {
                            });
                } else if (commentData.get(realPosition) instanceof AdView) { // TODO　AdView item
                    BaseViewHolder bannerHolder = (BaseViewHolder) holder;
                    AdView adView = (AdView) commentData.get(realPosition);
                    ViewGroup adCardView = (ViewGroup) bannerHolder.itemView;
                    // The AdViewHolder recycled by the RecyclerView may be a different
                    // instance than the one used previously for this position. Clear the
                    // AdViewHolder of any subviews in case it has a different
                    // AdView associated with it, and make sure the AdView for this position doesn't
                    // already have a parent of a different recycled AdViewHolder.
                    if (adCardView.getChildCount() > 0) {
                        adCardView.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((ViewGroup) adView.getParent()).removeView(adView);
                    }
                    // Add the banner ad to the ad view.
                    adCardView.addView(adView);
                }

                break;
            case TYPE_FOOTER:
                Log.i("llb", "getViewHolder TYPE_FOOTER");
                break;
            case TYPE_FOOTER_NO_MORE_DATA:
                Log.i("llb", "getViewHolder TYPE_FOOTER_NO_MORE_DATA");
                break;
            case BANNER_AD_VIEW_TYPE:
                // fall through
                BaseViewHolder bannerHolder = (BaseViewHolder) holder;
                AdView adView = (AdView) commentData.get(realPosition);
                ViewGroup adCardView = (ViewGroup) bannerHolder.itemView;
                // The AdViewHolder recycled by the RecyclerView may be a different
                // instance than the one used previously for this position. Clear the
                // AdViewHolder of any subviews in case it has a different
                // AdView associated with it, and make sure the AdView for this position doesn't
                // already have a parent of a different recycled AdViewHolder.
                if (adCardView.getChildCount() > 0) {
                    adCardView.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }
                // Add the banner ad to the ad view.
                adCardView.addView(adView);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        //TODO 有问题
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        } else if (hasAd && position == adIndex + 1) {
            return BANNER_AD_VIEW_TYPE;
        } else if (position > 0 && position <= commentData.size()) {
            return TYPE_ITEM;
        } else if (position == commentData.size() + 1 && isLastPage) {
            Log.i("llb", "最后一页");
            return TYPE_FOOTER_NO_MORE_DATA;
        } else {//if(position == commentData.size()+getHeadersCount()){
            return TYPE_FOOTER;
        }
    }

    @Override
    public int getItemCount() {
        if (headerView == null) {
            return 0;
        } else {
            return commentData.size() + getHeadersCount() + getFootersCount();
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

    public int getHeadersCount() {
        if (headerView == null) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getFootersCount() {
//        if (footerView == null && noMoreDataView == null) {
//            to_left 0;
//        } else {
        return 1;
//        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }

    public boolean isLastPage() {
        return isLastPage;
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        /**
         * 点击某一层的回复按钮
         *
         * @param replyPageUrl 请求回复页面的链接
         * @param authorName   层主的昵称，用来@用
         */
        void onReplyButtonClick(String replyPageUrl, String authorName);
    }

    /**
     * Adds banner ads to the items list.
     */
    private void addBannerAds() {
        // Loop through the items array and place a new banner ad in every ith position in
        // the items List.
//        for (int i = ITEMS_PER_AD; i <= commentData.size(); i += ITEMS_PER_AD) {
        if (commentData.size() > adIndex) {
            final AdView adView = new AdView(mContext);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(adUnitId);
            commentData.add(adIndex, adView);
            hasAd = true;
            this.response.commentNum = String.valueOf(Integer.valueOf(response.commentNum) + 1);
            loadBannerAd(adIndex);
        }

//        }
    }

    //
//    /**
//     * Sets up and loads the banner ads.
//     */
    private void loadBannerAds() {
        // Load the first banner ad in the items list (subsequent ads will be loaded automatically
        // in sequence).
//        loadBannerAd(0);
    }

    //
//    /**
//     * Loads the banner ads in the items list.
//     */
    private void loadBannerAd(final int index) {
        if (index >= commentData.size()) {
            return;
        }
        Object item = commentData.get(index);
        if (!(item instanceof AdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a banner ad"
                    + " ad.");
        }
        final AdView adView = (AdView) item;
        // Set an AdListener on the AdView to wait for the previous banner ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i("llb", "onAdLoaded");
                // The previous banner ad loaded successfully, call this method again to
                // load the next ad in the items list.
//                loadBannerAd(index + ITEMS_PER_AD);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.i("llb", "onAdFailedToLoad");
                // The previous banner ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous banner ad failed to load. Attempting to"
                        + " load the next banner ad in the items list.");
//                loadBannerAd(index + ITEMS_PER_AD);
            }
        });

        // Load the banner ad.
        adView.loadAd(new AdRequest.Builder().build());
    }
//    public class AdViewHolder extends RecyclerView.ViewHolder {
//        AdViewHolder(View view) {
//            super(view);
//        }
//    }
}
