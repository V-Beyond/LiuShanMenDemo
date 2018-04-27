package com.hpkj.gamesdk.interf;

import com.alipay.sdk.util.H5PayResultModel;

/**
*  @des 上传角色回调
*  @author huanglei
*  @date  2018/3/13 15:48
*
*/

public interface RoleInterf {
    void notifyRole(int code,String msg);//code：100表示成功   msg:接口返回消息
}
