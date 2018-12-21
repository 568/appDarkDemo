package com.haste.lv.faith.module.videoplayer.model;

import com.haste.lv.faith.domain.BaseData;

/**
 * Created by lv on 18-12-20.
 * 视频细节的信息节点
 * 标准：url
 * 高清:url
 * 超清:url
 * 蓝光:url
 */

public class SwitchVideoModel extends BaseData {
    private String url;
    private String name;

    public SwitchVideoModel(String name, String source1) {
        this.name=name;
        this.url=source1;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
