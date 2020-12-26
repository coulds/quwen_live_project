package com.hsjskj.quwen.ui.user.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.hjq.base.mvvm.BaseViewModel;
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


}
