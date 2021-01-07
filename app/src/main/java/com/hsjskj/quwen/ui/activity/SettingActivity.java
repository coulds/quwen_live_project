package com.hsjskj.quwen.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hjq.base.BaseDialog;
import com.hjq.widget.layout.SettingBar;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.helper.ActivityStackManager;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.response.UserInfoBean;
import com.hsjskj.quwen.ui.dialog.DateDialog;
import com.hsjskj.quwen.ui.dialog.MessageDialog;
import com.hsjskj.quwen.ui.dialog.SelectDialog;
import com.hjq.widget.view.SwitchButton;
import com.hsjskj.quwen.ui.home.viewmodel.HomePublishViewModel;
import com.hsjskj.quwen.ui.my.activity.BankCardActivity;
import com.hsjskj.quwen.ui.user.activity.FeedBackHistoryActivity;
import com.hsjskj.quwen.ui.user.activity.PorblemFeedBackActivity;
import com.hsjskj.quwen.ui.user.activity.LoginActivity;
import com.hsjskj.quwen.ui.user.repositioy.UserPreviewRepository;
import com.hsjskj.quwen.ui.user.viewmodel.UserInfoViewModel;
import com.hsjskj.quwen.upload.UploadBean;
import com.hsjskj.quwen.upload.UploadCallback;
import com.hsjskj.quwen.upload.UploadTxImpl;

import java.util.HashMap;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/03/01
 * desc   : 设置界面
 */
