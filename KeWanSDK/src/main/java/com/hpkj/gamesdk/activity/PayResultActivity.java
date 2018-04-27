package com.hpkj.gamesdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hpkj.gamesdk.base.MyBaseProgressCallbackImpl;
import com.hpkj.gamesdk.bean.BaseResult;
import com.hpkj.gamesdk.network.GameNetWork;
import com.hpkj.gamesdk.utils.ResourceUtil;
import com.hpkj.gamesdk.utils.SharePreferenceUtils;
import com.hpkj.gamesdk.utils.XActivityUtils;


/**
 * @author huanglei
 * @ClassNname：PayActivity.java
 * @Describe 支付结果页面
 * @time 2018/3/23 16:20
 */

public class PayResultActivity extends Activity implements View.OnClickListener {
    TextView kewan_tv_complete;
    TextView kewan_tv_pay;
    int id_kewan_tv_complete;
    int id_kewan_tv_pay;
    int kewan_dialog_pay_result;
    String ext;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XActivityUtils.getInstance().pushActivity(this);
        kewan_dialog_pay_result = ResourceUtil.getLayoutId(PayResultActivity.this, "kewan_dialog_pay_result");
        setContentView(kewan_dialog_pay_result);
        setFinishOnTouchOutside(false);
        id_kewan_tv_complete = ResourceUtil.getId(PayResultActivity.this, "kewan_tv_complete");
        kewan_tv_complete = findViewById(id_kewan_tv_complete);
        id_kewan_tv_pay = ResourceUtil.getId(PayResultActivity.this, "kewan_tv_pay");
        kewan_tv_pay = findViewById(id_kewan_tv_pay);
        kewan_tv_complete.setOnClickListener(this);
        kewan_tv_pay.setOnClickListener(this);
        ext=getIntent().getStringExtra("ext");
        url=getIntent().getStringExtra("url");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == id_kewan_tv_complete) {
            getPayState(ext);
        } else if (view.getId() == id_kewan_tv_pay) {
            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra("url",url);
            intent.putExtra("type",2);
            intent.putExtra("ext", ext);
            startActivity(intent);
            XActivityUtils.getInstance().popActivity(PayResultActivity.this);
        }
    }

    public void getPayState(String ext){
        GameNetWork.getWeiXinPayState(new MyBaseProgressCallbackImpl<BaseResult>(PayResultActivity.this){
            @Override
            public void onSuccess(BaseResult resul) {
                super.onSuccess(resul);
                Log.w("cc","查询结果："+resul.getMsg());
                if (resul.getCode()==100){
                    SharePreferenceUtils.putBoolean(PayResultActivity.this,"isPay",false);
                    Toast.makeText(PayResultActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                    XActivityUtils.getInstance().popActivity(PayResultActivity.this);
                }else{
                    //没有数据，支付失败
                    Toast.makeText(PayResultActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
                }
            }
        },ext);
    }
}
