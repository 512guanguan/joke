package com.dream.llb.subway.view.edit_post;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by llb on 2018-04-05.
 */

public class SimpleArrayAdapter extends ArrayAdapter {
    public SimpleArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public SimpleArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
