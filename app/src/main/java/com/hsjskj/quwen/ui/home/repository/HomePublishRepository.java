package com.hsjskj.quwen.ui.home.repository;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseRepository;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.response.TxCosBean;
import com.hsjskj.quwen.upload.UploadBean;
import com.hsjskj.quwen.upload.UploadCallback;
import com.hsjskj.quwen.upload.UploadListener;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:21
 * description   : AndroidProject
 */
public class HomePublishRepository extends BaseRepository {


    @Override
    public void clear() {
        super.clear();
    }

    public void upLoadPics(List<Object> list, AtomicInteger count, UploadListener listener
            , MutableLiveData<Boolean> mutableLiveData, List<String> remoteUrls) {
        String s = (String) list.get(0);
        listener.upload(new UploadBean(s), new UploadCallback() {
            @Override
            public void onSuccess(UploadBean bean) {
                //给后台传name就行
                remoteUrls.add(bean.getFileName());
                if (count.decrementAndGet() == 0) {
                    mutableLiveData.postValue(true);
                    return;
                }
                list.remove(0);
                upLoadPics(list, count, listener, mutableLiveData, remoteUrls);
            }

            @Override
            public void onFailure() {
                mutableLiveData.postValue(false);
            }
        });

    }

    public void upLoadPicsNew(List<UploadBean> list, AtomicInteger count, UploadListener listener
            , MutableLiveData<Boolean> mutableLiveData, List<String> remoteUrls) {
        UploadBean uploadBean = list.get(0);
        if (uploadBean.isUpload()) {
            //这里 只会是编辑，，因为已经上传过，所以 跳过上传
            resultSuccess(uploadBean, remoteUrls, count, mutableLiveData, list, listener);
        }else {
            listener.upload(uploadBean, new UploadCallback() {
                @Override
                public void onSuccess(UploadBean bean) {
                    resultSuccess(bean, remoteUrls, count, mutableLiveData, list, listener);
                }

                @Override
                public void onFailure() {
                    mutableLiveData.postValue(false);
                }
            });
        }


    }

    private boolean resultSuccess(UploadBean bean, List<String> remoteUrls, AtomicInteger count, MutableLiveData<Boolean> mutableLiveData, List<UploadBean> list, UploadListener listener) {
        //给后台传name就行
        remoteUrls.add(bean.getFileName());
        if (count.decrementAndGet() == 0) {
            mutableLiveData.postValue(true);
            return true;
        }
        list.remove(0);
        upLoadPicsNew(list, count, listener, mutableLiveData, remoteUrls);
        return false;
    }


    private MutableLiveData<TxCosBean> txCosLiveBean;

    public MutableLiveData<TxCosBean> getTxCosLiveBean() {
        if (txCosLiveBean == null) {
            txCosLiveBean = new MutableLiveData<>();
        }
        return txCosLiveBean;
    }

    public void loadTxCos(LifecycleOwner lifecycleOwner) {
        EasyHttp.post(lifecycleOwner)
                .api("Config/cos")
                .request(new HttpCallback<HttpData<TxCosBean>>(null) {

                    @Override
                    public void onSucceed(HttpData<TxCosBean> data) {
                        txCosLiveBean.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        txCosLiveBean.postValue(null);
                        ToastUtils.show(e.getMessage());
                    }
                });
    }

}
