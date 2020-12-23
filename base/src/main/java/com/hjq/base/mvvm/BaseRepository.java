package com.hjq.base.mvvm;

import androidx.lifecycle.MutableLiveData;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:19
 * description   : AndroidProject
 */
public class BaseRepository {

    /**
     * 当前仓库加载状态
     */
    public static final int STATUS_DEFAULT = 0;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_LOAD_ERROR = 2;
    public static final int STATUS_LOAD_END = 3;
    public static final int STATUS_LOAD_END_EMPTY = 4;

    /**
     * tag标签，，用于日志打印，网络请求取消
     */
    public final String TAG = getClass().toString();


    private MutableLiveData<Integer> liveDataStatus = new MutableLiveData<>();

    public MutableLiveData<Integer> getLiveDataStatus() {
        if (liveDataStatus == null) {
            liveDataStatus = new MutableLiveData<>();
        }
        return liveDataStatus;
    }


    protected void clear() {
        liveDataStatus.postValue(STATUS_DEFAULT);
    }
}
