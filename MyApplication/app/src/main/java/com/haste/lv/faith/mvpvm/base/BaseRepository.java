package com.haste.lv.faith.mvpvm.base;

import com.haste.lv.faith.mvpvm.model.reposity.AbsRepository;
import com.haste.lv.faith.network.eventbus.LiveBus;

/**
 * Created by lv on 18-12-12.
 * 数据仓储基类
 * 1.如果页面需要设置状态页面(加载中，加载失败，加载成功)的时候可以继承此类，一般在首次进入页面时候使用，
 * 2.如果页面的空白页面，错误页面，加载页面与默认的不一致可以使用此种方案
 * 3.如果是列表类页面，可以不使用这个方案，由于列表的处理已经添加了状态页面
 */
public class BaseRepository extends AbsRepository {

    public BaseRepository() {
    }

    protected void showPageState(Object eventKey, String state) {
        sendData(eventKey, state);
    }

    protected void showPageState(Object eventKey, String tag, String state) {
        sendData(eventKey, tag, state);
    }

    protected void sendData(Object eventKey, Object t) {
        sendData(eventKey, null, t);
    }

    protected void sendData(Object eventKey, String tag, Object t) {
        LiveBus.getDefault().postEvent(eventKey, tag, t);
    }
}
