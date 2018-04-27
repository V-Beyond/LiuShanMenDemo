package com.hpkj.gamesdk.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hujm
 * @version V1.0
 * @Title: XStringPars.java
 * @Package com.hpkj.stringUtil
 * @Description: 字符串解析
 * @date 2016年1月4日 下午2:54:47
 */
public class XStringPars {
    /*-正则表达式判断手机号*/
    public static boolean isMobileNO(String mobiles) {// 正确 返回 true
        Pattern p = Pattern
                .compile("^(13[0-9]|15[012356789]|16[0-9]|17[0-9]|18[0-9]|19[0-9]|14[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断密码是否是6-16为数字加字母
     * @param pwd
     * @return
     */
    public static boolean isCorrectPwd(String pwd) {// 正确 返回 true
//        Pattern p = Pattern
//                .compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$");
        Pattern p = Pattern
                .compile("^[a-zA-Z0-9]{6,12}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }


    /**
     * md5加密
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
