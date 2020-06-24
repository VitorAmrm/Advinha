package ufpb.luis.vitor.advinha.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Challenge implements Serializable, Parcelable {
    private Long id;
    private String word;
    private ufpb.luis.vitor.advinha.model.Creator creator;
    private String imageUrl;
    private String videoUrl;
    private String soundUrl;


    public Challenge() {
    }

    protected Challenge(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        word = in.readString();
        imageUrl = in.readString();
        videoUrl = in.readString();
        soundUrl = in.readString();
    }

    public static final Creator<Challenge> CREATOR = new Creator<Challenge>() {
        @Override
        public Challenge createFromParcel(Parcel in) {
            return new Challenge(in);
        }

        @Override
        public Challenge[] newArray(int size) {
            return new Challenge[size];
        }
    };

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public ufpb.luis.vitor.advinha.model.Creator getCreator() {
        return creator;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(word);
        dest.writeString(imageUrl);
        dest.writeString(videoUrl);
        dest.writeString(soundUrl);
    }
}
