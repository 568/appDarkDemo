package com.haste.lv.faith.module.videoplayer.model;

import com.haste.lv.faith.domain.BaseData;

/**
 * Created by lv on 18-12-17.
 * 视频相关的VO
 */
public class VideoVO extends BaseData {
    public int styleType = 100;
    public String videoUrl;
    public String advertUrl;
    public String coverUrl;
    public String title;
    public String desc;
    //是否播放广告
    public boolean isNeedAdOnStart=false;
    public String adTitle;
}
