package com.example.yds.mvpforlogin;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity {
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;
    /**
     * 是否允许全屏
     **/
    private boolean isAllowFullScreen = true;
    /**
     * 初始化界面
     **/
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 绑定事件
     */
    protected abstract void setEvent();
    /**
     * context
     **/
    protected Context ctx;
    private ScreenManager screenManager;
    ActivityStackManager asm;
    private ConstraintLayout container;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        screenManager = ScreenManager.getInstance();
        screenManager.setStatusBar(isSetStatusBar,this);
        screenManager.setFullScreen(isAllowFullScreen,this);
        setContentView(R.layout.activity_baselayout);
        findView();
        initView();
        initData();
        setEvent();
        ctx = this;
        asm = ActivityStackManager.getActivityStackManager();
        asm.pushActivity(this);

    }

    protected void findView(){
        container = findViewById(R.id.layout_container);
    };
    public void BaseSetContentView(int layoutID){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutID,null);
        container.addView(view);

    }

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.isAllowFullScreen = allowFullScreen;
    }
    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * 退出应用
     * called while exit app.
     */
    public void exitLogic() {
        asm.popAllActivity();//remove all activity.
        System.exit(0);//system exit.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asm.popActivity(this);
    }
}
