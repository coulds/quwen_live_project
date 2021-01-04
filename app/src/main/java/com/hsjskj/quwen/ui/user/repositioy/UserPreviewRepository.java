package com.hsjskj.quwen.ui.user.repositioy;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseRepository;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.UserFollowApi;
import com.hsjskj.quwen.http.request.UserInfoApi;
import com.hsjskj.quwen.http.response.UserInfoBean;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:21
 * description   : 用户信息 预览
 */
public class UserPreviewRepository extends BaseRepository {

    @Override
    public void clear() {
        super.clear();
    }

    private static MutableLiveData<UserInfoBean> currentUserInfoLiveData = new MutableLiveData<>();

    public static MutableLiveData<UserInfoBean> getCurrentUserInfoLiveData() {
        return currentUserInfoLiveData;
    }

    public void loadCurrentUserInfoLiveData(LifecycleOwner lifecycleOwner) {
        EasyHttp.post(lifecycleOwner)
                .api("User/myInfo")
                .request(new HttpCallback<HttpData<UserInfoBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<UserInfoBean> data) {
                        currentUserInfoLiveData.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtils.show(e.getMessage());
                    }
                });
    }

    private MutableLiveData<UserInfoBean> userInfoLiveData;

    public MutableLiveData<UserInfoBean> getUserInfoLiveData() {
        if (userInfoLiveData == null) {
            userInfoLiveData = new MutableLiveData<>();
        }
        return userInfoLiveData;
    }

    public void loadUserInfoLiveData(LifecycleOwner lifecycleOwner, String toUid) {
        EasyHttp.post(lifecycleOwner)
                .api(new UserInfoApi(toUid))
                .request(new HttpCallback<HttpData<UserInfoBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<UserInfoBean> data) {
                        userInfoLiveData.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtils.show(e.getMessage());
                        userInfoLiveData.postValue(null);
                    }
                });
    }

    private MutableLiveData<Boolean> followUserInfoLiveData;

    public MutableLiveData<Boolean> getFollowUserInfoLiveData() {
        if (followUserInfoLiveData == null) {
            followUserInfoLiveData = new MutableLiveData<>();
        }
        return followUserInfoLiveData;
    }

    /**
     * 关注和取消关注，返回操作是否成功
     */
    public void loadFollowUserInfoLiveData(LifecycleOwner lifecycleOwner, String toUid) {
        EasyHttp.post(lifecycleOwner)
                .api(new UserFollowApi(toUid))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        ToastUtils.show(data.getMessage());
                        followUserInfoLiveData.postValue(true);
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtils.show(e.getMessage());
                        followUserInfoLiveData.postValue(false);
                    }
                });
    }
}
