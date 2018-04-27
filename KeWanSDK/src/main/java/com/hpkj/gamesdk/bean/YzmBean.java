package com.hpkj.gamesdk.bean;

import org.xutils.http.annotation.HttpResponse;

/**
*  @des 验证码实体类
*  @author huanglei
*  @date  2018/3/13 14:03
*
*/
@HttpResponse(parser = JsonResponseParser.class)
public class YzmBean extends BaseResult {
    private  String mobile;
    private int create_time;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
