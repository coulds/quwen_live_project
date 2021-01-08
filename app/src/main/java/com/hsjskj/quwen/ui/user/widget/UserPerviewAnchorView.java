package com.hsjskj.quwen.ui.user.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.response.UserInfoBean;
import com.hsjskj.quwen.ui.user.activity.EvaluationActivity;

import java.util.ArrayList;
import java.util.List;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;


/**
 * @author : Jun
 * time          : 2020年12月26日 16:40
 * description   : quwen_live
 */
public class UserPerviewAnchorView extends FrameLayout implements TabLayout.OnTabSelectedListener {


    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private TextView tvCommentNumber;
    private List<UserInfoBean> list;
    private RecyclerViewAdapter mAdapter;
    private LinearLayout ll_click_more;
    private ViewPager2.OnPageChangeCallback mCallback = new ViewPager2.OnPageChangeCallback() {

        private int mPreviousScrollState, mScrollState = SCROLL_STATE_IDLE;

        @Override
        public void onPageScrollStateChanged(int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
            if (state == ViewPager2.SCROLL_STATE_IDLE && mTabLayout.getSelectedTabPosition() != mViewPager.getCurrentItem()) {
                final boolean updateIndicator = mScrollState == SCROLL_STATE_IDLE || (mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE);
                mTabLayout.selectTab(mTabLayout.getTabAt(mViewPager.getCurrentItem()), updateIndicator);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            final boolean updateText = mScrollState != SCROLL_STATE_SETTLING || mPreviousScrollState == SCROLL_STATE_DRAGGING;
            final boolean updateIndicator = !(mScrollState == SCROLL_STATE_SETTLING && mPreviousScrollState == SCROLL_STATE_IDLE);
            mTabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
        }
    };

    public UserPerviewAnchorView(@NonNull Context context) {
        this(context, null);
    }

    public UserPerviewAnchorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserPerviewAnchorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_user_anchor_view, this);
        mTabLayout = findViewById(R.id.tb_user_info_tab);
        mViewPager = findViewById(R.id.vp_user_info_page);
        ll_click_more = findViewById(R.id.ll_click_more);//
        tvCommentNumber = findViewById(R.id.tv_comment_number);
        list = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(context);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.addTab(mTabLayout.newTab().setText("关于TA"), true);
//        mTabLayout.addTab(mTabLayout.newTab().setText("评价(0)"));
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.registerOnPageChangeCallback(mCallback);
        ll_click_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EvaluationActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mViewPager != null) {
            mViewPager.unregisterOnPageChangeCallback(mCallback);
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (mViewPager.getCurrentItem() != tab.getPosition()) {
            mViewPager.setCurrentItem(tab.getPosition());
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void setUserInfo(UserInfoBean bean) {
        list.clear();
        list.add(bean);
        mAdapter.setData(list);
        tvCommentNumber.setText("评价(" + bean.comments_count + ")");
    }


    private final static class RecyclerViewAdapter extends MyAdapter<UserInfoBean> {

        private RecyclerViewAdapter(Context context) {
            super(context);
        }

        public static final int ADAPTER_LEFT = 1;
        public static final int ADAPTER_RIGHT = 2;

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return ADAPTER_LEFT;
            } else if (position == 1) {
                return ADAPTER_RIGHT;
            } else {
                return super.getItemViewType(position);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            switch (viewType) {
                case ADAPTER_LEFT:
                    return new ViewHolderLeft();
                case ADAPTER_RIGHT:
                    return new ViewHolderRight();
                default:
                    return null;
            }

        }

        private final class ViewHolderLeft extends MyAdapter.ViewHolder {
            TextView tv_career_experience;
            TextView tv_live_time;
            TextView tv_live_lv;
            TextView tv_exprience;
            TextView tv_price;

            ViewHolderLeft() {
                super(R.layout.item_layout_user_info_about_view);
                tv_career_experience = (TextView) findViewById(R.id.tv_career_experience);
                tv_live_time = (TextView) findViewById(R.id.tv_live_time);
                tv_live_lv = (TextView) findViewById(R.id.tv_live_lv);
                tv_exprience = (TextView) findViewById(R.id.tv_exprience);
                tv_price = (TextView) findViewById(R.id.tv_price);

            }

            @Override
            public void onBindView(int position) {
                UserInfoBean item = getItem(position);
                if (TextUtils.isEmpty(item.professional)) {
                    tv_career_experience.setText("无");
                } else {
                    tv_career_experience.setText("" + item.professional);
                }
                tv_live_time.setText("开播时间：工作日" + item.show_time + ",期待您的到来");
                tv_live_lv.setText("LV" + item.level);

                tv_exprience.setText("" + item.exprience);
                tv_price.setText("￥" + item.price);
            }
        }

        private final class ViewHolderRight extends MyAdapter.ViewHolder {
            ViewHolderRight() {
                super(R.layout.item_layout_user_info_comment_view);
            }

            @Override
            public void onBindView(int position) {
            }
        }
    }
}
