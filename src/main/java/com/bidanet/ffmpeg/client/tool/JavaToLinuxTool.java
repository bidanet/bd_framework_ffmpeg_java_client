package com.bidanet.ffmpeg.client.tool;


import com.bidanet.ffmpeg.client.Video;
import com.bidanet.ffmpeg.client.interfaces.CompleteListener;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/15.
 */
public class JavaToLinuxTool extends Thread {

    private String shell;
    private CompleteListener listener;
    private String fileSavePath;
    private String screenSize;
    private String ffmpegUri;

    public JavaToLinuxTool(String ffmpegUri,
                           String originFileUri,
                           String fileSavePath,
                           String screenSize,
                           String videoCode,
                           String audioCode,
                           CompleteListener listener) {
        this.shell = ffmpegUri + " -i " + originFileUri + " -vcodec " + videoCode + " -acodec " + audioCode + " -s " + screenSize + " " + fileSavePath;
        this.ffmpegUri = ffmpegUri;
        this.fileSavePath = fileSavePath;
        this.screenSize = screenSize;
        this.listener = listener;
    }

    @Override
    public void run(){
        try {
            Process process = null;
            List<String> processList = new ArrayList<String>();
            try {
                process = Runtime.getRuntime().exec(shell);
                /*为"输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞*/
                Thread t = new Thread(new InputStreamRunnable(process.getErrorStream(), "infoStream"));
                t.start();

                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = input.readLine()) != null) {
                    processList.add(line);
//                    System.out.println(line);
                }
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String line : processList) {
                System.out.println(line);
            }

        } catch (Throwable e) {
            System.out.print(e);
        }
    }


    /**
     * 读取InputStream的线程
     */
    class InputStreamRunnable implements Runnable {
        BufferedReader bReader = null;
        String type = null;

        public InputStreamRunnable(InputStream is, String _type) {
            try {
                bReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(is), "UTF-8"));
                type = _type;
            } catch (Exception ex) {
            }
        }

        public void run() {
            String line;
            int lineNum = 0;
            Long videoSize = 0L;
            try {
                while ((line = bReader.readLine()) != null) {
                    lineNum++;
                    System.out.println(line);
                    if (line.indexOf("Lsize") != -1){
                        videoSize = GetVideoInfoTool.getvideoSize(line.toString());
                    }
                    //Thread.sleep(200);
                }
                bReader.close();

                Video video = GetVideoInfoTool.getVideoInfo(fileSavePath , ffmpegUri);
                video.setVideoSize(videoSize);

                //成功的回调
                if (listener != null) {
                    listener.onComplete(fileSavePath , screenSize , video);
                }

            } catch (Exception ex) {
                System.out.println(ex);
                if (listener != null) {
                    listener.onError(ex);
                }
            }


        }
    }

}