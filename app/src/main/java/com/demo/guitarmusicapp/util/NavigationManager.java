package com.demo.guitarmusicapp.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class NavigationManager {
    //f5f6f9
    private static int bottomColor = Color.parseColor("#2c2c2c");
    private static int colorId = Color.parseColor("#2c2c2c");

    /**
     * 设置底部导航栏颜色
     * @param activity
     */
    public static void setBottomNavigationColor(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(bottomColor);
        }
    }


    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     */
    public static void setStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorId);
        }
    }

}
