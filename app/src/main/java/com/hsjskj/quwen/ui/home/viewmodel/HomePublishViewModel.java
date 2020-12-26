package com.hsjskj.quwen.ui.home.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseViewModel;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.ui.copy.CopyRepository;
import com.hsjskj.quwen.ui.home.repository.HomePublishRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class HomePublishViewModel extends BaseViewModel<HomePublishRepository> {

    public HomePublishViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Object> submitPublish(String title, String content, List<Object> list) {
        MutableLiveData<Object> mutableLiveData = new MutableLiveData<>();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.show(R.string.home_please_enter_the_title_to_be_published);
            return mutableLiveData;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show(R.string.home_please_enter_what_needs_to_be_published);
            return mutableLiveData;
        }
        if (list.size() <= 1) {
            ToastUtils.show(R.string.home_please_select_the_picture_to_be_published);
            return mutableLiveData;
        }

        //图片真实路径
        ArrayList<Object> objects = new ArrayList<>(list.subList(1, list.size()));
        return mutableLiveData;
    }
}
