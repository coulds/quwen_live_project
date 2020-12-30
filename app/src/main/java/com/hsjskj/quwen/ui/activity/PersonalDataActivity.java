package com.hsjskj.quwen.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.UpdateImageApi;
import com.hsjskj.quwen.ui.dialog.AddressDialog;
import com.hsjskj.quwen.ui.dialog.DateDialog;
import com.hsjskj.quwen.ui.dialog.InputDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hsjskj.quwen.ui.dialog.SelectDialog;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/04/20
 * desc   : 个人资料
 */
public final class PersonalDataActivity extends MyActivity {

    private ViewGroup mAvatarLayout;
    private ImageView mAvatarView;
    private SettingBar mIDView;
    private SettingBar mNameView;
    private SettingBar mAddressView;

    /**
     * 省
     */
    private String mProvince = "广东省";
    /**
     * 市
     */
    private String mCity = "广州市";
    /**
     * 区
     */
    private String mArea = "天河区";

    /**
     * 头像地址
     */
    private String mAvatarUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.personal_data_activity;
    }

    @Override
    protected void initView() {
        mAvatarLayout = findViewById(R.id.fl_person_data_avatar);
        mAvatarView = findViewById(R.id.iv_person_data_avatar);
        mIDView = findViewById(R.id.sb_person_data_id);
        mNameView = findViewById(R.id.sb_person_data_name);
        mAddressView = (SettingBar) findViewById(R.id.sb_person_data_address);
        setOnClickListener(this.mAvatarLayout, this.mAvatarView, this.mNameView, mAddressView, this.mIDView);
    }

    @Override
    protected void initData() {
        GlideApp.with(getActivity())
                .load(R.drawable.avatar_placeholder_ic)
                .placeholder(R.drawable.avatar_placeholder_ic)
                .error(R.drawable.avatar_placeholder_ic)
                .circleCrop()
                .into(mAvatarView);

        mAddressView.setRightText("1999年12月30日");
//        GlideApp.with(getContext()).load(MyUserInfo.getInstance().getLogin().avatar).into(mAvatarView);
        mIDView.setRightText(MyUserInfo.
                getInstance().getLogin().getUsername());
        mNameView.setRightText(MyUserInfo.getInstance().getLogin().isSexMale() ? "男" : "女");
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
                    GlideApp.with(getActivity())
                            .load(data.get(0))
                            .placeholder(R.drawable.avatar_placeholder_ic)
                            .error(R.drawable.avatar_placeholder_ic)
                            .circleCrop()
                            .into(mAvatarView);
                }
            });
        } else if (v == mAvatarView) {
            if (!TextUtils.isEmpty(mAvatarUrl)) {
                ImagePreviewActivity.start(getActivity(), mAvatarUrl);
            } else {
                onClick(mAvatarLayout);
            }
        } else if (v == mNameView) {
            ((SelectDialog.Builder) new SelectDialog.Builder(this)
                    .setTitle("请选择你的性别"))
                    .setList("男", "女")
                    .setSingleSelect()
                    .setSelect(MyUserInfo.getInstance().getLogin().isSexMale() ? 0 : 1)
                    .setListener(new SelectDialog.OnListener<String>() {
                        @Override
                        public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                            PersonalDataActivity personalDataActivity = PersonalDataActivity.this;
                            personalDataActivity.toast((CharSequence) ("确定了：" + data.toString()));
                            for (Integer integer : data.keySet()) {
                                PersonalDataActivity.this.mNameView.setRightText(data.get(integer));
                            }
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                            PersonalDataActivity.this.toast((CharSequence) "取消了");
                        }
                    }).show();
        } else if (v == mAddressView) {
            ((DateDialog.Builder) ((DateDialog.Builder) ((DateDialog.Builder) new DateDialog.Builder(this).setTitle(getString(R.string.date_title))).setConfirm(getString(R.string.common_confirm))).setCancel(getString(R.string.common_cancel))).setListener(new DateDialog.OnListener() {

                @Override
                public void onSelected(BaseDialog dialog, int year, int month, int day) {
                    PersonalDataActivity.this.toast((CharSequence) (year + PersonalDataActivity.this.getString(R.string.common_year) + month + PersonalDataActivity.this.getString(R.string.common_month) + day + PersonalDataActivity.this.getString(R.string.common_day)));
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(1, year);
                    calendar.set(2, month - 1);
                    calendar.set(5, day);
                    PersonalDataActivity.this.toast((CharSequence) ("时间戳：" + calendar.getTimeInMillis()));
                    PersonalDataActivity.this.mAddressView.setRightText(year + PersonalDataActivity.this.getString(R.string.common_year) + month + PersonalDataActivity.this.getString(R.string.common_month) + day + PersonalDataActivity.this.getString(R.string.common_day));
                }

                @Override
                public void onCancel(BaseDialog dialog) {
                    PersonalDataActivity.this.toast((CharSequence) "取消了");
                }
            }).show();
        }

    }
}