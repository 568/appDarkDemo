package com.haste.lv.faith.mvpvm.model.reposity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by lv on 18-12-12.
 * 数据仓储
 */
public abstract class AbsRepository {
    private CompositeDisposable mCompositeDisposable;
    public AbsRepository() {
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
}
