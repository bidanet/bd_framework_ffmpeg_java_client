# bd_framework_ffmpeg_java_client
FFmpeg 的java客户端


# 使用方法：
导入maven包之后 new一个FfmpegConvert
String shell = "ffmpeg -i /home/admin/Desktop/test.avi -vcodec h264 -acodec aac /home/admin/Desktop/testout.m4v";

FfmpegConvert ffmpegConvert = new FfmpegConvert();

ffmpegConvert.shell(shell);
执行这两句就能使用
