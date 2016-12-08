package com.bidanet.ffmpeg.client;


import com.bidanet.ffmpeg.client.interfaces.CompleteListener;
import com.bidanet.ffmpeg.client.tool.GetVideoInfoTool;
import com.bidanet.ffmpeg.client.tool.JavaToLinuxTool;

/**
 * Created by Administrator on 2016/10/15.
 */
public class FfmpegConvert {

    public void videoTransCode(String ffmpegUri ,
                               String originFileUri ,
                               String fileSavePath ,
                               String screenSize ,
                               String videoCode ,
                               String audioCode ,
                               CompleteListener listener){

        new JavaToLinuxTool(ffmpegUri , originFileUri , fileSavePath , screenSize , videoCode , audioCode , listener).start();
    }

    public String getVideoImage(String ffmpegUri , String time , String videoPath , String imagePath){
        return GetVideoInfoTool.getVideoImage(ffmpegUri , time , videoPath , imagePath);
    }



}
