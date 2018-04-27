package com.hpkj.kewan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.util.H5PayResultModel;
import com.hpkj.gamesdk.bean.KeWanRoleBaseData;
import com.hpkj.gamesdk.bean.LoginResult;
import com.hpkj.gamesdk.interf.PayUrlInterf;
import com.hpkj.gamesdk.interf.RoleInterf;
import com.hpkj.gamesdk.interf.UserEventInterf;
import com.hpkj.gamesdk.utils.GameUtils;
import com.hpkj.gamesdk.utils.SharePreferenceUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    @ViewInject(R.id.tv_userName)
    TextView tv_userName;
    @ViewInject(R.id.tv_password)
    TextView tv_password;
    @ViewInject(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
    @Event(value = {R.id.tv_kewan, R.id.tv_pay,R.id.tv_role_data,R.id.tv_changge_account}, type = View.OnClickListener.class)
    private void Click(View v) {
        switch (v.getId()) {
            case R.id.tv_kewan:
                //登录
                GameUtils.login(MainActivity.this, userEventInterf);
                break;
            case R.id.tv_changge_account:
                //切换账号
                GameUtils.changeAccount(MainActivity.this,userEventInterf);
                break;
            case R.id.tv_pay:
                //支付
//                GameUtils.showPay(MainActivity.this,payUrlInterf,"742","5570","0.01","11__13851928427","520","001");//支付宝
                GameUtils.showPay(MainActivity.this,payUrlInterf,"742","5770","0.01","adsasdasasdcasdxasdasfcasdasdasda","520","0.01");//微信
//                GameUtils.showPay(MainActivity.this,payUrlInterf,"741","5773","0.01","eyJzZXJ2ZXJfbmFtZSI6IuWPjOe6v%2BS4gOWNgeS4ieWMuiIsImV4dGVuc2lvbiI6InNlcnZlcmlkPTEzIiwic2VydmVyX2lkIjoiMTMiLCJwcm9kdWN0X2lkIjoiIiwicm9sZV9pZCI6IjIxODEwNTQ0NSIsInByb2R1Y3RfbmFtZSI6IjEwMDDlhYPlrp0iLCJwcm9kdWN0X2Rlc2MiOiLotK3kubAxMDAw5YWD5a6dIiwidXNlcl9pZCI6IjU3NzMiLCJyb2xlX25hbWUiOiLplbLjmsYnlrZAifQ%3D%3D","520","001");//微信
                break;
            case R.id.tv_role_data:
                //上传角色
//                String data="{\"type\":\"appkey\",\"zoneid\":\"11\",\"zonename\":\"appkey\",\"roleid\":\"11\",\"rolename\":\"appkey\",\"partyid\":\"11\",\"partyname\":\"appkey\",\"professionroleid\":\"11\",\"professionrolename\":\"appkey\",\"partyroleid\":\"11\",\"partyrolename\":\"appkey\",\"gender\":\"appkey\",\"balance\":\"appkey\",\"rolelevel\":\"11\",\"power\":\"11\",\"vip\":\"11\",\"friendlist\":\"aaaaa\"}";
                KeWanRoleBaseData keWanRoleBaseData=new KeWanRoleBaseData();
                keWanRoleBaseData.setType("createRole");
                keWanRoleBaseData.setZoneid(1);
                keWanRoleBaseData.setZonename("测试");
                keWanRoleBaseData.setRoleid("001");
                keWanRoleBaseData.setRolename("测试1号");
                keWanRoleBaseData.setPartyid(1);
                keWanRoleBaseData.setPartyname("帮派1号");
                keWanRoleBaseData.setProfessionroleid(1);
                keWanRoleBaseData.setProfessionrolename("职业1号");
                keWanRoleBaseData.setProfessionid(1);
                keWanRoleBaseData.setProfession("职业名称1号");
                keWanRoleBaseData.setGender("男");
                keWanRoleBaseData.setRolelevel(1);
                keWanRoleBaseData.setVip(1111);
                keWanRoleBaseData.setRolename("007");
                keWanRoleBaseData.setRoleid("001");
                keWanRoleBaseData.setPartyrolename("partyrolename");
                keWanRoleBaseData.setPower(1000);
                KeWanRoleBaseData.KeWanBalance keWanBalance=new KeWanRoleBaseData.KeWanBalance();
                keWanBalance.setBalanceid(1);
                keWanBalance.setBalancename("货币名称");
                keWanBalance.setBalancenum(2);
                keWanRoleBaseData.setBalance(keWanBalance);
                KeWanRoleBaseData.KeWanFriendlist keWanFriendlist= new KeWanRoleBaseData.KeWanFriendlist();
                keWanFriendlist.setRoleid(2);
                keWanFriendlist.setNexusid(2);
                keWanFriendlist.setIntimacy(5);
                keWanFriendlist.setNexusname("二娃");
                keWanRoleBaseData.setFriendlist(keWanFriendlist);
                GameUtils.uploadRole(MainActivity.this,"742","5772",keWanRoleBaseData,roleInterf);
                break;
            default:
                break;
        }
    }
    RoleInterf roleInterf=new RoleInterf() {
        @Override
        public void notifyRole(int code, String msg) {
            Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
        }
    };
    String  payResultUrl;
    PayUrlInterf payUrlInterf=new PayUrlInterf() {
        @Override
        public void notifyPayState(H5PayResultModel h5PayResultModel) {
            String resultCode=h5PayResultModel.getResultCode();
            if (resultCode.equalsIgnoreCase("6001")){//取消支付
                handler.sendEmptyMessage(1);
            }else if(resultCode.equalsIgnoreCase("9000")){//支付成功
                payResultUrl=h5PayResultModel.getReturnUrl();
                Log.w("cc","payResultUrl》》》"+payResultUrl);
                handler.sendEmptyMessage(2);

            }
        }
    };
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Toast.makeText(MainActivity.this, "取消支付", Toast.LENGTH_SHORT).show();
            }else if(msg.what==2){
                Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 用户登录回调
     */
    UserEventInterf userEventInterf = new UserEventInterf() {
        @Override
        public void loginState(int type, LoginResult loginResult) {
            switch (type) {
                case 1:
                    tv_userName.setText("用户名：" + loginResult.getData().getUser().getNickname());
                    break;
//                case 0:
//                    Toast.makeText(MainActivity.this, loginResult.getMsg(), Toast.LENGTH_SHORT).show();
//                    break;
            }
        }
    };
}
