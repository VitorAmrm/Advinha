package ufpb.luis.vitor.advinha.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ChallengeDTO implements Serializable, Parcelable {
    private Long id;
    private String word;
    private String imageUrl;
    private String videoUrl;
    private String soundUrl;
    private Long id_context_pai;

    public ChallengeDTO(){}

    public ChallengeDTO(Long id, String word, String imageUrl, String videoUrl, String soundUrl) {
        this.id = id;
        this.word = word;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.soundUrl = soundUrl;
    }

    protected ChallengeDTO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        word = in.readString();
        imageUrl = in.readString();
        videoUrl = in.readString();
        soundUrl = in.readString();
        if (in.readByte() == 0) {
            id_context_pai = null;
        } else {
            id_context_pai = in.readLong();
        }
    }

    public static final Creator<ChallengeDTO> CREATOR = new Creator<ChallengeDTO>() {
        @Override
        public ChallengeDTO createFromParcel(Parcel in) {
            return new ChallengeDTO(in);
        }

        @Override
        public ChallengeDTO[] newArray(int size) {
            return new ChallengeDTO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    public Long getId_context_pai() {return id_context_pai;}

    public void setId_context_pai(Long id_context_pai) {this.id_context_pai = id_context_pai;}

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
        if (id_context_pai == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id_context_pai);
        }
    }
}
