/**
 * 项目名称：工具库 <br/>
 * 文件名称: CkActivity.java <br/>
 * <p/>
 * Created by 谌珂 on 2015/12/29.  <br/>
 */
package com.uuzz.android.ui.activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzz.android.R;
import com.uuzz.android.util.PermissionUtil;
import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.uuzz.android.util.log.Logger;

/**
 * 项目名称：工具库 <br/>
 * 类  名: CkActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2015/12/29 <br/>
 * @author 谌珂 <br/>
 */
public abstract class CkActivity extends Activity {

    /** 日志对象 */
    protected final Logger logger = new Logger(getClass().getName());

    public Logger getLogger() {
        return logger;
    }

    /** 为了保证扩展性，抽离出整个activity中添加内容的根布局 */
    protected RelativeLayout mRootLayout;

    /** 整个头部的根布局 */
    public RelativeLayout mHeader;
    /** 头部左侧布局 */
    public LinearLayout mHeadLeftContainer;
    /** 头部中间文字 */
    public TextView mTitleText;
    /** 头部右侧布局 */
    public LinearLayout mHeadRightContainer;

    /** 内容的根布局 */
    protected View mContentView;

    /** 阴影区域 */
    protected View mShadeView;

    /** Fragment任务栈 */
    protected FragmentTransaction mFragmentTransaction;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //通知栏背景色透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initRootView();

        onDataRestored(savedInstanceState);

        onViewInjected();



        logger.d("activity created");
    }

    /**
     * 描 述：当数据被恢复时被调用<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/1 <br/>
     * @param savedInstanceState bundle对象
     */
    protected void onDataRestored(Bundle savedInstanceState) {
        //当activity不正常关闭时恢复数据
        InjectUtils.restoreInstance(this, savedInstanceState);
    }

    /**
     * 描 述：注入视图，绑定View，绑定View事件<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/1 <br/>
     */
    protected void onViewInjected() {
        InjectUtils.inject(this);
    }

    public RelativeLayout getmHeader() {
        return mHeader;
    }

    public LinearLayout getmHeadLeftContainer() {
        return mHeadLeftContainer;
    }

    public LinearLayout getmHeadRightContainer() {
        return mHeadRightContainer;
    }

    public TextView getmTitleText() {
        return mTitleText;
    }

    /**
     * 描 述：初始化根布局和头布局<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/6 注释 <br/>
     */
    protected void initRootView() {
        setContentView(R.layout.root_layout);
        mRootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        initHeadViews();
        mContentView = (ViewGroup) findViewById(R.id.content_view);
        mShadeView = findViewById(R.id.v_shader);
    }

    /**
     * 描 述：初始化Fragment事物对象<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/12 注释 <br/>
     */
    protected void initFragmentTransaction() {
        mFragmentTransaction = getFragmentManager().beginTransaction();
    }

    /**
     * 描 述：设置head内的具体布局<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/5 注释 <br/>
     */
    protected void initHeadViews() {
        mHeader = (RelativeLayout) findViewById(R.id.header);
        mHeadLeftContainer = (LinearLayout) findViewById(R.id.ll_head_left);
        mTitleText = (TextView) findViewById(R.id.tv_head_middle);
        mHeadRightContainer = (LinearLayout) findViewById(R.id.ll_head_right);

        mTitleText.setText(getHeadTitle());
        configHeadLeftView();
        configHeadRightView();

        if(!isShowHeader()){
            mHeader.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleText.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        mTitleText.setText(titleId);
    }

    /**
     * 描 述：是否显示头布局<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/11 注释 <br/>
     */
    protected boolean isShowHeader() {
        return true;
    }

    /**
     * 描 述：设置头部左边标题<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/5 注释 <br/>
     */
    protected abstract void configHeadLeftView();

    /**
     * 描 述：获取头部标题文字<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/5 注释 <br/>
     * @return 头部标题
     */
    protected abstract String getHeadTitle();

    /**
     * 描 述：设置头部左边标题<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/5 注释 <br/>
     */
    protected abstract void configHeadRightView();

    @Override
    protected void onRestart() {
        super.onRestart();
        logger.d("activity restarted");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.d("activity started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.d("activity resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.d("activity paused");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        InjectUtils.saveInstances(this, outState);
        logger.d("activity saved instances");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.d("activity stoped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logger.d("activity destroyed");
    }

    public RelativeLayout getmRootLayout() {
        return mRootLayout;
    }

    public void setmRootLayout(RelativeLayout mRootLayout) {
        this.mRootLayout = mRootLayout;
    }

    public View getmContentView() {
        return mContentView;
    }

    public void setmContentView(View mContentView) {
        this.mContentView = mContentView;
    }

    /**
     * 描 述：显示阴影层<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/28 注释 <br/>
     */
    public void showShader() {
        mShadeView.setVisibility(View.VISIBLE);
    }

    /**
     * 描 述：隐藏阴影层<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/28 注释 <br/>
     */
    public void hideShader() {
        mShadeView.setVisibility(View.GONE);
    }

    /**
     * 描 述：用于在activity中申请权限<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/7/12 注释 <br/>
     * @param permissions 申请的权限数组
     * @param task 申请权限成功后需要执行的任务
     */
    public void checkPromissions(@NonNull String[]permissions, Runnable task) {
        PermissionUtil.checkPermission(this, permissions, task, null);
    }

    /**
     * 描 述：用于在activity中申请权限<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/7/12 注释 <br/>
     * @param permissions 申请的权限数组
     * @param accessTask 申请权限成功后需要执行的任务
     * @param deniedTask 申请权限被拒绝后需要执行的任务
     */
    public void checkPromissions(@NonNull String[]permissions, Runnable accessTask, Runnable deniedTask) {
        PermissionUtil.checkPermission(this, permissions, accessTask, deniedTask);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.runTask(this, requestCode, permissions, grantResults);
    }
}
