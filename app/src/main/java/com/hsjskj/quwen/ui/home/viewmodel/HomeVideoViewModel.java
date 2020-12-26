package com.hsjskj.quwen.ui.home.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.hjq.base.mvvm.BaseViewModel;
import com.hsjskj.quwen.ui.copy.CopyRepository;
import com.hsjskj.quwen.ui.home.repository.HomeVideoRepository;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class HomeVideoViewModel extends BaseViewModel<HomeVideoRepository> {

    public HomeVideoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Object>> getLiveDataVideoList() {
        return repository.getLiveDataVideoList();
    }

    public void loadDataVideoList(int page, int pageSize) {
        repository.getDataVideoList(page, pageSize);
    }

}
