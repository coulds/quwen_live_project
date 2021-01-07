package com.hsjskj.quwen.http.intercept;

import android.text.TextUtils;

import com.hsjskj.quwen.common.MyUserInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : Jun
 * time          : 2020年12月28日 14:29
 * description   : quwen_live
 */
public class TokenIntercept implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        String token = MyUserInfo.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            return chain.proceed(chain.request());
        } else {
            Request original = chain.request();
            //请求体定制：统一添加token参数
            Request.Builder requestBuilder = original.newBuilder();
            if (original.body() instanceof FormBody) {
                FormBody.Builder newFormBody = new FormBody.Builder();
                FormBody oidFormBody = (FormBody) original.body();
                for (int i = 0; i < oidFormBody.size(); i++) {
                    newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                }
                newFormBody.add("token", token);
                requestBuilder.method(original.method(), newFormBody.build());
            } else if ("GET".equals(original.method())) {
                HttpUrl url = original.url();
                HttpUrl newUrl = url.newBuilder()
                        .addEncodedQueryParameter("token", token)
                        .build();
                requestBuilder.url(newUrl).build();
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
}
