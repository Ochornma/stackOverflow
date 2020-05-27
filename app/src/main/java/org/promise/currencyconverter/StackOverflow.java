package org.promise.currencyconverter;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = StackOverflow.tablename)
public class StackOverflow implements Parcelable {
    public static final String tablename = "stackoverflow";
    public static final String id_column = "id";
    public static final String owner_column = "userid";
    public static final String name_column = "displayname";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = StackOverflow.id_column)
    private int id;
    @ColumnInfo(name = StackOverflow.name_column)
    private String name;
    @ColumnInfo(name = StackOverflow.owner_column)
    private long userid;

    @Ignore
    public StackOverflow() {
    }

    protected StackOverflow(Parcel in) {
        id = in.readInt();
        name = in.readString();
        userid = in.readLong();
    }

    public static final Creator<StackOverflow> CREATOR = new Creator<StackOverflow>() {
        @Override
        public StackOverflow createFromParcel(Parcel in) {
            return new StackOverflow(in);
        }

        @Override
        public StackOverflow[] newArray(int size) {
            return new StackOverflow[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getUserid() {
        return userid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StackOverflow(String name, long userid) {
        this.name = name;
        this.userid = userid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeLong(userid);
    }
}
