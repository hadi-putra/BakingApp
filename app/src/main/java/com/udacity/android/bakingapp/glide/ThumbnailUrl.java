package com.udacity.android.bakingapp.glide;

/**
 * Created by hadi on 17/09/17.
 */

public class ThumbnailUrl {
    private String videoUrl;

    public ThumbnailUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ThumbnailUrl &&
                ((ThumbnailUrl) obj).getVideoUrl().equals(videoUrl);
    }

    @Override
    public int hashCode() {
        return videoUrl.hashCode();
    }
}
