package com.haste.lv.faith.module.videoplayer.viewholder;

import android.util.Log;
import android.view.View;

import com.haste.lv.faith.R;
import com.haste.lv.faith.module.videoplayer.model.VideoVO;
import com.haste.lv.faith.module.videoplayer.player.ListADVideoPlayer;
import com.haste.lv.faith.module.videoplayer.player.StandardCoverVideoPlayer;
import com.haste.lv.faith.uiviews.recyclerview.adapter.HelperRecyclerViewHolder;
import com.haste.lv.faith.uiviews.recyclerview.adapter.IRecycleItemView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.GSYADVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by lv on 18-12-17.
 * 列表样式的每一个条目，带封面图的
 * 1.可以设置片头播放广告的
 */
public class ItemVideoAdvertHolder<V extends VideoVO> implements IRecycleItemView<V> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.list_video_item_advert_layout;
    }

    @Override
    public boolean isForViewType(VideoVO item, int position) {
        return item.styleType == 101;
    }

    @Override
    public void convert(final HelperRecyclerViewHolder holder, V videoVO, final int position) {
        final StandardCoverVideoPlayer gsyVideoPlayer = holder.getView(R.id.video_item_player);
        final ListADVideoPlayer gsyAdVideoPlayer = holder.getView(R.id.video_ad_item_player);
        final VideoVO itemData = videoVO;
        gsyVideoPlayer.setTag("ListADNormalAdapter");
        gsyVideoPlayer.setPlayPosition(position);

//        gsyAdVideoPlayer.setTag("ListADNormalAdapter");
//        gsyAdVideoPlayer.setPlayPosition(position);


        Log.e("tag_mcb","1111 gsyVideoPlayer = " +position +" ---"+ gsyVideoPlayer.hashCode() +" gsyAdVideoPlayer = " +gsyAdVideoPlayer.hashCode());
        boolean isPlaying = gsyVideoPlayer.getCurrentPlayer().isInPlayingState();
        if (!isPlaying) {
            gsyVideoPlayer.setUpLazy(itemData.videoUrl, false, null, null, itemData.title);
        }
        //片头广告
        boolean isADPlaying = gsyAdVideoPlayer.getCurrentPlayer().isInPlayingState();
        if (!isADPlaying) {
            gsyAdVideoPlayer.setUpLazy(itemData.advertUrl, false, null, null, itemData.adTitle);
        }
        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StandardCoverVideoPlayer gsyVideoPlayer = holder.getView(R.id.video_item_player);
                resolveFullBtn(gsyVideoPlayer);
            }
        });
        gsyAdVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListADVideoPlayer gsyAdVideoPlayer = holder.getView(R.id.video_ad_item_player);
                resolveFullBtn(gsyAdVideoPlayer);
            }
        });
        gsyVideoPlayer.setRotateViewAuto(false);
        gsyVideoPlayer.setLockLand(true);
        gsyVideoPlayer.setReleaseWhenLossAudio(false);
        gsyVideoPlayer.setShowFullAnimation(false);
        gsyVideoPlayer.setIsTouchWiget(false);
        gsyVideoPlayer.setNeedLockFull(true);

        gsyAdVideoPlayer.setRotateViewAuto(false);
        gsyAdVideoPlayer.setLockLand(true);
        gsyAdVideoPlayer.setReleaseWhenLossAudio(false);
        gsyAdVideoPlayer.setShowFullAnimation(false);
        gsyAdVideoPlayer.setIsTouchWiget(false);
        //增加封面
        gsyVideoPlayer.loadCoverImage(itemData.videoUrl, R.mipmap.maintab_more_top_bg);
        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        //
        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);


        gsyVideoPlayer.setVideoAllCallBack(new GSYSampleCallBack() {

            @Override
            public void onClickStartIcon(String url, Object... objects) {
                super.onClickStartIcon(url, objects);
//                final ListADVideoPlayer gsyAdVideoPlayer = holder.getView(R.id.video_ad_item_player);
//                if (gsyAdVideoPlayer.getGSYVideoManager().listener() != null) {
//                    gsyAdVideoPlayer.getGSYVideoManager().listener().onAutoCompletion();
//                }
            }

            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                final StandardCoverVideoPlayer gsyVideoPlayer = holder.getView(R.id.video_item_player);
                final ListADVideoPlayer gsyAdVideoPlayer = holder.getView(R.id.video_ad_item_player);
                Log.e("tag_mcb","2222 gsyVideoPlayer = " + gsyVideoPlayer.hashCode() +" gsyAdVideoPlayer = " +gsyAdVideoPlayer.hashCode());
                if (itemData.isNeedAdOnStart) {
                    //gsyVideoPlayer.getCurrentPlayer().onVideoPause();
                    startAdPlay(gsyAdVideoPlayer, gsyVideoPlayer);
                }
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {
                super.onEnterFullscreen(url, objects);
                final StandardCoverVideoPlayer gsyVideoPlayer = holder.getView(R.id.video_item_player);
                gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
            }
        });

        gsyAdVideoPlayer.setVideoAllCallBack(new GSYSampleCallBack() {


            @Override
            public void onAutoComplete(String url, Object... objects) {
                final StandardCoverVideoPlayer gsyVideoPlayer = holder.getView(R.id.video_item_player);
                final ListADVideoPlayer gsyAdVideoPlayer = holder.getView(R.id.video_ad_item_player);
                //广告结束，释放
                gsyAdVideoPlayer.getCurrentPlayer().release();
                gsyAdVideoPlayer.onVideoReset();
                gsyAdVideoPlayer.setVisibility(View.GONE);

                //开始播放原视频，根据是否处于全屏状态判断
                int playPosition = gsyVideoPlayer.getGSYVideoManager().getPlayPosition();
                if (position == playPosition) {
                    gsyVideoPlayer.getCurrentPlayer().startAfterPrepared();
                }

                if (gsyAdVideoPlayer.getCurrentPlayer().isIfCurrentIsFullscreen()) {
                    gsyAdVideoPlayer.removeFullWindowViewOnly();
                    if (!gsyVideoPlayer.getCurrentPlayer().isIfCurrentIsFullscreen()) {
                        resolveFullBtn(gsyVideoPlayer);
                        gsyVideoPlayer.setSaveBeforeFullSystemUiVisibility(gsyAdVideoPlayer.getSaveBeforeFullSystemUiVisibility());
                    }
                }
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                final StandardCoverVideoPlayer gsyVideoPlayer = holder.getView(R.id.video_item_player);
                //退出全屏逻辑
                if (gsyVideoPlayer.isIfCurrentIsFullscreen()) {
                    gsyVideoPlayer.onBackFullscreen();
                }
            }

        });
        gsyAdVideoPlayer.getJumpAdView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListADVideoPlayer gsyAdVideoPlayer = holder.getView(R.id.video_ad_item_player);
                //这里设置广告的跳过逻辑
                if (gsyAdVideoPlayer.getGSYVideoManager().listener() != null) {
                    gsyAdVideoPlayer.getGSYVideoManager().listener().onAutoCompletion();
                }
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
        if (null != standardGSYVideoPlayer)
            standardGSYVideoPlayer.startWindowFullscreen(standardGSYVideoPlayer.getContext(), true, true);
    }

    /**
     * 显示播放广告
     */
    public void startAdPlay(GSYADVideoPlayer gsyadVideoPlayer, StandardGSYVideoPlayer normalPlayer) {
        gsyadVideoPlayer.setVisibility(View.VISIBLE);
        gsyadVideoPlayer.startPlayLogic();
        Log.e("tag_mcb","3333 gsyVideoPlayer = " + normalPlayer.hashCode() +" gsyAdVideoPlayer = " +gsyadVideoPlayer.hashCode());
        if (normalPlayer.getCurrentPlayer().isIfCurrentIsFullscreen()) {
            resolveFullBtn(gsyadVideoPlayer);
            gsyadVideoPlayer.setSaveBeforeFullSystemUiVisibility(normalPlayer.getSaveBeforeFullSystemUiVisibility());
        }
    }
}
