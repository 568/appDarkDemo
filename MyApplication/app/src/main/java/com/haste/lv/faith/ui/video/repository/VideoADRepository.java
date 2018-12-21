package com.haste.lv.faith.ui.video.repository;

import com.haste.lv.faith.mvpvm.base.BaseRepository;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by lv on 18-12-21.
 * 视频列表的数据仓库
 * 1.视频列表数据获取(暂时虚拟，位接通http)
 */

public class VideoADRepository extends BaseRepository {
    public Observable<String> getVideoList(){
       return  Observable.just("");
    }
}
