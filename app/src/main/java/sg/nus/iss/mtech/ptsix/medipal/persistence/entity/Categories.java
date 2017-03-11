package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WongCheeVui on 3/6/2017.
 */

/*
ID              interger
Category        string
Code            string
Description     string
Remind          Bool
 */

public class Categories implements Parcelable {

    private int id;
    private String category;
    private String code;
    private String description;
    private int remind;

    public Categories() {
        super();
    }

    private Categories(Parcel in) {
        super();
        this.id = in.readInt();
        this.category = in.readString();
        this.code = in.readString();
        this.description = in.readString();
        this.remind = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getRemind() {
        return remind;
    }

    @Override
    public String toString() {
        return "Categories [id=" + id +
                ", Category=" + category +
                ", Code=" + code +
                ", Description=" + description +
                ", Remind=" + remind +
                "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getCategory());
        dest.writeString(getCode());
        dest.writeString(getDescription());
        dest.writeInt(getRemind());
    }

    public static final Parcelable.Creator<Categories> CREATOR = new Parcelable.Creator<Categories>() {
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };
}