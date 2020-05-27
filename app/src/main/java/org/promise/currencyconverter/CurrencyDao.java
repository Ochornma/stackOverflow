package org.promise.currencyconverter;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;



@Dao
public interface CurrencyDao {

    @Query("SELECT COUNT(*) FROM " + Currency.tableName)
    int count();

    @Insert
    void insert(Currency currency);

    @Update
    void update(Currency currency);

    @Query("SELECT * FROM " + Currency.tableName + " ORDER BY id DESC")
    LiveData<List<Currency>> getAllNotes();

    @Query("SELECT * FROM " + Currency.tableName + " ORDER BY id ASC")
    DataSource.Factory<Integer, Currency> getAllPaged();
}
