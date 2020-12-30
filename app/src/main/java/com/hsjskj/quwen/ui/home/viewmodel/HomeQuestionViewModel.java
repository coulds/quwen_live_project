package com.hsjskj.quwen.ui.home.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseViewModel;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.ui.home.repository.HomeQuestionRepository;
import com.hsjskj.quwen.ui.home.repository.HomeVideoRepository;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class HomeQuestionViewModel extends BaseViewModel<HomeQuestionRepository> {

    public HomeQuestionViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<HomePublishBean.DataBean> getLiveDataDetails() {
        return repository.getLiveDataDetails();
    }

    public void loadDataDetails(LifecycleOwner lifecycleOwner, String id) {
        repository.loadPublishInfo(lifecycleOwner,id);
    }

}
