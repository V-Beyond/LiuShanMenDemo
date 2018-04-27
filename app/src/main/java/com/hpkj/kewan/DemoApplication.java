package com.hpkj.kewan;

import android.app.Application;

import com.hpkj.gamesdk.utils.GameUtils;

import org.xutils.x;

/**
 * Created by 77429 on 2018/3/12.
 */

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(DemoApplication.this);
        //是否打开log
        x.Ext.setDebug(true);
        GameUtils.isFirstInstall(DemoApplication.this);
    }
}
