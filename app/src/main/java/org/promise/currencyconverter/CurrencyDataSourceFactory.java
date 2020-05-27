package org.promise.currencyconverter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class CurrencyDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, StackOverflow>> itemLiveDataSource = new MutableLiveData<>();
    private Context context;

    public CurrencyDataSourceFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public DataSource<Integer, StackOverflow> create() {
        CurrencyDataSource currencyDataSource = new CurrencyDataSource(context);
        itemLiveDataSource.postValue(currencyDataSource);
        return currencyDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, StackOverflow>> getStackLiveDataSource() {
        return itemLiveDataSource;
    }
}
