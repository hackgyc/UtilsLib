package com.gyc.kotlinutils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by hackgyc on 2016/10/31.
 */

public class PopToast {

    public static void showPopup(Context context, View view, String content) {
        LayoutInflater mLayoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup mView = (ViewGroup) mLayoutInflater.inflate(
                R.layout.soft_calendar_popup, null, true);
        final PopupWindow popupWindow = new PopupWindow(mView,
                ScreenParam.getScreenWidth(context) / 2, ScreenParam.getScreenHeight(context) / 5, true);
        TextView tv = (TextView) mView.findViewById(R.id.tvContent);
        tv.setText(content);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int gravity = Gravity.CENTER;
        int x = 0, y = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            gravity = Gravity.BOTTOM | Gravity.RIGHT;
            x = ScreenParam.getScreenWidth(context) / 4;
            y = ScreenParam.getScreenHeight(context) / 5 * 2;
        }

        popupWindow.showAtLocation(view,
                gravity, x, y);//在屏幕的中间位置显示
        popupWindow.update();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                popupWindow.dismiss();
            }
        }, 1500);

    }

}
