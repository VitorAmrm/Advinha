package ufpb.luis.vitor.advinha.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Context implements Serializable {

    private Long id;
    private String name;
    private String creator;
    private String imageUrl;
    private String soundUrl;
    private String videoUrl;
    private ArrayList<Challenge> challenges;

    public Context(Long id, String name, String creator, String imageUrl, String soundUrl, String videoUrl, ArrayList<Challenge> challenges) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.imageUrl = imageUrl;
        this.soundUrl = soundUrl;
        this.videoUrl = videoUrl;
        this.challenges = challenges;
    }

    public Context() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }

    @Override
    public String toString() {
        return "Context{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", soundUrl='" + soundUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", challenges=" + challenges +
                '}';
    }
}

