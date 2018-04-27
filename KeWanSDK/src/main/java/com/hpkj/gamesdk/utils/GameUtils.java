package com.hpkj.gamesdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hpkj.gamesdk.R;
import com.hpkj.gamesdk.activity.LoginActivity;
import com.hpkj.gamesdk.activity.PayMethodActivity;
import com.hpkj.gamesdk.base.MyBaseProgressCallbackImpl;
import com.hpkj.gamesdk.bean.AccountBean;
import com.hpkj.gamesdk.bean.BaseResult;
import com.hpkj.gamesdk.bean.KeWanRoleBaseData;
import com.hpkj.gamesdk.bean.LoginResult;
import com.hpkj.gamesdk.interf.PayUrlInterf;
import com.hpkj.gamesdk.interf.RoleCallBack;
import com.hpkj.gamesdk.interf.RoleInterf;
import com.hpkj.gamesdk.interf.UserEventCallBack;
import com.hpkj.gamesdk.interf.UserEventInterf;
import com.hpkj.gamesdk.network.GameNetWork;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * @author huanglei
 * @ClassNname：GameUtils.java
 * @Describe 工具类
 * @time 2018/3/12 13:33
 */
public class GameUtils {
    public static UserEventInterf mUserEventInterf;
    public static PayUrlInterf mPayUrlInterf;
    static DbManager dbManager;

    /**
     * 登录
     *
     * @param context
     * @param userEventInterf
     */
    public static void login(Context context, UserEventInterf userEventInterf) {
        try {
            mUserEventInterf = userEventInterf;
            boolean isLogin = SharePreferenceUtils.getBoolean(context, "isLogin", false);
            if (isLogin == true) {
                dbManager = KeWanDbUtils.getDaoImpl(context);
                List<AccountBean> accountBean = dbManager.selector(AccountBean.class).orderBy("saveTime", true).findAll();
                if (accountBean != null && accountBean.size() > 0) {
                    final String account = accountBean.get(0).getPhone();
                    final String password = accountBean.get(0).getPassword();
                    GameNetWork.userLogin(new MyBaseProgressCallbackImpl<LoginResult>(context) {
                        @Override
                        public void onSuccess(LoginResult resul) {
                            super.onSuccess(resul);
                            if (resul != null) {
                                UserEventCallBack service = new UserEventCallBack(GameUtils.mUserEventInterf);
                                if (resul.getCode() == 100) {
                                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                                    SharePreferenceUtils.putBoolean(context, "isLogin", true);
                                    service.loginState(1, resul);
                                    AccountBean accountBean = new AccountBean();
                                    accountBean.setId(resul.getData().getUser().getId());
                                    accountBean.setPhone(account);
                                    accountBean.setPassword(password);
                                    accountBean.setSaveTime(System.currentTimeMillis());
                                    try {
                                        dbManager.saveOrUpdate(accountBean);
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(context, resul.getMsg(), Toast.LENGTH_SHORT).show();
                                    SharePreferenceUtils.putBoolean(context, "isLogin", false);
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                            }
                        }
                    }, account, password);
                }
            } else {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换账号
     *
     * @param context
     */
    public static void changeAccount(Activity context, UserEventInterf userEventInterf) {
        try {
            mUserEventInterf = userEventInterf;
            SharePreferenceUtils.putBoolean(context, "isLogin", false);
            SharePreferenceUtils.putString(context, "account", "");
            SharePreferenceUtils.putString(context, "password", "");
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传角色信息
     *
     * @param context
     * @param gameId
     * @param userId
     * @param keWanRoleBaseData
     */
    public static void uploadRole(Context context, String gameId, String userId, KeWanRoleBaseData keWanRoleBaseData, final RoleInterf roleInterf) {
        try {
            Gson gson = new Gson();
            String data = gson.toJson(keWanRoleBaseData);
            Log.w("cc", "角色信息:" + data);
            GameNetWork.roleData(new MyBaseProgressCallbackImpl<BaseResult>(context) {
                @Override
                public void onSuccess(BaseResult resul) {
                    super.onSuccess(resul);
                    RoleCallBack roleCallBack = new RoleCallBack(roleInterf);
                    roleCallBack.payState(resul.getCode(), resul.getMsg());
                }
            }, gameId, userId, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示支付页面
     *
     * @param context
     */
    public static void showPay(Context context, PayUrlInterf payUrlInterf, String gid, String userid, String price, String ext, String coins, String roleid) {
        try {
            mPayUrlInterf = payUrlInterf;
            Intent intent = new Intent(context, PayMethodActivity.class);
            intent.putExtra("gid", gid);
            intent.putExtra("userid", userid);
            intent.putExtra("price", price);
            intent.putExtra("ext", ext);
            intent.putExtra("coins", coins);
            intent.putExtra("roleid", roleid);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 首次安装apk上传信息
     *
     * @param context
     */
    public static void isFirstInstall(final Context context) {
        String ipAddress = getIpAddressString();
        String type = getType(context);
        Log.w("cc", "ip地址:" + ipAddress);
        Log.w("cc", "type:" + type);
        boolean isFirstInstall = SharePreferenceUtils.getBoolean(context, "isFirstInstall", true);
        if (isFirstInstall) {
            GameNetWork.uploadInstallInfo(new Callback.ProgressCallback<BaseResult>() {
                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {

                }

                @Override
                public void onLoading(long l, long l1, boolean b) {

                }

                @Override
                public void onSuccess(BaseResult baseResult) {
                    SharePreferenceUtils.putBoolean(context, "isFirstInstall", false);
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            }, ipAddress, type);
        }
    }

    /**
     * 判断应用是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            Log.w("cc", "应用信息:" + info.toString());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("cc", "异常信息:" + e.toString());
            return false;
        }
    }


    /**
     * 获取type文件里的值
     *
     * @param context
     * @return
     */
    public static String getType(Context context) {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.type);
            String text = readTextFromSDcard(is);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
}
