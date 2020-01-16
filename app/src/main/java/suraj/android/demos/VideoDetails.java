package suraj.android.demos;

/**
 * Created by abhinav on 14/1/20.
 */

public class VideoDetails
{
    String videoName, videoDuration, videoURL;
    boolean show_thumbnail = true;

    public VideoDetails(String videoName, String videoDuration, String videoURL)
    {
        this.videoName = videoName;
        this.videoDuration = videoDuration;
        this.videoURL = videoURL;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public boolean isShow_thumbnail() {
        return show_thumbnail;
    }

    public void setShow_thumbnail(boolean show_thumbnail) {
        this.show_thumbnail = show_thumbnail;
    }
}
