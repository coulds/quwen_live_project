package com.hsjskj.quwen.ui.my.viewmodel;

import com.hsjskj.quwen.ui.my.object.BankCard;
import java.util.List;

public interface RequestDataBackListener {
      void onFinish(List<BankCard.DataBean> responseData);
      void onError(Exception e);
}
