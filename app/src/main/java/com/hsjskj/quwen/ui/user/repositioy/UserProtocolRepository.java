package com.hsjskj.quwen.ui.user.repositioy;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseRepository;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.NoticeGetApi;
import com.hsjskj.quwen.http.request.PasswordApi;
import com.hsjskj.quwen.http.request.ProtocolGetApi;
import com.hsjskj.quwen.http.response.ProtocolBean;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:21
 * description   : AndroidProject
 */
public class UserProtocolRepository extends BaseRepository {

    private MutableLiveData<ProtocolBean> mutableLiveData;

    public MutableLiveData<ProtocolBean> getProtocolBean() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }
    public void loadProtocolBean(LifecycleOwner lifecycleOwner, String id) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new ProtocolGetApi().setId(id))
                .request(new HttpCallback<HttpData<ProtocolBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<ProtocolBean> data) {
                        mutableLiveData.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        mutableLiveData.postValue(null);
                    }
                });
    }

    public void loadNoticeBean(LifecycleOwner lifecycleOwner, String id) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new NoticeGetApi().setId(id))
                .request(new HttpCallback<HttpData<ProtocolBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<ProtocolBean> data) {
                        mutableLiveData.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        mutableLiveData.postValue(null);
                    }
                });
    }

    @Override
    public void clear() {
        EasyHttp.cancel(this);
        super.clear();
    }
}
