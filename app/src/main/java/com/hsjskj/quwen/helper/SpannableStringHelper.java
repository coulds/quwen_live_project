package com.hsjskj.quwen.helper;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.hsjskj.quwen.R;

/**
 * @author : Jun
 * time          : 2020年12月24日 12:33
 * description   : quwen_live
 */
public class SpannableStringHelper {

    public static SpannableString setProtocolString(Context context, String str1, String and, String protocol, String secrecy, ProtocolHelperListener listener) {
        SpannableString mSpannableString = new SpannableString(str1 + protocol + and + secrecy);
        mSpannableString.setSpan(new ClickableSpanProtocol(context, listener, 1)
                , str1.length(), (str1 + protocol).length()
                , Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        mSpannableString.setSpan(new ClickableSpanProtocol(context, listener, 2)
                , (str1 + protocol + and).length(), (str1 + protocol + and + secrecy).length()
                , Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return mSpannableString;
    }

    private static void updateDrawStateProtocol(TextPaint ds, Context context) {
        ds.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }

    public static class ClickableSpanProtocol extends ClickableSpan {
        private Context mContext;
        private ProtocolHelperListener mListener;
        private int type;

        public ClickableSpanProtocol(Context context, ProtocolHelperListener listener, int t) {
            mContext = context;
            mListener = listener;
            type = t;
        }

        @Override
        public void onClick(View view) {
            if (type == 1) {
                mListener.protocolClick();
            } else if (type == 2) {
                mListener.secrecyClick();
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            updateDrawStateProtocol(ds, mContext);
        }
    }

    public interface ProtocolHelperListener {
        void protocolClick();

        void secrecyClick();
    }
}
