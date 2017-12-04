package com.dream.llb.subway.common.htmlspanner.handlers;

import android.text.SpannableStringBuilder;

import net.nightwhistler.htmlspanner.SpanStack;
import net.nightwhistler.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

/**
 * Created by llb on 2017-11-18.
 */

public class MyLinkHandler extends TagNodeHandler {
    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder,
                              int start, int end, SpanStack spanStack) {

        final String href = node.getAttributeByName("href");
        spanStack.pushSpan(new MyURLSpan(href), start, end);
    }
}
