package com.hsjskj.quwen.upload;

import android.content.Context;
import android.util.Log;

import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.SessionQCloudCredentials;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;
import com.tencent.qcloud.core.auth.StaticCredentialProvider;


/**
 * @author : Jun
 * time          : 2020年12月30日 13:13
 * description   : quwen_live
 */
public class UploadTxImpl implements UploadListener {

    private static final String TAG = "VideoUploadTxImpl";

    private UploadBean mVideoUploadBean;
    private UploadCallback mVideoUploadCallback;
    private OnSuccessCallback mImageOnSuccessCallback;
    private CosXmlService mCosXmlService;
    private String mRegion;
    private String mBucketName;
    private String mCosImagePath;
    private Context context;

    private String secretId = "COS_SECRETID"; //永久密钥 secretId
    private String secretKey = "COS_SECRETKEY"; //永久密钥 secretKey

    public UploadTxImpl(Context context, String mRegion, String mBucketName, String mCosImagePath) {
        this.context = context;
        this.mRegion = mRegion;
        this.mBucketName = mBucketName;
        this.mCosImagePath = mCosImagePath;

        mImageOnSuccessCallback = url -> {
            if (mVideoUploadBean == null) {
                return;
            }
            mVideoUploadBean.setmResultUrl(url);
            if (mVideoUploadCallback != null) {
                mVideoUploadCallback.onSuccess(mVideoUploadBean);
            }
        };
    }

    @Override
    public void upload(UploadBean bean, UploadCallback callback) {
        if (bean == null || callback == null) {
            return;
        }
        mVideoUploadBean = bean;
        mVideoUploadCallback = callback;
        //expiredTime
        startUpload();
    }

    public void startUpload() {
        try {
            //使用永久密钥
            QCloudCredentialProvider qCloudCredentialProvider = new ShortTimeCredentialProvider(secretId, secretKey, 300);
            //使用临时密钥  需要后台加，，既然前端写了，，直接使用永久的吧
//            SessionQCloudCredentials credentials = new SessionQCloudCredentials(secretId, secretKey, token, expiredTime);
//            QCloudCredentialProvider qCloudCredentialProvider = new StaticCredentialProvider(credentials);
            CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                    .setRegion(mRegion)
                    .isHttps(true)
                    .builder();
            mCosXmlService = new CosXmlService(context, serviceConfig, qCloudCredentialProvider);
        } catch (Exception e) {
            if (mVideoUploadCallback != null) {
                mVideoUploadCallback.onFailure();
            }
        }
        //上传图片
        uploadFile(mVideoUploadBean.getLocalPath(), mImageOnSuccessCallback);
    }

    /**
     * 上传文件
     */
    private void uploadFile(String srcPath, final OnSuccessCallback callback) {
        if (mCosXmlService == null) {
            return;
        }
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        TransferManager transferManager = new TransferManager(mCosXmlService, transferConfig);
        COSXMLUploadTask cosxmlUploadTask = transferManager.upload(mBucketName, mCosImagePath, srcPath, null);

        cosxmlUploadTask.setCosXmlProgressListener((complete, target) -> {
            Log.e(TAG, "---上传进度--->" + target * 100 / complete);
        });

        //设置返回结果回调
        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
// COSXMLUploadTask.COSXMLUploadTaskResult cOSXMLUploadTaskResult = (COSXMLUploadTask.COSXMLUploadTaskResult) result;
                if (result != null) {
                    String resultUrl = "http://" + result.accessUrl;
                    if (callback != null) {
                        callback.onUploadSuccess(resultUrl);
                    }
                }
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                if (exception != null) {
                    exception.printStackTrace();
                } else {
                    serviceException.printStackTrace();
                }
            }
        });

//        //设置任务状态回调, 可以查看任务过程
//        cosxmlUploadTask.setTransferStateListener(new TransferStateListener() {
//            @Override
//            public void onStateChanged(TransferState state) {
//                // todo notify transfer state
//            }
//        });
    }

    @Override
    public void cancel() {
        mVideoUploadCallback = null;
        if (mCosXmlService != null) {
            mCosXmlService.release();
        }
        mCosXmlService = null;
        context = null;
    }

    public interface OnSuccessCallback {
        void onUploadSuccess(String url);
    }
}
