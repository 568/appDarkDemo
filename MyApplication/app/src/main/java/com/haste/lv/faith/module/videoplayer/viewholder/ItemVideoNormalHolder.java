package com.haste.lv.faith.module.videoplayer.viewholder;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haste.lv.faith.R;
import com.haste.lv.faith.module.videoplayer.model.VideoVO;
import com.haste.lv.faith.module.videoplayer.player.StandardCoverVideoPlayer;
import com.haste.lv.faith.ui.maintab.bean.VideoItem;
import com.haste.lv.faith.uiviews.recyclerview.adapter.HelperRecyclerViewHolder;
import com.haste.lv.faith.uiviews.recyclerview.adapter.IRecycleItemView;
import com.haste.lv.faith.utils.DisplayUtil;
import com.haste.lv.faith.utils.ToastUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by lv on 18-12-17.
 * 列表样式的每一个条目，带封面图的
 */
public class ItemVideoNormalHolder<V extends VideoVO> implements IRecycleItemView<V> {
    StandardCoverVideoPlayer gsyVideoPlayer;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.list_video_item_normal_layout;
    }

    @Override
    public boolean isForViewType(VideoVO item, int position) {
        return item.styleType == 100;
    }

    @Override
    public void convert(HelperRecyclerViewHolder holder, V videoVO, int position) {
        gsyVideoPlayer = holder.getView(R.id.video_item_player);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        final VideoVO itemData = videoVO;
        Log.e("tag_mbb","gsyVideoPlayer = "+gsyVideoPlayer.hashCode() +"  holder = " +holder.hashCode());
        //.setMapHeadData(header)
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setUrl(itemData.videoUrl)
                .setVideoTitle(itemData.title)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag("VideoRecyclerViewList")

                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                            //静音
                            GSYVideoManager.instance().setNeedMute(true);
                        }

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(true);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
                    }
                }).build(gsyVideoPlayer);

        //增加封面
        gsyVideoPlayer.loadCoverImage(itemData.videoUrl, R.mipmap.maintab_more_top_bg);
        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
    }

    @Override
    public void onCreateView(HelperRecyclerViewHolder holder) {

    }

    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        if (null != gsyVideoPlayer)
            standardGSYVideoPlayer.startWindowFullscreen(gsyVideoPlayer.getContext(), true, true);
    }

}
