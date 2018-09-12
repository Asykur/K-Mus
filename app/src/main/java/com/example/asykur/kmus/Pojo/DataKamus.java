package com.example.asykur.kmus.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class DataKamus implements Parcelable{

    private int id;
    private String kata;
    private String arti;

    public DataKamus() {
    }

    public DataKamus(String kata, String arti) {
        this.kata = kata;
        this.arti = arti;
    }

    public DataKamus(int id, String kata, String arti) {
        this.id = id;
        this.kata = kata;
        this.arti = arti;
    }

    protected DataKamus(Parcel in) {
        id = in.readInt();
        kata = in.readString();
        arti = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(kata);
        parcel.writeString(arti);
    }

    public static final Creator<DataKamus> CREATOR = new Creator<DataKamus>() {
        @Override
        public DataKamus createFromParcel(Parcel in) {
            return new DataKamus(in);
        }

        @Override
        public DataKamus[] newArray(int size) {
            return new DataKamus[size];
        }
    };

}
