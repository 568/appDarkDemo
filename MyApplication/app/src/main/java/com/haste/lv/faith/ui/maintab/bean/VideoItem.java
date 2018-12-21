package com.haste.lv.faith.ui.maintab.bean;

import com.haste.lv.faith.domain.BaseData;
import com.haste.lv.faith.module.videoplayer.model.VideoVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-12-13.
 * 视频条目实体
 */
public class VideoItem extends BaseData {
    public List<VideoVO> videoData=new ArrayList<>();
}
