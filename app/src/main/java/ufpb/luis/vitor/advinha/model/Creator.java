package ufpb.luis.vitor.advinha.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Creator implements Serializable {

    private Long id;
    private String Name;
    private String email;

    public Creator(Long id, String name, String email) {
        this.id = id;
        Name = name;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return email;
    }


}
