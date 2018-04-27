package com.hpkj.gamesdk.network;

import android.util.Log;

import com.hpkj.gamesdk.base.MyBaseProgressCallbackImpl;
import com.hpkj.gamesdk.bean.BaseResult;
import com.hpkj.gamesdk.bean.ForgetPwdBean;
import com.hpkj.gamesdk.bean.LoginResult;
import com.hpkj.gamesdk.bean.RegisterBean;
import com.hpkj.gamesdk.bean.YzmBean;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @author huanglei
 * @ClassNname：GameNetWork.java
 * @Describe 网络请求类
 * @time 2018/3/12 9:48
 */
public class GameNetWork {

//    public final static String httpUrl = "http://api.5971.com/";//线上接口地址
    public final static String httpUrl = "http://api.test.5971.com/";//测试接口地址


    public static RequestParams initRequestParams(RequestParams params) {
        params.setConnectTimeout(1000 * 5);
        return params;
    }

    /**
     * 用户注册
     *
     * @param callback
     * @param userName
     * @param passWord
     */
    public static void userRegister(MyBaseProgressCallbackImpl<RegisterBean> callback, String userName, String code, String passWord,String type) {
        Log.w("cc","注册时候的type:"+type);
        RequestParams params = initRequestParams(new RequestParams(httpUrl + "register"));
        params.addBodyParameter("mobile", userName);
        params.addBodyParameter("code", code);
        params.addBodyParameter("password", passWord);
        params.addBodyParameter("game","742");
        params.addBodyParameter("type", type);
        params.addBodyParameter("identity", "0");
        x.http().post(params, callback);
    }

    /**
     * 获取验证码
     *
     * @param callback
     * @param mobile
     */
    public static void getCode(MyBaseProgressCallbackImpl<YzmBean> callback, String mobile) {
        RequestParams params = initRequestParams(new RequestParams(httpUrl + "register/verification"));
        params.addBodyParameter("mobile", mobile);
        x.http().post(params, callback);
    }

    /**
     * 获取验证码
     *
     * @param callback
     * @param mobile
     */
    public static void getModifycode(MyBaseProgressCallbackImpl<YzmBean> callback, String mobile) {
        RequestParams params = initRequestParams(new RequestParams(httpUrl + "login/modifycode"));
        params.addBodyParameter("mobile", mobile);
        x.http().post(params, callback);
    }


    /**
     * 用户登录
     *
     * @param callback
     * @param userName
     * @param passWord
     */
    public static void userLogin(MyBaseProgressCallbackImpl<LoginResult> callback, String userName, String passWord) {
        RequestParams params = initRequestParams(new RequestParams(httpUrl + "login"));
        params.addBodyParameter("mobile", userName);
        params.addBodyParameter("password", passWord);
        x.http().post(params, callback);
    }


    /**
     * 忘记密码
     *
     * @param callback
     * @param mobile
     * @param code
     * @param password
     */
    public static void modifyPassWord(MyBaseProgressCallbackImpl<ForgetPwdBean> callback, String mobile, String code, String password) {
        RequestParams params = initRequestParams(new RequestParams(httpUrl + "login/modifypassword"));
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("code", code);
        params.addBodyParameter("password", password);
        x.http().post(params, callback);
    }

    /**
     * 上传游戏角色信息
     *
     * @param callback
     */
    public static void roleData(MyBaseProgressCallbackImpl<BaseResult> callback, String gameId, String userId, String data) {
        RequestParams params = initRequestParams(new RequestParams(httpUrl + "record/index"));
        params.addBodyParameter("gid", gameId);
        params.addBodyParameter("userid", userId);
        params.addBodyParameter("data", data);
        x.http().post(params, callback);
    }

    /**
     * 上传支付信息
     *
     * @param callback
     * @param ext
     * @param coins
     * @param gid
     * @param user_id
     */
    public static void uploadPayInfo(MyBaseProgressCallbackImpl<BaseResult> callback, String ext, String coins, String gid, String user_id) {
        RequestParams params = initRequestParams(new RequestParams(httpUrl + "shouyoupay/notifyurl"));
        params.addBodyParameter("ext", ext);
        params.addBodyParameter("coins", coins);
        params.addBodyParameter("gid", gid);
        params.addBodyParameter("user_id", user_id);
        x.http().post(params, callback);
    }

    /**
     * 上传支付信息
     */
    public static void uploadPayInfo(MyBaseProgressCallbackImpl<String> callback, String returnurl) {
        RequestParams params = initRequestParams(new RequestParams(returnurl));
        x.http().post(params, callback);
    }

    /**
     * 获取微信支付是否成功
     */
    public static void getWeiXinPayState(MyBaseProgressCallbackImpl<BaseResult> callback, String ext) {
        RequestParams params = initRequestParams(new RequestParams(httpUrl + "Shouyouweixinpay/wxorderQuery"));
        params.addBodyParameter("ext", ext);
        x.http().post(params, callback);
    }
    /**
     * 上传用户第一次安装信息
     */
    public static void uploadInstallInfo(Callback.ProgressCallback<BaseResult> callback, String ip, String type) {
        RequestParams params = initRequestParams(new RequestParams(httpUrl));
        params.addBodyParameter("ip", ip);
        params.addBodyParameter("type", type);
        x.http().post(params, callback);
    }


}
