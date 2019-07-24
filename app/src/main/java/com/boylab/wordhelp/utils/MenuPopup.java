package com.boylab.wordhelp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.boylab.wordhelp.R;

/**
 * Created by pengle on 2017-9-25.
 * email:pengle609@163.com
 * 内部逻辑，do not modify
 */

public class MenuPopup implements View.OnClickListener {

    private PopupWindow popupWindow;
    private Context context;
    private View popView;
    public static final int MENU_01 = 0x01,MENU_02 = 0x02,MENU_03 = 0x03,MENU_04 = 0x04,MENU_05 = 0x05;
    private Button btn_Menu_01, btn_Menu_02, btn_Menu_03, btn_Menu_04, btn_Menu_05;
    private OnMenuClickListener onMenuClickListener;

    public MenuPopup(Context context, OnMenuClickListener onMenuClickListener, String...menuText) {
        this.context = context;
        this.onMenuClickListener = onMenuClickListener;
        popView = LayoutInflater.from(context).inflate(R.layout.layout_menu_pop_window, null);

        btn_Menu_01 = (Button) popView.findViewById(R.id.btn_Menu_01);
        btn_Menu_02 = (Button) popView.findViewById(R.id.btn_Menu_02);
        btn_Menu_03 = (Button) popView.findViewById(R.id.btn_Menu_03);
        btn_Menu_04 = (Button) popView.findViewById(R.id.btn_Menu_04);
        btn_Menu_05 = (Button) popView.findViewById(R.id.btn_Menu_05);

        btn_Menu_01.setOnClickListener(this);
        btn_Menu_02.setOnClickListener(this);
        btn_Menu_03.setOnClickListener(this);
        btn_Menu_04.setOnClickListener(this);
        btn_Menu_05.setOnClickListener(this);

        if (menuText.length >= 5){
            btn_Menu_05.setText(menuText[4]);
        }else {
            btn_Menu_05.setVisibility(View.GONE);
        }

        if (menuText.length >= 4){
            btn_Menu_04.setText(menuText[3]);
        }else {
            btn_Menu_04.setVisibility(View.GONE);
        }

        if (menuText.length >= 3){
            btn_Menu_03.setText(menuText[2]);
        }else {
            btn_Menu_03.setVisibility(View.GONE);
        }

        if (menuText.length >= 2){
            btn_Menu_02.setText(menuText[1]);
        }else {
            btn_Menu_02.setVisibility(View.GONE);
        }

        if (menuText.length >= 1){
            btn_Menu_01.setText(menuText[0]);
        }else {
            btn_Menu_01.setVisibility(View.GONE);
        }

    }

    public void setEnabled(int position, boolean enabled){
        switch (position){
            case MENU_01:
                btn_Menu_01.setEnabled(enabled);
                break;
            case MENU_02:
                btn_Menu_02.setEnabled(enabled);
                break;
            case MENU_03:
                btn_Menu_03.setEnabled(enabled);
                break;
            case MENU_04:
                btn_Menu_04.setEnabled(enabled);
                break;
            case MENU_05:
                btn_Menu_05.setEnabled(enabled);
                break;
        }
    }


    public void showAsDropDown(View view) {
        // 一个自定义的布局，作为显示的内容
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
        popupWindow.showAsDropDown(view,0,30);
        //popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 750, 185);
    }

    @Override
    public void onClick(View v) {
        if (ViewClick.isFastDoubleClick()){
            return;
        }
        if (onMenuClickListener == null){
            popupWindow.dismiss();
            return;
        }

        switch (v.getId()){
            case R.id.btn_Menu_01:
                onMenuClickListener.onPopupClick(v, 1);
                break;
            case R.id.btn_Menu_02:
                onMenuClickListener.onPopupClick(v, 2);
                break;
            case R.id.btn_Menu_03:
                onMenuClickListener.onPopupClick(v, 3);
                break;
            case R.id.btn_Menu_04:
                onMenuClickListener.onPopupClick(v, 4);
                break;
            case R.id.btn_Menu_05:
                onMenuClickListener.onPopupClick(v, 5);
                break;
        }
        popupWindow.dismiss();
    }

    public interface OnMenuClickListener{
        void onPopupClick(View v, int position);
    }

}
