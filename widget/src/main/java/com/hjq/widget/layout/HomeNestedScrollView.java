package com.hjq.widget.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author : Jun
 * time          : 2020年12月14日 14:06
 * description   : 解决事件冲突
 */
public class HomeNestedScrollView extends NestedScrollView {

    private static final String TAG = "HomeNestedScrollView";

    ViewGroup content;
    View topView;

    private int velocityY = 0; // 记录当前滑动的Y轴速度
    private boolean isStartFling = false; // 判断 recyclerView 是否在 fling
    private FlingHelper flingHelper;
    private int totalDy = 0;

    public HomeNestedScrollView(@NonNull Context context) {
        super(context);
        init();
    }

    public HomeNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        flingHelper = new FlingHelper(getContext());
        setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (isStartFling) {
                    totalDy = 0;
                    isStartFling = false;
                }
                if (scrollY == 0) {
                    Log.i(TAG, "onScrollChange: top scroll");
                }
                if (scrollY == (getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.i(TAG, "onScrollChange: bottom scroll");
                    dispatchChildFling();
                }
                totalDy += scrollY - oldScrollY;
            }
        });
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(target, dx, dy, consumed, type);
        Log.e("onNestedPreScroll", "");
        boolean b = dy > 0 && getScrollY() < topView.getMeasuredHeight();
        if (b) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    private void dispatchChildFling() {
        if (this.velocityY != 0) {
            double splineFlingDistance = flingHelper.getSplineFlingDistance(velocityY);
            if (splineFlingDistance > totalDy) {
                childFling(flingHelper.getVelocityByDistance(splineFlingDistance - Double.valueOf(totalDy)));
            }
        }
        totalDy = 0;
        velocityY = 0;
    }

    private void childFling(int y){
        RecyclerView recyclerView = getChildRecyclerView(content);
        if (recyclerView != null) {
            recyclerView.fling(0, y);
        }
    }

    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RecyclerView) {
                return (RecyclerView) viewGroup.getChildAt(i);
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                ViewGroup childRecyclerView = getChildRecyclerView((ViewGroup) viewGroup.getChildAt(i));
                if (childRecyclerView instanceof RecyclerView) {
                    return (RecyclerView) childRecyclerView;
                }
            }
            continue;
        }

        return null;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams lp = content.getLayoutParams();
        lp.height = getMeasuredHeight();
        content.setLayoutParams(lp);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = ((ViewGroup) getChildAt(0)).getChildAt(0);
        content = (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(1);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY <= 0) {
            this.velocityY = 0;
        }else {
            isStartFling = true;
            this.velocityY = velocityY;
        }
    }

}
