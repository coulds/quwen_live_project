package com.hsjskj.quwen.ui.my.viewmodel;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.EasyUtils;
import com.hjq.http.listener.OnHttpListener;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.GetBankListApi;
import com.hsjskj.quwen.ui.my.object.BankCard;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BankCardNetworkRequest {


    public void getBankCardList(LifecycleOwner lifecycleOwner,final int page, final RequestDataBackListener requestDataBackListener) {
        EasyHttp.post(lifecycleOwner)
                .api(new GetBankListApi(page, 10))
                .request(new OnHttpListener<HttpData<BankCard>>() {
                    @Override
                    public void onSucceed(HttpData<BankCard> result) {
                        requestDataBackListener.onFinish(result.data.data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        requestDataBackListener.onError(e);
                    }
                });

    }
//    public void addBankCard(final String cityName, final RequestDataBackListener requestDataBackListener){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    okHttpClient=new OkHttpClient();
////                    FormBody formBody = new FormBody.Builder()
////                            .add("city",cityName)
////                            .add("key","b3b5ddc3e69a8af33f75b23060c01a42")
////                            .build();
//                    Request request= new Request.Builder()
//                            .url("http://apis.juhe.cn/simpleWeather/query?")
//                            .get()
//                            .build();
//                    Response response=okHttpClient.newCall(request).execute();
//                    String responseData=response.body().string();
//                    Log.e("setRequestData","234567890:"+responseData);
//                    //回调方法
//                    requestDataBackListener.onFinish(responseData);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //回调方法
//                    requestDataBackListener.onError(e);
//                }
//            }
//        }).start();
//    }

}
