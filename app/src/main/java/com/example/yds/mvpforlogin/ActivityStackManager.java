package com.example.yds.mvpforlogin;

import android.app.Activity;

import java.util.Stack;

public class ActivityStackManager {
    private static Stack<Activity> activityStack;
    private static volatile ActivityStackManager instance = null;

    private ActivityStackManager(){

    }
    public static ActivityStackManager getActivityStackManager(){
        if (instance==null){
            synchronized (ActivityStackManager.class){
                if (instance==null){
                    instance = new ActivityStackManager();
                }
            }
        }
        return instance;
    }
    /**
     * 关闭activity
     * finish the activity and remove it from stack.
     *
     * @param activity
     */
    public void popActivity(Activity activity){
        if (activityStack==null)return;
        if (activity!=null){
            activity.finish();
            activity.overridePendingTransition(0,0);
            activityStack.remove(activity);
            activity = null;
        }
    }
    /**
     * 获取当前的Activity
     * get the current activity.
     *
     * @return
     */
    public Activity getCurrentActivity(){
        if (activityStack==null||activityStack.isEmpty())return null;
        Activity activity = activityStack.lastElement();
        return activity;
    }
    /**
     * 获取最后一个的Activity
     * get the first activity in the stack.
     *
     * @return
     */
    public Activity getFirstActivity(){
        if (activityStack ==null||activityStack.isEmpty())return null;
        Activity activity = activityStack.firstElement();
        return activity;
    }
    /**
     * 添加activity到Stack
     * add the activity to the stack.
     *
     * @param activity
     */
    public void pushActivity(Activity activity){
        if (activityStack ==null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }
    /**
     * remove所有Activity
     * remove all activity.
     */
    public void popAllActivity(){
        if (activityStack ==null)return;
        while (true){
            if (activityStack.empty()){
                break;
            }
            Activity activity = getCurrentActivity();
            popActivity(activity);
        }
    }
    public void popAllActivityWithOutCurent(){
        Activity activity = getCurrentActivity();
        while (true){
            if (activityStack.size()==1){
                break;
            }
            if (getFirstActivity()==activity){
                break;
            }else{
                popActivity(getFirstActivity());
            }
        }
    }


}
