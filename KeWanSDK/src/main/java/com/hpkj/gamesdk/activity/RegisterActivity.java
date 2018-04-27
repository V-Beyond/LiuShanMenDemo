package com.hpkj.gamesdk.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hpkj.gamesdk.R;
import com.hpkj.gamesdk.base.MyBaseProgressCallbackImpl;
import com.hpkj.gamesdk.bean.RegisterBean;
import com.hpkj.gamesdk.bean.YzmBean;
import com.hpkj.gamesdk.network.GameNetWork;
import com.hpkj.gamesdk.utils.ResourceUtil;
import com.hpkj.gamesdk.utils.XActivityUtils;
import com.hpkj.gamesdk.utils.XStringPars;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author huanglei
 * @des 注册页面
 * @date 2018/3/13 14:09
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    EditText edittext_phone;
    EditText edittext_password;
    EditText edittext_code;
    ImageView iv_delete;
    TextView tv_getcode;
    TextView tv_register;
    String phone;
    String pwd;
    String code;
    public TimeCount mTime;

    int kewan_activity_register;
    int id_edittext_phone;
    int id_edittext_password;
    int id_edittext_code;
    int id_iv_delete;
    int id_tv_getcode;
    int id_tv_register;
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kewan_activity_register = ResourceUtil.getLayoutId(RegisterActivity.this, "kewan_activity_register");
        setContentView(kewan_activity_register);
        XActivityUtils.getInstance().pushActivity(this);
        setFinishOnTouchOutside(false);
        id_edittext_phone = ResourceUtil.getId(RegisterActivity.this, "kewan_edittext_phone");
        edittext_phone = (EditText) findViewById(id_edittext_phone);
        id_edittext_password = ResourceUtil.getId(RegisterActivity.this, "kewan_edittext_password");
        edittext_password = (EditText) findViewById(id_edittext_password);
        id_edittext_code = ResourceUtil.getId(RegisterActivity.this, "kewan_edittext_code");
        edittext_code = (EditText) findViewById(id_edittext_code);
        id_iv_delete = ResourceUtil.getId(RegisterActivity.this, "kewan_iv_delete");
        iv_delete=(ImageView) findViewById(id_iv_delete);
        id_tv_getcode = ResourceUtil.getId(RegisterActivity.this, "kewan_tv_getcode");
        tv_getcode=(TextView)findViewById(id_tv_getcode);
        id_tv_register = ResourceUtil.getId(RegisterActivity.this, "kewan_tv_register");
        tv_register=(TextView)findViewById(id_tv_register);
        tv_getcode.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        mTime = new TimeCount(60000, 1000);
        type=getType(RegisterActivity.this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == id_tv_getcode) {
            phone = edittext_phone.getText().toString().trim();
            if (phone.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (XStringPars.isMobileNO(phone)){
                mTime.start();
                GameNetWork.getCode(new MyBaseProgressCallbackImpl<YzmBean>(RegisterActivity.this){
                    @Override
                    public void onSuccess(YzmBean resul) {
                        super.onSuccess(resul);
                        if (resul!=null){
                            if (resul.getCode()==100){
                                Toast.makeText(RegisterActivity.this,"发送成功，请注意查收",Toast.LENGTH_SHORT).show();
                            }else if(resul.getCode()==103){
                                Toast.makeText(RegisterActivity.this,resul.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },phone);
            }else{
                Toast.makeText(RegisterActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == id_tv_register) {
            phone = edittext_phone.getText().toString().trim();
            pwd = edittext_password.getText().toString().trim();
            code = edittext_code.getText().toString().trim();
            if (XStringPars.isMobileNO(phone)){
                if (!code.equalsIgnoreCase("")){
                    if (XStringPars.isCorrectPwd(pwd)){
                        GameNetWork.userRegister(new MyBaseProgressCallbackImpl<RegisterBean>(RegisterActivity.this){
                            @Override
                            public void onSuccess(RegisterBean resul) {
                                super.onSuccess(resul);
                                if (resul!=null){
                                    if (resul.getCode()==100){
                                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                        XActivityUtils.getInstance().popActivity(RegisterActivity.this);
                                    }else{
                                        Toast.makeText(RegisterActivity.this,resul.getMsg(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        },phone,code,pwd,type);
                    }else{
                        Toast.makeText(RegisterActivity.this,"请输入6-12位数字或字母密码",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(RegisterActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            }
        }
        else if (view.getId()==id_iv_delete){
            XActivityUtils.getInstance().popActivity(RegisterActivity.this);
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_getcode.setClickable(false);
            int id_bg_getcode_gray=ResourceUtil.getDrawableId(RegisterActivity.this,"kewan_bg_getcode_gray");
            tv_getcode.setBackgroundResource(id_bg_getcode_gray);
            tv_getcode.setText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            int id_bg_getcode_gray=ResourceUtil.getDrawableId(RegisterActivity.this,"kewan_bg_getcode");
            tv_getcode.setBackgroundResource(id_bg_getcode_gray);
            tv_getcode.setText("重新获取");
            tv_getcode.setClickable(true);

        }
    }

    /**
     * 获取type文件里的值
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
}
