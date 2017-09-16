package com.udacity.android.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by msk-1196 on 7/29/17.
 */

public class StepModel implements Parcelable {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public StepModel(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public StepModel(){}

    public static final Creator<StepModel> CREATOR = new Creator<StepModel>() {
        @Override
        public StepModel createFromParcel(Parcel in) {
            return new StepModel(in);
        }

        @Override
        public StepModel[] newArray(int size) {
            return new StepModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    public String getValidVideoUrl() {
        String validVideoUrl = null;
        if (videoURL != null && !videoURL.isEmpty())
            validVideoUrl = videoURL;
        else if (thumbnailURL != null && hasMp4Extension())
            validVideoUrl = thumbnailURL;
        return validVideoUrl;
    }

    public boolean hasMp4Extension() {
        return thumbnailURL.substring(thumbnailURL.lastIndexOf(".")+1).equalsIgnoreCase("mp4");
    }
}
