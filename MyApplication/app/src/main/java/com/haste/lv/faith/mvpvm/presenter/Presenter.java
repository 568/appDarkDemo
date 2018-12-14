package com.haste.lv.faith.mvpvm.presenter;

/**
 * Created by lv on 18-12-12.
 * 1.Presenter内不出现View引用
 * 2.Presenter的生命周期与Android的控件相比，明显少一些。
 * 3·void onCreate(Bundle savedState) - 当Presenter被创建的时候会被调用。Javadoc
 * 4·void onDestroy() - 离开view的时候会被调用。
 * 5·void onSave(Bundle state) - 当View的onSaveInstanceState被调用时会调用，保持Presenter的状态。
 * 6·void onTakeView(ViewType view) -在Activity或者Fragment调用onResume()，或者在android.view.View#onAttachedToWindow()期间。
 * 7·void onDropView() - Activity或者Fragment调用onPause()，或者在android.view.View#onDetachedFromWindow()期间
 */
public class Presenter<ViewType> {
}
