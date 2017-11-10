package com.llb.subway.common;

import android.view.View;

/**
 * recyclerView用的
 * Created by Derrick on 2017/11/10.
 */

public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
