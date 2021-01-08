package com.hsjskj.quwen.ui.my.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.OnHttpListener;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.AddBankCardApi;
import com.hsjskj.quwen.http.request.GetBankListApi;
import com.hsjskj.quwen.ui.my.object.BankCard;

public class BankCardNetworkRequest {


    public void getBankCardList(LifecycleOwner lifecycleOwner,final int page, final RequestDataBackListener requestDataBackListener) {
        EasyHttp.post(lifecycleOwner)
                .api(new GetBankListApi(10, page))
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


//    public String token;
//    public String username;//持卡人
//    public String number;//卡号
//    public String bank_name;//开户行
//    public String mark;//备注
    public void addBankCard(LifecycleOwner lifecycleOwner,String username,String number,String bank_name,String mark,final RequestDataBackAddListener requestDataBackListener){
        EasyHttp.post(lifecycleOwner)
                .api(new AddBankCardApi(username,number,bank_name,mark))
                .request(new OnHttpListener<HttpData>() {

                    @Override
                    public void onSucceed(HttpData result) {
                        requestDataBackListener.onFinish((result.code));
                    }

                    @Override
                    public void onFail(Exception e) {
                        requestDataBackListener.onError(e);
                    }
                });
    }

}
