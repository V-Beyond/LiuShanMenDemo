package com.hpkj.gamesdk.interf;

import com.alipay.sdk.util.H5PayResultModel;

/**
 * Created by 77429 on 2018/3/12.
 */

public class RoleCallBack {
    private RoleInterf roleInterf;
    public RoleCallBack(RoleInterf roleInterf) {
        this.roleInterf = roleInterf;
    }
    public void payState(int code,String msg) {
        roleInterf.notifyRole(code,msg);
    }
}
