package com.huatec.weimenu;

import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private PopupWindow mPopupWindow;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private ImageButton mMenu_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter());

        //toolbar右上角按钮
        mMenu_add = findViewById(R.id.menu_add);
        mMenu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMenu(v);//显示popupWindow
            }
        });

    }

    /***
     * 核心代码：显示在某控件下的popupWindow
     */
    private void onMenu(View menuBtn) {
        //获取自定义菜单布局
        View view = getLayoutInflater().inflate(R.layout.popup_menu, null);
        mPopupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(null);
        mPopupWindow.setOutsideTouchable(true);//不设置的话，无法退出PopupWindow
        mPopupWindow.setFocusable(false);//不设置的话，返回键无效

        //显示mPopupWindow的位置（某某控件下，偏移一些距离）
        mPopupWindow.showAsDropDown(menuBtn,-menuBtn.getWidth()-200,50);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismissPopupWindow();//触摸其他地方时mPopupWindow消失
                return false;
            }
        });

        final TextView textView = view.findViewById(R.id.textView1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PopupWindow的item监听
                //....
                Toast.makeText(MainActivity.this,textView.getText().toString(),Toast.LENGTH_SHORT).show();

                //点击后关闭PopupWindow
                dismissPopupWindow();
            }
        });
    }


    @Override
    public void onBackPressed() {
        //回退键关闭PopupWindow
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
            return;
        }
        super.onBackPressed();
    }


    /**
     * 关闭PopupWindow
     */
    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }
}
