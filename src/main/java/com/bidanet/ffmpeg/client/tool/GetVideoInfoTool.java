package com.bidanet.ffmpeg.client.tool;

import com.bidanet.ffmpeg.client.Video;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/17.
 */
public class GetVideoInfoTool {

    /**
     * 获取视频总时间
     *
     * @param video_path  视频路径
     * @param ffmpeg_path ffmpeg路径
     * @return
     */
    public static Video getVideoInfo(String video_path, String ffmpeg_path) {
        Video video = new Video();
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpeg_path);
        commands.add("-i");
        commands.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长  Duration: 00:04:56.38, start: 0.000000, bitrate: 1159 kb/s
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            //                   Video: mpeg4 (Advanced Simple Profile) (XVID / 0x44495658), yuv420p, 720x480 [SAR 32:27 DAR 16:9], 101
            String regexVideo = "Video: (.*?), (.*?), (.*?)[,\\s]";
            //                  Audio: mp3 (U[0][0][0] / 0x0055), 44100 Hz, stereo, s16p, 128 kb/s
            String regexAudio = "Audio: (\\w*), (\\d*) Hz";

            Pattern durPattern = Pattern.compile(regexDuration);
            Matcher durMatch = durPattern.matcher(sb.toString());
            if (durMatch.find()) {
                int time = getTimelen(durMatch.group(1));
                System.out.println(video_path + ",视频时长：" + time + ", 开始时间：" + durMatch.group(2) + ",比特率：" + durMatch.group(3) + "kb/s");
                video.setVideoLength((long) time);
            }

            Pattern videoPattern = Pattern.compile(regexVideo);
            Matcher videoMatch = videoPattern.matcher(sb.toString());
            if (videoMatch.find()) {
                System.out.println(video_path + ",视频格式：" + videoMatch.group(1) + ", 分辨率：" + videoMatch.group(3));
                video.setVideoFormat(videoMatch.group(1));
                video.setResolution(videoMatch.group(3));
            }

            Pattern audioPattern = Pattern.compile(regexAudio);
            Matcher audioMatcher = audioPattern.matcher(sb.toString());
            if (audioMatcher.find()) {
                System.out.println("音频格式：" + audioMatcher.group(1) + "音频码率：" + audioMatcher.group(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return video;
    }

    //格式:"00:00:10.68"
    private static int getTimelen(String timelen) {
        int min = 0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }


    public static Long getvideoSize(String lineInfo) {
        String line[] = lineInfo.split("Lsize=    ");
        System.out.println("前一个 = " + line[0] + "\n后一个 = " + line[1]);
        String size[] = line[1].split("kB time");
        return Long.valueOf(size[0]);
    }


    public static String getVideoImage(String ffmpegUri, String time , String videoPath , String imageSavePath) {
//        ffmpeg -ss 01:23:45 -i jidu.mp4 image.jpg
        String imagePath = "";
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpegUri);
        commands.add("-ss");
        commands.add(time);
        commands.add("-i");
        commands.add(videoPath);
        commands.add(imageSavePath);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commands);
        final Process p;
        try {
            p = builder.start();
            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            String imageMessage = "Output #0, image2, to '(.*?)':";

            Pattern durPattern = Pattern.compile(imageMessage);
            Matcher durMatch = durPattern.matcher(sb.toString());
            if (durMatch.find()) {
                System.out.println(durMatch.group(1));
                imagePath = imageSavePath;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "获取失败";
        }
        return imagePath;

    }


}
