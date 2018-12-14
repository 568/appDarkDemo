package com.haste.lv.faith.network.eventbus.event;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.haste.lv.faith.utils.LogUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lv on 18-12-11.
 * 1.一种生命周期感知的可观察对象，只在订阅之后发送新的更新
 * 2.这避免了事件的一个常见问题:在配置更改(如旋转)时更新
 * 如果观察者处于活动状态，则可以发出。
 * 3.这个LiveData只在存在时调用observable,显式调用setValue()或call()。
 * 4.注意，只有一个观察者会被通知更改。
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private static final String TAG = "SingleLiveEvent";

    private final AtomicBoolean mPending = new AtomicBoolean(false);

    @MainThread
    public void observe(LifecycleOwner owner, final Observer<T> observer) {

        if (hasActiveObservers()) {
            LogUtils.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                if (mPending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        mPending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}