public final class SettingActivity extends MyActivity
        implements SwitchButton.OnCheckedChangeListener {

    private ViewGroup mAvatarLayout;
    private ImageView mAvatarView;
    private SettingBar mIDView;
    private SettingBar mNameView;
    private SettingBar mAddressView;
    private SettingBar mproblemfeedView;

    private SettingBar mphoneView;
    private SettingBar memailView;
    private SettingBar mbindweixinView;
    private SettingBar msetpasswordViwe;
    private SettingBar maboutView;
    private SettingBar mwechatView;
    private SettingBar sb_bankCard;

    private TextView msb_tuichu_about;
    private UserInfoViewModel userInfoViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.setting_activity;
    }

    @Override
    protected void initView() {
        userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        mAvatarLayout = findViewById(R.id.sb_setting_language);
        mAvatarView = findViewById(R.id.iv_person_data_avatar);
        mIDView = findViewById(R.id.sb_setting_update);
        mNameView = findViewById(R.id.sb_setting_phone);

        mphoneView = findViewById(R.id.sb_phone_view);
        memailView = findViewById(R.id.sb_email_about);
        mbindweixinView = findViewById(R.id.sb_weixin_auto);
        msetpasswordViwe = findViewById(R.id.sb_setting_password_about);
        maboutView = findViewById(R.id.sb_guanyu_about);
        mwechatView =findViewById(R.id.sb_weixin_auto);
        sb_bankCard =findViewById(R.id.sb_bankCard);


        msb_tuichu_about = findViewById(R.id.sb_tuichu_about);
        mproblemfeedView = findViewById(R.id.sb_problem_feed_back_about);
        mAddressView = (SettingBar) findViewById(R.id.sb_setting_password);


        setOnClickListener(this.mAvatarLayout, this.mNameView, mAddressView, this.mIDView, this.msb_tuichu_about,this.mproblemfeedView
                ,this.mphoneView,this.memailView,this.mbindweixinView,this.msetpasswordViwe,this.maboutView,this.mwechatView
                ,sb_bankCard
        );

    }

    @Override
    protected void initData() {
        //监听用户信息变化
        UserPreviewRepository.getCurrentUserInfoLiveData().observe(this, this::setUserInfoView);
        userInfoViewModel.getUserInfoBean().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mAddressView.setRightText(s);
                UserInfoBean userInfoBean = MyUserInfo.getInstance().getLogin();
                userInfoBean.birthday = s;
                UserPreviewRepository.getCurrentUserInfoLiveData().postValue(userInfoBean);
            }
        });
        userInfoViewModel.getUserInfoAvatarBean().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                UserInfoBean userInfoBean = MyUserInfo.getInstance().getLogin();
                userInfoBean.avatar = s;
                UserPreviewRepository.getCurrentUserInfoLiveData().postValue(userInfoBean);
            }
        });
        userInfoViewModel.getUserInfoSexBean().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                UserInfoBean userInfoBean = MyUserInfo.getInstance().getLogin();
                userInfoBean.sex = "" + s;
                UserPreviewRepository.getCurrentUserInfoLiveData().postValue(userInfoBean);
                mNameView.setRightText(s != 2 ? "男" : "女");
            }

        });
        setUserInfoView(MyUserInfo.getInstance().getLogin());
    }

    private void setUserInfoView(UserInfoBean infoBean) {
        GlideApp.with(getActivity())
                .load(MyUserInfo.getInstance().getLogin().avatar)
                .placeholder(R.drawable.avatar_placeholder_ic)
                .error(R.drawable.avatar_placeholder_ic)
                .circleCrop()
                .into(mAvatarView);

        mAddressView.setRightText("" + infoBean.birthday);
        mIDView.setRightText(infoBean.getUsername());
        mNameView.setRightText(infoBean.isSetMale() ? infoBean.isSexMale() ? "男" : "女" : "未设置");
    }

    private void upAvatar(String path) {
        HomePublishViewModel viewModel = new ViewModelProvider(this).get(HomePublishViewModel.class);
        viewModel.getTxCosLiveBean().observe(this, txCosBean -> {
            if (txCosBean != null) {
                UploadTxImpl listener = new UploadTxImpl(getContext(), txCosBean);
                listener.upload(new UploadBean(path), new UploadCallback() {
                    @Override
                    public void onSuccess(UploadBean bean) {
                        userInfoViewModel.loadUserInfoAvatarBean(SettingActivity.this, bean.getFileName());
                    }

                    @Override
                    public void onFailure() {
                        toast("上传失败");
                    }
                });
            } else {
                toast("上传失败");
            }
        });
        viewModel.loadTxCos(this);
    }


    @SingleClick
    @Override
    public void onClick(View v) {


        if (v == mIDView) {
            startActivity(NickNameEditActivity.class);
        } else if (v == mAvatarLayout) {
            ImageSelectActivity.start(this, new ImageSelectActivity.OnPhotoSelectListener() {
                @Override
                public void onSelected(List<String> data) {
                    upAvatar(data.get(0));
                }
            });
        } else if (v == mNameView) {
            new SelectDialog.Builder(this)
                    .setTitle("请选择你的性别")
                    .setList("男", "女")
                    .setSingleSelect()
                    .setSelect(!MyUserInfo.getInstance().getLogin().isSexMale() ? 1 : 0)
                    .setListener(new SelectDialog.OnListener<String>() {
                        @Override
                        public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                            for (Integer integer : data.keySet()) {
                                userInfoViewModel.loadUserInfoSexBean(SettingActivity.this, integer + 1);
                            }
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                        }
                    }).show();
        } else if (v == mAddressView) {
            new DateDialog.Builder(this)
                    .setTitle(getString(R.string.date_title))
                    .setConfirm(getString(R.string.common_confirm))
                    .setCancel(getString(R.string.common_cancel))
                    .setListener(new DateDialog.OnListener() {
                        @Override
                        public void onSelected(BaseDialog dialog, int year, int month, int day) {
                            userInfoViewModel.loadUserInfoBean(SettingActivity.this, year + "-" + month + "-" + day);
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                        }
                    }).show();
        } else if (v == msb_tuichu_about) {
            new MessageDialog.Builder(this)
                    .setTitle("退出登录")
                    .setMessage("是否退出当前登录")
                    .setConfirm(getString(R.string.common_confirm))
                    .setCancel(getString(R.string.common_cancel))
                    .setListener(new MessageDialog.OnListener() {

                        @Override
                        public void onConfirm(BaseDialog dialog) {
                            startActivity(LoginActivity.class);
                            ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
                        }


                        @Override
                        public void onCancel(BaseDialog dialog) {
                        }
                    })
                    .show();
        }else if (v == mproblemfeedView){
            startActivity(FeedBackHistoryActivity.class);
        }else if (v == mphoneView){
            startActivity(SetPhoneCodeActivity.class);
        }else if (v == memailView){
            startActivity(EmailCodeActivity.class);
        }else if(v == msetpasswordViwe){
            startActivity(SetPassWordActivity.class);
        }else if (v == maboutView){
            startActivity(AboutQuWenActivity.class);
        }else if (v == mwechatView){
            startActivity(WeChatActivity.class);
        }else if(v==sb_bankCard){
            startActivity(BankCardActivity.class);
        }

    }

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }
}