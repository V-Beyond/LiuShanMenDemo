package com.hpkj.gamesdk.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * @ClassNname：ResourceUtil.java
 * @Describe 通过反射获取资源的工具类
 * @author huanglei
 * @time 2018/3/29 15:57
 */

public class ResourceUtil {

    public static int getLayoutId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "layout",
                paramContext.getPackageName());
    }

    public static int getStringId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "string",
                paramContext.getPackageName());
    }

    public static int getDrawableId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "drawable", paramContext.getPackageName());
    }

    public static int getStyleId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "style", paramContext.getPackageName());
    }

    public static int getId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,"id", paramContext.getPackageName());
    }

    public static int getColorId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "color", paramContext.getPackageName());
    }
    public static int getArrayId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "array", paramContext.getPackageName());
    }

    public static int[] getStyleableIntArray(Context context, String name) {
        try {
            Field[] fields = Class.forName(context.getPackageName() + ".R$styleable").getFields();//.与$ difference,$表示R的子类
            for (Field field : fields) {
                if (field.getName().equals(name)) {
                    int ret[] = (int[]) field.get(null);
                    return ret;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 遍历R类得到styleable数组资源下的子资源，1.先找到R类下的styleable子类，2.遍历styleable类获得字段值
     * @param context
     * @param styleableName
     * @param styleableFieldName
     * @return
             */
    public static int getStyleableFieldId(Context context, String styleableName, String styleableFieldName) {
        String className = context.getPackageName() + ".R";
        String type = "styleable";
        String name = styleableName + "_" + styleableFieldName;
        try {
            Class<?> cla = Class.forName(className);
            for (Class<?> childClass : cla.getClasses()) {
                String simpleName = childClass.getSimpleName();
                if (simpleName.equals(type)) {
                    for (Field field : childClass.getFields()) {
                        String fieldName = field.getName();
                        if (fieldName.equals(name)) {
                            return (int) field.get(null);
                        }
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }
}
