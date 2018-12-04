package com.haste.lv.faith.ui.maintab.mainbehavior;

import android.os.Handler;
import android.os.Message;

import java.util.Stack;

/**
 * Created by lv on 18-11-30.
 */

public class MainBehaviorHelper {
    /**
     * 行为栈
     */
    private static Stack<MainBaseBehavior> mBehaviors;

    private static MainBehaviorHelper mUtils;


    private int Duration = 250;//动画时间

    private static final int CLOSE = 0X100;
    private static final int OPEN = 0X200;
    private int mSatate = OPEN;

    private int mOffsetDelta;  //最大滑动高度

    private onHeadstateListener mListener;//头部打开/关闭状态事件


    /**
     * 头部关闭后，true是可以下滑，false不可以下滑;默认true
     */
    private boolean closeAfterEndabled = true;

    private MainBehaviorHelper() {
    }

    public static MainBehaviorHelper getInstance() {
        synchronized (MainBehaviorHelper.class) {
            if (mUtils == null) {
                initInstance();
            }
            return mUtils;
        }

    }

    public static void initInstance() {
        if (mBehaviors != null) {
            mBehaviors.clear();
            mBehaviors = null;
        }
        mUtils = new MainBehaviorHelper();
        mBehaviors = new Stack<>();
    }

    //添加behavior
    public void put(MainBaseBehavior behavior) {
        mBehaviors.add(behavior);
    }

    //删除behavior
    public void delete(MainBaseBehavior behavior) {
        if (mBehaviors.contains(behavior)) {
            mBehaviors.remove(behavior);
        }
    }

    /**
     * behavior是否监听滑动改变
     */
    private volatile boolean canAsNeeded = true;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            canAsNeeded = true;
            if (msg.what == 100) {
                setOpenState();
            } else if (msg.what == 200) {
                setCloseState();
            }
        }
    };

    /**
     * 打开头部
     */
    public void openHeadPager() {
        canAsNeeded = false;
        for (MainBaseBehavior u : mBehaviors) {
            u.openHeadPager(Duration);
        }
        mHandler.sendEmptyMessageDelayed(100, (long) (Duration * 1.2f));

    }

    /**
     * 关闭头部
     */
    public void closeHeadPager() {
        canAsNeeded = false;
        for (MainBaseBehavior u : mBehaviors) {
            u.closeHeadPager(Duration);
        }
        mHandler.sendEmptyMessageDelayed(200, (long) (Duration * 1.2f));
    }

    public boolean isCanAsNeeded() {
        return canAsNeeded;
    }

    public void setCanAsNeeded(boolean canAsNeeded) {
        this.canAsNeeded = canAsNeeded;
    }


    public boolean isOpen() {
        return mSatate == OPEN;
    }

    public void setOpenState() {
        mSatate = OPEN;
        if (mListener != null) {
            mListener.onHeadIsOpen(true);
        }
    }

    public void setCloseState() {
        mSatate = CLOSE;
        if (mListener != null) {
            mListener.onHeadIsOpen(false);
        }
    }


    public boolean isCloseAfterEndabled() {
        if (mSatate == OPEN) {
            return true;
        }
        return closeAfterEndabled;
    }

    /**
     * 头部关闭后，true是可以下滑，false不可以下滑;默认true
     */
    public void setCloseAfterEndabled(boolean closeAfterEndabled) {
        this.closeAfterEndabled = closeAfterEndabled;
    }

    public void setOffsetDelta(int offsetDelta) {
        mOffsetDelta = offsetDelta;
    }

    public int getOffsetDelta() {
        return mOffsetDelta;
    }


    public void setListener(onHeadstateListener listener) {
        mListener = listener;
    }

    public interface onHeadstateListener {

        void onHeadIsOpen(boolean state);
    }
}
