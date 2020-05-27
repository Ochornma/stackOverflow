package org.promise.currencyconverter;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Currency.tableName)
public class Currency implements Parcelable {

    public static final String tableName = "currency";
    public static final String id_column = "id";
    public static final String rate_column = "rate";
    public static final String currency_column = "currency";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = id_column)
    private int id;
    @ColumnInfo(name = rate_column)
    private int rate;
    @ColumnInfo(name = currency_column)
    private String currency;

    @Ignore
    public Currency() {
    }

    public Currency(int rate, String currency) {
        this.rate = rate;
        this.currency = currency;
    }

    protected Currency(Parcel in) {
        id = in.readInt();
        rate = in.readInt();
        currency = in.readString();
    }

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(rate);
        dest.writeString(currency);
    }


    public static Currency fromContentValues(ContentValues values) {
        Currency currency = new Currency();
        if (values.containsKey(id_column)) {
            currency.id = values.getAsInteger(id_column);
        }

        if (values.containsKey(rate_column)) {
            currency.rate = values.getAsInteger(rate_column);
        }

        if (values.containsKey(currency_column)) {
            currency.currency = values.getAsString(currency_column);
        }
        return currency;
    }

}
