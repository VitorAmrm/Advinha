package ufpb.luis.vitor.advinha.model;



import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class ContextDTO implements Serializable {

    private Long id;
    private String name;
    private String imageUrl;
    private String videoUrl;

    private String soundUrl;

    private LinkedList<Challenge> listaChallenge = new LinkedList<Challenge>();


    public ContextDTO() {}

    public ContextDTO(long id, String name, String imageUrl, String videoUrl, String soundUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.soundUrl = soundUrl;
        this.listaChallenge = new LinkedList<>();
    }

    public ContextDTO(long id, String name, String imageUrl, String videoUrl, String soundUrl, LinkedList<Challenge> listaChallenge) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.soundUrl = soundUrl;
        this.listaChallenge = listaChallenge;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getAudioUrl() {
        return soundUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.soundUrl = soundUrl;
    }

    @Override
    public String toString() {
        return "ContextDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", audioUrl='" + soundUrl + '\'' +
                '}';
    }



    public LinkedList<Challenge> getListaChallenge() {
        return listaChallenge;
    }

    public void setListaChallenge(LinkedList<Challenge> listaChallenge) {
        this.listaChallenge = listaChallenge;
    }

    public void addToList (Challenge c){
        this.listaChallenge.add(c);
    }
}
