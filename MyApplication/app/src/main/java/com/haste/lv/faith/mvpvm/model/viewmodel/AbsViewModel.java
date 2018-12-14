package com.haste.lv.faith.mvpvm.model.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.haste.lv.faith.mvpvm.ClassUtil;
import com.haste.lv.faith.mvpvm.model.reposity.AbsRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by lv on 18-12-12.
 */

public abstract class AbsViewModel<T extends AbsRepository> extends AndroidViewModel {
    protected T mRepository;

    public AbsViewModel(@NonNull Application application) {
        super(application);
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            mRepository= ClassUtil.getNewInstance((ParameterizedType) type, 0);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }
}
