package com.hjq.base.mvvm;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class BaseViewModel<T extends BaseRepository> extends AndroidViewModel {
    protected Context context;

    protected T repository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        context = application;
        repository = TUtil.getInstance(this, 0);
    }

    public LiveData<Integer> getLiveDataStatus() {
        return repository.getLiveDataStatus();
    }

    @Override
    protected void onCleared() {
        repository.clear();
        super.onCleared();
    }
}
