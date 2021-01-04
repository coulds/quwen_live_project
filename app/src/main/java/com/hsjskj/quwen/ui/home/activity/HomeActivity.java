package com.hsjskj.quwen.ui.home.activity;

import android.view.KeyEvent;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.hjq.base.BaseFragmentAdapter;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.event.UserInfoUpgradeEvent;
import com.hsjskj.quwen.helper.ActivityStackManager;
import com.hsjskj.quwen.helper.DoubleClickHelper;
import com.hsjskj.quwen.other.KeyboardWatcher;
import com.hsjskj.quwen.ui.dialog.WaitDialog;
import com.hsjskj.quwen.ui.home.fragment.HomeFragment;
import com.hsjskj.quwen.ui.home.fragment.HomeLiveFragment;
import com.hsjskj.quwen.ui.home.fragment.MeFragment;
import com.hsjskj.quwen.ui.home.fragment.MessageFragment;
import com.hsjskj.quwen.ui.home.fragment.ToAskFragment;
import com.hsjskj.quwen.ui.home.widget.HomeBottomNavigationView;
import com.hsjskj.quwen.ui.user.viewmodel.UserPreviewViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author : Jun
 * time   : 2020年12月24日15:50:05
 * desc   : 主页界面
 */
public final class HomeActivity extends MyActivity
        implements KeyboardWatcher.SoftKeyboardStateListener, HomeBottomNavigationView.ItemSelectedListener {

    private ViewPager mViewPager;
    private HomeBottomNavigationView mBottomNavigationView;
    private UserPreviewViewModel mViewModel;

    private BaseFragmentAdapter<MyFragment> mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_home_pager);
        mBottomNavigationView = findViewById(R.id.bv_home_navigation);

        // 不使用图标默认变色
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        KeyboardWatcher.with(this)
                .setListener(this);
        mViewModel = new ViewModelProvider(this).get(UserPreviewViewModel.class);
        mViewModel.getCurrentUserInfoLiveData().observeForever(userInfoBean -> {
            MyUserInfo.getInstance().setLogin(userInfoBean);
        });
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpgradeUserInfoEvent(UserInfoUpgradeEvent event) {
        //需要更新用户信息,防止频繁调用
        postDelayed(() -> {
            if (!isFinishing()) {
                mViewModel.loadUserInfoLiveData(this, MyUserInfo.getInstance().getId());
            }
        }, 200);
    }

    @Override
    protected void initData() {
        mPagerAdapter = new BaseFragmentAdapter<>(this);
        mPagerAdapter.addFragment(HomeFragment.newInstance());
        mPagerAdapter.addFragment(ToAskFragment.newInstance());
        mPagerAdapter.addFragment(HomeLiveFragment.newInstance());
        mPagerAdapter.addFragment(MessageFragment.newInstance());
        mPagerAdapter.addFragment(MeFragment.newInstance());
        // 设置成懒加载模式
        mPagerAdapter.setLazyMode(true);
        mViewPager.setAdapter(mPagerAdapter);
    }

    /**
     * {@link KeyboardWatcher.SoftKeyboardStateListener}
     */
    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        mBottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onSoftKeyboardClosed() {
        mBottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 回调当前 Fragment 的 onKeyDown 方法
        if (mPagerAdapter.getShowFragment().onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            // 移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            postDelayed(() -> {

                // 进行内存优化，销毁掉所有的界面
                ActivityStackManager.getInstance().finishAllActivities();
                // 销毁进程（注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
                // System.exit(0);

            }, 300);
        } else {
            toast(R.string.home_exit_hint);
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onDestroy();
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(int position) {
        mViewPager.setCurrentItem(position);
        return false;
    }
}