package com.hpkj.gamesdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hpkj.gamesdk.network.GameNetWork;
import com.hpkj.gamesdk.utils.ResourceUtil;
import com.hpkj.gamesdk.utils.XActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * @author huanglei
 * @ClassNname：PayActivity.java
 * @Describe 支付页面
 * @time 2018/3/23 16:20
 */

public class PayMethodActivity extends Activity implements View.OnClickListener {
    TextView tv_cancel;
    TextView tv_alipay;
    TextView tv_wechat;
    String gid;
    String userid;
    String price;
    String ext;
    String coins;
    String roleid;
    int kewan_dialog_pay;
    int kewan_tv_cancel;
    int kewan_tv_alipay;
    int kewan_tv_wechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XActivityUtils.getInstance().pushActivity(this);
        kewan_dialog_pay = ResourceUtil.getLayoutId(PayMethodActivity.this, "kewan_dialog_pay");
        setContentView(kewan_dialog_pay);
        setFinishOnTouchOutside(false);
        kewan_tv_cancel = ResourceUtil.getId(PayMethodActivity.this, "kewan_tv_cancel");
        tv_cancel = findViewById(kewan_tv_cancel);
        kewan_tv_alipay = ResourceUtil.getId(PayMethodActivity.this, "kewan_tv_alipay");
        tv_alipay = findViewById(kewan_tv_alipay);
        kewan_tv_wechat = ResourceUtil.getId(PayMethodActivity.this, "kewan_tv_wechat");
        tv_wechat = findViewById(kewan_tv_wechat);
        gid = getIntent().getStringExtra("gid");
        userid = getIntent().getStringExtra("userid");
        price = getIntent().getStringExtra("price");
        ext = getIntent().getStringExtra("ext");
        coins = getIntent().getStringExtra("coins");
        roleid = getIntent().getStringExtra("roleid");
        tv_cancel.setOnClickListener(this);
        tv_alipay.setOnClickListener(this);
        tv_wechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == kewan_tv_cancel) {
            XActivityUtils.getInstance().popActivity(PayMethodActivity.this);
        } else if (view.getId() == kewan_tv_alipay) {
            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra("type", 1);
            intent.putExtra("gid", gid);
            intent.putExtra("userid", userid);
            intent.putExtra("ext", ext);
            intent.putExtra("coins", coins);
            intent.putExtra("roleid", roleid);
            intent.putExtra("url", GameNetWork.httpUrl + "Shouyoupay?gid=" + gid + "&userid=" + userid + "&price=" + price + "&ext=" + ext + "&coins=" + coins+"&roleid="+roleid);//六扇门
            startActivity(intent);
            XActivityUtils.getInstance().popActivity(PayMethodActivity.this);
        } else if (view.getId() == kewan_tv_wechat) {
            Intent intent = new Intent(PayMethodActivity.this, PayActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("ext", ext);
            intent.putExtra("url", GameNetWork.httpUrl + "Shouyouweixinpay?gid=" + gid + "&userid=" + userid + "&price=" + price + "&ext=" + ext + "&coins=" + coins+"&roleid="+roleid);
            startActivity(intent);
            XActivityUtils.getInstance().popActivity(PayMethodActivity.this);

        }
    }

}
