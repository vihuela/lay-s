package com.hadlink.library.application;

import android.app.Application;
import android.text.TextUtils;

import com.hadlink.library.base.BaseAppManager;
import com.hadlink.library.util.SystemTool;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by lyao on 2015/8/31.
 * <p>
 * appLogTag = "chehu";
 * appDebug = BuildConfig.LYAO_DEBUG;
 */
public abstract class CommonApplication extends Application {
    protected static CommonApplication sInstance;
    protected String processName;
    protected boolean defaultProcess;
    /**
     * 需要子类自行配置
     */
    protected boolean appDebug = true;
    protected String appLogTag = "chehu";

    public static CommonApplication getInstance() {
        return sInstance;
    }

    public boolean getAppDebug() {
        return appDebug;
    }

    public String getAppLogTag() {
        return appLogTag;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();

        /**
         * default process
         */
        if (defaultProcess) {
            appDebug = getLog();
            initStorage();
            initLogger();
        }
    }

    protected abstract boolean getLog();

    private void init() {
        sInstance = this;
        processName = SystemTool.getProcessName(this, android.os.Process.myPid());
        defaultProcess = !TextUtils.isEmpty(processName) && processName.equals(this.getPackageName());
    }

    private void initStorage() {
        Hawk
                .init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(appDebug ? LogLevel.FULL : LogLevel.NONE)
                .build();
    }

    private void initLogger() {
        Logger
                .init(appLogTag)
                .methodCount(0)//method invoke level
                .methodOffset(0)
                .hideThreadInfo()
                .logLevel(appDebug ? com.orhanobut.logger.LogLevel.FULL : com.orhanobut.logger.LogLevel.NONE);
    }


    /**
     * open method
     */
    public void exitApp() {
        BaseAppManager.getInstance().clear();
        System.gc();
        /*MobclickAgent.onKillProcess(this);*/
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
