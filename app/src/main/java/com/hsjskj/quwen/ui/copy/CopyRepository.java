package com.hsjskj.quwen.ui.copy;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseRepository;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:21
 * description   : AndroidProject
 */
public class CopyRepository extends BaseRepository {
    private MutableLiveData<Boolean> mLiveData1;
    private MutableLiveData<String> mLiveData2;

    public LiveData<Boolean> getLiveData1() {
        if (mLiveData1 == null) {
            mLiveData1 = new MutableLiveData<>();
        }
        return mLiveData1;
    }

    public LiveData<String> getLiveData2() {
        if (mLiveData2 == null) {
            mLiveData2 = new MutableLiveData<>();
        }
        return mLiveData2;
    }

    ThreadPoolExecutor executorService;

    public void getData1() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(), sThreadFactory);
        }
        getLiveDataStatus().postValue(STATUS_LOADING);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // 假设此处是耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mLiveData1.postValue(true);
                //设置当前状态为加载结束
                getLiveDataStatus().postValue(STATUS_LOAD_ERROR);
            }
        });
    }

    /**
     * 模拟网络请求
     */
    public void getData2() {
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
                Log.e(TAG, "getData2_start_" + Thread.currentThread().getName() + "_" + System.currentTimeMillis());
                try {
                    Thread.sleep(10000); // 假设此处是耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "getData2_end_" + Thread.currentThread().getName() + "_" + System.currentTimeMillis());
                mLiveData2.postValue("11111");

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
