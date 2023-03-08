package com.kiba.framework.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }
    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        //本服务不绑定组件
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        System.out.println("服务创建：onCreate被调用");
        super.onCreate();
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     * @param intent 启动时，启动组件传递过来的Intent， Activity可利用Intent封装所需要的参数并传递给Service，intentUser.putExtra("KEY", "518");
     * @param flags 表示启动请求时是否有额外数据，可选值有 0，START_FLAG_REDELIVERY，START_FLAG_RETRY，0代表没有，它们具体含义如下：
     *              START_FLAG_REDELIVERY 这个值代表了onStartCommand方法的返回值为
     *              START_REDELIVER_INTENT，而且在上一次服务被杀死前会去调用stopSelf方法停止服务。其中START_REDELIVER_INTENT意味着当Service因内存不足而被系统kill后，则会重建服务，并通过传递给服务的最后一个 Intent 调用 onStartCommand()，此时Intent时有值的。
     *              START_FLAG_RETRY 该flag代表当onStartCommand调用后一直没有返回值时，会尝试重新去调用onStartCommand()。
     * @param startId 指明当前服务的唯一ID，与stopSelfResult (int startId)配合使用，stopSelfResult 可以更安全地根据ID停止服务。
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("服务启动：onStartCommand被调用，flags:"+flags+"  startId:"+startId);
        return super.onStartCommand(intent, flags, startId);
    }
    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        System.out.println("服务销毁：onDestroy被调用");
        super.onDestroy();
    }
}