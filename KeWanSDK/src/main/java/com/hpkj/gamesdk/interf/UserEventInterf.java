package com.hpkj.gamesdk.interf;

import com.hpkj.gamesdk.bean.LoginResult;

/**
 * @ClassNname：UserEventInterf.java
 * @Describe 用户操作通知接口
 * @author huanglei
 * @time 2018/3/12 13:28
 */

public interface UserEventInterf {
     void loginState(int type, LoginResult loginResult);//1：登录成功 2：登录失败 loginResult:用户名实体类
}
