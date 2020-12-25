package com.hsjskj.quwen.ui.home.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.ui.home.adapter.HomeLiveAdapter;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月25日11:16:36
 * description   : quwen_live
 */
public class HomeLiveView extends LinearLayout {

    private RecyclerView recyclerView;
    private HomeLiveAdapter adapter;

    public HomeLiveView(Context context) {
        this(context, null);
    }

    public HomeLiveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeLiveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(context).inflate(R.layout.layout_home_live_view, this);
        initView();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new HomeLiveAdapter(getContext());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = dp2px(5);
                //因为是GridLayoutManager所以这里写死
                int layoutPosition = parent.getChildLayoutPosition(view);
                if (layoutPosition % 2 == 0) {
                    outRect.right = dp2px(5);
                }else {
                    outRect.left = dp2px(5);
                }
            }
        });
        adapter.setOnItemClickListener((recyclerView, itemView, position) -> {
            if (listener != null) {
                listener.onItemLiveClick(position);
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setData(List<Object> datas) {
        adapter.setData(datas);
    }

    private HomeVideoViewListener listener;

    public void setListener(HomeVideoViewListener listener) {
        this.listener = listener;
    }

    public interface HomeVideoViewListener {
        void onItemLiveClick(int index);
    }

    public int dp2px(float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getContext().getResources().getDisplayMetrics()) + 0.5f);
    }
}
