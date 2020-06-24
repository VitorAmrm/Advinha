package ufpb.luis.vitor.advinha.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

class Creator implements Serializable, Parcelable {

    private Long id;
    private String Name;
    private String email;

    public Creator(Long id, String name, String email) {
        this.id = id;
        Name = name;
        this.email = email;
    }

    protected Creator(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        Name = in.readString();
        email = in.readString();
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
        dest.writeString(Name);
        dest.writeString(email);
    }
}
