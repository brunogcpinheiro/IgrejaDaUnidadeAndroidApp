package com.brunogcpinheiro.igrejadaunidade;

/**
 * Created by Bruno on 11/11/2016.
 */

public class Video {

    public String video, video_desc, video_date;

    public Video() {
    }

    public Video(String video, String video_desc, String video_date) {
        this.video = video;
        this.video_desc = video_desc;
        this.video_date = video_date;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideo_desc() {
        return video_desc;
    }

    public void setVideo_desc(String video_desc) {
        this.video_desc = video_desc;
    }

    public String getVideo_date() {
        return video_date;
    }

    public void setVideo_date(String video_date) {
        this.video_date = video_date;
    }
}
