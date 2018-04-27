package com.hpkj.gamesdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hpkj.gamesdk.adapter.AccountListAdapter;
import com.hpkj.gamesdk.base.MyBaseProgressCallbackImpl;
import com.hpkj.gamesdk.bean.AccountBean;
import com.hpkj.gamesdk.bean.LoginResult;
import com.hpkj.gamesdk.interf.UserEventCallBack;
import com.hpkj.gamesdk.network.GameNetWork;
import com.hpkj.gamesdk.utils.GameUtils;
import com.hpkj.gamesdk.utils.KeWanDbUtils;
import com.hpkj.gamesdk.utils.ResourceUtil;
import com.hpkj.gamesdk.utils.SharePreferenceUtils;
import com.hpkj.gamesdk.utils.XActivityUtils;
import com.hpkj.gamesdk.utils.XStringPars;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huanglei
 * @des 登录页面
 * @date 2018/3/13 11:49
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    //通过反射获取控件id
    int id_tv_login;
    int id_tv_forget_pwd;
    int activity_login;
    int id_edittext_phone;
    int id_edittext_password;
    int id_kewan_tv_phone_register;

    EditText edittext_phone;//手机号
    EditText edittext_password;//密码
    TextView tv_login;//登录
    TextView tv_forget_pwd;//忘记密码
    String phone;//用户名
    String password;//密码
    LinearLayout kewan_tv_phone_register;
    RelativeLayout kewan_rl_account;
    ListView kewan_list_account;
    int id_kewan_rl_account;
    int id_kewan_list_account;
    ImageView kewan_iv_clear;
    int id_kewan_iv_clear;
    AccountListAdapter accountListAdapter;
    ArrayList<AccountBean> accountBeanArrayList = new ArrayList<>();
    DbManager dbManager;
    List<AccountBean> accountBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_login = ResourceUtil.getLayoutId(LoginActivity.this, "kewan_activity_login");
        setContentView(activity_login);
        XActivityUtils.getInstance().pushActivity(this);
        // 设置为true点击区域外消失
        setFinishOnTouchOutside(false);
        id_edittext_phone = ResourceUtil.getId(LoginActivity.this, "kewan_edittext_phone");
        edittext_phone = findViewById(id_edittext_phone);
        id_edittext_password = ResourceUtil.getId(LoginActivity.this, "kewan_edittext_password");
        edittext_password = findViewById(id_edittext_password);
        id_tv_login = ResourceUtil.getId(LoginActivity.this, "kewan_tv_login");
        tv_login = findViewById(id_tv_login);
        id_tv_forget_pwd = ResourceUtil.getId(LoginActivity.this, "kewan_tv_forget_pwd");
        tv_forget_pwd = findViewById(id_tv_forget_pwd);
        id_kewan_tv_phone_register = ResourceUtil.getId(LoginActivity.this, "kewan_tv_phone_register");
        kewan_tv_phone_register = findViewById(id_kewan_tv_phone_register);
        id_kewan_rl_account = ResourceUtil.getId(LoginActivity.this, "kewan_rl_account");
        kewan_rl_account = findViewById(id_kewan_rl_account);
        id_kewan_list_account = ResourceUtil.getId(LoginActivity.this, "kewan_list_account");
        kewan_list_account = findViewById(id_kewan_list_account);
        id_kewan_iv_clear = ResourceUtil.getId(LoginActivity.this, "kewan_iv_clear");
        kewan_iv_clear = findViewById(id_kewan_iv_clear);
        tv_login.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        kewan_tv_phone_register.setOnClickListener(this);
        kewan_iv_clear.setOnClickListener(this);
        try {
            dbManager=KeWanDbUtils.getDaoImpl(LoginActivity.this);
            accountBeanList = dbManager.selector(AccountBean.class).orderBy("saveTime",true).findAll() ;
            if (accountBeanList != null&&accountBeanList.size()>0) {
                kewan_iv_clear.setVisibility(View.VISIBLE);
                edittext_phone.setText(accountBeanList.get(0).getPhone());
                edittext_password.setText(accountBeanList.get(0).getPassword());
                accountBeanArrayList.addAll(accountBeanList);
                accountListAdapter = new AccountListAdapter(LoginActivity.this, accountBeanArrayList,handler);
                kewan_list_account.setAdapter(accountListAdapter);
                accountListAdapter.notifyDataSetChanged();
                kewan_list_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        kewan_rl_account.setVisibility(View.GONE);
                        phone = accountBeanArrayList.get(i).getPhone();
                        password = accountBeanArrayList.get(i).getPassword();
                        edittext_phone.setText(phone);
                        edittext_password.setText(password);
                    }
                });
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == id_tv_login) {
            phone = edittext_phone.getText().toString().trim();
            password = edittext_password.getText().toString().trim();
            if (phone.isEmpty()) {
                Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!XStringPars.isMobileNO(phone)) {
                Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            GameNetWork.userLogin(new MyBaseProgressCallbackImpl<LoginResult>(LoginActivity.this) {
                @Override
                public void onSuccess(LoginResult resul) {
                    super.onSuccess(resul);
                    if (resul != null) {
                        UserEventCallBack userEventCallBack = new UserEventCallBack(GameUtils.mUserEventInterf);
                        if (resul.getCode() == 100) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            SharePreferenceUtils.putBoolean(LoginActivity.this, "isLogin", true);
                            userEventCallBack.loginState(1, resul);
                            AccountBean accountBean=new AccountBean();
                            accountBean.setId(resul.getData().getUser().getId());
                            accountBean.setPhone(phone);
                            accountBean.setPassword(password);
                            accountBean.setSaveTime(System.currentTimeMillis());
                            handler.sendMessage(handler.obtainMessage(1,accountBean));
                        } else {
                            Toast.makeText(LoginActivity.this, resul.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, phone, password);
        } else if (view.getId() == id_tv_forget_pwd) {
            startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
        } else if (view.getId() == id_kewan_tv_phone_register) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        } else if (view.getId() == id_kewan_iv_clear) {
            if (kewan_rl_account.getVisibility() == View.VISIBLE) {
                kewan_rl_account.setVisibility(View.GONE);
            } else if (kewan_rl_account.getVisibility() == View.GONE) {
                kewan_rl_account.setVisibility(View.VISIBLE);
            }
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                try {
                    AccountBean accountBean= (AccountBean) msg.obj;
                    if (accountBean!=null){
                        dbManager.saveOrUpdate(accountBean);
                    }
                    XActivityUtils.getInstance().popActivity(LoginActivity.this);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }else if(msg.what==2){
                AccountBean accountBean= (AccountBean) msg.obj;
                try {
                    dbManager.delete(accountBean);
                    accountBeanList = dbManager.selector(AccountBean.class).findAll();
                    if (accountBeanList!=null&&accountBeanList.size()>0){
                        if (accountBeanArrayList!=null&&accountBeanArrayList.size()>0){
                            accountBeanArrayList.clear();
                        }
                        accountBeanArrayList.addAll(accountBeanList);
                        accountListAdapter = new AccountListAdapter(LoginActivity.this, accountBeanArrayList,handler);
                        kewan_list_account.setAdapter(accountListAdapter);
                        accountListAdapter.refresh(accountBeanArrayList);
                    }else{
                        kewan_iv_clear.setVisibility(View.GONE);
                        kewan_rl_account.setVisibility(View.GONE);
                        edittext_phone.setText("");
                        edittext_password.setText("");
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
