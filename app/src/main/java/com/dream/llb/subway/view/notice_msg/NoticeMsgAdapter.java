package com.dream.llb.subway.view.notice_msg;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dream.llb.subway.R;
import com.dream.llb.subway.common.BaseViewHolder;
import com.dream.llb.subway.common.OnItemClickListener;
import com.dream.llb.subway.common.htmlspanner.handlers.MyLinkHandler;

import net.nightwhistler.htmlspanner.HtmlSpanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derrick on 2017/11/6.
 */

public class NoticeMsgAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    protected List<String> mDatas;
    protected OnItemClickListener onItemClickListener;
    protected int layoutId;
    protected HtmlSpanner htmlSpanner;

    public NoticeMsgAdapter(Context mContext, @LayoutRes int layoutId) {
        this.mContext = mContext;
        this.mDatas = new ArrayList<>();
        this.layoutId = layoutId;
        htmlSpanner = new HtmlSpanner();
        htmlSpanner.registerHandler("a", new MyLinkHandler());
        htmlSpanner.setStripExtraWhiteSpace(true);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(mContext, itemView);
        return viewHolder;
    }

    /**
     * 更新or初始化数据，会清掉原有数据
     *
     * @param mDatas
     */
    public void setData(List mDatas) {
        this.mDatas.clear();
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void setMoreData(List mDatas) {
        this.mDatas.addAll(mDatas);
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
            TextView textView = ((TextView) holder.getView(R.id.noticeContentTv));
            if(textView != null){
                textView.setText(new HtmlSpanner().fromHtml(mDatas.get(position)));
            }
//            TextView textView = (TextView) holder.itemView.findViewById(R.id.noticeContentTv);
//            textView.setText(new HtmlSpanner().fromHtml(mDatas.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
