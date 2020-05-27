package org.promise.currencyconverter;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Currency.class, StackOverflow.class}, version = 2)
public abstract class CurrencyDatabase extends RoomDatabase {

    public abstract CurrencyDao currencyDao();
    private static CurrencyDatabase instance;

    public static synchronized CurrencyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), CurrencyDatabase.class, "currency_database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        new populateDbAsyncTask().execute();
    }
};


    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}

