package com.niule.a56.calculator.utils.update.fileload;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 作者： Hokas
 * 时间： 2016/7/15
 * 类别：
 */

public class RxBus {
    private static  volatile RxBus mInstance;
    private final Subject<Object,Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 单例RxBus
     */
    public static RxBus getDefault(){
        RxBus rxBus = mInstance;
        if(mInstance == null){
            synchronized (RxBus.class){
                rxBus = mInstance;
                if(mInstance == null){
                    rxBus = new RxBus();
                    mInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * 发送一个新事件
     */
    public void post(Object o){
        bus.onNext(o);
    }

    /**
     * 返回特定类型的被观察者
     */
    public <T> Observable<T> tObservable(Class<T> eventType){
        return bus.ofType(eventType);
    }

}
