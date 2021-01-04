package com.hsjskj.quwen.ui.user.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseViewModel;
import com.hsjskj.quwen.http.response.UserInfoBean;
import com.hsjskj.quwen.ui.user.repositioy.UserPreviewRepository;


/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class UserPreviewViewModel extends BaseViewModel<UserPreviewRepository> {

    public UserPreviewViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UserInfoBean> getCurrentUserInfoLiveData() {
        return UserPreviewRepository.getCurrentUserInfoLiveData();
    }

    public MutableLiveData<UserInfoBean> getUserInfoLiveData() {
        return repository.getUserInfoLiveData();
    }

    public void loadUserInfoLiveData(LifecycleOwner lifecycleOwner, String toUid) {
        repository.loadUserInfoLiveData(lifecycleOwner, toUid);
    }


    public MutableLiveData<Boolean> getFollowUserInfoLiveData() {
        return repository.getFollowUserInfoLiveData();
    }

    public void loadFollowUserInfoLiveData(LifecycleOwner lifecycleOwner, String toUid) {
        repository.loadFollowUserInfoLiveData(lifecycleOwner, toUid);
    }

}
