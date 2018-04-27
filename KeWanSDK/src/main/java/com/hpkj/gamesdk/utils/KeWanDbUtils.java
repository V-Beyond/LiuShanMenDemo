package com.hpkj.gamesdk.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.io.File;

/**
 * Created by 77429 on 2018/4/9.
 */

public class KeWanDbUtils {
    public static DbManager.DbUpgradeListener upListener;


    public static final String DB_NAME = "kewan.db"; // 保存的数据库文件名

    public static DbManager getDaoImpl(Context context) {// 使用相同的数据库文件（stockdb.db），
        return x.getDb(getDaoConfig(context));
    }

    public static DbManager.DaoConfig getDaoConfig(Context context) {// 使用相同的数据库文件（stockdb.db），
        DbManager.DaoConfig daoConfig = null;
        try {
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(DB_NAME)
                    // 不设置dbDir时, 默认存储在app的私有目录.
                    .setDbDir(new File(context.getFilesDir().getAbsolutePath()))
                    .setDbVersion(1)
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            // 开启WAL, 对写入加速提升巨大
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    });
            if (upListener != null) {
                daoConfig.setDbUpgradeListener(upListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daoConfig;
    }


}
