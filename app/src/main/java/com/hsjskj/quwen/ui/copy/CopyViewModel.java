package com.hsjskj.quwen.ui.copy;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.hjq.base.mvvm.BaseViewModel;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class CopyViewModel extends BaseViewModel<CopyRepository> {

    public CopyViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getLiveData1() {
        return repository.getLiveData1();
    }

    public LiveData<String> getLiveData2() {
        return repository.getLiveData2();
    }

    public void loadData1() {
        repository.getData1();
    }

    public void loadData2() {
        repository.getData2();
    }
}
