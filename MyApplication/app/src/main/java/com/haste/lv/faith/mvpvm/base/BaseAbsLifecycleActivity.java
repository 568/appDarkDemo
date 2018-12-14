package com.haste.lv.faith.mvpvm.base;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.haste.lv.faith.mvpvm.view.AbsLifecycleActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by lv on 18-12-13.
 */
public class BaseAbsLifecycleActivity<T extends BaseViewModel> extends AbsLifecycleActivity<T> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mViewModel == null) {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                initViewModel(savedInstanceState);
            }
            //如果没有指定泛型参数，则默认不使用ViewModel
        }
        if (mViewModel != null) {
            //让ViewModel拥有View的生命周期感应
            getLifecycle().addObserver(mViewModel);
            //注入RxLifecycle生命周期
            mViewModel.injectLifecycleProvider(this);
            registorUIChangeLiveDataCallBack();
            //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
            initViewObservable();
            //注册RxBus
            mViewModel.registerRxBus();
        }
    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    private void registorUIChangeLiveDataCallBack() {
        //加载对话框显示
        mViewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showDialog(title);
            }
        });
        //加载对话框消失
        mViewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                dismissDialog();
            }
        });
        //跳入新页面
        mViewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity
        mViewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        mViewModel.getUC().getFinishEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                finish();
            }
        });
        //关闭上一层
        mViewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                onBackPressed();
            }
        });
    }

    public void showDialog(String title) {
    }

    public void dismissDialog() {
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    public void initViewObservable() {

    }
}
