package com.hpkj.gamesdk.interf;

import com.alipay.sdk.util.H5PayResultModel;

/**
 * Created by 77429 on 2018/3/12.
 */

public class PayUrlCallBack {
    private PayUrlInterf payUrlInterf;
    public PayUrlCallBack(PayUrlInterf payUrlInterf) {
        this.payUrlInterf = payUrlInterf;
    }
    public void payState(H5PayResultModel h5PayResultModel) {
        payUrlInterf.notifyPayState(h5PayResultModel);
    }
}
