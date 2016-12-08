package com.bidanet.ffmpeg.client;


import com.bidanet.ffmpeg.client.interfaces.CompleteListener;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

//        String videoTransCode = "ffmpeg -i /home/admin/Desktop/dahuaxiyou3.mp4 -vcodec vp8 -acodec vorbis -strict -2 /home/admin/Desktop/dahuaxiyou3out.webm";
        final String ffmpegUri = "ffmpeg";
        final String originFileUri = "/home/admin/Desktop/sljz.mp4";
        String fileSavePath = "/home/admin/Videos/";
        String smallScreenSize = "640*360";
        String bigScreenSize = "1080*720";
        String videoM4vCode = "h264";
        String audioAccCode = "aac";
        final String videoImageSavePath = "/home/admin/Pictures/";
//        String videoTransCode = "ffmpeg -i /home/admin/Desktop/test.avi -vcodec h264 -acodec aac /home/admin/Desktop/testout.m4v";
//        String videoTransCode = "ffmpeg -i /home/admin/Desktop/test.avi -vcodec libtheora -acodec vorbis -strict -2 /home/admin/Desktop/testout.ogv";
        final FfmpegConvert ffmpegConvert = new FfmpegConvert();
        //视频转换
        ffmpegConvert.videoTransCode(ffmpegUri , originFileUri , fileSavePath + System.currentTimeMillis() + "sljz.mp4" , bigScreenSize , videoM4vCode , audioAccCode , new CompleteListener() {
            @Override
            public void onComplete(String fileSavePath, String screenSize, Video video) {
                System.out.println("转换成功： 保存路径 = " + fileSavePath + "分辨率 = " + screenSize);
                System.out.println("视频信息 :\n视频时长 = " + video.getVideoLength() + "\n视频大小 = " + video.getVideoSize()
                                + "\n视频格式 = " + video.getVideoFormat() + "\n视频分辨率 = " + video.getResolution());

                String uri = ffmpegConvert.getVideoImage(ffmpegUri , "00:00:01" , originFileUri , videoImageSavePath + System.currentTimeMillis() + "sljz.jpg");
                if (!uri.equals("")){
                    System.out.println("上传成功");
                }

            }

            @Override
            public void onError(Exception ex) {
                System.out.println(ex.toString());
            }
        });



    }
}
