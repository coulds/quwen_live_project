package com.hsjskj.quwen.ui.home.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:21
 * description   : AndroidProject
 */
public class HomeQuestionRepository extends BaseRepository {
    private MutableLiveData<Object> mLiveDataDetails;

    public LiveData<Object> getLiveDataDetails() {
        if (mLiveDataDetails == null) {
            mLiveDataDetails = new MutableLiveData<>();
        }
        return mLiveDataDetails;
    }

    ThreadPoolExecutor executorService;


    /**
     * 模拟网络请求
     */
    public void getDataDetails() {
        //线程池创建
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), sThreadFactory);
        }
        //设置当前请求为加载中
        getLiveDataStatus().postValue(STATUS_LOADING);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // 假设此处是耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mLiveDataDetails.postValue(new Object());
                //设置当前状态为加载结束
                getLiveDataStatus().postValue(STATUS_LOAD_END);
            }
        });
    }

    @Override
    public void clear() {
        super.clear();
        if (executorService != null) {
            executorService.shutdownNow();
            Log.e(TAG, "clear_" + Thread.currentThread().getName() + "_" + System.currentTimeMillis());
        }
    }

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "多线程");
        }
    };
}
