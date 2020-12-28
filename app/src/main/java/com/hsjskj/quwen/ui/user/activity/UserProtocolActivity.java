package com.hsjskj.quwen.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;


import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.aop.CheckNet;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.ui.user.viewmodel.UserProctocolModel;
import com.hsjskj.quwen.widget.BrowserView;
import com.hsjskj.quwen.widget.HintLayout;

/**
 * @author : Jun
 * time          : 2020年12月28日 13:23
 * description   : quwen_live
 */
public class UserProtocolActivity extends MyMvvmActivity<UserProctocolModel> implements StatusAction {

    public static final int ID_REGISTER = 1;
    public static final int ID_SECRECY = 2;
    public static final int ID_NOTICE = -1;

    /**
     * id 1注册协议 2隐私政策 3推广规则 4主播签约协议 5直播规则 6抢答规则
     */
    public static void start(Context context, int id) {
        Intent intent = new Intent(context, UserProtocolActivity.class);
        intent.putExtra(IntentKey.ID, id);
        context.startActivity(intent);
    }

    /**
     * 首页咨询详情
     */
    public static void start(Context context, int type, String noticeId) {
        Intent intent = new Intent(context, UserProtocolActivity.class);
        intent.putExtra(IntentKey.ID, type);
        intent.putExtra(IntentKey.NOTICE_ID, noticeId);
        context.startActivity(intent);
    }

    private HintLayout mHintLayout;
    private BrowserView mBrowserView;

    @Override
    protected void initView() {
        super.initView();
        mHintLayout = findViewById(R.id.hl_browser_hint);
        mBrowserView = findViewById(R.id.wv_browser_view);

        mViewModel.getProtocolBean().observe(this, status -> {
            if (status == null) {
                showError(v -> loadHttp());
                return;
            } else {
                showComplete();
                mBrowserView.loadDataWithBaseURL("", addHtml(status.content), "text/html", "UTF-8", "");
            }
            if (!isNotice()) {
                setTitle(mViewModel.getProtocolTitle(this, getInt(IntentKey.ID)));
            } else {
                setTitle("" + status.title);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_protocol_user;
    }

    @Override
    protected void initData() {
        showLoading();
        loadHttp();
    }

    private void loadHttp() {
        if (isNotice()) {
            mViewModel.loadNoticeData(this, getString(IntentKey.NOTICE_ID));
        } else {
            mViewModel.loadData(this, String.valueOf(getInt(IntentKey.ID)));
        }
    }

    private boolean isNotice() {
        return -1 == getInt(IntentKey.ID);
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onLeftClick(View v) {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mBrowserView.canGoBack()) {
            // 后退网页并且拦截该事件
            mBrowserView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        mBrowserView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mBrowserView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBrowserView.onDestroy();
        super.onDestroy();
    }

    /**
     * 重新加载当前页
     */
    @CheckNet
    private void reload() {
        mBrowserView.reload();
    }

    public String addHtml(String content) {
        String head = "<head><meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no\" />" +
                "<style>* {font-size:15px;padding:0;} img{max-width: 100%; width:auto; height: auto;} video{max-width: 100%; width:100%; height: auto;}</style></head>";
        return "<html>" + head + "<body style=\"word-wrap:break-word;\">" + content + "</body></html>";
    }

}
