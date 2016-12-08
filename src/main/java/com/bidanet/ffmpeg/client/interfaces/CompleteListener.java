package com.bidanet.ffmpeg.client.interfaces;


import com.bidanet.ffmpeg.client.Video;

/**
 * Created by Administrator on 2016/11/16.
 */
public interface CompleteListener {

    void onComplete(String fileSavePath, String screenSize, Video video);

    void onError(Exception ex);

}
