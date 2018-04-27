package com.hpkj.gamesdk.interf;

import com.alipay.sdk.util.H5PayResultModel;
import com.hpkj.gamesdk.bean.LoginResult;

/**
*  @des  支付地址回调
*  @author huanglei
*  @date  2018/3/13 15:48
*
*/

public interface PayUrlInterf {
    void notifyPayState(H5PayResultModel h5PayResultModel);//支付宝回调实体类
}
