package com.hsjskj.quwen.upload;

import android.content.Context;
import android.util.Log;

import com.hsjskj.quwen.http.response.TxCosBean;
import com.hsjskj.quwen.upload.cos.CosServiceFactory;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.cos.xml.transfer.TransferState;

/**
 * @author : Jun
 * time          : 2020年12月30日 13:13
 * description   : quwen_live
 */
public class UploadTxImpl implements UploadListener {

    private static final String TAG = "VideoUploadTxImpl";
    private String bucket;

    private UploadBean mVideoUploadBean;
    private UploadCallback mUploadCallback;

    private CosXmlService mCosXmlService;
    private TransferManager transferManager;
    private COSXMLUploadTask cosxmlTask;

    private OnSuccessCallback mImageOnSuccessCallback = new OnSuccessCallback() {
        @Override
        public void onUploadSuccess(String url) {
            if (mVideoUploadBean == null) {
                return;
            }
            mVideoUploadBean.setmResultUrl(url);
            if (mUploadCallback != null) {
                mUploadCallback.onSuccess(mVideoUploadBean);
            }
        }

        @Override
        public void onUploadError() {
            if (mUploadCallback != null) {
                mUploadCallback.onFailure();
            }
        }
    };


    public UploadTxImpl(Context context, TxCosBean txCosBean) {
        bucket = txCosBean.bucket;

        mCosXmlService = CosServiceFactory.getCosXmlService(context, txCosBean.region, txCosBean.secretId, txCosBean.secretKey, true);
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(mCosXmlService, transferConfig);
    }

    //获取Buckets 列表
//    public void getServiceAsync(){
//        mCosXmlService.getServiceAsync(new GetServiceRequest(), new CosXmlResultListener() {
//            @Override
//            public void onSuccess(CosXmlRequest request, final CosXmlResult result) {
//                List<ListAllMyBuckets.Bucket> buckets = ((GetServiceResult) result).listAllMyBuckets.buckets;
//            }
//
//            @Override
//            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
//                exception.printStackTrace();
//                serviceException.printStackTrace();
//            }
//        });
//    }

    @Override
    public void upload(UploadBean bean, UploadCallback c) {
        if (bean == null || c == null) {
            return;
        }
        String cosPath = bean.getFileName();

        mVideoUploadBean = bean;
        mUploadCallback = c;

        cosxmlTask = transferManager.upload(bucket, cosPath, bean.getLocalPath(), null);

        cosxmlTask.setTransferStateListener(state -> {

        });
        cosxmlTask.setCosXmlProgressListener((complete, target) -> {
//            Log.e(TAG, "---上传进度--->" + target * 100 / complete);
        });

        cosxmlTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                COSXMLUploadTask.COSXMLUploadTaskResult cOSXMLUploadTaskResult = (COSXMLUploadTask.COSXMLUploadTaskResult) result;
                cosxmlTask = null;
                if (result != null) {
                    String resultUrl = cOSXMLUploadTaskResult.accessUrl;
                    mImageOnSuccessCallback.onUploadSuccess(resultUrl);
                } else {
                    mImageOnSuccessCallback.onUploadError();
                }
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                if (cosxmlTask.getTaskState() != TransferState.PAUSED) {
                    cosxmlTask = null;
                }
                exception.printStackTrace();
                serviceException.printStackTrace();
                mImageOnSuccessCallback.onUploadError();
            }
        });
    }

    @Override
    public void cancel() {
        if (mCosXmlService != null) {
            mCosXmlService.release();
        }
        mCosXmlService = null;
        mVideoUploadBean = null;
    }

    public interface OnSuccessCallback {
        void onUploadSuccess(String url);

        void onUploadError();
    }
}
