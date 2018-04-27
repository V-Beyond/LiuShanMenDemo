package com.hpkj.gamesdk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hpkj.gamesdk.base.MyBaseProgressCallbackImpl;
import com.hpkj.gamesdk.bean.ForgetPwdBean;
import com.hpkj.gamesdk.bean.YzmBean;
import com.hpkj.gamesdk.network.GameNetWork;
import com.hpkj.gamesdk.utils.ResourceUtil;
import com.hpkj.gamesdk.utils.XActivityUtils;
import com.hpkj.gamesdk.utils.XStringPars;

/**
*  @des  忘记密码页面
*  @author huanglei
*  @date  2018/3/13 14:09
*
*/
public class ForgetPwdActivity extends Activity implements View.OnClickListener {
    public TimeCount mTime;
    EditText edittext_phone;
    EditText edittext_password;
    EditText edittext_confirm_password;
    EditText edittext_code;
    TextView tv_getcode;
    TextView tv_commit;
    ImageView iv_delete;
    String phone;
    String pwd;
    String newPwd;
    String code;


    int activity_forget_pwd;
    int id_edittext_phone;
    int id_edittext_password;
    int id_edittext_comfirm_password;
    int id_edittext_code;
    int id_tv_getcode;
    int id_tv_commit;
    int id_iv_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XActivityUtils.getInstance().pushActivity(this);
        activity_forget_pwd = ResourceUtil.getLayoutId(ForgetPwdActivity.this, "kewan_activity_forget_pwd");
        setContentView(activity_forget_pwd);
        setFinishOnTouchOutside(false);
        id_edittext_phone = ResourceUtil.getId(ForgetPwdActivity.this, "kewan_edittext_phone");
        edittext_phone = (EditText) findViewById(id_edittext_phone);
        id_edittext_password = ResourceUtil.getId(ForgetPwdActivity.this, "kewan_edittext_password");
        edittext_password = (EditText) findViewById(id_edittext_password);
        id_edittext_comfirm_password = ResourceUtil.getId(ForgetPwdActivity.this, "kewan_edittext_confirm_password");
        edittext_confirm_password = (EditText) findViewById(id_edittext_comfirm_password);
        id_edittext_code = ResourceUtil.getId(ForgetPwdActivity.this, "kewan_edittext_code");
        edittext_code = (EditText) findViewById(id_edittext_code);
        id_tv_getcode = ResourceUtil.getId(ForgetPwdActivity.this, "kewan_tv_getcode");
        tv_getcode=(TextView)findViewById(id_tv_getcode);
        id_tv_commit = ResourceUtil.getId(ForgetPwdActivity.this, "kewan_tv_commit");
        tv_commit=(TextView)findViewById(id_tv_commit);
        id_iv_delete = ResourceUtil.getId(ForgetPwdActivity.this, "kewan_iv_delete");
        iv_delete=(ImageView) findViewById(id_iv_delete);
        tv_getcode.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        mTime = new TimeCount(60000, 1000);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == id_tv_getcode) {
            phone = edittext_phone.getText().toString().trim();
            if (phone.isEmpty()) {
                Toast.makeText(ForgetPwdActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (XStringPars.isMobileNO(phone)){
                mTime.start();
                GameNetWork.getModifycode(new MyBaseProgressCallbackImpl<YzmBean>(ForgetPwdActivity.this){
                    @Override
                    public void onSuccess(YzmBean resul) {
                        super.onSuccess(resul);
                        if (resul!=null){
                            if (resul.getCode()==100){
                                Toast.makeText(ForgetPwdActivity.this,"发送成功，请注意查收",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ForgetPwdActivity.this,resul.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },phone);
            }else{
                Toast.makeText(ForgetPwdActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            }

        } else if (view.getId() == id_tv_commit) {
            phone = edittext_phone.getText().toString().trim();
            pwd = edittext_password.getText().toString().trim();
            newPwd=edittext_confirm_password.getText().toString().trim();
            code = edittext_code.getText().toString().trim();
            if (XStringPars.isMobileNO(phone)){
                if (!code.equalsIgnoreCase("")){
                    if (XStringPars.isCorrectPwd(pwd)&&XStringPars.isCorrectPwd(newPwd)){
                        if (pwd.equalsIgnoreCase(newPwd)){
                            GameNetWork.modifyPassWord(new MyBaseProgressCallbackImpl<ForgetPwdBean>(ForgetPwdActivity.this){
                                @Override
                                public void onSuccess(ForgetPwdBean resul) {
                                    super.onSuccess(resul);
                                    if (resul!=null){
                                        if (resul.getCode()==100){
                                            Toast.makeText(ForgetPwdActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                                            XActivityUtils.getInstance().popActivity(ForgetPwdActivity.this);
                                        }else{
                                            Toast.makeText(ForgetPwdActivity.this,resul.getMsg(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            },phone,code,pwd);
                        }else{
                            Toast.makeText(ForgetPwdActivity.this,"新密码输入不一致",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ForgetPwdActivity.this,"请输入6-12位数字或字母密码",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ForgetPwdActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ForgetPwdActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId()==id_iv_delete){
            XActivityUtils.getInstance().popActivity(ForgetPwdActivity.this);
        }
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_getcode.setClickable(false);
            int id_bg_getcode_gray=ResourceUtil.getDrawableId(ForgetPwdActivity.this,"kewan_bg_getcode_gray");
            tv_getcode.setBackgroundResource(id_bg_getcode_gray);
            tv_getcode.setText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            int id_bg_getcode_gray=ResourceUtil.getDrawableId(ForgetPwdActivity.this,"kewan_bg_getcode");
            tv_getcode.setBackgroundResource(id_bg_getcode_gray);
            tv_getcode.setText("重新获取");
            tv_getcode.setClickable(true);

        }
    }
}
