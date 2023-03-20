package com.example.contact.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Contact implements Parcelable {
    public static final Parcelable.Creator<Contact> CREATOR = new
            Parcelable.Creator<Contact>() {
                @Override
                public Contact createFromParcel(Parcel source) {
                    return new Contact(source);
                }

                @Override
                public Contact[] newArray(int size) {
                    return new Contact[size];
                }
            };
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "number")
    private String number;
    @ColumnInfo(name = "group")
    private String group;
    @ColumnInfo(name = "instagram")
    private String instagram;
    @ColumnInfo(name = "date")
    private String date;

    @Ignore
    public Contact() {
    }

    public Contact(String name, String number, String group, String instagram, String date) {
        this.name = name;
        this.number = number;
        this.group = group;
        this.instagram = instagram;
        this.date = date;
    }

    private Contact(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.number = in.readString();
        this.group = in.readString();
        this.instagram = in.readString();
        this.date = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.number);
        dest.writeString(this.group);
        dest.writeString(this.instagram);
        dest.writeString(this.date);
    }
}
