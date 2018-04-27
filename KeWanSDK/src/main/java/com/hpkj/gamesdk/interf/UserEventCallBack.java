package com.hpkj.gamesdk.interf;

import com.hpkj.gamesdk.bean.LoginResult;

/**
 * Created by 77429 on 2018/3/12.
 */

public class UserEventCallBack {
    private UserEventInterf userEventInterf;
    public UserEventCallBack(UserEventInterf userEventInterf) {
        this.userEventInterf = userEventInterf;
    }
    public void loginState(int type,LoginResult loginResult){
        userEventInterf.loginState(type,loginResult);
    }
}
