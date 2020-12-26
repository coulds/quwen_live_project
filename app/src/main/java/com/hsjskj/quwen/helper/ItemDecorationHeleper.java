package com.hsjskj.quwen.helper;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author : Jun
 * time          : 2020年12月26日 10:16
 * description   : quwen_live
 */
public class ItemDecorationHeleper {
    /**
     * 视频列表添加间距
     *
     * @param mRecyclerview
     * @param space         间距
     * @param column        多少列
     */
    public static void addVideoItemDecoration(RecyclerView mRecyclerview, int space, int column) {
        mRecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int layoutPosition = parent.getChildLayoutPosition(view);
                if (layoutPosition % column == 0) {
                    //左侧
                    outRect.left = 0;
                } else {
                    //右侧
                    outRect.left = space;
                }
                outRect.top = space;
                outRect.bottom = space;
            }
        });
    }
}
