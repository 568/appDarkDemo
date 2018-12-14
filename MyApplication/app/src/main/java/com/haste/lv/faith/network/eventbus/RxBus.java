package com.haste.lv.faith.network.eventbus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by lv on 18-12-11.
 * 事件总线(第一弹) ----利用RxJava机制，RxBus代替EventBus作为事件总线通信，以减少库的依赖。
 * <p>
 * 只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
 * <p>
 * 使用方法：
 * (1)在ViewModel中重写registerRxBus()方法来注册RxBus，重写removeRxBus()方法来移除RxBus
 * eg:
 * //订阅者
 * private Disposable mSubscription;
 * //注册RxBus
 *
 * @Override public void registerRxBus() {
 * super.registerRxBus();
 * mSubscription = RxBus.getDefault().toObservable(String.class)
 * .subscribe(new Consumer<String>() {
 * @Override public void accept(String s) throws Exception {
 * ..............
 * }
 * });
 * //将订阅者加入管理站
 * RxSubscriptions.add(mSubscription);
 * }
 * <p>
 * //移除RxBus
 * @Override public void removeRxBus() {
 * super.removeRxBus();
 * //将订阅者从管理站中移除
 * RxSubscriptions.remove(mSubscription);
 * }
 * (2)在需要执行回调的地方发送 RxBus.getDefault().post(object);
 */
public class RxBus {
    private static volatile RxBus mDefaultInstance;
    private final Subject<Object> mBus;

    private final Map<Class<?>, Object> mStickyEventMap;

    public RxBus() {
        mBus = PublishSubject.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * 发送事件
     */
    public void post(Object event) {
        mBus.onNext(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public void reset() {
        mDefaultInstance = null;
    }

    /**
     * Stciky 相关
     */

    /**
     * 发送一个新Sticky事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = mBus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);

            if (event != null) {
                return Observable.merge(observable, Observable.create(new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                        emitter.onNext(eventType.cast(event));
                    }
                }));
            } else {
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }
}
