package com.hsjskj.quwen.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.aop.CheckNet;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.response.UserInfoBean;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.ui.home.widget.StarTagView;
import com.hsjskj.quwen.ui.user.viewmodel.UserPreviewViewModel;
import com.hsjskj.quwen.ui.user.widget.UserPerviewAnchorView;
import com.hsjskj.quwen.ui.user.widget.UserPerviewTagView;
import com.hsjskj.quwen.widget.HintLayout;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月26日 15:17
 * description   : 用户信息 这里区分主播和非主播身份
 */
public class UserPreviewActivity extends MyMvvmActivity<UserPreviewViewModel> implements StatusAction {

    private NestedScrollView scrollView;
    private View llFollow;
    private TextView tvFollow;
    private TextView tvNickname;
    private TextView tvFollowNumber;
    private TextView tvFansNumber;
    private ImageView followIcon;
    private ImageView ivItemAvatar;
    private FlowLayout flowLayoutTag;
    private StarTagView starTag;
    private StarTagView starLv;
    private UserPerviewAnchorView userAnchorView;
    private int height = 80;

    //当前是否已关注
    private boolean isFollow = false;
    private boolean isSelf = false;

    public static void start(Context context, String userId) {
        Intent intent = new Intent(context, UserPreviewActivity.class);
        intent.putExtra(IntentKey.USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_user;
    }

    @Override
    protected void initView() {
        super.initView();
        initViewScrollView();
        tvFollow = findViewById(R.id.tv_follow);
        llFollow = findViewById(R.id.ll_follow);
        flowLayoutTag = findViewById(R.id.flowLayoutTag);
        followIcon = findViewById(R.id.follow_icon);
        userAnchorView = findViewById(R.id.user_anchor_view);
        tvFollowNumber = findViewById(R.id.tv_follow_number);
        tvFansNumber = findViewById(R.id.tv_fans_number);
        starLv = findViewById(R.id.star_lv);
        starTag = findViewById(R.id.star_tag);
        tvNickname = findViewById(R.id.tv_nickname);
        ivItemAvatar = findViewById(R.id.iv_item_avatar);
        setOnClickListener(llFollow);
    }

    @CheckNet
    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == llFollow) {
            mViewModel.loadFollowUserInfoLiveData(this, getString(IntentKey.USER_ID));
        }
    }

    public void getHttpData() {
        mViewModel.loadUserInfoLiveData(this, getString(IntentKey.USER_ID));
    }

    @Override
    protected void initData() {
        mViewModel.getFollowUserInfoLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                isFollow = !isFollow;
            }
            upgradeStatusFollow();
        });
        mViewModel.getUserInfoLiveData().observe(this, userInfoBean -> {
            if (userInfoBean != null) {
                showComplete();
                upgradeUserInfo(userInfoBean);
            } else {
                showError(v -> getHttpData());
            }
        });
        //如果是查看自己 隐藏关注按钮 和底部预约和提问按钮
        isSelf = MyUserInfo.getInstance().getId().equals(getString(IntentKey.USER_ID));
        if (isSelf) {
            llFollow.setVisibility(View.GONE);
            findViewById(R.id.ll_bottom_btn).setVisibility(View.GONE);
        }
        flowLayoutTag.removeAllViews();
        showLoading();
        getHttpData();
    }

    private void upgradeUserInfo(UserInfoBean userInfoBean) {
        if (isSelf) {
            MyUserInfo.getInstance().setLogin(userInfoBean);
        }

        flowLayoutTag.removeAllViews();
        //用户信息
        tvNickname.setText(userInfoBean.user_nickname);
        GlideApp.with(this).load(userInfoBean.avatar).into(ivItemAvatar);
        starLv.setTagText("LV" + userInfoBean.level, true, false);
        starTag.setTagText(userInfoBean.constellation, userInfoBean.isMale(), userInfoBean.isSetMale());
        tvFansNumber.setText("" + userInfoBean.attention_to_me_count);
        tvFollowNumber.setText("" + userInfoBean.i_attention_count);
        isFollow = userInfoBean.isFollow();
        upgradeStatusFollow();
//        if (!userInfoBean.isAnchor()) {
//            userAnchorView.setVisibility(View.GONE);
//            return;
//        }
        //主播信息
        userAnchorView.setVisibility(View.VISIBLE);
        userAnchorView.setUserInfo(userInfoBean);
        //用户标签
        if (userInfoBean.label != null) {
            List<String> label = userInfoBean.label;
            ViewGroup.MarginLayoutParams m = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            m.setMargins(0, 0, UiUtlis.dp2px(getContext(), 8), UiUtlis.dp2px(getContext(), 8));
            for (int i = 0; i < label.size(); i++) {
                UserPerviewTagView tagView = new UserPerviewTagView(getContext());
                tagView.setLayoutParams(m);
                tagView.setTextContent("" + label.get(i));
                flowLayoutTag.addView(tagView);
            }
        }
    }

    private void upgradeStatusFollow() {
        if (isFollow) {
            followIcon.setImageResource(R.drawable.user_info_icon_follow_2);
            tvFollow.setText("已关注");
        } else {
            followIcon.setImageResource(R.drawable.user_info_icon_follow);
            tvFollow.setText("关注");
        }
    }

    private void initViewScrollView() {
//        scrollView = findViewById(R.id.nestedScrollView);
//        scrollView.post(() -> {
//            if (getTitleBar() != null) {
//                height = findViewById(R.id.iv_bg).getMeasuredHeight() * 2 / 3;
//            }
//        });
//        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (scrollY > (height / 2)) {
//                setLeftIcon(R.drawable.arrows_left_ic);
//            } else {
//                setLeftIcon(R.drawable.arrow_back_fff);
//            }
//            if (scrollY >= height) {
//                setTitleBarColor(Color.WHITE);
//            } else if (scrollY >= 0) {
//                float persent = scrollY * 1f / (height);
//                int alpha = (int) (255 * persent);
//                int color = Color.argb(alpha, 255, 255, 255);
//                setTitleBarColor(color);
//            }
//        });
    }

    public void setTitleBarColor(int color) {
        if (getTitleBar() != null) {
            getTitleBar().setBackgroundColor(color);
        }
    }

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hint_layout);
    }
}
