package com.bidanet.ffmpeg.client;

/**
 * Created by Administrator on 2016/11/17.
 */
public class Video {

    /**
     * 视频原名称
     */
    private String oldName;

    /**
     * 视频名称
     */
    private String name;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 视频时长
     */
    private Long videoLength;

    /**
     * 文件大小
     */
    private Long videoSize;

    /**
     * 视频格式
     */
    private String videoFormat;

    /**
     * 视频标记
     */
    private String videoTag;

    /**
     * 分辨率
     */
    private String resolution;

    /**
     * 图片地址
     */
    private String imageUrl;

    private VideoTransCodeStatus status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(Long videoLength) {
        this.videoLength = videoLength;
    }

    public Long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(Long videoSize) {
        this.videoSize = videoSize;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(String videoFormat) {
        this.videoFormat = videoFormat;
    }

    public String getVideoTag() {
        return videoTag;
    }

    public void setVideoTag(String videoTag) {
        this.videoTag = videoTag;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public VideoTransCodeStatus getStatus() {
        return status;
    }

    public void setStatus(VideoTransCodeStatus status) {
        this.status = status;
    }
}
