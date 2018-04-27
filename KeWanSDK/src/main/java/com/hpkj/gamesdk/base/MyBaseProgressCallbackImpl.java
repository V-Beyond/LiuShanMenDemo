package com.hpkj.gamesdk.base;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.hpkj.gamesdk.R;
import com.hpkj.gamesdk.activity.ForgetPwdActivity;
import com.hpkj.gamesdk.utils.ResourceUtil;
import com.hpkj.gamesdk.view.CustomDialog;

import org.xutils.common.Callback;


/**
 * @author huanglei
 * @version V1.0
 * @Title: MyBaseProgressCallbackImpl
 * @Package com.hpkj.nrw.base
 * @Description:自定义请求回调类
 * @date 2017/2/15 14:46
 */

public class MyBaseProgressCallbackImpl<T> implements Callback.ProgressCallback<T> {
    public Context context;
    public CustomDialog logDialog;

    Handler mHandler = new Handler();

    public MyBaseProgressCallbackImpl() {
        super();
    }

    public MyBaseProgressCallbackImpl(final Context content) {
        this.context = content;
        if (content != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (logDialog == null) {
                        int kewan_mystyle= ResourceUtil.getStyleId(content,"kewan_mystyle");
                         int    kewan_layout_wait = ResourceUtil.getLayoutId(content, "kewan_layout_wait");
                        logDialog = new CustomDialog(content, kewan_mystyle,kewan_layout_wait);
                        logDialog.setCanceledOnTouchOutside(false);
                }

                }
            });
        }
    }


    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {
        if (logDialog != null && !logDialog.isShowing()) {
            logDialog.show();
        }
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }


    @Override
    public void onSuccess(T resul) {
        if (logDialog != null) {
            logDialog.dismiss();
        }
    }

    public void stopSlert() {

        if (logDialog != null) {
            logDialog.dismiss();
        }

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

        if (logDialog != null) {
            logDialog.dismiss();
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}

